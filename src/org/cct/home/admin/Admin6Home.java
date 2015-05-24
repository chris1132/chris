package org.cct.home.admin;

import org.cct.home.R;
import org.cct.home.welcome.home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class Admin6Home extends Activity {

	// 医生
	private Button btn2;
	private Button btn3;
	private Button btn4;
	private Button btn5;
	ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome6);

		ImageButton buttonBack = (ImageButton) findViewById(R.id.imageButton_adminhome6);
		btn2 = (Button) findViewById(R.id.admin6_btn2);
		btn3 = (Button) findViewById(R.id.admin6_btn3);
		btn4 = (Button) findViewById(R.id.admin6_btn4);
		btn5 = (Button) findViewById(R.id.admin6_btn5);

		btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog = ProgressDialog.show(Admin6Home.this, "加载中",
						"正在加载，请稍候...", true, false);
				new Admin6HomeThread().start();
			}
		});

		btn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Admin6Home.this, Admin6Infomation.class);
				startActivity(intent);
			}
		});

		btn4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Admin6Home.this, Admin6PasswordChange.class);
				startActivity(intent);
			}
		});
		btn5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Admin6Home.this, AdminLogin.class);
				startActivity(intent);
				Admin6Home.this.finish();
			}
		});

		buttonBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Admin6Home.this.finish();

			}
		});

	}

	private class Admin6HomeThread extends Thread {

		@Override
		public void run() {
			Intent intent = new Intent();
			intent.setClass(Admin6Home.this, Admin6CareSelect.class);
			startActivity(intent);
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}

	}
}
