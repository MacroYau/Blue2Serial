package com.macroyau.blue2serial;

/**
 * Listener for Bluetooth events.
 *
 * @author Macro Yau
 */
public interface BluetoothSerialListener {

    /**
     * Bluetooth adapter is not present on this device.
     */
    void onBluetoothNotSupported();

    /**
     * This device's Bluetooth adapter is turned off.
     */
    void onBluetoothDisabled();

    /**
     * Disconnected from a remote Bluetooth device.
     */
    void onBluetoothDeviceDisconnected();

    /**
     * Connecting to a remote Bluetooth device.
     */
    void onConnectingBluetoothDevice();

    /**
     * Connected to a remote Bluetooth device.
     *
     * @param name The name of the remote device.
     * @param address The MAC address of the remote device.
     */
    void onBluetoothDeviceConnected(String name, String address);

    /**
     * Specified message is read from the serial port.
     *
     * @param message The message read.
     */
    void onBluetoothSerialRead(String message);

    /**
     * Specified message is written to the serial port.
     *
     * @param message The message written.
     */
    void onBluetoothSerialWrite(String message);

}
