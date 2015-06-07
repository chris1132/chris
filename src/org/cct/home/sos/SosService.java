package org.cct.home.sos;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.org.ifamily.device.Deviceimpl;
import com.org.ifamily.devicedto.UplocationDTO;

public class SosService extends Service {

	TelephonyManager telephonyManager = null;
	LocationManager locationManager = null;
	NetworkLocationListener networkLocationListener = null;
	GpsLocationListener gpsLocationListener = null;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("Service onBind");
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		try {
		
			telephonyManager = (TelephonyManager) SosService.this
					.getSystemService(Context.TELEPHONY_SERVICE);
			locationManager = (LocationManager) SosService.this
					.getSystemService(Context.LOCATION_SERVICE);
			String imei = null;
			imei = telephonyManager.getDeviceId();
			gpsLocationListener = new GpsLocationListener(imei);
			networkLocationListener = new NetworkLocationListener(imei);
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 600000, 0,
					gpsLocationListener);
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER,600000, 0,
					networkLocationListener);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(toString());
		}

		super.onCreate();
		System.out.println("create");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		System.out.println("Service onStartCommond");
		return START_NOT_STICKY;

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

		try {
			locationManager.removeUpdates(gpsLocationListener);
			locationManager.removeUpdates(networkLocationListener);
			System.out.println("Service onDestory");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		super.onDestroy();
	}

	class NetworkLocationListener implements LocationListener {
		String imei = null;

		NetworkLocationListener(String str) {
			Log.d("imei", "run");
			imei = str;
		}

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			System.out.println(location.getLongitude());
			System.out.println(location.getLatitude());
			Deviceimpl deviceimpl = new Deviceimpl();
			UplocationDTO uplocationDTO = new UplocationDTO();
			uplocationDTO.setHostnum(Long.parseLong(imei));
			System.out.println(Long.parseLong(imei));
			uplocationDTO.setLat((float) location.getLatitude());
			uplocationDTO.setLng((float) location.getLongitude());
			uplocationDTO.setAlt((float) location.getAltitude());
			uplocationDTO.setCep(location.getAccuracy());
			uplocationDTO.setUpdatetime(location.getTime());
			if (deviceimpl.upsosalert(uplocationDTO) == true) {
				System.out.println("OK");
				Toast.makeText(SosService.this, "救援信号已发送", Toast.LENGTH_LONG).show();
				onDestroy();
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}

	class GpsLocationListener implements LocationListener {
		String imei = null;

		GpsLocationListener(String str) {
			Log.d("imei", "run");
			imei = str;
		}

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			System.out.println(location.getLongitude());
			System.out.println(location.getLatitude());
			Deviceimpl deviceimpl = new Deviceimpl();
			UplocationDTO uplocationDTO = new UplocationDTO();
			uplocationDTO.setHostnum(Long.parseLong(imei));
			System.out.println(Long.parseLong(imei));
			uplocationDTO.setLat((float) location.getLatitude());
			uplocationDTO.setLng((float) location.getLongitude());
			uplocationDTO.setAlt((float) location.getAltitude());
			uplocationDTO.setCep(location.getAccuracy());
			uplocationDTO.setUpdatetime(location.getTime());
			if (deviceimpl.upsosalert(uplocationDTO) == true)  {
				System.out.println("OK");
				Toast.makeText(SosService.this, "救援信号已发送", Toast.LENGTH_LONG).show();
				onDestroy();
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}

}
