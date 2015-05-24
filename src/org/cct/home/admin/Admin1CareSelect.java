package org.cct.home.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.cct.home.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.org.ifamily.entity.Area;
import com.org.ifamily.entity.Countyinfo;
import com.org.ifamily.implement.Areaimpl;
import com.org.ifamily.implement.Countyinfoimpl;

public class Admin1CareSelect extends Activity {
	private long areaid;
	private Countyinfoimpl countyinfoimpl = null;
	private List<Countyinfo> list_area = null;
	private ArrayAdapter<String> city_adapter;
	private ArrayAdapter<String> county_adapter;
	private long Id;
	boolean check = false;
	boolean check2 = false;
	private Spinner admin1selectspinner1;
	private Spinner admin1selectspinner2;
	private Spinner admin1selectspinner3;
	private Spinner admin1selectspinner4;
	private Spinner admin1selectspinner5;
	private Button admin1selectbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.adminhome_2_1_all);

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_adminhome_2_1_all);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Admin1CareSelect.this.finish();

			}
		});

		admin1selectspinner1 = (Spinner) findViewById(R.id.admin1selectspinner1);
		admin1selectspinner2 = (Spinner) findViewById(R.id.admin1selectspinner2);
		admin1selectspinner3 = (Spinner) findViewById(R.id.admin1selectspinner3);
		admin1selectspinner4 = (Spinner) findViewById(R.id.admin1selectspinner4);
		admin1selectspinner5 = (Spinner) findViewById(R.id.admin1selectspinner5);
		admin1selectbtn = (Button) findViewById(R.id.admin1selectbtn);
		admin1selectbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("areaId", areaid + "");
				intent.setClass(Admin1CareSelect.this, CareTo.class);
				startActivity(intent);
			}
		});
		countyinfoimpl = new Countyinfoimpl();
		list_area = countyinfoimpl.querybyparentid(0);
		List<String> list_area_string = new ArrayList<String>();

		Iterator<Countyinfo> iterator = list_area.iterator();
		while (iterator.hasNext()) {
			Countyinfo countyinfo = iterator.next();
			list_area_string.add(countyinfo.getAreaname());

		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.item, R.id.textviewId, list_area_string);
		admin1selectspinner1.setAdapter(adapter);
		admin1selectspinner1.setPrompt("请选择省份：");
		admin1selectspinner2.setPrompt("请选择市：");
		admin1selectspinner3.setPrompt("请选择县/区：");
		admin1selectspinner1
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View item,
							int arg2, long arg3) {
						// TODO Auto-generated method stub

						String areaname = admin1selectspinner1
								.getItemAtPosition(arg2).toString();
						final List<Countyinfo> list2 = mySelect(areaname,
								list_area, city_adapter, admin1selectspinner2);
						admin1selectspinner2
								.setOnItemSelectedListener(new OnItemSelectedListener() {

									@Override
									public void onItemSelected(
											AdapterView<?> arg0, View item,
											int arg2, long arg3) {
										// TODO Auto-generated method stub

										String areaname = admin1selectspinner2
												.getItemAtPosition(arg2)
												.toString();
										final List<Countyinfo> list3 = mySelect(
												areaname, list2,
												county_adapter,
												admin1selectspinner3);
										check = false;
										admin1selectspinner3
												.setOnItemSelectedListener(new OnItemSelectedListener() {
													public void onItemSelected(
															AdapterView<?> arg0,
															View arg1,
															int arg2, long arg3) {
														if (check) {
															String areaname = admin1selectspinner3
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
															Areaimpl areaimpl = new Areaimpl();
															final List<Area> list_area = areaimpl
																	.querybyparentid(areaid);
															List<String> list_area_string5 = new ArrayList<String>();
															if (list_area != null) {
																Iterator<Area> iterator = list_area
																		.iterator();
																while (iterator
																		.hasNext()) {
																	Area area = iterator
																			.next();
																	list_area_string5
																			.add(area
																					.getAreaname());

																}
															}
															ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(
																	Admin1CareSelect.this,
																	R.layout.item,
																	R.id.textviewId,
																	list_area_string5);
															admin1selectspinner4
																	.setAdapter(adapter5);
															check2 = false;
															admin1selectspinner4
																	.setOnItemSelectedListener(new OnItemSelectedListener() {

																		@Override
																		public void onItemSelected(
																				AdapterView<?> arg0,
																				View arg1,
																				int arg2,
																				long arg3) {
																			if (check2) {

																				String areaname = admin1selectspinner4
																						.getItemAtPosition(
																								arg2)
																						.toString();
																				if (list_area != null) {
																					Iterator<Area> iterator2 = list_area
																							.iterator();
																					while (iterator2
																							.hasNext()) {
																						Area area = iterator2
																								.next();
																						if (areaname
																								.equals(area
																										.getAreaname())) {
																							areaid = area
																									.getAreaid();
																							break;
																						}
																					}
																				}
																				Areaimpl areaimpl = new Areaimpl();
																				final List<Area> list_area6 = areaimpl
																						.querybyparentid(areaid);
																				List<String> list_area_string6 = new ArrayList<String>();

																				if (list_area6 != null) {
																					Iterator<Area> iterator = list_area6
																							.iterator();
																					while (iterator
																							.hasNext()) {
																						Area area = iterator
																								.next();
																						list_area_string6
																								.add(area
																										.getAreaname());

																					}
																				}
																				ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(
																						Admin1CareSelect.this,
																						R.layout.item,
																						R.id.textviewId,
																						list_area_string6);
																				admin1selectspinner5
																						.setAdapter(adapter6);
																				admin1selectspinner5
																						.setOnItemSelectedListener(new OnItemSelectedListener() {

																							@Override
																							public void onItemSelected(
																									AdapterView<?> arg0,
																									View arg1,
																									int arg2,
																									long arg3) {
																								String areaname = admin1selectspinner5
																										.getItemAtPosition(
																												arg2)
																										.toString();
																								if (list_area6 != null) {
																									Iterator<Area> iterator2 = list_area6
																											.iterator();
																									while (iterator2
																											.hasNext()) {
																										Area area = iterator2
																												.next();
																										if (areaname
																												.equals(area
																														.getAreaname())) {
																											areaid = area
																													.getAreaid();
																											break;
																										}
																									}
																								}
																							}

																							public void onNothingSelected(
																									AdapterView<?> arg0) {

																							}
																						});

																			}
																			check2 = true;
																		}

																		@Override
																		public void onNothingSelected(
																				AdapterView<?> arg0) {
																		}
																	});

														}
														check = true;
													}

													@Override
													public void onNothingSelected(
															AdapterView<?> arg0) {

													}

												});

									}

									@Override
									public void onNothingSelected(
											AdapterView<?> arg0) {
										// TODO Auto-generated method stub

									}
								});

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

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
		adapter = new ArrayAdapter<String>(Admin1CareSelect.this,
				R.layout.item, R.id.textviewId, string);
		spinner.setAdapter(adapter);
		return list;
	}
}
