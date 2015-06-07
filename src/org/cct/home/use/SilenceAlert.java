package org.cct.home.use;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.org.ifamily.entity.Host;
import com.org.ifamily.entity.Location;
import com.org.ifamily.entity.Sliencealert;
import com.org.ifamily.implement.Locationimpl;
import com.org.ifamily.implement.Sliencealertimpl;

public class SilenceAlert extends Activity {

	private Button btnset;
	int i, j;
	private ListView list;
	private ProgressDialog dialog;
	SimpleAdapter listItemAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.use_2_4);

		list = (ListView) findViewById(R.id.ListView2_4);
		ImageButton button = (ImageButton) findViewById(R.id.imageButton_use_2_4);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SilenceAlert.this.finish();
			}
		});

		Intent intent = getIntent();
		final Host host = (Host) intent.getSerializableExtra("HOSTSURE");
		dialog = ProgressDialog.show(this, "提示", "正在加载,请稍后..", true, false);

		// 启动业务线程
		new SilenceThread(this, host).start();

		btnset = (Button) findViewById(R.id.use_2_4set);
		btnset.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent(SilenceAlert.this,
						SilenceAlertSet.class);
				intent1.putExtra("HOSTSURE", host);
				SilenceAlert.this.startActivity(intent1);
			}
		});
	}

	private class SilenceThread extends Thread {
		private SilenceAlert activity;
		private Host host;

		public SilenceThread(SilenceAlert act, Host host) {
			activity = act;
			this.host = host;
		}

		@Override
		public void run() {
			Sliencealertimpl sliencealertimpl = new Sliencealertimpl();
			List<Sliencealert> sliencealerts = sliencealertimpl.query(host
					.getHostnum());
			i = 0;
			final int size = sliencealertimpl.queryamount(host.getHostnum());
			if (size == 0) {
				j = 1;
			} else {
				j = 0;
				Sliencealert mysliencealert[] = new Sliencealert[size];
				for (Iterator<Sliencealert> iterator = sliencealerts.iterator(); iterator
						.hasNext();) {
					mysliencealert[i] = iterator.next();
					i++;
				}

				Locationimpl locationimpl = new Locationimpl();
				List<Location> locations = locationimpl
						.query(host.getHostnum());
				Location[] mylocations = new Location[size];
				for (Iterator<Location> iterator = locations.iterator(); iterator
						.hasNext();) {
					Location location = iterator.next();
					for (int flag = 0; flag < size; flag++) {
						if (location.getLocid().equals(
								mysliencealert[flag].getLocid())) {
							mylocations[flag] = location;
							break;
						}
					}
				}

				// 生成动态数组，加入数据
				ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < size; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("numcount", (i + 1) + ".");
					map.put("ItemLatLng",
							"经纬度: " + "(" + mylocations[i].getLat() + ","
									+ mylocations[i].getLng() + ")");
					map.put("ItemLocation",
							"实际位置: " + mylocations[i].getAddress());
					Date date = mysliencealert[i].getUpdatetime();
					SimpleDateFormat dft = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String time = dft.format(date);
					map.put("ItemTime", time);
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
			switch (j) {
			case 0:
				listItemAdapter.notifyDataSetChanged(); // 发送消息更新ListView
				list.setAdapter(listItemAdapter); // 重新设置ListView的数据适配器
				break;

			case 1:
				Toast.makeText(SilenceAlert.this, "未查询到任何记录",
						Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}

	};
}
