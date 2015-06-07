package org.cct.home.location;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

public class ThirdService extends Service {
	SensorManager sensorManager = null;
	Sensor accelerometerSensor = null;
	SensorEventListener sensorEventListener = null;
	private float gravity[] = new float[3];
	private float acceleration[] = new float[3];
	private double acc;
	private float a;
	private float b;
	private float c;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("Service onBind");
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		accelerometerSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
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

				if (acc > 11) {
					Intent intent = new Intent();
					intent.setClass(ThirdService.this, SecondService.class);
					Intent intentTemp = new Intent(
							"org.cct.home.location.SecondService");
					startService(intentTemp);

				}

			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub

			}
		};
		sensorManager.registerListener(sensorEventListener,
				accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
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

}
