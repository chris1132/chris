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

public class Admin5System extends Activity {
	private Button btn1;
	private Button btn3;
	private Button btn4;
	int i;
	ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome5_new_system);
		ImageButton buttonBack = (ImageButton) findViewById(R.id.imageButton_adminhome5_new_system);
		btn1 = (Button) findViewById(R.id.admin5_new_s_btn1);
		btn3 = (Button) findViewById(R.id.admin5_new_s_btn3);
		btn4 = (Button) findViewById(R.id.admin5_new_s_btn4);
		buttonBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Admin5System.this.finish();
			}
		});
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				i = 1;
				dialog = ProgressDialog.show(Admin5System.this, "加载中",
						"正在加载，请稍候", true, false);
				new Admin5SystemThread(Admin5System.this).start();
			}
		});
		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				i = 3;
				dialog = ProgressDialog.show(Admin5System.this, "加载中",
						"正在加载，请稍候", true, false);
				new Admin5SystemThread(Admin5System.this).start();
			}
		});
		btn4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Admin5System.this, Admin5Service.class);
				startActivity(intent);
			}
		});
	}

	private class Admin5SystemThread extends Thread {
		public Admin5SystemThread(Admin5System act) {

		}

		@Override
		public void run() {
			if (i == 1) {
				Intent intent = new Intent();
				intent.setClass(Admin5System.this, Admin5StreetQuery.class);
				startActivity(intent);
			} else if (i == 3) {
				Intent intent = new Intent();
				intent.setClass(Admin5System.this, Admin5CountyQuery.class);
				startActivity(intent);
			}
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}

	}
}