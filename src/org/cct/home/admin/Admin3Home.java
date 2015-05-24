package org.cct.home.admin;

import org.cct.home.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class Admin3Home extends Activity {

	// 街道管理员
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;
	private Button btn5;
	private Button btn6;
	private Button btn7;
	private Button btn8;
	private Button btn9;
	private Button btn10;
	private ProgressDialog dialog;
	private Handler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome3);

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_adminhome3);
		btn1 = (Button) findViewById(R.id.admin3_btn1);
		btn2 = (Button) findViewById(R.id.admin3_btn2);
		btn3 = (Button) findViewById(R.id.admin3_btn3);
		btn4 = (Button) findViewById(R.id.admin3_btn4);
		// btn5 = (Button) findViewById(R.id.admin3_btn5);
		// btn6 = (Button) findViewById(R.id.admin3_btn6);
		btn7 = (Button) findViewById(R.id.admin3_btn7);
		btn8 = (Button) findViewById(R.id.admin3_btn8);
		btn9 = (Button) findViewById(R.id.admin3_btn9);
		btn10 = (Button) findViewById(R.id.admin3_btn10);
		// 用户信息查看
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent();
				intent1.setClass(Admin3Home.this, Admin3UseQuery.class);
				startActivity(intent1);
			}
		});
		// 服务热线
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse("tel:6581890");
				Intent intent = new Intent(Intent.ACTION_DIAL, uri);
				startActivity(intent);
			}
		});
		// 选择关怀对象
		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int i = 3;
				handler = new Admin3Handler();

				dialog = ProgressDialog.show(Admin3Home.this, "加载中",
						"加载中，请稍后..");

				Thread workThread = new Admin3HomeThread(i);
				workThread.start();

			}
		});
		// 系统管理
		btn4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent.setClass(Admin3Home.this, Admin3System.class);
				startActivity(intent);
			}
		});

		btn7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		
				Intent intent = new Intent();
				intent.setClass(Admin3Home.this, Admin3Infomation.class);
				startActivity(intent);

			}
		});
		btn8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Admin3Home.this, Admin3PasswordChange.class);
				startActivity(intent);
			}
		});
		btn9.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int i =9;
				handler = new Admin3Handler();

				dialog = ProgressDialog.show(Admin3Home.this, "加载中",
						"加载中，请稍后..");

				Thread workThread = new Admin3HomeThread(i);
				workThread.start();

			}
		});
		btn10.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(Admin3Home.this, AdminLogin.class);
				startActivity(intent);
				Admin3Home.this.finish();
			}
		});
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Admin3Home.this.finish();

			}
		});

	}

	private class Admin3HomeThread extends Thread {
		int i;

		public Admin3HomeThread(int i2) {
			// TODO Auto-generated constructor stub
			i = i2;
		}

		@Override
		public void run() {
			switch (i) {
			case 3:
				Intent intent = new Intent();
				intent.setClass(Admin3Home.this, Admin3CareSelect.class);
				startActivity(intent);
				handler.sendEmptyMessage(1);
				break;
			case 9:
				Intent intent3 = new Intent();
				intent3.setClass(Admin3Home.this, StreetManageQuery.class);
				startActivity(intent3);
				handler.sendEmptyMessage(1);
				break;
				
			}

		}
	}

	private class Admin3Handler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				dialog.dismiss();
			}
		}

	}

}
