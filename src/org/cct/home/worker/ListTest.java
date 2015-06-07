package org.cct.home.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cct.home.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListTest extends Activity implements OnScrollListener {

	private String[] names = new String[] { "wujianguo", "xuzhuoquan",
			"chenziqi", "zhumingqing", "wujianguo", "xuzhuoquan", "chenziqi",
			"zhumingqing", "wujianguo", "xuzhuoquan", "chenziqi",
			"zhumingqing", "wujianguo", "xuzhuoquan", "chenziqi",
			"zhumingqing", "wujianguo", "xuzhuoquan", "chenziqi",
			"zhumingqing", "wujianguo", "xuzhuoquan", "chenziqi", "zhumingqing" };
	private String[] sexs = new String[] { "女", "男", "女", "男", "女", "男", "女",
			"男", "女", "男", "女", "男", "女", "男", "女", "男", "女", "男", "女", "男",
			"女", "男", "女", "男" };
	private int[] imageIds = new int[] { R.drawable.tiger, R.drawable.nongyu,
			R.drawable.qingzhao, R.drawable.libai, R.drawable.tiger,
			R.drawable.nongyu, R.drawable.qingzhao, R.drawable.libai,
			R.drawable.tiger, R.drawable.nongyu, R.drawable.qingzhao,
			R.drawable.libai, R.drawable.tiger, R.drawable.nongyu,
			R.drawable.qingzhao, R.drawable.libai, R.drawable.tiger,
			R.drawable.nongyu, R.drawable.qingzhao, R.drawable.libai,
			R.drawable.tiger, R.drawable.nongyu, R.drawable.qingzhao,
			R.drawable.libai };

	private int lastVisibleIndex;
	private ListView listview;
	private View loadMoreView;
	private ProgressBar pg;
	private TextView tv;
	private SimpleAdapter adapter;
	private List<Map<String, Object>> listItems;
	int size;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jiazheng);

		ImageButton buttonBack = (ImageButton) findViewById(R.id.imageButton_listtest);
		buttonBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ListTest.this.finish();
			}
		});

		listview = (ListView) findViewById(R.id.mylist);
		loadMoreView = getLayoutInflater().inflate(R.layout.load_more, null);
		pg = (ProgressBar) loadMoreView.findViewById(R.id.loadingProgress);
		pg.setVisibility(View.INVISIBLE);
		tv = (TextView) loadMoreView.findViewById(R.id.loadMoreTextView);

		listview.addFooterView(loadMoreView);
		listview.setOnScrollListener(this);

		listItems = new ArrayList<Map<String, Object>>();
		size = names.length;
		for (int i = 0; i < 12; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("header", imageIds[i]);
			listItem.put("name", names[i]);
			listItem.put("sex", sexs[i]);
			listItems.add(listItem);
		}

		adapter = new SimpleAdapter(this, listItems, R.layout.listview11,
				new String[] { "name", "header", "sex" }, new int[] {
						R.id.name, R.id.header, R.id.name_sex });

		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ListTest.this, WorkerHome.class);
				startActivity(intent);
				finish();
			}
		});
	}

	private void loadMoreDate() {
		int count = adapter.getCount();
		int addSize = 5;
		if (count + addSize < size) {
			for (int j = count; j < count + addSize; j++) {
				Map<String, Object> listItem = new HashMap<String, Object>();
				listItem.put("header", imageIds[j]);
				listItem.put("name", names[j]);
				listItem.put("sex", sexs[j]);
				listItems.add(listItem);
			}

		} else {
			for (int j = count; j < size; j++) {
				Map<String, Object> listItem = new HashMap<String, Object>();
				listItem.put("header", imageIds[j]);
				listItem.put("name", names[j]);
				listItem.put("sex", sexs[j]);
				listItems.add(listItem);
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;
		// 所有的条目已经和最大条数相等，则移除底部的View
		if (totalItemCount == size + 1) {
			listview.removeFooterView(loadMoreView);
			Toast.makeText(this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
			tv.setVisibility(View.INVISIBLE);
			pg.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& lastVisibleIndex == adapter.getCount()) {
			tv.setVisibility(View.INVISIBLE);
			pg.setVisibility(View.VISIBLE);
			loadMoreDate();
			try {
				Thread.sleep(1000);
				tv.setVisibility(View.VISIBLE);
				pg.setVisibility(View.INVISIBLE);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			adapter.notifyDataSetChanged();
		}

	}
}
