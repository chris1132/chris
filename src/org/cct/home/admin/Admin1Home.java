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

public class Admin1Home extends Activity {

	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;
	private Button btn5;
	private Button btn6;
	private Button btn7;
	private Button btn8;
	ProgressDialog dialog;
	int i = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome1);

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_adminhome1);
		btn1 = (Button) findViewById(R.id.admin1_btn1);
		btn2 = (Button) findViewById(R.id.admin1_btn2);
		btn3 = (Button) findViewById(R.id.admin1_btn3);
		btn4 = (Button) findViewById(R.id.admin1_btn4);
		btn5 = (Button) findViewById(R.id.admin1_btn5);
		btn6 = (Button) findViewById(R.id.admin1_btn6);
		btn7 = (Button) findViewById(R.id.admin1_btn7);
		btn8 = (Button) findViewById(R.id.admin1_btn8);

		btn1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent();
				intent1.setClass(Admin1Home.this, Admin1UseRegist.class);
				startActivity(intent1);
			}
		});

		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse("tel:6581890");
				Intent intent = new Intent(Intent.ACTION_DIAL, uri);
				startActivity(intent);
			}
		});

		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Admin1Home.this, Admin1UseQuery.class);
				startActivity(intent);
			}
		});

		btn4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				i = 0;
				dialog = ProgressDialog.show(Admin1Home.this, "加载中",
						"正在加载,请稍后..", true, false);
				
				new Admin1HomeThread(Admin1Home.this).start();
				
			}
		});

		btn5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Admin1Home.this, Admin1Service.class);
				startActivity(intent);
			}
		});

		btn6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent.setClass(Admin1Home.this, Admin1Infomation.class);
				startActivity(intent);
			}
		});

		btn7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent.setClass(Admin1Home.this, Admin1PasswordChange.class);
				startActivity(intent);
			}
		});
		btn8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Admin1Home.this, AdminLogin.class);
				startActivity(intent);
				Admin1Home.this.finish();
			}
		});

		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 返回上一级
				Admin1Home.this.finish();

			}
		});
	}

	private class Admin1HomeThread extends Thread {
		private Admin1Home activity;

		public Admin1HomeThread(Admin1Home act) {
			activity = act;
		}

		@Override
		public void run() {
			activity.handler.sendEmptyMessage(i);
			
			if(dialog.isShowing()) {
				dialog.dismiss();
			}
		}
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Intent intent = new Intent();
				intent.setClass(Admin1Home.this, Admin1CareSelect.class);
				startActivity(intent);
				break;

			default:
				break;
			}
		}

	};
}
