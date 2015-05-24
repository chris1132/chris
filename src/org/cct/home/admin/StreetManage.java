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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

import com.org.ifamily.entity.Admin;
import com.org.ifamily.entity.AdminroleKey;
import com.org.ifamily.implement.Adminimpl;
import com.org.ifamily.implement.Adminroleimpl;

public class StreetManage extends Activity implements OnScrollListener {

	private List<Admin> list_area = null;
	private List<AdminroleKey> list_role = null;
	private TextView text;
	private SimpleAdapter listItemAdapter;
	private Button load;
	private ProgressBar pg;
	private View moreView;
	private ProgressDialog dialog;
	private Handler handler;
	private Handler handler2;
	private ListView list;
	private int lastVisibleIndex;
	int isPerload;
	ArrayList<HashMap<String, Object>> listItem;
	Adminimpl adminimpl;
	int size;
	long areaid;
	int firstload = 10;
	int perload = 5;
	int k = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome_result3);

		text = (TextView) findViewById(R.id.manage_text);
		text.setText("街道管理员列表");
		list = (ListView) findViewById(R.id.manage_lian_listview);

		moreView = getLayoutInflater().inflate(R.layout.moredata, null);
		load = (Button) moreView.findViewById(R.id.bt_load);
		pg = (ProgressBar) moreView.findViewById(R.id.pg);

		ImageButton buttonBack = (ImageButton) findViewById(R.id.imageButton_result);
		dialog = ProgressDialog.show(StreetManage.this, "提示", "加载中，请稍后..");
		handler = new StreetManageHandler();
		buttonBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StreetManage.this.finish();
			}
		});
		Thread workthread = new StreetManageThread();
		workthread.start();

		list.setOnScrollListener(this);
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

	private class StreetManageThread extends Thread {

		@Override
		public void run() {
			Intent intent = getIntent();
			String string_value = intent.getExtras().getString("areaId");
			areaid = Long.parseLong(string_value);
			adminimpl = new Adminimpl();
			size = adminimpl.querybyareaidacount(areaid);

			Adminroleimpl adminroleimpl = new Adminroleimpl();
			list_role = adminroleimpl.querybyroleid(3);

			if (size < firstload) {
				firstload = size;
			}
			list_area = adminimpl.querybyareaidandroleidbypage(3, areaid, 0,
					firstload);
			Iterator<Admin> iterator = list_area.iterator();
			Iterator<AdminroleKey> iterator2 = list_role.iterator();

			listItem = new ArrayList<HashMap<String, Object>>();
			int i = 1;
			while (iterator.hasNext()) {
				Admin admin = iterator.next();
				int adminid1 = admin.getAdminid();
				while (iterator2.hasNext()) {
					AdminroleKey adminroleKey = iterator2.next();
					int adminid2 = adminroleKey.getAdminid();
					if (adminid2 == adminid1) {
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("manager3Numcount", i + ".");
						map.put("manager3adminName",
								"用户名: " + admin.getAdminname());
						map.put("manager3Name", "姓名: " + admin.getName());
						map.put("manager3Phone", "联系方式:" + admin.getPhonenum());
						map.put("manager3Address", "地址：" + admin.getAddress());
						listItem.add(map);
						i++;
					}
				}
				iterator2 = list_role.iterator();
			}
			// 生成适配器的Item和动态数组对应的元素
			listItemAdapter = new SimpleAdapter(
					StreetManage.this,
					listItem,// 数据源
					R.layout.listviewadmindiff,// ListItem的XML实现
					// 动态数组与ImageItem对应的子项
					new String[] { "manager3Numcount", "manager3adminName",
							"manager3Name", "manager3Phone", "manager3Address" },
					// ImageItem的XML文件里面的一个ImageView,两个TextView ID
					new int[] { R.id.managerNumcount, R.id.manageradminName,
							R.id.managerName, R.id.managerPhone,
							R.id.managerAddress });
			// 添加并且显示
			Message msg = handler.obtainMessage();
			msg.what = 1;
			handler.sendMessage(msg);

		}// run end

	}// Thread end

	class StreetManageHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				list.addFooterView(moreView);
				list.setAdapter(listItemAdapter);// display ListView
				dialog.dismiss();
			} else {
				dialog.dismiss();
				Toast.makeText(StreetManage.this, "未查询到任何记录",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	private void loadMoreDate() {
		int count = list.getCount();
		k = count;
		isPerload = 0;
		adminimpl = new Adminimpl();
		list_area = adminimpl.querybyareaidandroleidbypage(3, areaid,
				count - 1, size);
		Adminroleimpl adminroleimpl = new Adminroleimpl();
		list_role = adminroleimpl.querybyroleid(3);
		k++;
		if (list_area != null) {
			Iterator<Admin> iterator = list_area.iterator();
			Iterator<AdminroleKey> iterator2 = list_role.iterator();
			int i = count;
			while (iterator.hasNext() && isPerload < perload) {
				Admin admin = iterator.next();
				int adminid1 = admin.getAdminid();
				while (iterator2.hasNext()) {
					AdminroleKey adminroleKey = iterator2.next();
					int adminid2 = adminroleKey.getAdminid();
					if (adminid2 == adminid1) {
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("manager3Numcount", i + ".");
						map.put("manager3adminName",
								"用户名: " + admin.getAdminname());
						map.put("manager3Name", "姓名: " + admin.getName());
						map.put("manager3Phone", "联系方式:" + admin.getPhonenum());
						map.put("manager3Address", "地址：" + admin.getAddress());
						listItem.add(map);
						isPerload++;
						i++;
					}
				}
				iterator2 = list_role.iterator();
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;
		// 所有的条目已经和最大条数相等，则移除底部的View
		if (totalItemCount + 1 == k) {
			list.removeFooterView(moreView);
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
