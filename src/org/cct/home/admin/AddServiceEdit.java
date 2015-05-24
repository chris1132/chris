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

public class AddServiceEdit extends Activity {

	private EditText editywname;
	private EditText editsf;
	private EditText editbz;
	private EditText editywNum;
	private Button czbtn;
	String name;
	String cost;
	String remarks;
	String chargecode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminaddedit_all);

		ImageButton buttonBack = (ImageButton) findViewById(R.id.imageButton_adminaddedit_all);
		buttonBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddServiceEdit.this.finish();
			}
		});
		Intent intent = getIntent();
		final Servicesetimpl servicesetimpl = new Servicesetimpl();
		final Serviceset serviceset = (Serviceset) intent
				.getSerializableExtra("SERVICE");

		editywname = (EditText) findViewById(R.id.editywname);
		editsf = (EditText) findViewById(R.id.editsf);
		editbz = (EditText) findViewById(R.id.editbz);
		editywNum = (EditText) findViewById(R.id.editywNum);
		czbtn = (Button) findViewById(R.id.czbtn);

		editywname.setText(serviceset.getName());
		editsf.setText(serviceset.getCost());
		editbz.setText(serviceset.getRemarks());
		editywNum.setText(serviceset.getChargecode());

		czbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				name = editywname.getText().toString().trim();
				cost = editsf.getText().toString().trim();
				remarks = editbz.getText().toString().trim();
				chargecode = editywNum.getText().toString().trim();
				serviceset.setName(name);
				serviceset.setCost(cost);
				serviceset.setRemarks(remarks);
				serviceset.setChargecode(chargecode);

				servicesetimpl.update(serviceset);

				Intent intent = new Intent(AddServiceEdit.this,
						Admin1Service.class);
				AddServiceEdit.this.startActivity(intent);
				AddServiceEdit.this.finish();
			}
		});
	}

}
