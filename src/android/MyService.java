// follow the instructions here : https://github.com/Red-Folder/bgs-core/wiki/Using-the-MyService-Sample
// then copy this code into myService.java

package com.red_folder.phonegap.plugin.backgroundservice.sample;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ComponentName;
import android.content.Context;
import android.content.BroadcastReceiver;

import com.inteco.bleesmviewer.MainActivity;

import com.red_folder.phonegap.plugin.backgroundservice.BackgroundService;

public class MyService extends BackgroundService {
	
	private final static String TAG = MyService.class.getSimpleName();
	
	private String mHelloTo = "World";

	private int workCount = 0;

	private Intent intent;

	@Override
	protected JSONObject doWork() {
		this.workCount++;

		JSONObject result = new JSONObject();

			boolean screenOff = this.intent.getBooleanExtra("screen_state", false);
			Log.d(TAG, "ScreenOff is "+ screenOff);
			try {
				result.put("ScreenOff", screenOff);
				result.put("AppStartedByService", false);
			} catch (JSONException e) {
			}

			if (this.mHelloTo == "World") {
				Log.d(TAG, "HEED App is not launched yet.");

				// launch if screen is off
				if (screenOff){
					Log.d(TAG, "Launching HEED Main Activity ...");
					Intent it = new Intent("android.intent.action.VIEW");
					Context context     = getApplicationContext();
					it.setComponent(new ComponentName(context.getPackageName(), MainActivity.class.getName()));
					it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					getApplicationContext().startActivity(it);
					try {
						result.put("AppStartedByService", true);
					} catch (JSONException e) {
					}
					
				} 

			}

		
		

		try {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
			String now = df.format(new Date(System.currentTimeMillis())); 

			String msg = "Hello " + this.mHelloTo + " - its currently " + now;
			result.put("Message", msg);

			Log.d(TAG, msg);
		} catch (JSONException e) {
		}
		
		return result;	
	}

	@Override
	protected JSONObject getConfig() {
		JSONObject result = new JSONObject();
		
		try {
			result.put("HelloTo", this.mHelloTo);
		} catch (JSONException e) {
		}
		
		return result;
	}

	@Override
	protected void setConfig(JSONObject config) {
		try {
			if (config.has("HelloTo"))
				this.mHelloTo = config.getString("HelloTo");
		} catch (JSONException e) {
		}
		
	}     

	@Override
	protected JSONObject initialiseLatestResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onTimerEnabled() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);
		
	}

	@Override
	protected void onTimerDisabled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		this.intent = intent;
	    return super.onStartCommand(intent, flags, startId);
	}


}
