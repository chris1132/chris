package org.cct.home.admin;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

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

import com.org.ifamily.entity.Admin;
import com.org.ifamily.entity.AdminroleKey;
import com.org.ifamily.implement.Adminimpl;
import com.org.ifamily.implement.Adminroleimpl;

public class AdminLogin extends Activity {
	private EditText adminname;
	private EditText adminpassword;
	private Button login;
	public static int adminId;
	SharedPreferences sp = null;
	private CheckBox auto = null;
	ProgressDialog dialog;
	String name;
	String password;
	Adminimpl adminimpl;
	Admin admin;
	int i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin);

		sp = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		auto = (CheckBox) findViewById(R.id.auto);
		adminname = (EditText) findViewById(R.id.adminname);
		adminpassword = (EditText) findViewById(R.id.adminpassword);

		if (sp.getBoolean("auto", false)) {
			adminname.setText(sp.getString("uname", null));
			adminpassword.setText(sp.getString("upswd", null));
			auto.setChecked(true);
		}

		login = (Button) findViewById(R.id.login2);
		login.setOnClickListener(new LoginListener());
	}

	private class AdminThread extends Thread {

		private AdminLogin activity;

		public AdminThread(AdminLogin act) {
			activity = act;
		}

		@Override
		public void run() {
			adminimpl = new Adminimpl();
			admin = new Admin();
			admin = adminimpl.loginwithreadmin(name, password,
					getLocalIpAddress());

			if (admin == null) {
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
			Intent intent = new Intent();
			switch (msg.what) {
			case 0:
				boolean autoLogin = auto.isChecked();
				if (autoLogin) {
					Editor editor = sp.edit();
					editor.putString("uname", name);
					editor.putString("upswd", password);
					editor.putBoolean("auto", true);
					editor.commit();
				} else {
					Editor editor = sp.edit();
					editor.putString("uname", null);
					editor.putString("upswd", null);
					editor.putBoolean("auto", false);
					editor.commit();
				}
				adminId = admin.getAdminid();
				Adminroleimpl adminroleimpl = new Adminroleimpl();
				List<AdminroleKey> list = adminroleimpl.querybiadminid(adminId);
				if (list == null || list.isEmpty()) {
					Toast.makeText(AdminLogin.this, "没有与该用户对应的管理员角色！",
							Toast.LENGTH_SHORT).show();
				} else {
					AdminroleKey adminroleKey = list.iterator().next();
					switch (adminroleKey.getRoleid()) {
					case 1:
						intent.setClass(AdminLogin.this, Admin1Home.class);
						AdminLogin.this.startActivity(intent);
						AdminLogin.this.finish();
						break;
					case 2:
						intent.setClass(AdminLogin.this, Admin2Home.class);
						AdminLogin.this.startActivity(intent);
						AdminLogin.this.finish();
						break;
					case 3:
						intent.setClass(AdminLogin.this, Admin3Home.class);
						AdminLogin.this.startActivity(intent);
						AdminLogin.this.finish();
						break;
					case 4:
						intent.setClass(AdminLogin.this, Admin4Home.class);
						AdminLogin.this.startActivity(intent);
						AdminLogin.this.finish();
						break;
					case 5:
						intent.setClass(AdminLogin.this, Admin5Home.class);
						AdminLogin.this.startActivity(intent);
						AdminLogin.this.finish();
						break;
					case 6:
						intent.setClass(AdminLogin.this, Admin6Home.class);
						AdminLogin.this.startActivity(intent);
						AdminLogin.this.finish();
						break;

					default:
						break;
					}
				}

				break;

			case 1:
				Toast.makeText(AdminLogin.this, R.string.warning,
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
			name = adminname.getText().toString().trim();
			password = adminpassword.getText().toString().trim();

			dialog = ProgressDialog.show(AdminLogin.this, "登入中", "正在登入,请稍后..",
					true, false);

			new AdminThread(AdminLogin.this).start();
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