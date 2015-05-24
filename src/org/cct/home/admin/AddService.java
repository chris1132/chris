package org.cct.home.admin;

import org.cct.home.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.org.ifamily.entity.Serviceset;
import com.org.ifamily.implement.Servicesetimpl;

public class AddService extends Activity {

	private EditText edname;
	private EditText edfee;
	private EditText edbeizhu;
	private EditText ednum;
	private Button ok;

	String name;
	String cost;
	String remarks;
	String chargecode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addservice_all);

		edname = (EditText) findViewById(R.id.edityewuname);
		edfee = (EditText) findViewById(R.id.editshoufeibiaozhun);
		edbeizhu = (EditText) findViewById(R.id.editbeizhu);
		ednum = (EditText) findViewById(R.id.edityewuNum);
		ok = (Button) findViewById(R.id.caozuobtn);

		ImageButton buttonBack = (ImageButton) findViewById(R.id.imageButton_addservice);

		buttonBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddService.this.finish();
			}
		});
		final Servicesetimpl servicesetimpl = new Servicesetimpl();

		ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				name = edname.getText().toString().trim();
				cost = edfee.getText().toString().trim();
				remarks = edbeizhu.getText().toString().trim();
				chargecode = ednum.getText().toString().trim();

				Serviceset serviceset = new Serviceset();
				serviceset.setName(name);
				serviceset.setCost(cost);
				serviceset.setRemarks(remarks);
				serviceset.setChargecode(chargecode);
				servicesetimpl.add(serviceset);
				servicesetimpl.update(serviceset);

				Intent intent = new Intent();
				intent.setClass(AddService.this, Admin1Service.class);
				AddService.this.startActivity(intent);
				AddService.this.finish();
			}
		});
	}

}
