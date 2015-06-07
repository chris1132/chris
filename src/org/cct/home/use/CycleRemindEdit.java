package org.cct.home.use;

import java.sql.Timestamp;

import org.cct.home.R;

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

import com.org.ifamily.entity.Cyclereminder;
import com.org.ifamily.entity.Host;
import com.org.ifamily.implement.Cyclereminderimpl;

public class CycleRemindEdit extends Activity {

	private EditText repeat_remind;
	private EditText repeat_starttime;
	private EditText repeat_intervaltime;
	private RadioGroup radioGroup;
	private RadioButton able;
	private RadioButton unable;
	private Button sure;
	String remindcontent;
	String remindstarttime;
	String remindbewteen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.use_3_1edit);
		
		ImageButton button = (ImageButton) findViewById(R.id.imageButton_use_3_1_edit);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				CycleRemindEdit.this.finish();

			}
		});
		
		repeat_remind = (EditText) findViewById(R.id.use_3_1_edit_repeat_remind);
		repeat_starttime = (EditText) findViewById(R.id.use_3_1_edit_repeat_starttime);
		repeat_intervaltime = (EditText) findViewById(R.id.use_3_1_edit_repeat_intervaltime);
		radioGroup = (RadioGroup) findViewById(R.id.use_3_1_edit_nowstate);
		able = (RadioButton) findViewById(R.id.use_3_1_edit_disposable_useable);
		unable = (RadioButton) findViewById(R.id.use_3_1_edit_disposable_useunable);
		sure = (Button) findViewById(R.id.use_3_1_edit_use_3_1sure);
		
		Intent intent = getIntent();
		final Host host = (Host) intent.getSerializableExtra("HOSTSURE");
		final Cyclereminderimpl cyclereminderimpl = new Cyclereminderimpl();
		final Cyclereminder cyclereminder = (Cyclereminder) intent.getSerializableExtra("CYCLEREMEMBER");
		
		repeat_remind.setText(cyclereminder.getContent() + "");
		repeat_starttime.setText(cyclereminder.getStarttime() + "");
		repeat_intervaltime.setText(cyclereminder.getBetween() + "");
		able.setChecked(cyclereminder.getState());
		unable.setChecked(!cyclereminder.getState());
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.use_3_1_edit_disposable_useable) {
					cyclereminder.setState(true);
				}
				else if(checkedId == R.id.use_3_1_edit_disposable_useunable) {
					cyclereminder.setState(false);
				}
			}
		});
		
		sure.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				remindcontent = repeat_remind.getText().toString().trim();
				remindstarttime = repeat_starttime.getText().toString().trim();
				remindbewteen = repeat_intervaltime.getText().toString().trim();
				
				Timestamp timestamp = Timestamp.valueOf(remindstarttime);

				int between = Integer.parseInt(remindbewteen);
				
				cyclereminder.setContent(remindcontent);
				cyclereminder.setStarttime(timestamp);
				cyclereminder.setBetween(between);
				
				cyclereminderimpl.update(cyclereminder);
				
				Intent intent = new Intent(CycleRemindEdit.this, CycleRemind.class);
				intent.putExtra("HOSTSURE", host);
				CycleRemindEdit.this.startActivity(intent);
				CycleRemindEdit.this.finish();
			}
		});
	}

}
