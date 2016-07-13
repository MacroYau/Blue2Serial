package com.macroyau.blue2serial;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Encapsulated service class for implementing the Bluetooth Serial Port Profile (SPP).
 *
 * @author Macro Yau
 */
public class SPPService {

    private static final String TAG = "SPPService";

    private static final UUID UUID_SPP = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private Handler mHandler;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private int mState;

    public SPPService(Handler handler) {
        mState = BluetoothSerial.STATE_DISCONNECTED;
        mHandler = handler;
    }

    private synchronized void setState(int state) {
        Log.d(TAG, "setState() " + mState + " -> " + state);

        mState = state;
        mHandler.obtainMessage(BluetoothSerial.MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
    }

    public synchronized int getState() {
        return mState;
    }

    public synchronized void start() {
        Log.d(TAG, "start()");

        resetThreads();
        setState(BluetoothSerial.STATE_DISCONNECTED);
    }

    public synchronized void connect(BluetoothDevice device) {
        Log.d(TAG, "connect(" + device + ")");

        if (mState == BluetoothSerial.STATE_CONNECTING) {
            resetConnectThread();
        }

        if (mState == BluetoothSerial.STATE_CONNECTED) {
            resetConnectedThread();
        }

        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
        setState(BluetoothSerial.STATE_CONNECTING);
    }

    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        Log.d(TAG, "Connected to " + device + "!");

        resetThreads();
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();

        Message msg = mHandler.obtainMessage(BluetoothSerial.MESSAGE_DEVICE_INFO);
        Bundle bundle = new Bundle();
        bundle.putString(BluetoothSerial.KEY_DEVICE_NAME, device.getName());
        bundle.putString(BluetoothSerial.KEY_DEVICE_ADDRESS, device.getAddress());
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        setState(BluetoothSerial.STATE_CONNECTED);
    }

    public synchronized void stop() {
        Log.d(TAG, "stop()");

        resetThreads();
        setState(BluetoothSerial.STATE_DISCONNECTED);
    }

    public void write(byte[] data) {
        ConnectedThread t;
        synchronized (this) {
            if (mState == BluetoothSerial.STATE_CONNECTED)
                t = mConnectedThread;
            else
                return;
        }
        t.write(data);
    }

    private synchronized void resetThreads() {
        resetConnectThread();
        resetConnectedThread();
    }

    private synchronized void resetConnectThread() {
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
    }

    private synchronized void resetConnectedThread() {
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
    }

    private void reconnect() {
        SPPService.this.start();
    }

    private class ConnectThread extends Thread {

        private final BluetoothSocket mSocket;
        private final BluetoothDevice mDevice;

        public ConnectThread(BluetoothDevice device) {
            Log.d(TAG, "ConnectThread(" + device + ")");
            mDevice = device;
            BluetoothSocket tempSocket = null;
            try {
                tempSocket = device.createRfcommSocketToServiceRecord(UUID_SPP);
            } catch (IOException e1) {
                Log.e(TAG, "Failed to create a secure socket!");
                try {
                    tempSocket = device.createInsecureRfcommSocketToServiceRecord(UUID_SPP);
                } catch (IOException e2) {
                    Log.e(TAG, "Failed to create an insecure socket!");
                }
            }
            mSocket = tempSocket;
        }

        public void run() {
            try {
                mSocket.connect();
            } catch (IOException e) {
                Log.e(TAG, "Failed to connect to the socket!");
                cancel();
                reconnect(); // Connection failed
                return;
            }

            synchronized (SPPService.this) {
                mConnectThread = null;
            }

            connected(mSocket, mDevice);
        }

        public void cancel() {
            try {
                mSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Unable to close the socket!");
            }
        }

    }

    private class ConnectedThread extends Thread {

        private final BluetoothSocket mSocket;
        private final InputStream mInputStream;
        private final OutputStream mOutputStream;

        public ConnectedThread(BluetoothSocket socket) {
            Log.d(TAG, "ConnectedThread()");

            mSocket = socket;
            InputStream tempInputStream = null;
            OutputStream tempOutputStream = null;

            try {
                tempInputStream = socket.getInputStream();
                tempOutputStream = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "I/O streams cannot be created from the socket!");
            }

            mInputStream = tempInputStream;
            mOutputStream = tempOutputStream;
        }

        public void run() {
            byte[] data = new byte[1024];
            int length;

            while (true) {
                try {
                    length = mInputStream.read(data);
                    byte[] read = new byte[length];
                    System.arraycopy(data, 0, read, 0, length);
                    mHandler.obtainMessage(BluetoothSerial.MESSAGE_READ, length, -1, read).sendToTarget();
                } catch (IOException e) {
                    reconnect(); // Connection lost
                    SPPService.this.start();
                    break;
                }
            }
        }

        public void write(byte[] data) {
            try {
                mOutputStream.write(data);
                mHandler.obtainMessage(BluetoothSerial.MESSAGE_WRITE, -1, -1, data).sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Unable to write the socket!");
            }
        }

        public void cancel() {
            try {
                mSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Unable to close the socket!");
            }
        }

    }

}
