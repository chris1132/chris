package org.cct.home.admin;

import org.cct.home.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class Admin3System extends Activity{
	private ImageButton buttonBack;
	private ProgressDialog dialog;
	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_3);
		 buttonBack = (ImageButton)findViewById(R.id.imageButton_usehome);
		Button button1 = (Button) findViewById(R.id.newbtn1);
		Button button2 = (Button) findViewById(R.id.newbtn2);
		Button button3 = (Button) findViewById(R.id.newbtn3);
		//街道管理
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				int i = 1;
				handler = new Admin3Handler();

				dialog = ProgressDialog.show(Admin3System.this, "加载中",
						"加载中，请稍后..");

				Thread workThread = new Admin3HomeThread(i);
				workThread.start();
			
		}
		});
		//小区管理
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				int i = 2;
				handler = new Admin3Handler();

				dialog = ProgressDialog.show(Admin3System.this, "加载中",
						"加载中，请稍后..");

				Thread workThread = new Admin3HomeThread(i);
				workThread.start();
			
			
		}
		});
		//增值服务
		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent3 = new Intent();
				intent3.setClass(Admin3System.this, Admin3Service.class);
				startActivity(intent3);
				
			}
		});
		//回退
		buttonBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Admin3System.this.finish();
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
			case 1:
				Intent intent = new Intent();
				intent.setClass(Admin3System.this, Admin3StreetQuery.class);
				startActivity(intent);
				handler.sendEmptyMessage(1);
				break;
			case 2:
				Intent intent2 = new Intent();
				intent2.setClass(Admin3System.this, Admin3CountyQuery.class);
				startActivity(intent2);
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
