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

public class Admin1Infomation extends Activity {

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
	private Admin admin1 = null;
	private Adminimpl adminimpl = null;
	ProgressDialog dialog;
	Timestamp createtime;
	Timestamp lastLogintime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome1_4_1);

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_adminhome1_4_1);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 返回上一级
				Admin1Infomation.this.finish();
			}
		});

		personaladminname = (TextView) findViewById(R.id.personaladminname);
		personalname = (EditText) findViewById(R.id.personalname);
		personalphonenumber = (EditText) findViewById(R.id.personalphonenumber);
		personalemail = (EditText) findViewById(R.id.personalemail);
		personaladdress = (EditText) findViewById(R.id.personaladdress);
		personalcanceltime = (TextView) findViewById(R.id.personalcanceltime);
		personallasttime = (TextView) findViewById(R.id.personallasttime);
		personalbtn = (Button) findViewById(R.id.personalbtn);

		dialog = ProgressDialog.show(this, "加载中", "正在加载,请稍后..", true, false);

		new Admin1InfoThread(this).start();

		personalbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(Admin1Infomation.this)
						.setTitle("提示")
						.setMessage("确定保存修改?")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										admin1.setName(personalname.getText()
												.toString().trim());
										admin1.setPhonenum(personalphonenumber
												.getText().toString().trim());
										admin1.setEmail(personalemail.getText()
												.toString().trim());
										admin1.setAddress(personaladdress
												.getText().toString().trim());
										if (null == adminimpl
												.updatewithoutnull(admin1)) {
											Toast.makeText(
													Admin1Infomation.this,
													"更新失败，请检查网络或稍后重试",
													Toast.LENGTH_LONG).show();
										} else {
											finish();
											Admin1Infomation.this.finish();
											Toast.makeText(
													Admin1Infomation.this,
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

	private class Admin1InfoThread extends Thread {
		private Admin1Infomation activity;

		public Admin1InfoThread(Admin1Infomation act) {
			activity = act;
		}

		@Override
		public void run() {
			adminimpl = new Adminimpl();
			admin1 = adminimpl.query(AdminLogin.adminId);

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
				adminname = admin1.getAdminname().toString().trim();
				name = admin1.getName().toString().trim();
				phonenumber = admin1.getPhonenum().toString().trim();
				email = admin1.getEmail().toString().trim();
				address = admin1.getAddress().toString().trim();
				createtime = admin1.getCreatetime();
				lastLogintime = admin1.getLastlogintime();

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

	};
}
