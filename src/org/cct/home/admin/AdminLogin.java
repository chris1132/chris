package org.cct.home.admin;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.cct.home.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.org.ifamily.entity.Admin;
import com.org.ifamily.implement.Adminimpl;

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

	class LoginListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			name = adminname.getText().toString().trim();
			password = adminpassword.getText().toString().trim();

			Toast.makeText(AdminLogin.this, name + "," + password, Toast.LENGTH_SHORT).show();
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