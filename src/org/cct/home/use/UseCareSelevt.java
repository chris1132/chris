package org.cct.home.use;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.cct.home.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import com.org.ifamily.entity.Host;
import com.org.ifamily.implement.Hostimpl;

public class UseCareSelevt extends Activity implements OnScrollListener {
	int i = 0;
	int size;
	private ListView listview;
	private View loadMoreView;
	private ProgressBar pg;
	private TextView tv;
	Hostimpl hostimpl;
	Host host[];
	List<Host> list;
	Iterator<Host> iterator;
	SimpleAdapter listItemAdapter;
	// 最后可见条目的索引
	private int lastVisibleIndex;
	private Handler handler2;
	ArrayList<HashMap<String, Object>> listItem;
	private int initSize = 10;
	private int addSize = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.use_1_1);
		listview = (ListView) findViewById(R.id.firstListView);

		loadMoreView = getLayoutInflater().inflate(R.layout.load_more, null);
		pg = (ProgressBar) loadMoreView.findViewById(R.id.loadingProgress);
		pg.setVisibility(View.INVISIBLE);
		tv = (TextView) loadMoreView.findViewById(R.id.loadMoreTextView);
		listview.addFooterView(loadMoreView);
		listview.setOnScrollListener(this);
		handler2 = new Handler();

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_use_1_1);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				UseCareSelevt.this.finish();
			}
		});
		// 宿主接口
		hostimpl = new Hostimpl();
		size = hostimpl.querybyuseridacount(UseLogin.useId);
		host = new Host[size];
		if (initSize >= size)
			initSize = size;
		list = hostimpl.querybyuseridbypage(UseLogin.useId, 0, initSize);
		if (size == 0) {
			Toast.makeText(UseCareSelevt.this, "未查询到任何记录", Toast.LENGTH_SHORT)
					.show();
		} else {
			iterator = list.iterator();
			while (iterator.hasNext()) {
				host[i] = iterator.next();
				i++;
			}
			// 生成动态数组，加入数据
			listItem = new ArrayList<HashMap<String, Object>>();
			for (int j = 0; j < initSize; j++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("selectnumcount", "编号:" + (j + 1) + ".");
				map.put("selectItemcontent", "关怀对象姓名: " + host[j].getHostname());
				listItem.add(map);
			}
			// 生成适配器的Item和动态数组对应的元素
			listItemAdapter = new SimpleAdapter(this,
					listItem,// 数据源
					R.layout.listviewusefirst,// ListItem的XML实现
					new String[] { "selectnumcount", "selectItemcontent" },
					new int[] { R.id.selectnumcount, R.id.selectItemcontent });

			// 添加并且显示
			listview.setAdapter(listItemAdapter);

			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					final int k = position;
					Intent intent = new Intent(UseCareSelevt.this,
							UseHome.class);
					intent.putExtra("HOSTSELECT", host[k]);
					UseCareSelevt.this.startActivity(intent);
					UseCareSelevt.this.finish();
				}
			});
		}
	}

	private void loadMoreDate() {
		int count = listItemAdapter.getCount();
		if (count + addSize < size) {
			// on create 的接口对象被销毁
			hostimpl = new Hostimpl();
			list = hostimpl.querybyuseridbypage(UseLogin.useId, count, addSize);
			iterator = list.iterator();
			while (iterator.hasNext()) {
				host[i] = iterator.next();
				i++;
			}
			for (int j = count; j < count + addSize; j++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("selectnumcount", "编号:" + (j + 1) + ".");
				map.put("selectItemcontent", "关怀对象姓名: " + host[j].getHostname());
				listItem.add(map);
			}
		} else {
			hostimpl = new Hostimpl();
			list = hostimpl.querybyuseridbypage(UseLogin.useId, count, size
					- count);
			iterator = list.iterator();
			while (iterator.hasNext()) {
				host[i] = iterator.next();
				i++;
			}
			for (int j = count; j < size; j++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("selectnumcount", "编号:" + (j + 1) + ".");
				map.put("selectItemcontent", "关怀对象姓名: " + host[j].getHostname());
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
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
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
