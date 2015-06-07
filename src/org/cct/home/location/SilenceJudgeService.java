package org.cct.home.location;

import java.util.Calendar;
import java.util.Map;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

public class SilenceJudgeService extends Service {

	SensorManager sensorManager = null;
	Sensor accelerometerSensor = null;
	SensorEventListener sensorEventListener = null;
	private float gravity[] = new float[3];
	private float acceleration[] = new float[3];
	private double acc;
	private float a;
	private float b;
	private float c;
	private String sliencestarttime;
	private String slienceendtime;
	private String slienceinterval;
	MyJudgeThread thread = new MyJudgeThread();

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("Service onBind");
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

		System.out.println("OnCreate");
		thread.start();
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		try {
			sensorManager.unregisterListener(sensorEventListener);
			System.out.println("Service onDestory");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		super.onDestroy();
	}

	class MyJudgeThread extends Thread {

		@Override
		public void run() {
			sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
			accelerometerSensor = sensorManager
					.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			
			SharedPreferences sharedPreferences = getSharedPreferences("deviceconf", Context.MODE_PRIVATE);
			Map<String, ?> map = sharedPreferences.getAll();
			
			sliencestarttime = map.get("sliencestarttime").toString();
			slienceendtime = map.get("slienceendtime").toString();
			slienceinterval = map.get("slienceinterval").toString();
			System.out.println(sliencestarttime);
			System.out.println(slienceendtime);
			System.out.println(slienceinterval);
			
			int starttime = Integer.parseInt(sliencestarttime);
			int endtime = Integer.parseInt(slienceendtime);
			final Calendar mCalendar = Calendar.getInstance();
			mCalendar.setTimeInMillis(System.currentTimeMillis());
			int mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
			System.out.println(mHour);
			if (starttime < endtime) {
				
				if (starttime < mHour && mHour < endtime && sensorManager == null) {
					sensorEventListener = new SensorEventListener() {

						@Override
						public void onSensorChanged(SensorEvent event) {
							// TODO Auto-generated method stub

							final float alpha = 0.8f;

							gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
							gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
							gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

							acceleration[0] = event.values[0] - gravity[0];
							acceleration[1] = event.values[1] - gravity[1];
							acceleration[2] = event.values[2] - gravity[2];

							a = acceleration[0] * acceleration[0];
							b = acceleration[1] * acceleration[1];
							c = acceleration[2] * acceleration[2];

							acc = Math.sqrt(a + b + c);
							System.out.println(acc);
							if (acc < 1) {
								
							}
						}

						@Override
						public void onAccuracyChanged(Sensor sensor, int accuracy) {
							// TODO Auto-generated method stub

						}
					};
				} else {
					
				}
			} else {
				endtime += 24;
				if (starttime < mHour && (mHour + 24) < endtime && sensorManager==null) {
					sensorEventListener = new SensorEventListener() {

						@Override
						public void onSensorChanged(SensorEvent event) {
							// TODO Auto-generated method stub

							final float alpha = 0.8f;

							gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
							gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
							gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

							acceleration[0] = event.values[0] - gravity[0];
							acceleration[1] = event.values[1] - gravity[1];
							acceleration[2] = event.values[2] - gravity[2];

							a = acceleration[0] * acceleration[0];
							b = acceleration[1] * acceleration[1];
							c = acceleration[2] * acceleration[2];

							acc = Math.sqrt(a + b + c);
							
							if (acc < 1) {
								
							}
						}

						@Override
						public void onAccuracyChanged(Sensor sensor, int accuracy) {
							// TODO Auto-generated method stub

						}
					};
				} else {
					
				}
			}
		}
		
	}
}
