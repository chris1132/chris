package org.cct.home.use;

import org.cct.home.R;

import com.org.ifamily.entity.Host;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class WarnRecord extends Activity {
	
	ImageButton buttonBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_2);
		Button button1 = (Button) findViewById(R.id.newbtn21);
		Button button2 = (Button) findViewById(R.id.newbtn22);
		Button button3 = (Button) findViewById(R.id.newbtn23);
		Button button4 = (Button) findViewById(R.id.newbtn24);
		buttonBack = (ImageButton)findViewById(R.id.imageButton_usehome);
		
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent1 = getIntent();
				final Host host = (Host) intent1
						.getSerializableExtra("HOSTSURE");
				if (host == null) {
					Toast.makeText(WarnRecord.this, "请先选择对象", Toast.LENGTH_LONG)
							.show();
				} else {
					Intent intent = new Intent();
					intent.setClass(WarnRecord.this, SosAlert.class);
					intent.putExtra("HOSTSURE", host);
					startActivity(intent);
				}
			}
		});
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent1 = getIntent();
				final Host host = (Host) intent1
						.getSerializableExtra("HOSTSURE");
				if (host == null) {
					Toast.makeText(WarnRecord.this, "请先选择对象", Toast.LENGTH_LONG)
							.show();
				} else {
					Intent intent = new Intent();
					intent.setClass(WarnRecord.this, TempreAlert.class);
					intent.putExtra("HOSTSURE", host);
					startActivity(intent);
				}
			}
		});

		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent1 = getIntent();
				final Host host = (Host) intent1
						.getSerializableExtra("HOSTSURE");
				if (host == null) {
					Toast.makeText(WarnRecord.this, "请先选择对象", Toast.LENGTH_LONG)
							.show();
				} else {
					Intent intent = new Intent();
					intent.setClass(WarnRecord.this, FallAlert.class);
					intent.putExtra("HOSTSURE", host);
					startActivity(intent);
				}
			}
		});
		
		button4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = getIntent();
				final Host host = (Host) intent1
						.getSerializableExtra("HOSTSURE");
				if (host == null) {
					Toast.makeText(WarnRecord.this, "请先选择对象", Toast.LENGTH_LONG)
							.show();
				} else {
					Intent intent = new Intent();
					intent.setClass(WarnRecord.this, SilenceAlert.class);
					intent.putExtra("HOSTSURE", host);
					startActivity(intent);
				}
			}
		});
		
		buttonBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WarnRecord.this.finish();
			}
		});
	}
}
