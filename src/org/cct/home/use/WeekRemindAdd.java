package org.cct.home.use;

import org.cct.home.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

import com.org.ifamily.entity.Host;
import com.org.ifamily.entity.Weekreminder;
import com.org.ifamily.implement.Weekreminderimpl;

public class WeekRemindAdd extends Activity {

	private EditText weekormonth_remind;
	private EditText weekormonth_remindtime;
	private Spinner repeattype;
	private Spinner repeattime;
	private RadioGroup radioGroup;
	private Button use_3_3sure;
	Integer selectId;
	Integer weekId;
	Integer monthId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.use_3_3add);

		Intent intent = getIntent();
		final Host host = (Host) intent.getSerializableExtra("HOSTSURE");

		use_3_3sure = (Button) findViewById(R.id.use_3_3_add_sure);
		repeattype = (Spinner) findViewById(R.id.use_3_3add_sp1);
		repeattime = (Spinner) findViewById(R.id.use_3_3add_sp2);
		radioGroup = (RadioGroup) findViewById(R.id.use_3_3_add_nowstate);
		weekormonth_remind = (EditText) findViewById(R.id.use_3_3_add_repeat_remind);
		weekormonth_remindtime = (EditText) findViewById(R.id.use_3_3_add_repeat_starttime);

		final Weekreminderimpl weekreminderimpl = new Weekreminderimpl();
		final Weekreminder weekreminder = new Weekreminder();
		
		ImageButton button = (ImageButton) findViewById(R.id.imageButton_use_3_3_add);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 返回上一级
				WeekRemindAdd.this.finish();
				//mDrawerLayout.openDrawer(Gravity.LEFT);

			}
		});
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.use_3_3_add_disposable_useable) {
					weekreminder.setState(true);
				} else if (checkedId == R.id.use_3_3_add_disposable_useunable) {
					weekreminder.setState(false);
				}
			}
		});

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.types, android.R.layout.simple_spinner_item);
		repeattype.setAdapter(adapter);
		repeattype.setPrompt("选择重复类型");
		repeattype.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				selectId = repeattype.getSelectedItemPosition();
				switch (selectId) {
				case 0:
					weekreminder.setRepeatstyle(false);
					ArrayAdapter<CharSequence> adapter = ArrayAdapter
							.createFromResource(WeekRemindAdd.this, R.array.week,
									android.R.layout.simple_spinner_item);
					repeattime.setAdapter(adapter);
					repeattime.setPrompt("选择重复时间");
					repeattime
							.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									weekId = repeattime
											.getSelectedItemPosition();
									weekreminder.setRepeatday(weekId + 1);
								}

								@Override
								public void onNothingSelected(
										AdapterView<?> arg0) {
									// TODO Auto-generated method stub

								}
							});
					break;

				case 1:
					weekreminder.setRepeatstyle(true);
					ArrayAdapter<CharSequence> adapter2 = ArrayAdapter
							.createFromResource(WeekRemindAdd.this, R.array.month,
									android.R.layout.simple_spinner_item);
					repeattime.setAdapter(adapter2);
					repeattime.setPrompt("选择重复时间");
					repeattime
							.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									monthId = repeattime
											.getSelectedItemPosition();
									weekreminder.setRepeatday(monthId + 1);
								}

								@Override
								public void onNothingSelected(
										AdapterView<?> arg0) {
									// TODO Auto-generated method stub

								}
							});
					break;

				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		use_3_3sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String weekormonthremind = weekormonth_remind.getText()
						.toString();
				String weekormonthremindtime = weekormonth_remindtime.getText()
						.toString();

				weekreminder.setContent(weekormonthremind);
				weekreminder.setTime(weekormonthremindtime);
				weekreminder.setHostnum(host.getHostnum());

				weekreminderimpl.add(weekreminder);
				if (weekreminderimpl.update(weekreminder) != null) {
					Toast.makeText(WeekRemindAdd.this, "添加成功", Toast.LENGTH_LONG)
							.show();
				}

				Intent intent = new Intent();
				intent.setClass(WeekRemindAdd.this, WeekRemind.class);
				intent.putExtra("HOSTSURE", host);
				WeekRemindAdd.this.startActivity(intent);
				WeekRemindAdd.this.finish();
			};
		});

	}

}
