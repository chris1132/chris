package org.cct.home.sos;





import org.cct.home.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class Sos extends Activity {


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sos);
		Intent intent = new Intent();
		intent.setClass(Sos.this, SosService.class);
		Intent intentTemp = new Intent("org.cct.home.sos.SosService");
		startService(intentTemp);    
//		clickGroup = (RadioGroup)findViewById(R.id.clickGroup);
//		startServiceButton = (RadioButton)findViewById(R.id.startServiceButton);
//		startServiceButton.setOnClickListener(new StartServiceListener());
//		stopServiceButton = (RadioButton)findViewById(R.id.stopServiceButton);
//		stopServiceButton.setOnClickListener(new StopServiceListener());
//		clickGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//			
//			@Override
//			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				// TODO Auto-generated method stub
//				if(startServiceButton.getId()==checkedId)
//				{
//					System.out.println("on");
//					Toast.makeText(TestActivity.this,"on", Toast.LENGTH_SHORT).show();
//				}
//				else if(stopServiceButton.getId()==checkedId)
//				{
//					System.out.println("off");
//					Toast.makeText(TestActivity.this,"off", Toast.LENGTH_SHORT).show();
//				}
//			}
//		});
//		System.out.println("Activity onCreate");
	}
	
//	class StartServiceListener implements OnClickListener{
//
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			Intent intent = new Intent();
//			intent.setClass(TestActivity.this, FirstService.class);
//			Intent intentTemp = new Intent("com.example.testservice.FirstService");
//			startService(intentTemp);
////			startService(intent);
//		}
//		
//	}
//	class StopServiceListener implements OnClickListener{
//
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			Intent intent = new  Intent();
//			intent.setClass(TestActivity.this, FirstService.class);
//			stopService(intent);
//		}
//		
//	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.test, menu);
//		return true;
//	}

}
