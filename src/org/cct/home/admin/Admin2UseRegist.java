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

public class Admin2UseRegist extends Activity {

	private EditText editText1;
	private EditText editText2;
	private EditText editText3;
	private EditText editText4;
	private EditText editText5;
	private EditText editText6;
	private EditText editText7;
	private Button btn;
	private ProgressDialog dialog;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome_1_1_all);
		handler = new Admin2UseRegistHandler();

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_adminhome_1_1_all);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Admin2UseRegist.this.finish();
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

		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// String username = editText1.getText().toString().trim();
				// String name = editText2.getText().toString().trim();
				// String idnumber = editText3.getText().toString().trim();
				// String phonenum = editText4.getText().toString().trim();
				// String email = editText5.getText().toString().trim();
				// String password = editText6.getText().toString().trim();
				// String passwordagain = editText7.getText().toString().trim();
				dialog= ProgressDialog.show(Admin2UseRegist.this, "提交中", "正在提交中,请稍后..");
				Thread workThread = new WorkThread();
				workThread.start();

				// Userimpl userimpl = new Userimpl();
				// User user = new User();
				//
				// if ((passwordagain.equals(password)) &&
				// (!username.equals(""))
				// && (!name.equals("")) && (!idnumber.equals(""))
				// && (!phonenum.equals("")) && (!email.equals(""))
				// && (!password.equals(""))) {
				// user.setUsername(username);
				// user.setName(name);
				// user.setIdnumber(idnumber);
				// user.setPhonenum(phonenum);
				// user.setEmail(email);
				// user.setPassword(password);
				// userimpl.register(user);
				// if (null == userimpl.updatewithoutnull(user)) {
				// Toast.makeText(Admin2UseRegist.this, "注册成功",
				// Toast.LENGTH_LONG).show();
				// }
				// } else {
				// Toast.makeText(Admin2UseRegist.this, "信息输入有误,请重新输入",
				// Toast.LENGTH_LONG).show();
				// }
				// }
				// });
			}
		});
	}

	class WorkThread extends Thread {

		@Override
		public void run() {
			String username = editText1.getText().toString().trim();
			String name = editText2.getText().toString().trim();
			String idnumber = editText3.getText().toString().trim();
			String phonenum = editText4.getText().toString().trim();
			String email = editText5.getText().toString().trim();
			String password = editText6.getText().toString().trim();
			String passwordagain = editText7.getText().toString().trim();

			Userimpl userimpl = new Userimpl();
			User user = new User();

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
					
					Message msg = handler.obtainMessage();
					msg.what = 1;
					handler.sendMessage(msg);
				}
			} else {
				Message msg = handler.obtainMessage();
				msg.what = 0;
				handler.sendMessage(msg);
			}

		}
	}

	class Admin2UseRegistHandler extends Handler {
		public void handleMessage(Message msg) {
			if (msg.what==1) {
				Toast.makeText(Admin2UseRegist.this, "注册成功", Toast.LENGTH_LONG)
						.show();
				dialog.dismiss();
				Admin2UseRegist.this.finish();
			} else {
				Toast.makeText(Admin2UseRegist.this, "信息输入有误,请重新输入",
						Toast.LENGTH_LONG).show();
				dialog.dismiss();

			}

		}
	}
}
