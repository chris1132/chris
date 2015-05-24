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

public class Admin3Infomation extends Activity {

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
	private Admin admin3 = null;
	private Adminimpl adminimpl = null;
	private Handler handler;
	private ProgressDialog dialog;
	Timestamp createtime;
	Timestamp lastLogintime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome3_4_1);
		dialog = ProgressDialog.show(this, "加载中", "正在加载,请稍后..");
		Thread workThread = new Admin3InfoThread();
		workThread.start();
		handler = new Admin3InfoHandler();
		ImageButton button = (ImageButton) findViewById(R.id.imageButton_admin_3_4_1);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Admin3Infomation.this.finish();

			}
		});

		personaladminname = (TextView) findViewById(R.id.personal3adminname);
		personalname = (EditText) findViewById(R.id.personal3name);
		personalphonenumber = (EditText) findViewById(R.id.personal3phonenumber);
		personalemail = (EditText) findViewById(R.id.personal3email);
		personaladdress = (EditText) findViewById(R.id.personal3address);
		personalcanceltime = (TextView) findViewById(R.id.personal3canceltime);
		personallasttime = (TextView) findViewById(R.id.personal3lasttime);
		personalbtn = (Button) findViewById(R.id.personal3btn);

		personalbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(Admin3Infomation.this)
						.setTitle("提示")
						.setMessage("确定保存修改?")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										admin3.setName(personalname.getText()
												.toString().trim());
										admin3.setPhonenum(personalphonenumber
												.getText().toString().trim());
										admin3.setEmail(personalemail.getText()
												.toString().trim());
										admin3.setAddress(personaladdress
												.getText().toString().trim());
										if (null == adminimpl
												.updatewithoutnull(admin3)) {
											Toast.makeText(
													Admin3Infomation.this,
													"更新失败，请检查网络或稍后重试",
													Toast.LENGTH_LONG).show();
										} else {
											finish();
											Admin3Infomation.this.finish();
											Toast.makeText(
													Admin3Infomation.this,
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

	private class Admin3InfoThread extends Thread {

		@Override
		public void run() {
			adminimpl = new Adminimpl();
			admin3 = adminimpl.query(AdminLogin.adminId);

			adminname = admin3.getAdminname().toString().trim();
			name = admin3.getName().toString().trim();
			phonenumber = admin3.getPhonenum().toString().trim();
			email = admin3.getEmail().toString().trim();
			address = admin3.getAddress().toString().trim();
			createtime = admin3.getCreatetime();
			lastLogintime = admin3.getLastlogintime();
			
			handler.sendEmptyMessage(1);
			if (dialog.isShowing()) {
				dialog.dismiss();
			}

		}

	}

	private class Admin3InfoHandler extends Handler {

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

}
