package org.cct.home.use;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.cct.home.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.org.ifamily.entity.Fallalert;
import com.org.ifamily.entity.Host;
import com.org.ifamily.entity.Location;
import com.org.ifamily.entity.Sosalert;
import com.org.ifamily.entity.Temperaturealert;
import com.org.ifamily.implement.Locationimpl;
import com.org.ifamily.implement.Sosalertimpl;

public class SosAlert extends Activity implements OnScrollListener {

	int i = 0, j;
	int size;
	private ListView listview;
	private View loadMoreView;
	private ProgressDialog dialog;
	private ProgressBar pg;
	private TextView tv;
	private int lastVisibleIndex;
	private int initSize = 10;
	private int addSize = 5;
	private Handler handler2;
	SimpleAdapter listItemAdapter;
	ArrayList<HashMap<String, Object>> listItem;
	Location[] mylocations;
	Sosalert[] mysosalerts;
	Locationimpl locationimpl;
	Sosalertimpl sosalertimpl;
	List<Sosalert> sosalerts;
	Host host;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.use_2_1);
		listview = (ListView) findViewById(R.id.ListView01);

		loadMoreView = getLayoutInflater().inflate(R.layout.load_more, null);
		listview.addFooterView(loadMoreView);
		pg = (ProgressBar) loadMoreView.findViewById(R.id.loadingProgress);
		pg.setVisibility(View.INVISIBLE);
		tv = (TextView) loadMoreView.findViewById(R.id.loadMoreTextView);
		listview.setOnScrollListener(this);
		handler2 = new Handler();

		Intent intent = getIntent();
		host = (Host) intent.getSerializableExtra("HOSTSURE");

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_use_2_1);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 返回上一级
				SosAlert.this.finish();
			}
		});

		dialog = ProgressDialog.show(this, "提示", "正在加载,请稍后..", true, false);

		// 启动业务线程
		new SOS(this, host).start();
	}

	private class SOS extends Thread {

		private SosAlert activity;
		Host host;

		public SOS(SosAlert act, Host host) {
			activity = act;
			this.host = host;
		}

		@Override
		public void run() {
			sosalertimpl = new Sosalertimpl();
			// 将所有的sos放入list中
			size = sosalertimpl.queryamount(host.getHostnum());
			if (initSize >= size)
				initSize = size;
			sosalerts = sosalertimpl
					.querybypage(host.getHostnum(), 0, initSize);
			if (size == 0) {
				j = 1;

			} else {
				j = 0;
				mysosalerts = new Sosalert[size];
				for (Iterator<Sosalert> iterator = sosalerts.iterator(); iterator
						.hasNext();) {
					mysosalerts[i] = iterator.next();
					i++;
				}
//				iterator = sosalerts.iterator();
//				while (iterator.hasNext()) {
//					mysosalerts[i] = iterator.next();
//					i++;
//				}
//
				locationimpl = new Locationimpl();
				List<Location> locations = locationimpl
						.query(host.getHostnum());
				mylocations = new Location[size];
//				Iterator<Location> iterator = locations.iterator();
//				while (iterator.hasNext()) {
//					Location location = iterator.next();	
//					for (int flag = 0; flag < initSize; flag++) {
//						System.out.println("loacation="+location.getLocid());
//						System.out.println("mysos="+mysosalerts[flag].getLocid());
//						if (location.getLocid().equals(
//								mysosalerts[flag].getLocid())) {
//							mylocations[flag] = location;
//							break;
//						}
//					}
//				}
				for (Iterator<Location> iterator = locations.iterator(); iterator
						.hasNext();) {
					Location location = iterator.next();
					for (int flag = 0; flag < initSize; flag++) {
						if (location.getLocid().equals(
								mysosalerts[flag].getLocid())) {
							mylocations[flag] = location;
							break;
						}
					}
				}
				

				// 生成动态数组，加入数据
				listItem = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < initSize; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("numcount", (i + 1) + ".");
					map.put("ItemLatLng",
							"经纬度: " + "(" + mylocations[i].getLat() + ","
									+ mylocations[i].getLng() + ")");
					map.put("ItemLocation",
							"实际位置: " + mylocations[i].getAddress());
					map.put("ItemTime",
							"记录时间: " + mysosalerts[i].getUpdatetime());
					listItem.add(map);
				}
				// 生成适配器的Item和动态数组对应的元素
				listItemAdapter = new SimpleAdapter(activity, listItem,// 数据源
						R.layout.listviewsos,// ListItem的XML实现
						// 动态数组与ImageItem对应的子项
						new String[] { "numcount", "ItemLatLng",
								"ItemLocation", "ItemTime" },
						// ImageItem的XML文件里面的一个ImageView,两个TextView ID
						new int[] { R.id.numcount, R.id.ItemLatLng,
								R.id.ItemLocation, R.id.ItemTime });
			}

			activity.handler.sendEmptyMessage(j);
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				listItemAdapter.notifyDataSetChanged(); // 发送消息更新ListView
				listview.setAdapter(listItemAdapter); // 重新设置ListView的数据适配器
				break;

			case 1:
				Toast.makeText(SosAlert.this, "未查询到任何记录", Toast.LENGTH_SHORT)
						.show();
				break;

			default:
				break;
			}
		}

	};

	private void loadMoreDate() {
		int count = listItemAdapter.getCount();
		if (count + addSize < size) {
			sosalertimpl = new Sosalertimpl();
			sosalerts = sosalertimpl.querybypage(host.getHostnum(), count,
					addSize);
			for (Iterator<Sosalert> iterator = sosalerts.iterator(); iterator
					.hasNext();) {
				mysosalerts[i] = iterator.next();
				i++;
			}
			locationimpl = new Locationimpl();
			List<Location> locations = locationimpl.query(host.getHostnum());
			// mylocations = new Location[size];
			for (Iterator<Location> iterator = locations.iterator(); iterator
					.hasNext();) {
				Location location = iterator.next();
				for (int flag = count; flag < count + addSize; flag++) {
					if (location.getLocid()
							.equals(mysosalerts[flag].getLocid())) {
						mylocations[flag] = location;
						break;
					}
				}
			}
			for (int k = count; k < count + addSize; k++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("numcount", (k + 1) + ".");
				map.put("ItemLatLng", "经纬度: " + "(" + mylocations[k].getLat()
						+ "," + mylocations[k].getLng() + ")");
				map.put("ItemLocation", "实际位置: " + mylocations[k].getAddress());
				map.put("ItemTime", "记录时间: " + mysosalerts[k].getUpdatetime());
				listItem.add(map);
			}
		} else {
			sosalertimpl = new Sosalertimpl();
			sosalerts = sosalertimpl.querybypage(host.getHostnum(), count, size
					- count);
			for (Iterator<Sosalert> iterator = sosalerts.iterator(); iterator
					.hasNext();) {
				mysosalerts[i] = iterator.next();
				i++;
			}
//			iterator = sosalerts.iterator();
//			while (iterator.hasNext()) {
//				mysosalerts[i] = iterator.next();
//				i++;
//			}
			locationimpl = new Locationimpl();
			List<Location> locations = locationimpl.query(host.getHostnum());
			// mylocations = new Location[size];
			for (Iterator<Location> iterator = locations.iterator(); iterator
					.hasNext();) {
				Location location = iterator.next();
				for (int flag = count; flag < size; flag++) {
					if (location.getLocid()
							.equals(mysosalerts[flag].getLocid())) {
						mylocations[flag] = location;
						break;
					}
				}
			}
			for (int k = count; k < size; k++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("numcount", (k + 1) + ".");
				map.put("ItemLatLng", "经纬度: " + "(" + mylocations[k].getLat()
						+ "," + mylocations[k].getLng() + ")");
				map.put("ItemLocation", "实际位置: " + mylocations[k].getAddress());
				map.put("ItemTime", "记录时间: " + mysosalerts[k].getUpdatetime());
				listItem.add(map);
			}
		}

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		// 计算最后可见条目的索引
		lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;
		// 所有的条目已经和最大条数相等，则移除底部的View
		if (totalItemCount == size + 1) {
			listview.removeFooterView(loadMoreView);
			Toast.makeText(this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& lastVisibleIndex == listItemAdapter.getCount()) {
			tv.setVisibility(View.INVISIBLE);
			pg.setVisibility(View.VISIBLE);
			handler2.postDelayed(new Runnable() {
				@Override
				public void run() {
					loadMoreDate();
					tv.setVisibility(View.VISIBLE);
					pg.setVisibility(View.INVISIBLE);
					listItemAdapter.notifyDataSetChanged();
				}
			}, 2000);

		}

	}

}
