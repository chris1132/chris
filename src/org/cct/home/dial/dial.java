package org.cct.home.dial;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class dial extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Uri uri = Uri.parse("tel:6581890");   
		 
		Intent intent = new Intent(Intent.ACTION_DIAL, uri);     
		 
		startActivity(intent);  
		dial.this.finish();
	}
	
}
