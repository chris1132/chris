package org.cct.home.welcome;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.cct.home.R;
import org.cct.home.admin.AdminLogin;
import org.cct.home.use.UseLogin;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class home extends Activity {
	private boolean is2CallBack = false;
	private Button uselogin = null;
	private Button adminlogin = null;
	private Button workerlogin = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		uselogin = (Button) findViewById(R.id.uselogin);
		adminlogin = (Button) findViewById(R.id.adminlogin);
		workerlogin = (Button) findViewById(R.id.workerlogin);

		uselogin.setOnClickListener(new UseLoginListener());
		adminlogin.setOnClickListener(new AdminLoginListener());
		workerlogin.setOnClickListener(new WorkerLoginListener());

	}

	class UseLoginListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(home.this, UseLogin.class);
			startActivity(intent);
		}
	}

	class AdminLoginListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(home.this, AdminLogin.class);
			startActivity(intent);
		}
	}

	class WorkerLoginListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Toast.makeText(home.this, "Worker Login", Toast.LENGTH_LONG).show();
		}

	}

//	public boolean isNetworkConnected(Context context) {
//		if (context != null) {
//			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
//					.getSystemService(Context.CONNECTIVITY_SERVICE);
//			NetworkInfo mNetworkInfo = mConnectivityManager
//					.getActiveNetworkInfo();
//			if (mNetworkInfo != null) {
//				return mNetworkInfo.isAvailable();
//			}
//		}
//		return false;
//	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!is2CallBack) {
				is2CallBack = true;
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						is2CallBack = false;
					}
				}, 2500);

			} else {
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		}
		return true;
	}

}
