package com.red_folder.phonegap.plugin.backgroundservice.sample;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.util.Log;


public class ScreenReceiver extends BroadcastReceiver {
 
    private boolean screenOff;

    private final static String TAG = MyService.class.getSimpleName();
 
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            screenOff = true;
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            screenOff = false;
        }
        Log.d(TAG, "ScreenOff update. "+ screenOff);
        Intent i = new Intent(context, MyService.class);
        i.putExtra("screen_state", screenOff);
        context.startService(i);
    }
 
}
