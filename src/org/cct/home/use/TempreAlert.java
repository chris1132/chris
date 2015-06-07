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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.org.ifamily.entity.Host;
import com.org.ifamily.entity.Location;
import com.org.ifamily.entity.Sosalert;
import com.org.ifamily.entity.Temperaturealert;
import com.org.ifamily.implement.Temperaturealertimpl;

public class TempreAlert extends Activity implements OnScrollListener {

	int i = 0, j, flag = 1;
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
	Host host;
	SimpleAdapter listItemAdapter;
	ArrayList<HashMap<String, Object>> listItem;
	Temperaturealert[] mytemprealerts;
	Temperaturealertimpl temprealertimpl;
	List<Temperaturealert> temprealerts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.use_2_2);
		listview = (ListView) findViewById(R.id.ListView02);

		loadMoreView = getLayoutInflater().inflate(R.layout.load_more, null);
		listview.addFooterView(loadMoreView);
		pg = (ProgressBar) loadMoreView.findViewById(R.id.loadingProgress);
		pg.setVisibility(View.INVISIBLE);
		tv = (TextView) loadMoreView.findViewById(R.id.loadMoreTextView);
		listview.setOnScrollListener(this);
		handler2 = new Handler();

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_use_2_2);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				TempreAlert.this.finish();
			}
		});

		Intent intent = getIntent();
		host = (Host) intent.getSerializableExtra("HOSTSURE");
		dialog = ProgressDialog.show(this, "提示", "正在加载,请稍后..", true, false);

		// 启动业务线程
		new Tempre(this, host).start();
	}

	private class Tempre extends Thread {

		private TempreAlert activity;
		Host host;

		public Tempre(TempreAlert act, Host host) {
			activity = act;
			this.host = host;
		}

		@Override
		public void run() {
			temprealertimpl = new Temperaturealertimpl();
			size = temprealertimpl.queryamount(host.getHostnum());
			if (initSize >= size)
				initSize = size;
			temprealerts = temprealertimpl.querybypage(host.getHostnum(), 0,
					initSize);
			if (size == 0) {
				j = 1;
			} else {
				j = 0;
				mytemprealerts = new Temperaturealert[size];
				for (Iterator<Temperaturealert> iterator = temprealerts
						.iterator(); iterator.hasNext();) {
					mytemprealerts[i] = iterator.next();
					i++;
				}

				// 生成动态数组，加入数据
				 listItem = new ArrayList<HashMap<String, Object>>();
				for (int k = 0; k < initSize; k++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("numcount", (k + 1) + ".");
					map.put("ItemLocation",
							"实际位置: " + mytemprealerts[k].getAddress());
					map.put("ItemCurrentTemperature", "当时温度："
							+ mytemprealerts[k].getTemperature());
					int year = mytemprealerts[k].getUpdatetime().getYear() + 1900;
					int month = mytemprealerts[k].getUpdatetime().getMonth() + 1;
					int day = mytemprealerts[k].getUpdatetime().getDate();
					int hour = mytemprealerts[k].getUpdatetime().getHours();
					int minute = mytemprealerts[k].getUpdatetime().getMinutes();
					int second = mytemprealerts[k].getUpdatetime().getSeconds();
					map.put("ItemTime", "记录时间: " + year + "-" + month + "-"
							+ day + " " + hour + ":" + minute + ":" + second);
					listItem.add(map);
				}
				// 生成适配器的Item和动态数组对应的元素
				listItemAdapter = new SimpleAdapter(activity,
						listItem,// 数据源
						R.layout.listviewht,// ListItem的XML实现
						// 动态数组与ImageItem对应的子项
						new String[] { "numcount", "ItemLocation",
								"ItemCurrentTemperature", "ItemTime" },
						new int[] { R.id.numcount, R.id.ItemLocation,
								R.id.ItemCurrentTemperature, R.id.ItemTime });
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
				Toast.makeText(TempreAlert.this, "未查询到任何记录", Toast.LENGTH_SHORT)
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
			temprealertimpl = new Temperaturealertimpl();
			temprealerts = temprealertimpl.querybypage(host.getHostnum(),
					count, addSize);
			for (Iterator<Temperaturealert> iterator = temprealerts.iterator(); iterator
					.hasNext();) {
				mytemprealerts[i] = iterator.next();
				i++;
			}
			for (int k = count; k < count + addSize; k++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("numcount", (k + 1) + ".");
				map.put("ItemLocation",
						"实际位置: " + mytemprealerts[k].getAddress());
				map.put("ItemCurrentTemperature",
						"当时温度：" + mytemprealerts[k].getTemperature());
				int year = mytemprealerts[k].getUpdatetime().getYear() + 1900;
				int month = mytemprealerts[k].getUpdatetime().getMonth() + 1;
				int day = mytemprealerts[k].getUpdatetime().getDate();
				int hour = mytemprealerts[k].getUpdatetime().getHours();
				int minute = mytemprealerts[k].getUpdatetime().getMinutes();
				int second = mytemprealerts[k].getUpdatetime().getSeconds();
				map.put("ItemTime", "记录时间: " + year + "-" + month + "-" + day
						+ " " + hour + ":" + minute + ":" + second);
				listItem.add(map);
			}
		} else {
			temprealertimpl = new Temperaturealertimpl();
			temprealerts = temprealertimpl.querybypage(host.getHostnum(),
					count, size - count);
			for (Iterator<Temperaturealert> iterator = temprealerts.iterator(); iterator
					.hasNext();) {
				mytemprealerts[i] = iterator.next();
				i++;
			}

			for (int k = count; k < size; k++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("numcount", (k + 1) + ".");
				map.put("ItemLocation",
						"实际位置: " + mytemprealerts[k].getAddress());
				map.put("ItemCurrentTemperature",
						"当时温度：" + mytemprealerts[k].getTemperature());
				int year = mytemprealerts[k].getUpdatetime().getYear() + 1900;
				int month = mytemprealerts[k].getUpdatetime().getMonth() + 1;
				int day = mytemprealerts[k].getUpdatetime().getDate();
				int hour = mytemprealerts[k].getUpdatetime().getHours();
				int minute = mytemprealerts[k].getUpdatetime().getMinutes();
				int second = mytemprealerts[k].getUpdatetime().getSeconds();
				map.put("ItemTime", "记录时间: " + year + "-" + month + "-" + day
						+ " " + hour + ":" + minute + ":" + second);
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
			tv.setVisibility(View.INVISIBLE);
			pg.setVisibility(View.INVISIBLE);
			flag = 1;
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& lastVisibleIndex == listItemAdapter.getCount()) {
			if (flag == 1) {
			} else {
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
}
