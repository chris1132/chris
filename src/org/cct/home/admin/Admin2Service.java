package org.cct.home.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.cct.home.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.org.ifamily.entity.Serviceset;
import com.org.ifamily.implement.Servicesetimpl;

public class Admin2Service extends Activity {

	private Button add;
	private int i, j;
	Servicesetimpl servicesetimpl;
	Serviceset[] servicesets;
	List<Serviceset> lists;
	ListView list;
	SimpleAdapter listItemAdapter;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome_3_1_all);

		list = (ListView) findViewById(R.id.price_serve_listview);
		ImageButton buttonBack = (ImageButton) findViewById(R.id.imageButton_adminhome_3_1_all);
		buttonBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 返回上一级
				Admin2Service.this.finish();
			}
		});

		add = (Button) findViewById(R.id.addservicebtn);
		add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Admin2Service.this, AddService.class);
				Admin2Service.this.startActivity(intent);
				Admin2Service.this.finish();
			}
		});

		dialog = ProgressDialog.show(Admin2Service.this, "加载中", "正在加载,请稍后..",
				true, false);
		new Admin2ServiceThread(Admin2Service.this).start();

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) { // TODO Auto-generated method
												// stub
				final int k = position;
				new AlertDialog.Builder(Admin2Service.this)
						.setTitle("选择操作")
						.setPositiveButton(
								"编辑",
								new android.content.DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										// final int m = (Integer) //
										// view.getTag();
										Intent intent = new Intent(
												Admin2Service.this,
												AddServiceEdit.class);
										intent.putExtra("SERVICE",
												servicesets[k]);
										Admin2Service.this
												.startActivity(intent);
										Admin2Service.this.finish();
									}

								})
						.setNegativeButton("删除",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										new AlertDialog.Builder(
												Admin2Service.this)
												.setTitle("提示")
												.setMessage("确定删除此增值服务?")
												.setPositiveButton(
														"确定",
														new DialogInterface.OnClickListener() {

															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {
																boolean flag = servicesetimpl
																		.delete(servicesets[k]
																				.getServiceid());
																if (flag) {
																	finish();
																	Admin2Service.this
																			.finish();
																	Toast.makeText(
																			Admin2Service.this,
																			"删除成功!",
																			Toast.LENGTH_LONG)
																			.show();
																}
															}
														})
												.setNegativeButton(
														"取消",
														new DialogInterface.OnClickListener() {

															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {

															}
														}).show();
									}
								}).show();
			}
		});

	}

	private class Admin2ServiceThread extends Thread {

		private Admin2Service activity;

		public Admin2ServiceThread(Admin2Service act) {
			activity = act;
		}

		@Override
		public void run() {

			i = 0;
			servicesetimpl = new Servicesetimpl();
			int size = servicesetimpl.queryacount();
			if (size == 0) {
				j = 1;
			} else {
				j = 0;
				servicesets = new Serviceset[size];
				lists = servicesetimpl.query(0, size);
				for (Iterator<Serviceset> iterator = lists.iterator(); iterator
						.hasNext();) {
					servicesets[i] = iterator.next();
					i++;
				}

				// 生成动态数组，加入数据
				ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < size; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("serviceNumcount", (i + 1) + ".");
					map.put("serviceName", "业务名称: " + servicesets[i].getName());
					map.put("serviceCost", "收费标准: " + servicesets[i].getCost());
					map.put("serviceRemarks",
							"备注:" + servicesets[i].getRemarks());
					map.put("serviceChangecode",
							"业务代码" + servicesets[i].getChargecode());

					listItem.add(map);
				}

				// 生成适配器的Item和动态数组对应的元素
				listItemAdapter = new SimpleAdapter(Admin2Service.this,
						listItem,// 数据源
						R.layout.listviewservice,// ListItem的XML实现
						// 动态数组与ImageItem对应的子项
						new String[] { "serviceNumcount", "serviceName",
								"serviceCost", "serviceRemarks",
								"serviceChangecode" },
						// ImageItem的XML文件里面的一个ImageView,两个TextView ID
						new int[] { R.id.serviceNumcount, R.id.serviceName,
								R.id.serviceCost, R.id.serviceRemarks,
								R.id.serviceChangecode });
			}
			
			activity.handler.sendEmptyMessage(j);
			if(dialog.isShowing()) {
				dialog.dismiss();
			}
		}
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				listItemAdapter.notifyDataSetChanged();
				list.setAdapter(listItemAdapter);
				break;

			case 1:
				Toast.makeText(Admin2Service.this, "未查询到任何记录",
						Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}

	};
}
