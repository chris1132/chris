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

public class Admin5Infomation extends Activity {

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
	private Admin admin5 = null;
	private Adminimpl adminimpl = null;
	Timestamp createtime;
	Timestamp lastLogintime;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome5_4_1);

		ImageButton buttonBack = (ImageButton) findViewById(R.id.imageButton5_4_1);
		buttonBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Admin5Infomation.this.finish();
			}
		});

		personaladminname = (TextView) findViewById(R.id.personal5adminname);
		personalname = (EditText) findViewById(R.id.personal5name);
		personalphonenumber = (EditText) findViewById(R.id.personal5phonenumber);
		personalemail = (EditText) findViewById(R.id.personal5email);
		personaladdress = (EditText) findViewById(R.id.personal5address);
		personalcanceltime = (TextView) findViewById(R.id.personal5canceltime);
		personallasttime = (TextView) findViewById(R.id.personal5lasttime);
		personalbtn = (Button) findViewById(R.id.personal5btn);

		dialog = ProgressDialog.show(this, "加载中", "正在加载,请稍后..", true, false);

		new Admin5InfoThread(this).start();

		personalbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(Admin5Infomation.this)
						.setTitle("提示")
						.setMessage("确定保存修改?")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										admin5.setName(personalname.getText()
												.toString().trim());
										admin5.setPhonenum(personalphonenumber
												.getText().toString().trim());
										admin5.setEmail(personalemail.getText()
												.toString().trim());
										admin5.setAddress(personaladdress
												.getText().toString().trim());
										if (null == adminimpl
												.updatewithoutnull(admin5)) {
											Toast.makeText(
													Admin5Infomation.this,
													"更新失败，请检查网络或稍后重试",
													Toast.LENGTH_LONG).show();
										} else {
											finish();
											Admin5Infomation.this.finish();
											Toast.makeText(
													Admin5Infomation.this,
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

	private class Admin5InfoThread extends Thread {
		private Admin5Infomation activity;

		public Admin5InfoThread(Admin5Infomation act) {
			activity = act;
		}

		@Override
		public void run() {
			adminimpl = new Adminimpl();
			admin5 = adminimpl.query(AdminLogin.adminId);

			activity.handler.sendEmptyMessage(0);
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
				adminname = admin5.getAdminname().toString().trim();
				name = admin5.getName().toString().trim();
				phonenumber = admin5.getPhonenum().toString().trim();
				email = admin5.getEmail().toString().trim();
				address = admin5.getAddress().toString().trim();
				createtime = admin5.getCreatetime();
				lastLogintime = admin5.getLastlogintime();

				if(adminname != null && (!adminname.equals(""))) {
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

	};

}