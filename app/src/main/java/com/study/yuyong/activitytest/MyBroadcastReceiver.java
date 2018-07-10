package com.study.yuyong.activitytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        Toast.makeText(context,intent.getStringExtra("broadcast"),Toast.LENGTH_SHORT).show();
        //优先级高可以截断广播
        abortBroadcast();
       // Log.d("=============", intent.getStringExtra("broadcast"));
    }
}
