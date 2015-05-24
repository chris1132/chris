package org.cct.home.admin;

import org.cct.home.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.org.ifamily.entity.User;
import com.org.ifamily.implement.Userimpl;

public class Admin1UseRegist extends Activity {

	private EditText editText1;
	private EditText editText2;
	private EditText editText3;
	private EditText editText4;
	private EditText editText5;
	private EditText editText6;
	private EditText editText7;
	private Button btn;
	ProgressDialog dialog;
	Userimpl userimpl;
	User user;
	String username;
	String name;
	String idnumber;
	String phonenum;
	String email;
	String password;
	String passwordagain;
	int i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome_1_1_all);

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_adminhome_1_1_all);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 返回上一级
				Admin1UseRegist.this.finish();

			}
		});

		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);
		editText3 = (EditText) findViewById(R.id.editText3);
		editText4 = (EditText) findViewById(R.id.editText4);
		editText5 = (EditText) findViewById(R.id.editText5);
		editText6 = (EditText) findViewById(R.id.editText6);
		editText7 = (EditText) findViewById(R.id.editText7);
		btn = (Button) findViewById(R.id.btn);

		btn.setOnClickListener(new Admin1RegistListener());
	}

	private class Admin1RegistThread extends Thread {
		private Admin1UseRegist activity;

		public Admin1RegistThread(Admin1UseRegist act) {
			activity = act;
		}

		@Override
		public void run() {
			username = editText1.getText().toString().trim();
			name = editText2.getText().toString().trim();
			idnumber = editText3.getText().toString().trim();
			phonenum = editText4.getText().toString().trim();
			email = editText5.getText().toString().trim();
			password = editText6.getText().toString().trim();
			passwordagain = editText7.getText().toString().trim();
			userimpl = new Userimpl();
			user = new User();

			if ((passwordagain.equals(password)) && (!username.equals(""))
					&& (!name.equals("")) && (!idnumber.equals(""))
					&& (!phonenum.equals("")) && (!email.equals(""))
					&& (!password.equals(""))) {
				user.setUsername(username);
				user.setName(name);
				user.setIdnumber(idnumber);
				user.setPhonenum(phonenum);
				user.setEmail(email);
				user.setPassword(password);
				userimpl.register(user);
				if (null == userimpl.updatewithoutnull(user)) {
					i = 0;
				} else {
					i = 2;
				}
			} else {
				i = 1;
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
				if (null == userimpl.updatewithoutnull(user)) {
					Toast.makeText(Admin1UseRegist.this, "注册成功",
							Toast.LENGTH_LONG).show();
					Admin1UseRegist.this.finish();
				}
				break;

			case 1:
				Toast.makeText(Admin1UseRegist.this, "信息输入有误,请重新输入",
						Toast.LENGTH_LONG).show();
				break;

			case 2:
				Toast.makeText(Admin1UseRegist.this, "注册失败", Toast.LENGTH_LONG)
						.show();
				break;

			default:
				break;
			}
		}

	};

	class Admin1RegistListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			dialog = ProgressDialog.show(Admin1UseRegist.this, "注册保存中",
					"正在保存注册,请稍后..", true, false);

			new Admin1RegistThread(Admin1UseRegist.this).start();
		}

	}
}
