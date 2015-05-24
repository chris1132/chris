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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.org.ifamily.entity.Area;
import com.org.ifamily.implement.Areaimpl;

public class Street extends Activity implements OnScrollListener {
	private List<Area> list_area = null;
	private Button load;
	private ProgressBar pg;
	private View moreView;
	private ImageButton buttonBack;
	private ListView listview;
	private ProgressDialog dialog;
	private Handler handler;
	private Handler handler2;
	private SimpleAdapter listItemAdapter;
	private int lastVisibleIndex;
	long areaid;
	Areaimpl areaimpl;
	ArrayList<HashMap<String, Object>> listItem;
	Area area[];
	int size;
	int firstload = 10;
	int perload = 5;
	int i = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome3_3_1);

		moreView = getLayoutInflater().inflate(R.layout.moredata, null);
		load = (Button) moreView.findViewById(R.id.bt_load);
		pg = (ProgressBar) moreView.findViewById(R.id.pg);

		listview = (ListView) findViewById(R.id.StreetManageListView);
		buttonBack = (ImageButton) findViewById(R.id.imageButton3_3_1_result);
		dialog = ProgressDialog.show(Street.this, "提示", "加载中，请稍后..");
		handler = new StreetHandler();
		Thread workthread = new StreetThread();
		workthread.start();

		buttonBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Street.this.finish();
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
	}// On create end

	private class StreetThread extends Thread {

		@Override
		public void run() {
			Intent intent = getIntent();
			String string_value = intent.getExtras().getString("areaId");
			areaid = Long.parseLong(string_value);
			areaimpl = new Areaimpl();

			size = areaimpl.querybyparentidacount(areaid);

			if (size == 0) {
				Toast.makeText(Street.this, "未查询到任何记录", Toast.LENGTH_SHORT)
						.show();

			} else {
				area = new Area[size];
				if (size <= firstload) {
					firstload = size;
				}
				list_area = areaimpl
						.querybyparentidbypage(areaid, 0, firstload);
				Iterator<Area> iterator = list_area.iterator();
				while (iterator.hasNext()) {
					area[i] = iterator.next();
					i++;
				}
				listItem = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < firstload; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("areaNum", "区域编号：" + area[i].getAreaid() + "");
					map.put("areaName", "区域名称：" + area[i].getAreaname());
					map.put("sosNum", "sos号码：" + area[i].getSos() + "");
					listItem.add(map);
				}

				listItemAdapter = new SimpleAdapter(Street.this, listItem,
						R.layout.listviewstreet, new String[] { "areaNum",
								"areaName", "sosNum" }, new int[] {
								R.id.AreaNum, R.id.AreaName, R.id.SosNum });

				Message msg = handler.obtainMessage();
				msg.what = 1;
				msg.obj = listview;
				handler.sendMessage(msg);

			}

		}
	}

	private class StreetHandler extends Handler {

		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				ListView listview = (ListView) msg.obj;
				listview.addFooterView(moreView);
				listview.setAdapter(listItemAdapter);
				dialog.dismiss();
			} else {
				dialog.dismiss();
				Toast.makeText(Street.this, "未查询到任何记录", Toast.LENGTH_SHORT)
						.show();
			}
		}

	}

	private void loadMoreDate() {
		int count = listItemAdapter.getCount();
		if (count + perload < size) {
			areaimpl = new Areaimpl();
			Area area2[] = new Area[perload];
			List<Area> list2 = areaimpl.querybyparentidbypage(areaid, count,
					perload);
			Iterator<Area> iterator2 = list2.iterator();
			i = 0;
			while (iterator2.hasNext()) {
				area2[i] = iterator2.next();
				i++;
			}
			i = 0;
			for (int j = count; j < count + perload; j++) {
				area[j] = area2[i];
				i++;
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("areaNum", "区域编号：" + area[j].getAreaid() + "");
				map.put("areaName", "区域名称：" + area[j].getAreaname());
				map.put("sosNum", "sos号码：" + area[j].getSos() + "");
				listItem.add(map);
			}
		} else {
			areaimpl = new Areaimpl();
			Area area2[] = new Area[size - count];
			List<Area> list2 = areaimpl.querybyparentidbypage(areaid, count,
					size - count);
			Iterator<Area> iterator2 = list2.iterator();
			i = 0;
			while (iterator2.hasNext()) {
				area2[i] = iterator2.next();
				i++;
			}
			i = 0;
			for (int j = count; j < size; j++) {
				area[j] = area2[i];
				i++;
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("areaNum", "区域编号：" + area[j].getAreaid() + "");
				map.put("areaName", "区域名称：" + area[j].getAreaname());
				map.put("sosNum", "sos号码：" + area[j].getSos() + "");
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
}// activity end
