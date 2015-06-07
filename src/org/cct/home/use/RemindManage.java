package org.cct.home.use;

import org.cct.home.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.org.ifamily.entity.Host;

public class RemindManage extends Activity{
	private ImageButton buttonBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_1);
		 buttonBack = (ImageButton)findViewById(R.id.imageButton_usehome);
		Button button1 = (Button) findViewById(R.id.newbtn1);
		Button button2 = (Button) findViewById(R.id.newbtn2);
		Button button3 = (Button) findViewById(R.id.newbtn3);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent1 = getIntent();
				final Host host = (Host) intent1
						.getSerializableExtra("HOSTSURE");
				if (host == null) {
					Toast.makeText(RemindManage.this, "请先选择对象", Toast.LENGTH_LONG)
							.show();
				} else {
					Intent intent = new Intent();
					intent.setClass(RemindManage.this, CycleRemind.class);
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
					Toast.makeText(RemindManage.this, "请先选择对象", Toast.LENGTH_LONG)
							.show();
				} else {
					Intent intent = new Intent();
					intent.setClass(RemindManage.this, OnceRemind.class);
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
					Toast.makeText(RemindManage.this, "请先选择对象", Toast.LENGTH_LONG)
							.show();
				} else {
					Intent intent = new Intent();
					intent.setClass(RemindManage.this, WeekRemind.class);
					intent.putExtra("HOSTSURE", host);
					startActivity(intent);
				}
			}
		});
		buttonBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RemindManage.this.finish();
			}
		});
		
		
	}


}
