package org.cct.home.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.cct.home.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.org.ifamily.entity.Countyinfo;
import com.org.ifamily.implement.Countyinfoimpl;

public class UnicomSaleQuery extends Activity {
	private long areaid;
	private Button admin2manage1btn;
	private Spinner admin2manage1spinner1;
	private Spinner admin2manage1spinner2;
	private Spinner admin2manage1spinner3;
	private Countyinfoimpl countyinfoimpl = null;
	private List<Countyinfo> list_area = null;
	private List<Countyinfo> list_area2 = null;
	private ArrayAdapter<String> city_adapter;
	private ArrayAdapter<String> county_adapter;
	private long Id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome2_5and6_1);
		

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_adminhome2_5and6_1);
		button.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				UnicomSaleQuery.this.finish();

			}
		});
		
		TextView text = (TextView)findViewById(R.id.liantext);
		text.setText("联通营销人员管理");
		admin2manage1spinner1 = (Spinner) findViewById(R.id.admin2manage1spinner1);
		admin2manage1spinner2 = (Spinner) findViewById(R.id.admin2manage1spinner2);
		admin2manage1spinner3 = (Spinner) findViewById(R.id.admin2manage1spinner3);
		countyinfoimpl = new Countyinfoimpl();
		list_area = countyinfoimpl.querybyparentid(0);
		list_area2 = list_area;
		List<String> list_area_string = new ArrayList<String>();

		Iterator<Countyinfo> iterator = list_area.iterator();
		while (iterator.hasNext()) {
			Countyinfo countyinfo = iterator.next();
			list_area_string.add(countyinfo.getAreaname());
		}
		ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item,
				R.id.textviewId, list_area_string);
		admin2manage1spinner1.setAdapter(adapter);
		admin2manage1spinner1.setPrompt("请选择省份：");
		admin2manage1spinner2.setPrompt("请选择市：");
		admin2manage1spinner3.setPrompt("请选择县/区：");
		admin2manage1spinner1
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View item,
							int arg2, long arg3) {
						// TODO Auto-generated method stub

						String areaname = admin2manage1spinner1
								.getItemAtPosition(arg2).toString();
						final List<Countyinfo> list2 = mySelect(areaname,
								list_area, city_adapter, admin2manage1spinner2);
						admin2manage1spinner2
								.setOnItemSelectedListener(new OnItemSelectedListener() {
									@Override
									public void onItemSelected(
											AdapterView<?> arg0, View item,
											int arg2, long arg3) {
										// TODO Auto-generated method stub
										String areaname = admin2manage1spinner2
												.getItemAtPosition(arg2)
												.toString();
										final List<Countyinfo> list3 = mySelect(
												areaname, list2,
												county_adapter,
												admin2manage1spinner3);
										admin2manage1spinner3
												.setOnItemSelectedListener(new OnItemSelectedListener() {
													public void onItemSelected(
															AdapterView<?> arg0,
															View arg1,
															int arg2, long arg3) {
														String areaname = admin2manage1spinner3
																.getItemAtPosition(
																		arg2)
																.toString();
														Iterator<Countyinfo> iterator2 = list3
																.iterator();
														while (iterator2
																.hasNext()) {
															Countyinfo countyinfo2 = iterator2
																	.next();
															if (areaname
																	.equals(countyinfo2
																			.getAreaname())) {
																areaid = countyinfo2
																		.getId();
																break;
															}
														}
													}

													@Override
													public void onNothingSelected(
															AdapterView<?> arg0) {
														// TODO Auto-generated
														// method stub
													}
												});
									}

									public void onNothingSelected(
											AdapterView<?> arg0) {
										// TODO Auto-generated
										// method stub
									}
								});
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}
				});

		admin2manage1btn = (Button) findViewById(R.id.admin2manage1btn);
		admin2manage1btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("areaId", areaid + "");
				intent.setClass(UnicomSaleQuery.this,
						UnicomSaleManage.class);
				startActivity(intent);
			}
		});
	}

	private List<Countyinfo> mySelect(String areaname, List<Countyinfo> list,
			ArrayAdapter<String> adapter, Spinner spinner) {
		List<String> string = new ArrayList<String>();
		Iterator<Countyinfo> iterator2 = list.iterator();
		while (iterator2.hasNext()) {
			Countyinfo countyinfo2 = iterator2.next();
			if (areaname.equals(countyinfo2.getAreaname())) {
				Id = countyinfo2.getId();
				break;
			}
		}
		countyinfoimpl = new Countyinfoimpl();
		list = countyinfoimpl.querybyparentid(Id);
		Iterator<Countyinfo> iterator = list.iterator();
		while (iterator.hasNext()) {
			Countyinfo countyinfo = iterator.next();
			string.add(countyinfo.getAreaname());
		}
		adapter = new ArrayAdapter<String>(UnicomSaleQuery.this, R.layout.item,
				R.id.textviewId, string);
		spinner.setAdapter(adapter);
		return list;
	}
}
