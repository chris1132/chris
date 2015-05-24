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

public class Admin4Infomation extends Activity {
	
	private TextView personaladminname;
	private EditText ed1 = null;
	private EditText ed2 = null;
	private EditText ed3 = null;
	private EditText ed4 = null;
	private TextView tv1 = null;
	private TextView tv2 = null;
	private Button button = null;
	
	private String adminname;
	private String name;
	private String phonenumber;
	private String email;
	private String address;
	private Admin admin = null;
	private Adminimpl adminimpl = null;
	Timestamp createtime;
	Timestamp lastlogintime;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome4_4_1);

		personaladminname = (TextView) findViewById(R.id.personal4adminname);
		ed1 = (EditText) findViewById(R.id.personal4name);
		ed2 = (EditText) findViewById(R.id.personal4phonenumber);
		ed3 = (EditText) findViewById(R.id.personal4email);
		ed4 = (EditText) findViewById(R.id.personal4address);
		tv1 = (TextView) findViewById(R.id.personal4canceltime);
		tv2 = (TextView) findViewById(R.id.personal4lasttime);
		button = (Button) findViewById(R.id.personal4btn);

		ImageButton buttonBack = (ImageButton) findViewById(R.id.imageButton_adminhome4_4_1);
		buttonBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 按钮按下，将抽屉打开
				Admin4Infomation.this.finish();

			}
		});

		dialog = ProgressDialog.show(this, "加载中", "正在加载,请稍后..", true, false);
		new Admin4InfoThread(this).start();

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(Admin4Infomation.this)
						.setTitle("提示")
						.setMessage("确定保存修改？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										admin.setName(ed1.getText().toString()
												.trim());
										admin.setPhonenum(ed2.getText()
												.toString().trim());
										admin.setEmail(ed3.getText().toString()
												.trim());
										admin.setAddress(ed4.getText()
												.toString().trim());
										if (null == adminimpl
												.updatewithoutnull(admin)) {
											Toast.makeText(
													Admin4Infomation.this,
													"更新失败，请检查网络或稍后重试",
													Toast.LENGTH_LONG).show();

										} else {
											finish();
											Admin4Infomation.this.finish();
											Toast.makeText(
													Admin4Infomation.this,
													"个人信息更新成功",
													Toast.LENGTH_LONG).show();

										}

									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										// 取消按钮事件
									}
								}).show();

			}

		});

	}

	private class Admin4InfoThread extends Thread {
		private Admin4Infomation activity;

		public Admin4InfoThread(Admin4Infomation act) {
			activity = act;
		}

		@Override
		public void run() {
			adminimpl = new Adminimpl();
			admin = adminimpl.query(AdminLogin.adminId);

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
				adminname = admin.getAdminname().toString().trim();
				name = admin.getName().toString().trim();
				phonenumber = admin.getPhonenum().toString().trim();
				email = admin.getEmail().toString().trim();
				address = admin.getAddress().toString().trim();
				createtime = admin.getCreatetime();
				lastlogintime = admin.getLastlogintime();

				if(adminname != null && (!adminname.equals(""))) {
					personaladminname.setText(adminname);
				} else {
					personaladminname.setText("无");
				}
				if (name != null && (!name.equals(""))) {
					ed1.setText(name);
				} else {
					ed1.setText("无");
				}
				if (phonenumber != null && (!phonenumber.equals(""))) {
					ed2.setText(phonenumber);
				} else {
					ed2.setText("无");
				}
				if (email != null && (!email.equals(""))) {
					ed3.setText(email);
				} else {
					ed3.setText("无");
				}
				if (address != null && (!address.equals(""))) {
					ed4.setText(address);
				} else {
					ed4.setText("无");
				}
				if (createtime != null) {
					tv1.setText(createtime + "");
				} else {
					tv1.setText("无");
				}
				if (lastlogintime != null) {
					tv2.setText(lastlogintime + "");
				} else {
					tv2.setText("无");
				}
				break;

			default:
				break;
			}
		}

	};

}
