package org.cct.home.location;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.cct.home.R;
import org.cct.home.savecontext.SaveService;
import org.cct.home.welcome.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class TestActivity extends Activity {
	private RadioGroup clickGroup = null;
	private RadioButton startServiceButton = null;
	private RadioButton stopServiceButton = null;
	// 定位
	private RadioGroup clickGroup1 = null;
	private RadioButton startServiceButton1 = null;
	private RadioButton stopServiceButton1 = null;
	// 加速度
	private RadioGroup clickGroup2 = null;
	private RadioButton startServiceButton2 = null;
	private RadioButton stopServiceButton2 = null;
	// 静默
	private Button backButton = null;
	
	private String wkp;
	private String sliencestarttime;
	private String slienceendtime;
	private String slienceinterval;
	private String hightemperature;
	private String lowtemperature;
	private Properties properties;
	private SaveService service;
	private Map<String, ?> map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		properties = new Properties();
		service = new SaveService(this);
		InputStream is;
		try {
			is = this.getAssets().open("deviceconf.properties");
			properties.load(is);
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		wkp = properties.getProperty("wkp");
		sliencestarttime = properties.getProperty("sliencestarttime");
		slienceendtime = properties.getProperty("slienceendtime");
		slienceinterval = properties.getProperty("slienceinterval");
		hightemperature = properties.getProperty("hightemperature");
		lowtemperature = properties.getProperty("lowtemperature");
		map = service.getSharePreference("deviceconf");
		if(map == null || map.isEmpty()) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("wkp", wkp);
			data.put("sliencestarttime", sliencestarttime);
			data.put("slienceendtime", slienceendtime);
			data.put("slienceinterval", slienceinterval);
			data.put("hightemperature", hightemperature);
			data.put("lowtemperature", lowtemperature);
			service.saveSharePreference("deviceconf", data);
		}
		
		clickGroup = (RadioGroup) findViewById(R.id.clickGroup);
		clickGroup1 = (RadioGroup) findViewById(R.id.clickGroup1);
		clickGroup2 = (RadioGroup) findViewById(R.id.clickGroup2);
		
		startServiceButton = (RadioButton) findViewById(R.id.startServiceButton);
		startServiceButton.setOnClickListener(new StartServiceListener());
		stopServiceButton = (RadioButton) findViewById(R.id.stopServiceButton);
		stopServiceButton.setOnClickListener(new StopServiceListener());

		startServiceButton1 = (RadioButton) findViewById(R.id.startServiceButton1);
		startServiceButton1.setOnClickListener(new StartServiceListener1());
		stopServiceButton1 = (RadioButton) findViewById(R.id.stopServiceButton1);
		stopServiceButton1.setOnClickListener(new StopServiceListener1());

		startServiceButton2 = (RadioButton) findViewById(R.id.startServiceButton2);
		startServiceButton2.setOnClickListener(new StartServiceListener2());
		stopServiceButton2 = (RadioButton) findViewById(R.id.stopServiceButton2);
		stopServiceButton2.setOnClickListener(new StopServiceListener2());

		backButton = (Button) findViewById(R.id.backButton);
		backButton.setOnClickListener(new BackButtonListener());

		clickGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						if (startServiceButton.getId() == checkedId) {
							System.out.println("on");
							Toast.makeText(TestActivity.this, "打开定位服务",
									Toast.LENGTH_SHORT).show();
						} else if (stopServiceButton.getId() == checkedId) {
							System.out.println("off");
							Toast.makeText(TestActivity.this, "关闭定位服务",
									Toast.LENGTH_SHORT).show();
						}
					}
				});

		clickGroup1
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						if (startServiceButton1.getId() == checkedId) {
							System.out.println("on");
							Toast.makeText(TestActivity.this, "打开疑似跌倒报警",
									Toast.LENGTH_SHORT).show();
						} else if (stopServiceButton1.getId() == checkedId) {
							System.out.println("off");
							Toast.makeText(TestActivity.this, "关闭疑似跌倒报警",
									Toast.LENGTH_SHORT).show();
						}
					}
				});

		clickGroup2
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						if (startServiceButton2.getId() == checkedId) {
							System.out.println("on");
							Toast.makeText(TestActivity.this, "打开疑似静默报警",
									Toast.LENGTH_SHORT).show();
						} else if (stopServiceButton2.getId() == checkedId) {
							System.out.println("off");
							Toast.makeText(TestActivity.this, "关闭疑似静默报警",
									Toast.LENGTH_SHORT).show();
						}
					}
				});

		System.out.println("Activity onCreate");
	}

	class StartServiceListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(TestActivity.this, FirstService.class);
			Intent intentTemp = new Intent("org.cct.home.location.FirstService");
			startService(intentTemp);
			// startService(intent);
		}

	}

	class StopServiceListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(TestActivity.this, FirstService.class);
			stopService(intent);

		}

	}

	class StartServiceListener1 implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			System.out.println("yes");
			Intent intent = new Intent();
			intent.setClass(TestActivity.this, ThirdService.class);
			Intent intentTemp = new Intent("org.cct.home.location.ThirdService");
			startService(intentTemp);

			// startService(intent);
		}

	}

	class StopServiceListener1 implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			Intent intent = new Intent();
			intent.setClass(TestActivity.this, ThirdService.class);
			stopService(intent);

		}

	}

	class StartServiceListener2 implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(TestActivity.this, SilenceJudgeService.class);
			Intent intent1 = new Intent();
			intent1.setClass(TestActivity.this, SilenceUpdateService.class);
			Intent intentTemp = new Intent("org.cct.home.location.SilenceJudgeService");
			Intent intentTemp2 = new Intent("org.cct.home.location.SilenceUpdateService");			
			startService(intentTemp);
			startService(intentTemp2);
		}

	}

	class StopServiceListener2 implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(TestActivity.this, SilenceJudgeService.class);
			Intent intent1 = new Intent(TestActivity.this, SilenceUpdateService.class);
			stopService(intent);
			stopService(intent1);
		}

	}

	class BackButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(TestActivity.this, home.class);
			startActivity(intent);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
