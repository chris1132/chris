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

import com.org.ifamily.entity.Host;
import com.org.ifamily.entity.Oncereminder;
import com.org.ifamily.implement.Oncereminderimpl;

public class OnceRemind extends Activity implements OnScrollListener {
	private Button sure;
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
	Oncereminderimpl oncereminderimpl;
	List<Oncereminder> onceReminders;
	SimpleAdapter listItemAdapter;
	Oncereminder[] oncereminders;
	Iterator<Oncereminder> iterator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.use_3_2);
		listview = (ListView) findViewById(R.id.onceListView);

		loadMoreView = getLayoutInflater().inflate(R.layout.load_more, null);
		listview.addFooterView(loadMoreView);
		pg = (ProgressBar) loadMoreView.findViewById(R.id.loadingProgress);
		pg.setVisibility(View.INVISIBLE);
		tv = (TextView) loadMoreView.findViewById(R.id.loadMoreTextView);
		listview.setOnScrollListener(this);
		handler2 = new Handler();

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_use_3_2);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				OnceRemind.this.finish();

			}
		});

		Intent intent = getIntent();
		host = (Host) intent.getSerializableExtra("HOSTSURE");
		dialog = ProgressDialog.show(this, "提示", "正在加载,请稍后..", true, false);

		sure = (Button) findViewById(R.id.use_3_2add);
		sure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(OnceRemind.this, OnceRemindAdd.class);
				intent.putExtra("HOSTSURE", host);
				OnceRemind.this.startActivity(intent);
				OnceRemind.this.finish();
			}
		});

		// 启动业务线程
		new OnceThread(this, host).start();

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final int k = position;
				new AlertDialog.Builder(OnceRemind.this)
						.setTitle("选择操作")
						.setPositiveButton(
								"编辑",
								new android.content.DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										Intent intent = new Intent(
												OnceRemind.this,
												OnceRemindEdit.class);
										intent.putExtra("ONCEREMINDER",
												oncereminders[k]);
										intent.putExtra("HOSTSURE", host);
										OnceRemind.this.startActivity(intent);
										OnceRemind.this.finish();
									}

								})
						.setNegativeButton("删除",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										new AlertDialog.Builder(OnceRemind.this)
												.setTitle("提示")
												.setMessage("确定删除此提醒?")
												.setPositiveButton(
														"确定",
														new DialogInterface.OnClickListener() {

															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {
																boolean flag = oncereminderimpl
																		.deletebyid(oncereminders[k]
																				.getId());
																if (flag) {
																	finish();
																	OnceRemind.this
																			.finish();
																	Toast.makeText(
																			OnceRemind.this,
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

	private class OnceThread extends Thread {

		private OnceRemind activity;
		Host host;

		public OnceThread(OnceRemind act, Host host) {
			activity = act;
			this.host = host;
		}

		@Override
		public void run() {
			oncereminderimpl = new Oncereminderimpl();
			size = oncereminderimpl.queryamount(host.getHostnum());
			if (initSize >= size)
				initSize = size;
			onceReminders = oncereminderimpl.querybypage(host.getHostnum(), 0,
					initSize);
			if (size == 0) {
				j = 1;
			} else {
				j = 0;
				oncereminders = new Oncereminder[size];
				for (Iterator<Oncereminder> iterator = onceReminders.iterator(); iterator
						.hasNext();) {
					oncereminders[i] = iterator.next();
					i++;
				}

				// 生成动态数组，加入数据
				listItem = new ArrayList<HashMap<String, Object>>();
				for (int k = 0; k < initSize; k++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					String s;
					map.put("oncenumcount", (k + 1) + ".");
					map.put("onceItemcontent",
							"提醒内容: " + oncereminders[k].getContent());
					map.put("onceItemTime",
							"提醒时间: " + oncereminders[k].getTime() + "");
					if (oncereminders[k].getState() == null) {
						s = "无";
					} else {
						if (oncereminders[k].getState()) {
							s = "启用";
						} else {
							s = "禁用";
						}
					}
					map.put("onceItemState", "当前状态: " + s);
					listItem.add(map);
				}
				// 生成适配器的Item和动态数组对应的元素
				listItemAdapter = new SimpleAdapter(activity, listItem,// 数据源
						R.layout.listviewonce,// ListItem的XML实现
						// 动态数组与ImageItem对应的子项
						new String[] { "oncenumcount", "onceItemcontent",
								"onceItemTime", "onceItemState" },
						// ImageItem的XML文件里面的一个ImageView,两个TextView ID
						new int[] { R.id.oncenumcount, R.id.onceItemcontent,
								R.id.onceItemTime, R.id.onceItemState });
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
				Toast.makeText(OnceRemind.this, "未查询到任何记录。", Toast.LENGTH_SHORT)
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
			oncereminderimpl = new Oncereminderimpl();
			onceReminders = oncereminderimpl.querybypage(host.getHostnum(),
					count, addSize);

			for (Iterator<Oncereminder> iterator = onceReminders.iterator(); iterator
					.hasNext();) {
				oncereminders[i] = iterator.next();
				i++;
			}

			for (int k = count; k < count + addSize; k++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				String s;
				map.put("oncenumcount", (k + 1) + ".");
				map.put("onceItemcontent",
						"提醒内容: " + oncereminders[k].getContent());
				map.put("onceItemTime", "提醒时间: " + oncereminders[k].getTime()
						+ "");
				if (oncereminders[k].getState() == null) {
					s = "无";
				} else {
					if (oncereminders[k].getState()) {
						s = "启用";
					} else {
						s = "禁用";
					}
				}
				map.put("onceItemState", "当前状态: " + s);
				listItem.add(map);
			}
		} else {
			oncereminderimpl = new Oncereminderimpl();
			onceReminders = oncereminderimpl.querybypage(host.getHostnum(),
					count, size - count);
			for (Iterator<Oncereminder> iterator = onceReminders.iterator(); iterator
					.hasNext();) {
				oncereminders[i] = iterator.next();
				i++;
			}
			for (int k = count; k < size; k++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				String s;
				map.put("oncenumcount", (k + 1) + ".");
				map.put("onceItemcontent",
						"提醒内容: " + oncereminders[k].getContent());
				map.put("onceItemTime", "提醒时间: " + oncereminders[k].getTime()
						+ "");
				if (oncereminders[k].getState() == null) {
					s = "无";
				} else {
					if (oncereminders[k].getState()) {
						s = "启用";
					} else {
						s = "禁用";
					}
				}
				map.put("onceItemState", "当前状态: " + s);
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
