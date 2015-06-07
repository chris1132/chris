package org.cct.home.use;

import org.cct.home.R;
import org.cct.home.map.MapActivity;

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
import android.widget.Toast;

import com.org.ifamily.entity.Host;

public class UseHome extends Activity {

	private Button btn2;
	private Button btn3;
	private Button btn4;
	private Button btn7;
	private Button btn8;
	private Button btn9;
	private Button btn10;
	private Button btn11;
	private Button btn12;
	Host host;
	int i;
	ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usehome);

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_usehome);

		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);
		btn7 = (Button) findViewById(R.id.btn7);
		btn8 = (Button) findViewById(R.id.btn8);
		btn9 = (Button) findViewById(R.id.btn9);
		btn10 = (Button) findViewById(R.id.btn10);
		btn11 = (Button) findViewById(R.id.btn11);
		btn12 = (Button) findViewById(R.id.btn12);

		Intent intent = getIntent();
		host = (Host) intent.getSerializableExtra("HOSTSELECT");

		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (host == null) {
					Toast.makeText(UseHome.this, "请先选择对象", Toast.LENGTH_LONG)
							.show();
				} else {
					Intent intent = new Intent();
					intent.setClass(UseHome.this, WarnRecord.class);
					intent.putExtra("HOSTSURE", host);
					startActivity(intent);

				}
			}
		});
		btn4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (host == null) {
					Toast.makeText(UseHome.this, "请先选择对象", Toast.LENGTH_LONG)
							.show();
				} else {
					Intent intent = new Intent();
					intent.setClass(UseHome.this, RemindManage.class);
					intent.putExtra("HOSTSURE", host);
					startActivity(intent);
				}
			}
		});

		btn7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (host == null) {
					Toast.makeText(UseHome.this, "请先选择对象", Toast.LENGTH_LONG)
							.show();
				} else {
					dialog = ProgressDialog.show(UseHome.this, "加载中",
							"正在加载,请稍后..", true, false);
					i = 0;
					new UseHomeThread(UseHome.this).start();
				}
			}
		});
		btn8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (host == null) {
					Toast.makeText(UseHome.this, "请先选择对象", Toast.LENGTH_LONG)
							.show();
				} else {
					dialog = ProgressDialog.show(UseHome.this, "加载中",
							"正在加载,请稍后..", true, false);
					i = 1;
					new UseHomeThread(UseHome.this).start();
				}
			}
		});

		btn9.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (host == null) {
					Toast.makeText(UseHome.this, "请先选择对象", Toast.LENGTH_LONG)
							.show();
				} else {
					dialog = ProgressDialog.show(UseHome.this, "加载中",
							"正在加载,请稍后..", true, false);
					i = 2;
					new UseHomeThread(UseHome.this).start();
				}
			}
		});
		btn10.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent1 = new Intent();
				intent1.setClass(UseHome.this, UseInformation.class);
				startActivity(intent1);
			}
		});
		btn11.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent1 = new Intent();
				intent1.setClass(UseHome.this, PasswordChange.class);
				startActivity(intent1);
			}
		});
		btn12.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent1 = new Intent();
				intent1.setClass(UseHome.this, UseLogin.class);
				startActivity(intent1);
				UseHome.this.finish();
			}
		});

		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(UseHome.this, UseCareSelevt.class);
				startActivity(intent);
				UseHome.this.finish();

			}
		});

	}

	private class UseHomeThread extends Thread {

		private UseHome activity;

		public UseHomeThread(UseHome act) {
			activity = act;
		}

		@Override
		public void run() {
			activity.handler.sendEmptyMessage(i);

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
				Intent intent = new Intent();
				intent.setClass(UseHome.this, CareInformation.class);
				intent.putExtra("HOSTSURE", host);
				startActivity(intent);
				break;

			case 1:
				Intent intent1 = new Intent();
				intent1.setClass(UseHome.this, MapActivity.class);
				intent1.putExtra("HOSTSURE", host);
				startActivity(intent1);
				break;
				
			case 2:
				Intent intent2 = new Intent();
				intent2.setClass(UseHome.this, QuickSet.class);
				intent2.putExtra("HOSTSURE", host);
				startActivity(intent2);
				break;

			default:
				break;
			}
		}

	};
}
