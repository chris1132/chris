package org.cct.home.use;

import org.cct.home.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

import com.org.ifamily.entity.Host;
import com.org.ifamily.entity.Weekreminder;
import com.org.ifamily.implement.Weekreminderimpl;

public class WeekRemindEdit extends Activity {

	private EditText weekormonth_remind;
	private EditText weekormonth_remindtime;
	private Spinner repeattype;
	private Spinner repeattime;
	private RadioGroup radioGroup;
	private RadioButton able;
	private RadioButton unable;
	private Button use_3_3_edit_sure;
	String remindcontext;
	String remindtime;
	Integer selectId;
	Integer weekId;
	Integer monthId;
	boolean position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.use_3_3edit);
		
		ImageButton button = (ImageButton) findViewById(R.id.imageButton_use_3_3_edit);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 按钮按下，将抽屉打开
				WeekRemindEdit.this.finish();

			}
		});

		Intent intent = getIntent();
		final Host host = (Host) intent.getSerializableExtra("HOSTSURE");

		final Weekreminderimpl weekreminderimpl = new Weekreminderimpl();
		final Weekreminder weekreminder = (Weekreminder) intent
				.getSerializableExtra("WEEKREMINDER");

		repeattype = (Spinner) findViewById(R.id.use_3_3edit_sp1);
		repeattime = (Spinner) findViewById(R.id.use_3_3edit_sp2);
		radioGroup = (RadioGroup) findViewById(R.id.use_3_3_edit_nowstate);
		able = (RadioButton) findViewById(R.id.use_3_3_edit_disposable_useable);
		unable = (RadioButton) findViewById(R.id.use_3_3_edit_disposable_useunable);
		weekormonth_remind = (EditText) findViewById(R.id.use_3_3_edit_repeat_remind);
		weekormonth_remindtime = (EditText) findViewById(R.id.use_3_3_edit_repeat_starttime);
		use_3_3_edit_sure = (Button) findViewById(R.id.use_3_3_edit_sure);

		weekormonth_remind.setText(weekreminder.getContent() + "");
		weekormonth_remindtime.setText(weekreminder.getTime() + "");

		able.setChecked(weekreminder.getState());
		unable.setChecked(!weekreminder.getState());

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.use_3_3_edit_disposable_useable) {
					weekreminder.setState(true);
				} else if (checkedId == R.id.use_3_3_edit_disposable_useunable) {
					weekreminder.setState(false);
				}
			}
		});

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.types, android.R.layout.simple_spinner_item);
		repeattype.setAdapter(adapter);
		position = weekreminder.getRepeatstyle();
		if (!position) {
			repeattype.setSelection(0);
			ArrayAdapter<CharSequence> adapter1 = ArrayAdapter
					.createFromResource(WeekRemindEdit.this, R.array.week,
							android.R.layout.simple_spinner_item);
			repeattime.setAdapter(adapter1);
			int myWeekId = weekreminder.getRepeatday() - 1;
			repeattime.setSelection(myWeekId);
		} else {
			repeattype.setSelection(1);
			ArrayAdapter<CharSequence> adapter2 = ArrayAdapter
					.createFromResource(WeekRemindEdit.this, R.array.month,
							android.R.layout.simple_spinner_item);
			repeattime.setAdapter(adapter2);
			int myMonthId = weekreminder.getRepeatday() - 1;
			repeattime.setSelection(myMonthId);
		}
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
							.createFromResource(WeekRemindEdit.this, R.array.week,
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
							.createFromResource(WeekRemindEdit.this,
									R.array.month,
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

		use_3_3_edit_sure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				remindcontext = weekormonth_remind.getText().toString().trim();
				remindtime = weekormonth_remindtime.getText().toString().trim();

				weekreminder.setContent(remindcontext);
				weekreminder.setTime(remindtime);

				weekreminderimpl.update(weekreminder);

				Intent intent = new Intent(WeekRemindEdit.this, WeekRemind.class);
				intent.putExtra("HOSTSURE", host);
				WeekRemindEdit.this.startActivity(intent);
				WeekRemindEdit.this.finish();
			}
		});
	}

}
