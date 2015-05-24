package org.cct.home.admin;

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

public class CareDetail extends Activity {
	TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12,
			tv13, tv14, tv15, tv16, tv17, tv18, tv19, tv20;

	Areaimpl areaimpl1;
	Area area1;
	Areaimpl areaimpl2;
	Area area2;
	Host host;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome_2_2_all);
		tv1 = (TextView) findViewById(R.id.textnum);
		tv2 = (TextView) findViewById(R.id.textname);
		tv3 = (TextView) findViewById(R.id.textsex);
		tv4 = (TextView) findViewById(R.id.textnation);
		tv5 = (TextView) findViewById(R.id.textnum1);
		tv6 = (TextView) findViewById(R.id.textnum2);
		tv7 = (TextView) findViewById(R.id.textidnum);
		tv8 = (TextView) findViewById(R.id.textbirth);
		tv9 = (TextView) findViewById(R.id.textloc);
		tv10 = (TextView) findViewById(R.id.texth);
		tv11 = (TextView) findViewById(R.id.textw);
		tv12 = (TextView) findViewById(R.id.textprovince);
		tv13 = (TextView) findViewById(R.id.textcity);
		tv14 = (TextView) findViewById(R.id.textcounty);
		tv15 = (TextView) findViewById(R.id.textstreet);
		tv16 = (TextView) findViewById(R.id.textneighbor);
		tv17 = (TextView) findViewById(R.id.textparentnum1);
		tv18 = (TextView) findViewById(R.id.textparentnum2);
		tv19 = (TextView) findViewById(R.id.textparentnum3);
		tv20 = (TextView) findViewById(R.id.textother);
		ImageButton buttonBack = (ImageButton) findViewById(R.id.imageButton_2_2_all);

		buttonBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CareDetail.this.finish();
			}
		});

		dialog = ProgressDialog.show(this, "加载中", "正在加载，请稍候...", true, false);
		new CareDetailThread(this).start();

	}

	private class CareDetailThread extends Thread {
		private CareDetail activity;

		public CareDetailThread(CareDetail act) {
			activity = act;
		}

		@Override
		public void run() {
			Intent intent = getIntent();
			host = (Host) intent.getSerializableExtra("Host");
			areaimpl1 = new Areaimpl();
			area1 = areaimpl1.querybyareaid(host.getAreaid());
			areaimpl2 = new Areaimpl();
			area2 = areaimpl2.querybyareaid(area1.getParentid());

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
				tv1.setText(host.getHostnum() + "");
				tv2.setText(host.getHostname() + "");
				if (host.getGender()) {
					tv3.setText("男");
				} else {
					tv3.setText("女");
				}
				tv4.setText(host.getNationality() + "");
				tv5.setText(host.getPhonenum() + "");
				tv6.setText(host.getTelephone() + "");
				tv7.setText(host.getIdnumber() + "");

				Date data = host.getBirthday();
				int i1 = data.getYear() + 1900;
				int i2 = data.getMonth() + 1;
				int i3 = data.getDate();
				tv8.setText(i1 + "-" + i2 + "-" + i3);
				tv9.setText(host.getAddress() + "");
				tv10.setText(host.getHeight() + "");
				tv11.setText(host.getWeight() + "");
				tv12.setText(host.getProvince() + "");
				tv13.setText(host.getCity() + "");
				tv14.setText(host.getCounty() + "");

				tv15.setText(area2.getAreaname() + "");
				tv16.setText(area1.getAreaname() + "");
				tv17.setText(host.getCallone() + "");
				tv18.setText(host.getCalltwo() + "");
				tv19.setText(host.getCallthree() + "");
				tv20.setText(host.getRemarks() + "");
				break;

			default:
				break;
			}
		}

	};
}