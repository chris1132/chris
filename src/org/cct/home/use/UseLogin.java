package org.cct.home.use;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.cct.home.R;
import org.cct.home.location.TestActivity;

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

import com.org.ifamily.entity.User;
import com.org.ifamily.implement.Userimpl;

public class UseLogin extends Activity {

	private EditText usename;
	private EditText usepassword;
	String name;
	String password;
	ProgressDialog dialog;
	Userimpl userimpl;
	User user;
	int i;
	private Button login;
	private Button location;
	public static int useId;
	SharedPreferences sp2 = null;
	private CheckBox auto = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.use);
		sp2 = this.getSharedPreferences("userinfo2", Context.MODE_PRIVATE);
		auto = (CheckBox) findViewById(R.id.auto2);

		usename = (EditText) findViewById(R.id.usename);
		usepassword = (EditText) findViewById(R.id.usepassword);
		if (sp2.getBoolean("auto", false)) {
			usename.setText(sp2.getString("uname", null));
			usepassword.setText(sp2.getString("upswd", null));
			auto.setChecked(true);
		}
		login = (Button) findViewById(R.id.login);
		location = (Button) findViewById(R.id.location);

		login.setOnClickListener(new LoginListener());
		location.setOnClickListener(new LocationListener());

	}

	private class LoginThread extends Thread {

		private UseLogin activity;

		public LoginThread(UseLogin act) {
			activity = act;
		}

		@Override
		public void run() {
			userimpl = new Userimpl();
			user = new User();
			user = userimpl
					.loginwithreuser(name, password, getLocalIpAddress());

			if (user == null) {
				i = 1;
			} else {
				i = 0;
			}

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
				boolean autoLogin = auto.isChecked();
				if (autoLogin) {
					Editor editor = sp2.edit();
					editor.putString("uname", name);
					editor.putString("upswd", password);
					editor.putBoolean("auto", true);
					editor.commit();
				} else {
					Editor editor = sp2.edit();
					editor.putString("uname", null);
					editor.putString("upswd", null);
					editor.putBoolean("auto", false);
					editor.commit();
				}
				useId = user.getUserid();
				Intent intent = new Intent();
				intent.setClass(UseLogin.this, UseCareSelevt.class);
				UseLogin.this.startActivity(intent);
				UseLogin.this.finish();
				break;

			case 1:
				Toast.makeText(UseLogin.this, R.string.warning,
						Toast.LENGTH_LONG).show();
				break;

			default:
				break;
			}
		}

	};

	class LocationListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(UseLogin.this, TestActivity.class);
			startActivity(intent);
		}

	}

	class LoginListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			name = usename.getText().toString();
			password = usepassword.getText().toString();

			dialog = ProgressDialog.show(UseLogin.this, "登入中", "正在登入,请稍后..",
					true, false);
			new LoginThread(UseLogin.this).start();

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
