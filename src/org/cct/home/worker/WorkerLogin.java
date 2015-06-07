package org.cct.home.worker;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.cct.home.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class WorkerLogin extends Activity {
	private EditText workername;
	private EditText workerpassword;
	private Button login;
	SharedPreferences sp3 = null;
	private CheckBox auto3 = null;
	ProgressDialog dialog;
	String name;
	String password;
	int i = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.worker);

		sp3 = this.getSharedPreferences("workerinfo", Context.MODE_PRIVATE);
		auto3 = (CheckBox) findViewById(R.id.auto3);
		workername = (EditText) findViewById(R.id.workername);
		workerpassword = (EditText) findViewById(R.id.workerpassword);

		if (sp3.getBoolean("auto3", false)) {
			workername.setText(sp3.getString("uname", null));
			workerpassword.setText(sp3.getString("upswd", null));
			auto3.setChecked(true);
		}

		login = (Button) findViewById(R.id.workerlogin);
		login.setOnClickListener(new LoginListener());
	}

	private class WorkerThread extends Thread {

		private WorkerLogin activity;

		public WorkerThread(WorkerLogin act) {
			activity = act;
		}

		@Override
		public void run() {

			activity.handler.sendEmptyMessage(i);

			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}

	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				boolean autoLogin = auto3.isChecked();
				if (autoLogin) {
					Editor editor = sp3.edit();
					editor.putString("uname", name);
					editor.putString("upswd", password);
					editor.putBoolean("auto3", true);
					editor.commit();
				} else {
					Editor editor = sp3.edit();
					editor.putString("uname", null);
					editor.putString("upswd", null);
					editor.putBoolean("auto3", false);
					editor.commit();
				}
				Intent intent = new Intent();
				intent.setClass(WorkerLogin.this, ListTest.class);
				startActivity(intent);
				finish();
				break;

			case 1:
				Toast.makeText(WorkerLogin.this, R.string.warning,
						Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}

	};

	class LoginListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			name = workername.getText().toString().trim();
			password = workerpassword.getText().toString().trim();

			dialog = ProgressDialog.show(WorkerLogin.this, "登入中", "正在登入,请稍后..",
					true, false);

			new WorkerThread(WorkerLogin.this).start();
		}

	}

	public String getLocalIpAddress() {

		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& !inetAddress.isLinkLocalAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
			return null;
		} catch (SocketException ex) {
			Log.e("WifiPreference IpAddress", ex.toString());
			return null;
		}
	}
}
