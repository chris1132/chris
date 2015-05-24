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

public class Admin6Infomation extends Activity {

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
	private Admin admin6 = null;
	private Adminimpl adminimpl = null;
	Timestamp createtime;
	Timestamp lastLogintime;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome6_2_1);

		ImageButton buttonBack = (ImageButton) findViewById(R.id.imageButton6_2_1);
		buttonBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Admin6Infomation.this.finish();

			}
		});

		personaladminname = (TextView) findViewById(R.id.personal6adminname);
		personalname = (EditText) findViewById(R.id.personal6name);
		personalphonenumber = (EditText) findViewById(R.id.personal6phonenumber);
		personalemail = (EditText) findViewById(R.id.personal6email);
		personaladdress = (EditText) findViewById(R.id.personal6address);
		personalcanceltime = (TextView) findViewById(R.id.personal6canceltime);
		personallasttime = (TextView) findViewById(R.id.personal6lasttime);
		personalbtn = (Button) findViewById(R.id.personal6btn);

		dialog = ProgressDialog.show(this, "加载中", "正在加载，请稍候...", true, false);
		new Admin6InfomationThread(this).start();
		personalbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(Admin6Infomation.this)
						.setTitle("提示")
						.setMessage("确定保存修改?")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										admin6.setName(personalname.getText()
												.toString().trim());
										admin6.setPhonenum(personalphonenumber
												.getText().toString().trim());
										admin6.setEmail(personalemail.getText()
												.toString().trim());
										admin6.setAddress(personaladdress
												.getText().toString().trim());
										if (null == adminimpl
												.updatewithoutnull(admin6)) {
											Toast.makeText(
													Admin6Infomation.this,
													"更新失败，请检查网络或稍后重试",
													Toast.LENGTH_LONG).show();
										} else {
											finish();
											Admin6Infomation.this.finish();
											Toast.makeText(
													Admin6Infomation.this,
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

	private class Admin6InfomationThread extends Thread {
		private Admin6Infomation actvity;

		public Admin6InfomationThread(Admin6Infomation act) {
			actvity = act;
		}

		@Override
		public void run() {
			adminimpl = new Adminimpl();
			admin6 = adminimpl.query(AdminLogin.adminId);

			actvity.handler.sendEmptyMessage(0);
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
				adminname = admin6.getAdminname().toString().trim();
				name = admin6.getName().toString().trim();
				phonenumber = admin6.getPhonenum().toString().trim();
				email = admin6.getEmail().toString().trim();
				address = admin6.getAddress().toString().trim();
				createtime = admin6.getCreatetime();
				lastLogintime = admin6.getLastlogintime();

				if(adminname != null && (!name.equals(""))) {
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
				if (createtime != null && (!createtime.equals(""))) {
					personalcanceltime.setText(createtime + "");
				} else {
					personalcanceltime.setText("无");
				}
				if (lastLogintime != null && (!lastLogintime.equals(""))) {
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