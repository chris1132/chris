package org.cct.home.worker;

import org.cct.home.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class WorkerHome extends Activity {

	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;
	private Button btn5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workerhome);

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_workerhome);
		btn1 = (Button) findViewById(R.id.worker_btn1);
		btn2 = (Button) findViewById(R.id.worker_btn2);
		btn3 = (Button) findViewById(R.id.worker_btn3);
		btn4 = (Button) findViewById(R.id.worker_btn4);
		btn5 = (Button) findViewById(R.id.worker_btn5);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(WorkerHome.this, ListTest.class);
				startActivity(intent);
				WorkerHome.this.finish();
			}
		});

		btn1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(WorkerHome.this, WorkerInfomation.class);
				startActivity(intent);
				finish();
			}
		});

		btn2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(WorkerHome.this, WorkerCome.class);
				startActivity(intent);
				finish();
			}
		});

		btn3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(WorkerHome.this, WorkerBack.class);
				startActivity(intent);
				finish();
			}
		});

		btn4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(WorkerHome.this, WorkerPswdChange.class);
				startActivity(intent);
				finish();
			}
		});

		btn5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(WorkerHome.this, WorkerLogin.class);
				startActivity(intent);
				finish();
			}
		});
	}

}
