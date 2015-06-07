package org.cct.home.worker;

import org.cct.home.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class WorkerInfomation extends Activity {

	private ImageButton back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workerinfo);

		back = (ImageButton) findViewById(R.id.imageButton_use_7_1);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(WorkerInfomation.this, WorkerHome.class);
				startActivity(intent);
				WorkerInfomation.this.finish();
			}
		});
	}

}
