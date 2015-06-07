package org.cct.home.use;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;

import org.cct.home.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.org.ifamily.entity.Cyclereminder;
import com.org.ifamily.entity.Host;
import com.org.ifamily.implement.Cyclereminderimpl;

public class CycleRemindAdd extends Activity {

	private EditText repeat_remind;
	private EditText repeat_intervaltime;
	private RadioGroup radioGroup;
	private Button use_3_1sure;
	private EditText year;
	private EditText month;
	private EditText day;
	private EditText hour;
	private EditText minute;
	private EditText second;
	private Button yearup, monthup, dayup, hourup, minuteup, secondup,
			yeardown, monthdown, daydown, hourdown, minutedown, seconddown;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.use_3_1add);

		Intent intent = getIntent();
		final Host host = (Host) intent.getSerializableExtra("HOSTSURE");

		use_3_1sure = (Button) findViewById(R.id.use_3_1sure);
		repeat_remind = (EditText) findViewById(R.id.repeat_remind);
		repeat_intervaltime = (EditText) findViewById(R.id.repeat_intervaltime);
		radioGroup = (RadioGroup) findViewById(R.id.nowstate);
		year = (EditText) findViewById(R.id.use_3_1add_year);
		month = (EditText) findViewById(R.id.use_3_1add_month);
		day = (EditText) findViewById(R.id.use_3_1add_day);
		hour = (EditText) findViewById(R.id.use_3_1add_hour);
		minute = (EditText) findViewById(R.id.use_3_1add_minute);
		second = (EditText) findViewById(R.id.use_3_1add_second);
		yearup = (Button) findViewById(R.id.use_3_1add_yearup);
		monthup = (Button) findViewById(R.id.use_3_1add_monthup);
		dayup = (Button) findViewById(R.id.use_3_1add_dayup);
		hourup = (Button) findViewById(R.id.use_3_1add_hourup);
		minuteup = (Button) findViewById(R.id.use_3_1add_minuteup);
		secondup = (Button) findViewById(R.id.use_3_1add_secondup);
		yeardown = (Button) findViewById(R.id.use_3_1add_yeardown);
		monthdown = (Button) findViewById(R.id.use_3_1add_monthdown);
		daydown = (Button) findViewById(R.id.use_3_1add_daydown);
		hourdown = (Button) findViewById(R.id.use_3_1add_hourdown);
		minutedown = (Button) findViewById(R.id.use_3_1add_minutedown);
		seconddown = (Button) findViewById(R.id.use_3_1add_seconddown);

		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		int iyear = calendar.get(Calendar.YEAR);
		int imonth = calendar.get(Calendar.MONTH) + 1;
		int iday = calendar.get(Calendar.DAY_OF_MONTH);
		int ihour = calendar.get(Calendar.HOUR_OF_DAY);
		int iminute = calendar.get(Calendar.MINUTE);
		int isecond = calendar.get(Calendar.SECOND);
		year.setText(iyear + "");
		month.setText(imonth + "");
		day.setText(iday + "");
		hour.setText(ihour + "");
		minute.setText(iminute + "");
		second.setText(isecond + "");

		yearup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int myYear = Integer.parseInt(year.getText().toString().trim());
				myYear++;
				year.setText(myYear + "");
			}
		});
		yeardown.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int myYear = Integer.parseInt(year.getText().toString().trim());
				myYear--;
				year.setText(myYear + "");
			}
		});
		monthup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int myMonth = Integer.parseInt(month.getText().toString()
						.trim());
				myMonth++;
				if (myMonth > 12) {
					myMonth = 1;
				}
				month.setText(myMonth + "");
			}
		});
		monthdown.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int myMonth = Integer.parseInt(month.getText().toString()
						.trim());
				myMonth--;
				if (myMonth < 1) {
					myMonth = 12;
				}
				month.setText(myMonth + "");
			}
		});
		dayup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int myDay = Integer.parseInt(day.getText().toString().trim());
				myDay++;
				if (myDay > 31) {
					myDay = 1;
				}
				day.setText(myDay + "");
			}
		});
		daydown.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int myDay = Integer.parseInt(day.getText().toString().trim());
				myDay--;
				if (myDay < 1) {
					myDay = 31;
				}
				day.setText(myDay + "");
			}
		});
		hourup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int myHour = Integer.parseInt(hour.getText().toString().trim());
				myHour++;
				if (myHour > 23) {
					myHour = 0;
				}
				hour.setText(myHour + "");
			}
		});
		hourdown.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int myHour = Integer.parseInt(hour.getText().toString().trim());
				myHour--;
				if (myHour < 0) {
					myHour = 23;
				}
				hour.setText(myHour + "");
			}
		});
		minuteup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int myMinute = Integer.parseInt(minute.getText().toString()
						.trim());
				myMinute++;
				if (myMinute > 59) {
					myMinute = 0;
				}
				minute.setText(myMinute + "");
			}
		});
		minutedown.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int myMinute = Integer.parseInt(minute.getText().toString()
						.trim());
				myMinute--;
				if (myMinute < 0) {
					myMinute = 59;
				}
				minute.setText(myMinute + "");
			}
		});
		secondup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int mySecond = Integer.parseInt(second.getText().toString()
						.trim());
				mySecond++;
				if (mySecond > 59) {
					mySecond = 0;
				}
				second.setText(mySecond + "");
			}
		});
		seconddown.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int mySecond = Integer.parseInt(second.getText().toString()
						.trim());
				mySecond--;
				if (mySecond < 0) {
					mySecond = 59;
				}
				second.setText(mySecond + "");
			}
		});

		final Cyclereminderimpl cyclereminderimpl = new Cyclereminderimpl();
		final Cyclereminder cyclereminder = new Cyclereminder();

		cyclereminder.setState(false);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.disposable_useable) {
					cyclereminder.setState(true);
				} else if (checkedId == R.id.disposable_useunable) {
					cyclereminder.setState(false);
				}
			}
		});

		use_3_1sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String syear = year.getText().toString().trim();
				String smonth;
				String sday;
				String shour;
				String sminute;
				String ssecond;
				int month1 = Integer.parseInt(month.getText().toString().trim());
				if (month1 < 10) {
					smonth = "0" + month1;
				} else {
					smonth = "" + month1;
				}
				int day1 = Integer.parseInt(day.getText().toString().trim());
				if (day1 < 10) {
					sday = "0" + day1;
				} else {
					sday = "" + day1;
				}

				int hour1 = Integer.parseInt(hour.getText().toString().trim());
				if (hour1 < 10) {
					shour = "0" + hour1;
				} else {
					shour = "" + hour1;
				}
				int minute1 = Integer
						.parseInt(minute.getText().toString().trim());
				if (minute1 < 10) {
					sminute = "0" + minute1;
				} else {
					sminute = "" + minute1;
				}
				int second1 = Integer
						.parseInt(second.getText().toString().trim());
				if (second1 < 10) {
					ssecond = "0" + second1 + ".0";
				} else {
					ssecond = "" + second1 + ".0";
				}
				String repeatremind = repeat_remind.getText().toString().trim();
				String repeatstarttime = syear + "-" + smonth + "-" + sday
						+ " " + shour + ":" + sminute + ":" + ssecond;
				String repeatintervaltime = repeat_intervaltime.getText()
						.toString().trim();

				if ((!repeatremind.equals(""))
						&& (!repeatintervaltime.equals(""))) {
					Timestamp timestamp = Timestamp.valueOf(repeatstarttime);

					int between = Integer.parseInt(repeatintervaltime);

					cyclereminder.setContent(repeatremind);
					cyclereminder.setStarttime(timestamp);
					cyclereminder.setBetween(between);
					cyclereminder.setHostnum(host.getHostnum());

					cyclereminderimpl.add(cyclereminder);
					if (cyclereminderimpl.update(cyclereminder) != null) {
						Toast.makeText(CycleRemindAdd.this, "添加成功",
								Toast.LENGTH_LONG).show();
					}

					Intent intent = new Intent();
					intent.setClass(CycleRemindAdd.this, CycleRemind.class);
					intent.putExtra("HOSTSURE", host);
					CycleRemindAdd.this.startActivity(intent);
					CycleRemindAdd.this.finish();
				} else {
					Toast.makeText(CycleRemindAdd.this, "填写有误,请重试!",
							Toast.LENGTH_LONG).show();
				}
			}
		});
		ImageButton button = (ImageButton) findViewById(R.id.imageButton_use_3_1_add);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 返回上一级
				CycleRemindAdd.this.finish();
				//mDrawerLayout.openDrawer(Gravity.LEFT);

			}
		});
	}
	

}
