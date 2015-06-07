package org.cct.home.use;

import java.sql.Timestamp;

import org.cct.home.R;

import com.org.ifamily.entity.Host;
import com.org.ifamily.entity.Oncereminder;
import com.org.ifamily.implement.Oncereminderimpl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class OnceRemindEdit extends Activity {

	private EditText repeat_remind;
	private EditText repeat_remindtime;
	private RadioGroup radioGroup;
	private RadioButton able;
	private RadioButton unable;
	private Button sure;
	String remindcontext;
	String remindtime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.use_3_2edit);
		
		ImageButton button = (ImageButton) findViewById(R.id.imageButton_use_3_2_edit);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 按钮按下，将抽屉打开
				OnceRemindEdit.this.finish();

			}
		});
		
		repeat_remind = (EditText) findViewById(R.id.use_3_2_edit_repeat_remind);
		repeat_remindtime = (EditText) findViewById(R.id.use_3_2_edit_repeat_starttime);
		radioGroup = (RadioGroup) findViewById(R.id.use_3_2_edit_nowstate);
		able = (RadioButton) findViewById(R.id.use_3_2_edit_disposable_useable);
		unable = (RadioButton) findViewById(R.id.use_3_2_edit_disposable_useunable);
		sure = (Button) findViewById(R.id.use_3_2_edit_use_3_2sure);
		
		Intent intent = getIntent();
		final Host host = (Host) intent.getSerializableExtra("HOSTSURE");
		final Oncereminderimpl oncereminderimpl = new Oncereminderimpl();
		final Oncereminder oncereminder = (Oncereminder) intent.getSerializableExtra("ONCEREMINDER");
		
		repeat_remind.setText(oncereminder.getContent() + "");
		repeat_remindtime.setText(oncereminder.getTime() + "");
		able.setChecked(oncereminder.getState());
		unable.setChecked(!oncereminder.getState());
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.use_3_2_edit_disposable_useable) {
					oncereminder.setState(true);
				}
				else if(checkedId == R.id.use_3_2_edit_disposable_useunable) {
					oncereminder.setState(false);
				}
			}
		});
		
		sure.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				remindcontext = repeat_remind.getText().toString().trim();
				remindtime = repeat_remindtime.getText().toString().trim();
				
				Timestamp timestamp = Timestamp.valueOf(remindtime);
				
				oncereminder.setContent(remindcontext);
				oncereminder.setTime(timestamp);
				
				oncereminderimpl.update(oncereminder);
				
				Intent intent = new Intent(OnceRemindEdit.this, OnceRemind.class);
				intent.putExtra("HOSTSURE", host);
				OnceRemindEdit.this.startActivity(intent);
				OnceRemindEdit.this.finish();
			}
		});
	}

}
