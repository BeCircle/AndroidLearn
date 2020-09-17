package cn.codeyourlife.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: ");
        Toast.makeText(context, "收到收到", Toast.LENGTH_SHORT).show();
        abortBroadcast();
    }
}
