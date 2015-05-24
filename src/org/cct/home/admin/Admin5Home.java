package org.cct.home.admin;

import org.cct.home.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class Admin5Home extends Activity {

	// 社区干部（老干部局）

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
		setContentView(R.layout.adminhome5);

		ImageButton buttonBack = (ImageButton) findViewById(R.id.imageButton_adminhome5);

		btn2 = (Button) findViewById(R.id.admin5_btn2);
		btn3 = (Button) findViewById(R.id.admin5_btn3);
		btn4 = (Button) findViewById(R.id.admin5_btn4);
		btn5 = (Button) findViewById(R.id.admin5_btn5);
		btn6 = (Button) findViewById(R.id.admin5_btn6);
		btn7 = (Button) findViewById(R.id.admin5_btn7);

		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				i = 2;
				dialog = ProgressDialog.show(Admin5Home.this, "加载中",
						"正在加载,请稍后..", true, false);
				new Admin5HomeThread(Admin5Home.this).start();
			}
		});

		btn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Admin5Home.this, Admin5System.class);
				startActivity(intent);
			}
		});

		btn4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				i = 4;
				dialog = ProgressDialog.show(Admin5Home.this, "加载中",
						"正在加载,请稍后..", true, false);
				new Admin5HomeThread(Admin5Home.this).start();
			}
		});

		btn5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Admin5Home.this, Admin5Infomation.class);
				startActivity(intent);
			}
		});

		btn6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Admin5Home.this, Admin5PasswordChange.class);
				startActivity(intent);
			}
		});

		btn7.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Admin5Home.this, AdminLogin.class);
				startActivity(intent);
				Admin5Home.this.finish();
			}
		});

		buttonBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Admin5Home.this.finish();
			}
		});

	}

	private class Admin5HomeThread extends Thread {
		public Admin5HomeThread(Admin5Home act) {

		}

		@Override
		public void run() {
			if (i == 2) {
				Intent intent = new Intent();
				intent.setClass(Admin5Home.this, Admin5CareSelect.class);
				startActivity(intent);
			} else if (i == 4) {
				Intent intent = new Intent();
				intent.setClass(Admin5Home.this, OldCadreManageQuery.class);
				startActivity(intent);
			}
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}

	}
}
