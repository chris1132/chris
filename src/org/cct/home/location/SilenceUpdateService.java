package org.cct.home.location;

import java.util.Calendar;
import java.util.Map;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;

public class SilenceUpdateService extends Service {
	
	Map<String, ?> map;
	MyThread t = new MyThread();
	boolean flag = false;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("Service onBind");
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("Create");
		t.start();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("Destroy");
	}

	class MyThread extends Thread {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (!flag) {
				Calendar mCalendar = Calendar.getInstance();
				mCalendar.setTimeInMillis(System.currentTimeMillis());
				int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
				
				if(hour > 14 && hour < 17) {
					SharedPreferences sharedPreferences = getSharedPreferences("deviceconf", Context.MODE_PRIVATE);
					Editor editor = sharedPreferences.edit();
					
					editor.putString("sliencestarttime", "6");
					editor.putString("slienceendtime", "22");
					editor.putString("slienceinterval", "5");
					editor.commit();
					map = sharedPreferences.getAll();
					if(map != null && !map.isEmpty()) {
						System.out.println(map.get("sliencestarttime").toString());
						System.out.println(map.get("slienceendtime").toString());
						System.out.println(map.get("slienceinterval").toString());
					}
					flag = true;
				}
				
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
