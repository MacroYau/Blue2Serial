package com.macroyau.blue2serial.demo.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.os.Handler;
import android.util.Log;

import com.macroyau.blue2serial.BluetoothSerialListener;

public class BluetoothManager {

    private static final String TAG = "BluetoothManager";

    private boolean isConnected = false;
    private String deviceName = null;

    private int mState;

    private Handler mHandler;

    private final BluetoothSerialListener mListener;
    private final BluetoothAdapter mAdapter;

    public BluetoothManager(Handler handler, BluetoothSerialListener listener) {
        mListener = listener;
        mAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public synchronized void initialize() {
        Log.d(TAG, "initialize()");
        if (mAdapter == null) {
            // Bluetooth is not supported on this device
        } else {
            if (!mAdapter.isEnabled()) {
                // Bluetooth is off
            } else {
                // Setup SPP
            }
        }
    }



}
