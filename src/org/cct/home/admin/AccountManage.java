package org.cct.home.admin;

import org.cct.home.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class AccountManage extends Activity {
	private Button btn2;
	private Button btn3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome4_new_account);
		ImageButton buttonBack = (ImageButton) findViewById(R.id.imageButton_adminhome4_new_account);
		btn2 = (Button) findViewById(R.id.admin4_new_a_btn2);
		btn3 = (Button) findViewById(R.id.admin4_new_a_btn3);
		buttonBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AccountManage.this.finish();
			}
		});
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(AccountManage.this,
						Admin4Infomation.class);
				startActivity(intent);
			}
		});
		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(AccountManage.this,
						Admin4PasswordChange.class);
				startActivity(intent);
			}
		});

	}
}