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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.org.ifamily.entity.Fallalert;
import com.org.ifamily.entity.Host;
import com.org.ifamily.entity.Location;
import com.org.ifamily.implement.Fallalertimpl;
import com.org.ifamily.implement.Locationimpl;

public class FallAlert extends Activity implements OnScrollListener {

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
	Fallalert[] myfallalters;
	Location[] mylocations;
	Host host;
	Fallalertimpl fallalertimpl;
	Locationimpl locationimpl;
	List<Fallalert> fallalers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.use_2_3);
		listview = (ListView) findViewById(R.id.ListView03);

		loadMoreView = getLayoutInflater().inflate(R.layout.load_more, null);
		listview.addFooterView(loadMoreView);
		pg = (ProgressBar) loadMoreView.findViewById(R.id.loadingProgress);
		pg.setVisibility(View.INVISIBLE);
		tv = (TextView) loadMoreView.findViewById(R.id.loadMoreTextView);
		listview.setOnScrollListener(this);
		handler2 = new Handler();

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_use_2_3);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				FallAlert.this.finish();
			}
		});

		Intent intent = getIntent();
		host = (Host) intent.getSerializableExtra("HOSTSURE");
		dialog = ProgressDialog.show(this, "提示", "正在加载,请稍后..", true, false);

		// 启动业务线程
		new Fall(this, host).start();
	}

	private class Fall extends Thread {

		private FallAlert activity;
		Host host;

		public Fall(FallAlert act, Host host) {
			activity = act;
			this.host = host;
		}

		@Override
		public void run() {
			fallalertimpl = new Fallalertimpl();
			size = fallalertimpl.queryamount(host.getHostnum());
			if (initSize >= size)
				initSize = size;
			fallalers = fallalertimpl.querybypage(host.getHostnum(), 0,
					initSize);
			if (size == 0) {
				j = 1;
			} else {
				j = 0;
				myfallalters = new Fallalert[size];
				for (Iterator<Fallalert> iterator = fallalers.iterator(); iterator
						.hasNext();) {
					myfallalters[i] = iterator.next();
					i++;
				}
				Locationimpl locationimpl = new Locationimpl();
				List<Location> locations = locationimpl
						.query(host.getHostnum());
				mylocations = new Location[size];
				for (Iterator<Location> iterator = locations.iterator(); iterator
						.hasNext();) {
					Location location = iterator.next();
					for (int flag = 0; flag < initSize; flag++) {
						if (location.getLocid().equals(
								myfallalters[flag].getLocid())) {
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
							"记录时间: " + myfallalters[i].getUpdatetime());
					listItem.add(map);
				}
				// 生成适配器的Item和动态数组对应的元素
				listItemAdapter = new SimpleAdapter(activity, listItem,// 数据源
						R.layout.listviewsos,// ListItem的XML实现
						new String[] { "numcount", "ItemLatLng",
								"ItemLocation", "ItemTime" }, new int[] {
								R.id.numcount, R.id.ItemLatLng,
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
				Toast.makeText(FallAlert.this, "未查询到任何记录", Toast.LENGTH_SHORT)
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
			fallalertimpl = new Fallalertimpl();
			fallalers = fallalertimpl.querybypage(host.getHostnum(), count,
					addSize);
			for (Iterator<Fallalert> iterator = fallalers.iterator(); iterator
					.hasNext();) {
				myfallalters[i] = iterator.next();
				i++;
			}
			locationimpl = new Locationimpl();
			List<Location> locations = locationimpl.query(host.getHostnum());
			mylocations = new Location[size];
			for (Iterator<Location> iterator = locations.iterator(); iterator
					.hasNext();) {
				Location location = iterator.next();
				for (int flag = count; flag < count + addSize; flag++) {
					if (location.getLocid().equals(
							myfallalters[flag].getLocid())) {
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
				map.put("ItemTime", "记录时间: " + myfallalters[k].getUpdatetime());
				listItem.add(map);
			}

		} else {
			fallalertimpl = new Fallalertimpl();
			fallalers = fallalertimpl.querybypage(host.getHostnum(), count,
					size - count);
			if (fallalers != null) {
				for (Iterator<Fallalert> iterator = fallalers.iterator(); iterator
						.hasNext();) {
					myfallalters[i] = iterator.next();
					i++;
				}
				locationimpl = new Locationimpl();
				List<Location> locations = locationimpl
						.query(host.getHostnum());
				mylocations = new Location[size];
				for (Iterator<Location> iterator = locations.iterator(); iterator
						.hasNext();) {
					Location location = iterator.next();
					for (int flag = count; flag < size; flag++) {
						if (location.getLocid().equals(
								myfallalters[flag].getLocid())) {
							mylocations[flag] = location;
							break;
						}
					}

				}
				for (int k = count; k < size; k++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("numcount", (k + 1) + ".");
					map.put("ItemLatLng",
							"经纬度: " + "(" + mylocations[k].getLat() + ","
									+ mylocations[k].getLng() + ")");
					map.put("ItemLocation",
							"实际位置: " + mylocations[k].getAddress());
					map.put("ItemTime",
							"记录时间: " + myfallalters[k].getUpdatetime());
					listItem.add(map);
				}
			} else {
				
			}
		}

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
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
