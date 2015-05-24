package org.cct.home.admin;

import java.sql.Timestamp;

import org.cct.home.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.org.ifamily.entity.Admin;
import com.org.ifamily.implement.Adminimpl;

public class Admin2Infomation extends Activity {

	private TextView personaladminname;
	private EditText personalname;
	private EditText personalphonenumber;
	private EditText personalemail;
	private EditText personaladdress;
	private TextView personalcanceltime;
	private TextView personallasttime;
	private Button personalbtn;
	private String adminname;
	private String name;
	private String phonenumber;
	private String email;
	private String address;
	private Admin admin2 = null;
	private Adminimpl adminimpl = null;
	private Handler handler;
	private ProgressDialog dialog;
	Timestamp createtime;
	Timestamp lastLogintime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome2_4_1);
		dialog = ProgressDialog.show(this, "加载中", "正在加载,请稍后..");
		Thread workThread = new Admin2InfoThread();
		workThread.start();
		handler = new Admin2InfoHandler();
		
		personaladminname = (TextView) findViewById(R.id.personal2adminname);
		personalname = (EditText) findViewById(R.id.personal2name);
		personalphonenumber = (EditText) findViewById(R.id.personal2phonenumber);
		personalemail = (EditText) findViewById(R.id.personal2email);
		personaladdress = (EditText) findViewById(R.id.personal2address);
		personalcanceltime = (TextView) findViewById(R.id.personal2canceltime);
		personallasttime = (TextView) findViewById(R.id.personal2lasttime);
		personalbtn = (Button) findViewById(R.id.personal2btn);

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_adminhome_2_4_1);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Admin2Infomation.this.finish();

			}
		});

		personalbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(Admin2Infomation.this)
						.setTitle("提示")
						.setMessage("确定保存修改?")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										admin2.setName(personalname.getText()
												.toString().trim());
										admin2.setPhonenum(personalphonenumber
												.getText().toString().trim());
										admin2.setEmail(personalemail.getText()
												.toString().trim());
										admin2.setAddress(personaladdress
												.getText().toString().trim());
										if (null == adminimpl
												.updatewithoutnull(admin2)) {
											Toast.makeText(
													Admin2Infomation.this,
													"更新失败，请检查网络或稍后重试",
													Toast.LENGTH_LONG).show();
										} else {
											finish();
											Admin2Infomation.this.finish();
											Toast.makeText(
													Admin2Infomation.this,
													"个人信息更新成功",
													Toast.LENGTH_LONG).show();
										}
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}
								}).show();
			}
		});

	}

	private class Admin2InfoThread extends Thread {

		@Override
		public void run() {
			
			adminimpl = new Adminimpl();
			admin2 = adminimpl.query(AdminLogin.adminId);
			
			adminname = admin2.getAdminname().toString().trim();
			name = admin2.getName().toString().trim();
			phonenumber = admin2.getPhonenum().toString().trim();
			email = admin2.getEmail().toString().trim();
			address = admin2.getAddress().toString().trim();
			createtime = admin2.getCreatetime();
			lastLogintime = admin2.getLastlogintime();

			handler.sendEmptyMessage(1);
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}

	}

	private class Admin2InfoHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (adminname != null && (!adminname.equals(""))) {
					personaladminname.setText(adminname);
				} else {
					personaladminname.setText("无");
				}
				if (name != null && (!name.equals(""))) {
					personalname.setText(name);
				} else {
					personalname.setText("无");
				}
				if (phonenumber != null && (!phonenumber.equals(""))) {
					personalphonenumber.setText(phonenumber);
				} else {
					personalphonenumber.setText("无");
				}
				if (email != null && (!email.equals(""))) {
					personalemail.setText(email);
				} else {
					personalemail.setText("无");
				}
				if (address != null && (!address.equals(""))) {
					personaladdress.setText(address);
				} else {
					personaladdress.setText("无");
				}
				if (createtime != null) {
					personalcanceltime.setText(createtime + "");
				} else {
					personalcanceltime.setText("无");
				}
				if (lastLogintime != null) {
					personallasttime.setText(lastLogintime + "");
				} else {
					personallasttime.setText("无");
				}
				break;

			default:
				break;
			}
		}

	}

}// activity end