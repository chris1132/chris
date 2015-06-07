package org.cct.home.use;

import java.util.Date;

import org.cct.home.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.org.ifamily.entity.Area;
import com.org.ifamily.entity.Host;
import com.org.ifamily.implement.Areaimpl;

public class CareInformation extends Activity {

	private TextView t1;
	private TextView t2;
	private TextView t3;
	private TextView t4;
	private TextView t5;
	private TextView t6;
	private TextView t7;
	private TextView t8;
	private TextView t9;
	private TextView t10;
	private TextView t11;
	private TextView t12;
	private TextView t13;
	private TextView t14;
	private TextView t15;
	private TextView t16;
	private TextView t17;
	private TextView t18;
	private TextView t19;
	private TextView t20;
	Host host;
	ProgressDialog dialog;
	Areaimpl areaimpl1;
	Area area1;
	Areaimpl areaimpl2;
	Area area2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.use_4_1);

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_use_4_1);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				CareInformation.this.finish();
			}
		});

		t1 = (TextView) findViewById(R.id.use_4_1_t1);
		t2 = (TextView) findViewById(R.id.use_4_1_t2);
		t3 = (TextView) findViewById(R.id.use_4_1_t3);
		t4 = (TextView) findViewById(R.id.use_4_1_t4);
		t5 = (TextView) findViewById(R.id.use_4_1_t5);
		t6 = (TextView) findViewById(R.id.use_4_1_t6);
		t7 = (TextView) findViewById(R.id.use_4_1_t7);
		t8 = (TextView) findViewById(R.id.use_4_1_t8);
		t9 = (TextView) findViewById(R.id.use_4_1_t9);
		t10 = (TextView) findViewById(R.id.use_4_1_t10);
		t11 = (TextView) findViewById(R.id.use_4_1_t11);
		t12 = (TextView) findViewById(R.id.use_4_1_t12);
		t13 = (TextView) findViewById(R.id.use_4_1_t13);
		t14 = (TextView) findViewById(R.id.use_4_1_t14);
		t15 = (TextView) findViewById(R.id.use_4_1_t15);
		t16 = (TextView) findViewById(R.id.use_4_1_t16);
		t17 = (TextView) findViewById(R.id.use_4_1_t17);
		t18 = (TextView) findViewById(R.id.use_4_1_t18);
		t19 = (TextView) findViewById(R.id.use_4_1_t19);
		t20 = (TextView) findViewById(R.id.use_4_1_t20);

		dialog = ProgressDialog.show(this, "加载中", "正在加载,请稍后..", true, false);

		// 启动业务线程
		new CareInfThread(this).start();
	}

	private class CareInfThread extends Thread {

		private CareInformation activity;

		public CareInfThread(CareInformation act) {
			activity = act;
		}

		@Override
		public void run() {
			Intent intent = getIntent();
			host = (Host) intent.getSerializableExtra("HOSTSURE");
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
				t1.setText(host.getHostnum() + "");
				t2.setText(host.getHostname() + "");
				if (host.getGender()) {
					t3.setText("男");
				} else {
					t3.setText("女");
				}
				t4.setText(host.getNationality() + "");
				t5.setText(host.getPhonenum() + "");
				t6.setText(host.getTelephone() + "");
				t7.setText(host.getIdnumber() + "");
				
				Date data = host.getBirthday();
				int i1 = data.getYear() + 1900;
				int i2 = data.getMonth() + 1;
				int i3 = data.getDate();

				t8.setText(i1 + "-" + i2 + "-" + i3);
				t9.setText(host.getHeight() + "");
				t10.setText(host.getWeight() + "");
				t11.setText(host.getProvince() + "");
				t12.setText(host.getCity() + "");
				t13.setText(host.getCounty() + "");

				areaimpl1 = new Areaimpl();
				area1 = areaimpl1.querybyareaid(host.getAreaid());
				areaimpl2 = new Areaimpl();
				area2 = areaimpl2.querybyareaid(area1.getParentid());
				
				t14.setText(area2.getAreaname() + "");
				t15.setText(area1.getAreaname() + "");
				t16.setText(host.getAddress() + "");
				t17.setText(host.getCallone() + "");
				t18.setText(host.getCalltwo() + "");
				t19.setText(host.getCallthree() + "");
				t20.setText(host.getRemarks() + "");
				break;

			default:
				break;
			}
		}

	};
}
