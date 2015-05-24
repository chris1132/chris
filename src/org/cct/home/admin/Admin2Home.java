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

public class Admin2Home extends Activity {

	// 联通主管
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
	int i;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome2);

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_adminhome2);

		btn1 = (Button) findViewById(R.id.admin2_btn1);
		btn2 = (Button) findViewById(R.id.admin2_btn2);
		btn3 = (Button) findViewById(R.id.admin2_btn3);
		btn4 = (Button) findViewById(R.id.admin2_btn4);
		btn5 = (Button) findViewById(R.id.admin2_btn5);
		btn6 = (Button) findViewById(R.id.admin2_btn6);
		btn7 = (Button) findViewById(R.id.admin2_btn7);
		btn8 = (Button) findViewById(R.id.admin2_btn8);
		btn9 = (Button) findViewById(R.id.admin2_btn9);
		btn10 = (Button) findViewById(R.id.admin2_btn10);

		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent();
				intent1.setClass(Admin2Home.this, Admin2UseRegist.class);
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
				intent.setClass(Admin2Home.this, Admin2UseQuery.class);
				startActivity(intent);
			}
		});

		btn4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				i=4;
				handler = new Admin2Handler();

				dialog = ProgressDialog.show(Admin2Home.this, "加载中",
						"加载中，请稍后..");

				Thread workThread = new Admin2HomeThread();
				workThread.start();

			}
		});

		btn5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Admin2Home.this, Admin2Service.class);
				startActivity(intent);
			}
		});

		btn6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent.setClass(Admin2Home.this, Admin2Infomation.class);
				startActivity(intent);
			}
		});

		btn7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent.setClass(Admin2Home.this, Admin2PasswordChange.class);
				startActivity(intent);
			}
		});
		btn8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Admin2Home.this, AdminLogin.class);
				startActivity(intent);
				Admin2Home.this.finish();
			}
		});
		btn9.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				i=9;
				handler = new Admin2Handler();

				dialog = ProgressDialog.show(Admin2Home.this, "加载中",
						"加载中，请稍后..");

				Thread workThread = new Admin2HomeThread();
				workThread.start();

			}
		});
		btn10.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				i=10;
				handler = new Admin2Handler();

				dialog = ProgressDialog.show(Admin2Home.this, "加载中",
						"加载中，请稍后..");

				Thread workThread = new Admin2HomeThread();
				workThread.start();

			}
		});
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Admin2Home.this.finish();
			}
		});
	}

	private class Admin2HomeThread extends Thread {

		@Override
		public void run() {
//			Intent intent = new Intent();
//			intent.setClass(Admin2Home.this, Admin2CareSelect.class);
//			startActivity(intent);
//			handler.sendEmptyMessage(1);
			switch (i) {
			case 4:
				Intent intent = new Intent();
				intent.setClass(Admin2Home.this, Admin2CareSelect.class);
				startActivity(intent);
				handler.sendEmptyMessage(1);
				break;
			case 9:
				Intent intent2 = new Intent();
				intent2.setClass(Admin2Home.this, UnicomSaleQuery.class);
				startActivity(intent2);
				handler.sendEmptyMessage(1);
				break;
			case 10:
				Intent intent3 = new Intent();
				intent3.setClass(Admin2Home.this, UnicomQuery.class);
				startActivity(intent3);
				handler.sendEmptyMessage(1);
				break;
				
			}
		}
	}

	private class Admin2Handler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				dialog.dismiss();
			}
		}

	}

}
