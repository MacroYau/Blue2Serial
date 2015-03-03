package com.macroyau.blue2serial.demo.bluetooth;

import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;
import java.util.Set;

public class BluetoothDeviceList {

    private final Set<BluetoothDevice> devices;
    private final ArrayList<String> nameList, addressList;

    public BluetoothDeviceList(Set<BluetoothDevice> devices) {
        this.devices = devices;
        nameList = new ArrayList<>();
        addressList = new ArrayList<>();
        for (BluetoothDevice d : devices) {
            nameList.add(d.getName());
            addressList.add(d.getAddress());
        }
    }

    public String[] getNameList() {
        return toArray(nameList);
    }

    public String[] getAddressList() {
        return toArray(addressList);
    }

    public BluetoothDevice getDevice(int index) {
        if (index >= 0 && index < devices.size()) {
            String address = addressList.get(index);
            return getDevice(address);
        } else {
            return null;
        }
    }

    public BluetoothDevice getDevice(String address) {
        for (BluetoothDevice d : devices) {
            if (address.equals(d.getAddress())) {
                return d;
            }
        }

        return null;
    }

    private static String[] toArray(final ArrayList<String> list) {
        if (list.size() > 0) {
            return list.toArray(new String[list.size()]);
        } else {
            return new String[0];
        }
    }

}
