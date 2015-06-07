package org.cct.home.use;

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
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.org.ifamily.entity.Cyclereminder;
import com.org.ifamily.entity.Host;
import com.org.ifamily.implement.Cyclereminderimpl;

public class CycleRemind extends Activity implements OnScrollListener {

	private Button use_3_1add;
	int i = 0, j;
	int size;
	private ListView listview;
	private ProgressDialog dialog;
	private ProgressBar pg;
	private TextView tv;
	private int lastVisibleIndex;
	private int initSize = 10;
	private int addSize = 5;
	private Handler handler2;
	Host host;
	ArrayList<HashMap<String, Object>> listItem;
	private View loadMoreView;
	Cyclereminderimpl cyclereminderimpl;
	List<Cyclereminder> cycleReminders;
	SimpleAdapter listItemAdapter;
	Cyclereminder[] cyclereminders;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.use_3_1);
		listview = (ListView) findViewById(R.id.recycleListView);

		loadMoreView = getLayoutInflater().inflate(R.layout.load_more, null);
		listview.addFooterView(loadMoreView);
		pg = (ProgressBar) loadMoreView.findViewById(R.id.loadingProgress);
		pg.setVisibility(View.INVISIBLE);
		tv = (TextView) loadMoreView.findViewById(R.id.loadMoreTextView);
		listview.setOnScrollListener(this);
		handler2 = new Handler();

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_use_3_1);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 返回上一级
				CycleRemind.this.finish();
			}
		});

		Intent intent = getIntent();
		host = (Host) intent.getSerializableExtra("HOSTSURE");
		dialog = ProgressDialog.show(this, "提示", "正在加载,请稍后..", true, false);

		use_3_1add = (Button) findViewById(R.id.use_3_1add);
		use_3_1add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(CycleRemind.this, CycleRemindAdd.class);
				intent.putExtra("HOSTSURE", host);
				CycleRemind.this.startActivity(intent);
				CycleRemind.this.finish();
			}
		});

		// 启动业务线程
		new CycleThread(this, host).start();

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final int k = position;
				new AlertDialog.Builder(CycleRemind.this)
						.setTitle("选择操作")
						.setPositiveButton(
								"编辑",
								new android.content.DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										Intent intent = new Intent(
												CycleRemind.this,
												CycleRemindEdit.class);
										intent.putExtra("CYCLEREMEMBER",
												cyclereminders[k]);
										intent.putExtra("HOSTSURE", host);
										CycleRemind.this.startActivity(intent);
										CycleRemind.this.finish();
									}

								})
						.setNegativeButton("删除",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										new AlertDialog.Builder(
												CycleRemind.this)
												.setTitle("提示")
												.setMessage("确定删除此提醒?")
												.setPositiveButton(
														"确定",
														new DialogInterface.OnClickListener() {

															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {
																boolean flag = cyclereminderimpl
																		.deletebyid(cyclereminders[k]
																				.getId());
																if (flag) {
																	finish();
																	CycleRemind.this
																			.finish();
																	Toast.makeText(
																			CycleRemind.this,
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

	private class CycleThread extends Thread {

		private CycleRemind activity;
		Host host;

		public CycleThread(CycleRemind act, Host host) {
			activity = act;
			this.host = host;
		}

		@Override
		public void run() {
			cyclereminderimpl = new Cyclereminderimpl();
			size = cyclereminderimpl.queryamount(host.getHostnum());
			if (initSize >= size)
				initSize = size;
			cycleReminders = cyclereminderimpl.querybypage(host.getHostnum(), 0,
					initSize);
			if (size == 0) {
				j = 1;
			} else {
				j = 0;
				cyclereminders = new Cyclereminder[size];
				for (Iterator<Cyclereminder> iterator = cycleReminders.iterator(); iterator
						.hasNext();) {
					cyclereminders[i] = iterator.next();
					i++;
				}
				// 生成动态数组，加入数据
				listItem = new ArrayList<HashMap<String, Object>>();
				for (int k = 0; k < initSize; k++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					String s;
					map.put("recyclenumcount", (k + 1) + ".");
					map.put("recycleItemcontent",
							"提醒内容: " + cyclereminders[k].getContent());
					map.put("recycleItemStartTime", "开始时间: "
							+ cyclereminders[k].getStarttime() + "");
					map.put("recycleItemInternalTime", "间隔时间(天): "
							+ cyclereminders[k].getBetween() + "");
					if (cyclereminders[k].getState() == null) {
						s = "无";
					} else {
						if (cyclereminders[k].getState()) {
							s = "启用";
						} else {
							s = "禁用";
						}
					}
					map.put("recycleItemInternalState", "当前状态: " + s);
					listItem.add(map);
				}
				// 生成适配器的Item和动态数组对应的元素
				listItemAdapter = new SimpleAdapter(activity, listItem,// 数据源
						R.layout.listviewrecycle,// ListItem的XML实现
						// 动态数组与ImageItem对应的子项
						new String[] { "recyclenumcount", "recycleItemcontent",
								"recycleItemStartTime",
								"recycleItemInternalTime",
								"recycleItemInternalState" },
						// ImageItem的XML文件里面的一个ImageView,两个TextView ID
						new int[] { R.id.recyclenumcount,
								R.id.recycleItemcontent,
								R.id.recycleItemStartTime,
								R.id.recycleItemInternalTime,
								R.id.recycleItemInternalState });
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
				Toast.makeText(CycleRemind.this, "未查询到任何记录", Toast.LENGTH_SHORT)
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
			cyclereminderimpl = new Cyclereminderimpl();
			cycleReminders = cyclereminderimpl.querybypage(host.getHostnum(), count, addSize);
			for (Iterator<Cyclereminder> iterator = cycleReminders.iterator(); iterator
					.hasNext();) {
				cyclereminders[i] = iterator.next();
				i++;
			}
			for (int k = count; k < count + addSize; k++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				String s;
				map.put("recyclenumcount", (k + 1) + ".");
				map.put("recycleItemcontent",
						"提醒内容: " + cyclereminders[k].getContent());
				map.put("recycleItemStartTime", "开始时间: "
						+ cyclereminders[k].getStarttime() + "");
				map.put("recycleItemInternalTime", "间隔时间(天): "
						+ cyclereminders[k].getBetween() + "");
				if (cyclereminders[k].getState() == null) {
					s = "无";
				} else {
					if (cyclereminders[k].getState()) {
						s = "启用";
					} else {
						s = "禁用";
					}
				}
				map.put("recycleItemInternalState", "当前状态: " + s);
				listItem.add(map);
			}
		} else {
			cyclereminderimpl = new Cyclereminderimpl();
			cycleReminders = cyclereminderimpl.querybypage(host.getHostnum(), count, size-count);
			for (Iterator<Cyclereminder> iterator = cycleReminders.iterator(); iterator
					.hasNext();) {
				cyclereminders[i] = iterator.next();
				i++;
			}
			for (int k = count; k < size; k++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				String s;
				map.put("recyclenumcount", (k + 1) + ".");
				map.put("recycleItemcontent",
						"提醒内容: " + cyclereminders[k].getContent());
				map.put("recycleItemStartTime", "开始时间: "
						+ cyclereminders[k].getStarttime() + "");
				map.put("recycleItemInternalTime", "间隔时间(天): "
						+ cyclereminders[k].getBetween() + "");
				if (cyclereminders[k].getState() == null) {
					s = "无";
				} else {
					if (cyclereminders[k].getState()) {
						s = "启用";
					} else {
						s = "禁用";
					}
				}
				map.put("recycleItemInternalState", "当前状态: " + s);
				listItem.add(map);
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
			tv.setVisibility(View.INVISIBLE);
			pg.setVisibility(View.INVISIBLE);
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
