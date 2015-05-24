package org.cct.home.admin;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.org.ifamily.entity.Host;
import com.org.ifamily.implement.Hostimpl;

public class CareTo extends Activity implements OnScrollListener {
	private ListView listview;
	private Button load;
	private ProgressBar pg;
	private ProgressDialog dialog;
	private View moreView;
	SimpleAdapter listItemAdapter;
	Hostimpl hostimpl;
	Host host[];
	int firstload = 10;
	int perload = 3;
	int i = 0;
	int j = 0;
	int size;
	long areaid;
	ArrayList<HashMap<String, Object>> listItem;
	// 最后可见条目的索引
	private int lastVisibleIndex;
	private Handler handler2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome_2_1_result_all);
		listview = (ListView) findViewById(R.id.CareUseListView);

		moreView = getLayoutInflater().inflate(R.layout.moredata, null);
		load = (Button) moreView.findViewById(R.id.bt_load);
		pg = (ProgressBar) moreView.findViewById(R.id.pg);

		ImageButton buttonBack = (ImageButton) findViewById(R.id.imageButton_2_1_result);

		buttonBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CareTo.this.finish();
			}
		});

		dialog = ProgressDialog.show(this, "加载中", "正在加载，请稍候", true, false);
		new CareToThread(this).start();

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("Host", host[position]);
				intent.setClass(CareTo.this, CareDetail.class);
				CareTo.this.startActivity(intent);
			}
		});

		listview.setOnScrollListener(this);
		handler2 = new Handler();
		load.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pg.setVisibility(View.VISIBLE);
				load.setVisibility(View.GONE);
				handler2.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						loadMoreDate();
						load.setVisibility(View.VISIBLE);
						pg.setVisibility(View.GONE);
						listItemAdapter.notifyDataSetChanged();
					}
				}, 2000);
			}
		});

	}

	private class CareToThread extends Thread {
		private CareTo activity;

		public CareToThread(CareTo act) {
			activity = act;
		}

		@Override
		public void run() {
			Intent intent = getIntent();
			String string_value = intent.getExtras().getString("areaId");
			areaid = Long.parseLong(string_value);
			hostimpl = new Hostimpl();
			size = hostimpl.querybyareaidacount(areaid);

			if (size == 0) {
				j = 1;
			} else {
				j = 0;
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
				host = new Host[size];
				if (size <= firstload) {
					firstload = size;
				}
				List<Host> list = hostimpl.querybyareaidbypage(areaid, 0,
						firstload);
				Iterator<Host> iterator1 = list.iterator();
				while (iterator1.hasNext()) {
					host[i] = iterator1.next();
					i++;
					System.out.println("currentI" + i);
				}
				listItem = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < firstload; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("hostNum", "宿主编号：" + host[i].getHostnum() + "");
					map.put("hostName", "宿主名称：" + host[i].getHostname());
					listItem.add(map);
				}

				// 生成适配器的Item和动态数组对应的元素
				listItemAdapter = new SimpleAdapter(CareTo.this, listItem,// 数据源
						R.layout.listviewcareuse,// ListItem的XML实现
						// 动态数组与ImageItem对应的子项
						new String[] { "hostNum", "hostName" },
						// ImageItem的XML文件里面的一个ImageView,两个TextView ID
						new int[] { R.id.CareUseHostNum, R.id.CareUseHostName });
				// listItemAdapter.notifyDataSetChanged();
				listview.addFooterView(moreView);
				listview.setAdapter(listItemAdapter);

				break;
			case 1:
				Toast.makeText(CareTo.this, "未查询到任何记录", Toast.LENGTH_SHORT)
						.show();

				break;
			default:
				break;
			}
		}

	};

	private void loadMoreDate() {
		int count = listItemAdapter.getCount();
		if (count + perload < size) {
			hostimpl = new Hostimpl();
			Host host2[] = new Host[perload];
			List<Host> list2 = hostimpl.querybyareaidbypage(areaid, count,
					perload);
			Iterator<Host> iterator2 = list2.iterator();
			i = 0;
			while (iterator2.hasNext()) {
				host2[i] = iterator2.next();
				i++;
			}
			i = 0;
			for (int j = count; j < count + perload; j++) {
				host[j] = host2[i];
				i++;
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("hostNum", "宿主编号：" + host[j].getHostnum() + "");
				map.put("hostName", "宿主名称：" + host[j].getHostname());
				listItem.add(map);
			}
		} else {
			hostimpl = new Hostimpl();
			Host host2[] = new Host[size - count];
			List<Host> list2 = hostimpl.querybyareaidbypage(areaid, count, size
					- count);
			Iterator<Host> iterator2 = list2.iterator();
			i = 0;
			while (iterator2.hasNext()) {
				host2[i] = iterator2.next();
				i++;
			}
			i = 0;
			for (int j = count; j < size; j++) {
				host[j] = host2[i];
				i++;
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("hostNum", "宿主编号：" + host[j].getHostnum() + "");
				map.put("hostName", "宿主名称：" + host[j].getHostname());
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
			listview.removeFooterView(moreView);
			Toast.makeText(this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& lastVisibleIndex == listItemAdapter.getCount()) {
			// 当滑到底部时自动加载
			pg.setVisibility(View.VISIBLE);
			load.setVisibility(View.GONE);
			handler2.postDelayed(new Runnable() {

				@Override
				public void run() {
					loadMoreDate();
					load.setVisibility(View.VISIBLE);
					pg.setVisibility(View.GONE);
					listItemAdapter.notifyDataSetChanged();
				}
			}, 2000);
		}
	}

}
