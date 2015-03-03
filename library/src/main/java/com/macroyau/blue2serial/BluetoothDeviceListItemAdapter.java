package com.macroyau.blue2serial;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Adapter for {@link com.macroyau.blue2serial.BluetoothDeviceListDialog}.
 *
 * @author Macro Yau
 */
public class BluetoothDeviceListItemAdapter extends BaseAdapter implements View.OnClickListener {

    private final Context mContext;
    private final String[] mNames, mAddresses;
    private final boolean mShowAddress;

    public BluetoothDeviceListItemAdapter(Context context, String[] names, String[] addresses, boolean showAddress) {
        mContext = context;
        mNames = names;
        mAddresses = addresses;
        mShowAddress = showAddress;
    }

    @Override
    public int getCount() {
        return mAddresses.length;
    }

    @Override
    public String getItem(int position) {
        return mAddresses[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.dialog_devicelistitem, null);
        ((TextView) view.findViewById(R.id.device_name)).setText(mNames[position]);
        if (mShowAddress)
            ((TextView) view.findViewById(R.id.device_address)).setText(mAddresses[position]);
        return view;
    }

    @Override
    public void onClick(View v) {

    }

}
