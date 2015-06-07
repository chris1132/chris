package org.cct.home.use;

import java.util.HashMap;
import java.util.Map;

import org.cct.home.R;
import org.cct.home.savecontext.SaveService;

import com.org.ifamily.entity.Host;
import com.org.ifamily.entity.Locsetting;
import com.org.ifamily.implement.Locsettingimpl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SilenceAlertSet extends Activity {

	private ImageButton button;
	private Button btnsure;
	private EditText starttime;
	private EditText endtime;
	private EditText silencetime;
	private String start;
	private String end;
	private String silence;
	private boolean flag;
	private SaveService service;
	private Locsettingimpl locsettingimpl;
	private Locsetting locsetting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.use_2_4_set);

		Intent intent = getIntent();
		final Host host = (Host) intent.getSerializableExtra("HOSTSURE");
		
		button = (ImageButton) findViewById(R.id.imageButton_use_2_4_set);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SilenceAlertSet.this.finish();
			}
		});

		service = new SaveService(this);

		starttime = (EditText) findViewById(R.id.use_2_4_starttime);
		endtime = (EditText) findViewById(R.id.use_2_4_endtime);
		silencetime = (EditText) findViewById(R.id.use_2_4_silencetime);
		
		locsettingimpl = new Locsettingimpl();
		locsetting = locsettingimpl.getdeviceconf(host.getHostnum());

		btnsure = (Button) findViewById(R.id.use_2_4_setsure);
		btnsure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				start = starttime.getText().toString().trim();
				end = endtime.getText().toString().trim();
				silence = silencetime.getText().toString().trim();
				flag = start.matches("[0-9]+") && end.matches("[0-9]+")
						&& silence.matches("[0-9]+");
				if (flag) {
					locsetting.setSliencestarttime(Integer.parseInt(start));
					locsetting.setSlienceendtime(Integer.parseInt(end));
					locsetting.setSlienceinterval(Integer.parseInt(silence));
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("sliencestarttime", start);
					map.put("slienceendtime", end);
					map.put("slienceinterval", silence);
					if (service.saveSharePreference("deviceconf", map)) {
						Toast.makeText(SilenceAlertSet.this, "设置成功",
								Toast.LENGTH_LONG).show();
						SilenceAlertSet.this.finish();
					} else {
						Toast.makeText(SilenceAlertSet.this, "设置失败,请重试",
								Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(SilenceAlertSet.this, "输入有误,请重新输入",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

}
