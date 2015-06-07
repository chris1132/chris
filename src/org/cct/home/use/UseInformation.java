package org.cct.home.use;

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

import com.org.ifamily.entity.User;
import com.org.ifamily.implement.Userimpl;

public class UseInformation extends Activity {

	private Button usesubmit;
	private EditText usename;
	private EditText usephonenumber;
	private EditText useemail;
	private EditText useaddress;
	private TextView useadminname;
	private TextView uselasttime;
	private TextView usecanceltime;
	private TextView idnum;
	private String name;
	private String phonenumber;
	private String email;
	private String address;
	private String adname;
	private String useidnum;
	private User user = null;
	private Userimpl userimpl = null;
	private Timestamp createtime;
	private Timestamp lastLogintime;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.use_7_1);

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_use_7_1);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				UseInformation.this.finish();

			}
		});

		usesubmit = (Button) findViewById(R.id.usesubmit);
		usename = (EditText) findViewById(R.id.usename);
		usephonenumber = (EditText) findViewById(R.id.usephonenumber);
		useemail = (EditText) findViewById(R.id.useemail);
		useaddress = (EditText) findViewById(R.id.useaddress);
		useadminname = (TextView) findViewById(R.id.useadminname);
		idnum = (TextView) findViewById(R.id.idnum);
		usecanceltime = (TextView) findViewById(R.id.usecanceltime);
		uselasttime = (TextView) findViewById(R.id.uselasttime);

		dialog = ProgressDialog.show(this, "加载中", "正在加载,请稍后..", true, false);

		// 启动业务线程
		new UseInfoThread(this).start();

		usesubmit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(UseInformation.this)
						.setTitle("提示")
						.setMessage("确定保存修改?")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										user.setName(usename.getText()
												.toString().trim());
										user.setPhonenum(usephonenumber
												.getText().toString().trim());
										user.setEmail(useemail.getText()
												.toString().trim());
										user.setAddress(useaddress.getText()
												.toString().trim());
										if (null == userimpl
												.updatewithoutnull(user)) {
											Toast.makeText(UseInformation.this,
													"更新失败，请检查网络或稍后重试",
													Toast.LENGTH_LONG).show();
										} else {
											finish();
											UseInformation.this.finish();
											Toast.makeText(UseInformation.this,
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

	private class UseInfoThread extends Thread {

		private UseInformation activity;

		public UseInfoThread(UseInformation act) {
			activity = act;
		}

		@Override
		public void run() {
			userimpl = new Userimpl();
			user = userimpl.querybyuserid(UseLogin.useId);
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
				name = user.getName().toString().trim();
				phonenumber = user.getPhonenum().toString().trim();
				email = user.getEmail().toString().trim();
				address = user.getAddress().toString().trim();

				adname = user.getUsername().toString().trim();
				useidnum = user.getIdnumber().toString().trim();
				createtime = user.getCreatetime();
				lastLogintime = user.getLastlogintime();

				if (adname != null && (!adname.equals(""))) {
					useadminname.setText(adname);
				} else {
					useadminname.setText("无");
				}
				if (name != null && (!name.equals(""))) {
					usename.setText(name);
				} else {
					usename.setText("无");
				} 
				if (phonenumber != null && (!phonenumber.equals(""))) {
					usephonenumber.setText(phonenumber);
				} else {
					usephonenumber.setText("无");
				}
				if (useidnum != null && (!useidnum.equals(""))) {
					idnum.setText(useidnum);
				} else {
					idnum.setText("无");
				}
				if (email != null && (!email.equals(""))) {
					useemail.setText(email);
				} else {
					useemail.setText("无");
				}
				if (address != null && (!address.equals(""))) {
					useaddress.setText(address);
				} else {
					useaddress.setText("无");
				}
				if (createtime != null) {
					usecanceltime.setText(createtime + "");
				} else {
					usecanceltime.setText("无");
				}
				if (lastLogintime != null) {
					uselasttime.setText(lastLogintime + "");
				} else {
					uselasttime.setText("无");
				}
				break;

			default:
				break;
			}
		}

	};

}
