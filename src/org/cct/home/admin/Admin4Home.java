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

public class Admin4Home extends Activity {

	// 小区管理员

	private Button btn2;
	private Button btn3;
	private Button btn4;
	private Button btn5;
	private Button btn6;
	private Button btn7;
	ProgressDialog dialog;
	int i;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome4);

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_adminhome4);

		btn2 = (Button) findViewById(R.id.admin4_btn2);
		btn3 = (Button) findViewById(R.id.admin4_btn3);
		btn4 = (Button) findViewById(R.id.admin4_btn4);
		btn5 = (Button) findViewById(R.id.admin4_btn5);
		btn6 = (Button) findViewById(R.id.admin4_btn6);
		btn7 = (Button) findViewById(R.id.admin4_btn7);

		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Admin4Home.this, Admin4UseQuery.class);
				startActivity(intent);
			}
		});

		btn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				i = 3;
				dialog = ProgressDialog.show(Admin4Home.this, "加载中",
						"正在加载,请稍后..", true, false);
				new Admin4HomeThread(Admin4Home.this).start();
			}
		});

		btn4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Admin4Home.this, Admin4System.class);
				startActivity(intent);
			}
		});

		btn5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Admin4Home.this, AccountManage.class);
				startActivity(intent);
			}
		});

		btn6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				i = 6;
				dialog = ProgressDialog.show(Admin4Home.this, "加载中",
						"正在加载,请稍后..", true, false);
				new Admin4HomeThread(Admin4Home.this).start();
			}
		});

		btn7.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Admin4Home.this, AdminLogin.class);
				startActivity(intent);
				Admin4Home.this.finish();
			}
		});

		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Admin4Home.this.finish();

			}
		});

	}

	private class Admin4HomeThread extends Thread {

		public Admin4HomeThread(Admin4Home act) {
		}

		@Override
		public void run() {
			if (i == 3) {
				Intent intent = new Intent();
				intent.setClass(Admin4Home.this, Admin4CareSelect.class);
				startActivity(intent);
			} else if (i == 6) {
				Intent intent = new Intent();
				intent.setClass(Admin4Home.this, CountyManageQuery.class);
				startActivity(intent);
			}
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}
	}
}
