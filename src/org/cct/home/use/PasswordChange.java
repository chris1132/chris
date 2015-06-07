package org.cct.home.use;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cct.home.R;
import org.cct.home.map.MapActivity;
import org.cct.home.welcome.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import com.org.ifamily.entity.Host;
import com.org.ifamily.entity.User;
import com.org.ifamily.implement.Userimpl;

public class PasswordChange extends Activity {

	private EditText alterEditText1;
	private EditText alterEditText2;
	private EditText alterEditText3;
	private Button alterbtn;
	private User user = null;
	private Userimpl userimpl = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.use_7_2);
		// final DrawerLayout mDrawerLayout = (DrawerLayout)
		// findViewById(R.id.drawer_layout);

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_use_7_2);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 按钮按下，将抽屉打开
				PasswordChange.this.finish();

			}
		});
		/*
		 * List<Map<String, String>> groups = new ArrayList<Map<String,
		 * String>>(); Map<String, String> homepage = new HashMap<String,
		 * String>(); homepage.put("group", "首页");
		 * 
		 * Map<String, String> police = new HashMap<String, String>();
		 * police.put("group", "报警记录");
		 * 
		 * Map<String, String> health = new HashMap<String, String>();
		 * health.put("group", "健康管理");
		 * 
		 * Map<String, String> careob = new HashMap<String, String>();
		 * careob.put("group", "关怀对象");
		 * 
		 * Map<String, String> place = new HashMap<String, String>();
		 * place.put("group", "位置查询");
		 * 
		 * Map<String, String> lifehelp = new HashMap<String, String>();
		 * lifehelp.put("group", "生活助手");
		 * 
		 * Map<String, String> manage = new HashMap<String, String>();
		 * manage.put("group", "账户管理");
		 * 
		 * groups.add(homepage); groups.add(police); groups.add(health);
		 * groups.add(careob); groups.add(place); groups.add(lifehelp);
		 * groups.add(manage);
		 * 
		 * // 首页 List<Map<String, String>> c_home = new ArrayList<Map<String,
		 * String>>(); Map<String, String> c_home_data = new HashMap<String,
		 * String>(); c_home_data.put("child", "选择关怀对象");
		 * c_home.add(c_home_data);
		 * 
		 * // 报警记录 List<Map<String, String>> c_police = new
		 * ArrayList<Map<String, String>>(); Map<String, String> c_police_data1
		 * = new HashMap<String, String>(); c_police_data1.put("child",
		 * "SOS记录"); c_police.add(c_police_data1); // Map<String, String>
		 * c_police_data2 = new HashMap<String, String>(); //
		 * c_police_data2.put("child", "高温记录"); // c_police.add(c_police_data2);
		 * // Map<String, String> c_police_data3 = new HashMap<String,
		 * String>(); // c_police_data3.put("child", "SOS记录"); //
		 * c_police.add(c_police_data3);
		 * 
		 * // 健康管理 List<Map<String, String>> c_health = new
		 * ArrayList<Map<String, String>>(); Map<String, String> c_health_data1
		 * = new HashMap<String, String>(); c_health_data1.put("child", "循环提醒");
		 * c_health.add(c_health_data1); Map<String, String> c_health_data2 =
		 * new HashMap<String, String>(); c_health_data2.put("child", "一次性提醒");
		 * c_health.add(c_health_data2); Map<String, String> c_health_data3 =
		 * new HashMap<String, String>(); c_health_data3.put("child", "按周/月提醒");
		 * c_health.add(c_health_data3); // Map<String, String> c_health_data4 =
		 * new HashMap<String, String>(); // c_health_data4.put("child",
		 * "健康详情"); // c_health.add(c_health_data4); // Map<String, String>
		 * c_health_data5 = new HashMap<String, String>(); //
		 * c_health_data5.put("child", "健康月报"); // c_health.add(c_health_data5);
		 * // Map<String, String> c_health_data6 = new HashMap<String,
		 * String>(); // c_health_data6.put("child", "计步器"); //
		 * c_health.add(c_health_data6);
		 * 
		 * // 关怀对象 List<Map<String, String>> c_careob = new
		 * ArrayList<Map<String, String>>(); Map<String, String> c_careob_data1
		 * = new HashMap<String, String>(); c_careob_data1.put("child", "基本信息");
		 * c_careob.add(c_careob_data1); // Map<String, String> c_careob_data2 =
		 * new HashMap<String, String>(); // c_careob_data2.put("child",
		 * "自理情况"); // c_careob.add(c_careob_data2); // Map<String, String>
		 * c_careob_data3 = new HashMap<String, String>(); //
		 * c_careob_data3.put("child", "健康信息"); // c_careob.add(c_careob_data3);
		 * // Map<String, String> c_careob_data4 = new HashMap<String,
		 * String>(); // c_careob_data4.put("child", "血压/脉搏信息"); //
		 * c_careob.add(c_careob_data4);
		 * 
		 * // 位置查询 List<Map<String, String>> c_place = new ArrayList<Map<String,
		 * String>>(); Map<String, String> c_place_data1 = new HashMap<String,
		 * String>(); c_place_data1.put("child", "当前位置");
		 * c_place.add(c_place_data1); // Map<String, String> c_place_data2 =
		 * new HashMap<String, String>(); // c_place_data2.put("child", "轨迹回放");
		 * // c_place.add(c_place_data2); // Map<String, String> c_place_data3 =
		 * new HashMap<String, String>(); // c_place_data3.put("child", "电子围栏");
		 * // c_place.add(c_place_data3);
		 * 
		 * // 生活助手 List<Map<String, String>> c_lifehelp = new
		 * ArrayList<Map<String, String>>(); Map<String, String>
		 * c_lifehelp_data1 = new HashMap<String, String>();
		 * c_lifehelp_data1.put("child", "快捷键设置");
		 * c_lifehelp.add(c_lifehelp_data1); Map<String, String>
		 * c_lifehelp_data2 = new HashMap<String, String>();
		 * c_lifehelp_data2.put("child", "社区服务热线");
		 * c_lifehelp.add(c_lifehelp_data2);
		 * 
		 * // 账户管理 List<Map<String, String>> c_manage = new
		 * ArrayList<Map<String, String>>(); Map<String, String> c_manage_data1
		 * = new HashMap<String, String>(); c_manage_data1.put("child", "用户信息");
		 * c_manage.add(c_manage_data1); Map<String, String> c_manage_data2 =
		 * new HashMap<String, String>(); c_manage_data2.put("child", "密码修改");
		 * c_manage.add(c_manage_data2); Map<String, String> c_manage_data3 =
		 * new HashMap<String, String>(); c_manage_data3.put("child", "退出登录");
		 * c_manage.add(c_manage_data3);
		 * 
		 * // 定义一个List，该List对象用来存储所有的二级条目的数据 List<List<Map<String, String>>>
		 * childs = new ArrayList<List<Map<String, String>>>();
		 * childs.add(c_home); childs.add(c_police); childs.add(c_health);
		 * childs.add(c_careob); childs.add(c_place); childs.add(c_lifehelp);
		 * childs.add(c_manage);
		 * 
		 * final SimpleExpandableListAdapter sela = new
		 * SimpleExpandableListAdapter( this, groups, R.layout.use_home_group,
		 * new String[] { "group" }, new int[] { R.id.groupTo }, childs,
		 * R.layout.use_home_child, new String[] { "child" }, new int[] {
		 * R.id.childTo });
		 * 
		 * setListAdapter(sela);
		 */

		alterEditText1 = (EditText) findViewById(R.id.alterEditText1);
		alterEditText2 = (EditText) findViewById(R.id.alterEditText2);
		alterEditText3 = (EditText) findViewById(R.id.alterEditText3);
		alterbtn = (Button) findViewById(R.id.alterbtn);

		userimpl = new Userimpl();
		user = userimpl.querybyuserid(UseLogin.useId);
		final int userid = user.getUserid();

		alterbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String old_password = alterEditText1.getText().toString()
						.trim();
				final String new_password = alterEditText2.getText().toString()
						.trim();

				if (!alterEditText1.getText().toString().trim()
						.equals(user.getPassword())) {
					Toast.makeText(PasswordChange.this, "输入的旧密码不正确，请重新输入",
							Toast.LENGTH_SHORT).show();
					alterEditText1.setText("");
					alterEditText2.setText("");
					alterEditText3.setText("");
				} else if (!alterEditText2.getText().toString().trim()
						.equals(alterEditText3.getText().toString().trim())) {
					Toast.makeText(PasswordChange.this, "两次输入的新密码不一致，请重新输入",
							Toast.LENGTH_SHORT).show();
					alterEditText1.setText("");
					alterEditText2.setText("");
					alterEditText3.setText("");
				} else if (alterEditText2.getText().toString().trim()
						.equals("")) {
					Toast.makeText(PasswordChange.this, "输入的新密码不能仅为空格，请重新输入",
							Toast.LENGTH_SHORT).show();
					alterEditText1.setText("");
					alterEditText2.setText("");
					alterEditText3.setText("");
				} else {
					new AlertDialog.Builder(PasswordChange.this)
							.setTitle("提示")
							.setMessage("确定保存修改？")
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int whichButton) {
											boolean check = userimpl
													.changepassword(userid,
															old_password,
															new_password);
											if (!check) {
												Toast.makeText(PasswordChange.this,
														"密码修改失败...",
														Toast.LENGTH_SHORT)
														.show();
											} else {
												Toast.makeText(PasswordChange.this,
														"密码修改成功！",
														Toast.LENGTH_SHORT)
														.show();
												PasswordChange.this.finish();
											}
										}
									})
							.setNegativeButton("取消",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int whichButton) {
											// 取消按钮事件
										}
									}).show();
				}
			}
		});

		// Intent intent = getIntent();
		// final Host host = (Host) intent.getSerializableExtra("HOSTSELECT");
		/*
		 * ExpandableListView expandableListView = (ExpandableListView)
		 * findViewById(android.R.id.list);
		 * expandableListView.setOnChildClickListener(new OnChildClickListener()
		 * {
		 * 
		 * // 侧边菜单的监听在这里定义，套用了双层swich。。。case
		 * 
		 * @Override public boolean onChildClick(ExpandableListView parent, View
		 * v, int groupPosition, int childPosition, long id) { switch ((int)
		 * sela.getGroupId(groupPosition)) { case 0: switch ((int)
		 * sela.getChildId(groupPosition, childPosition)) { case 0: Intent
		 * intent1 = new Intent(); intent1.setClass(PasswordChange.this,
		 * UseCareSelevt.class); startActivity(intent1); PasswordChange.this.finish(); break;
		 * 
		 * default: break; } break; case 1: switch ((int)
		 * sela.getChildId(groupPosition, childPosition)) { case 0: if (host ==
		 * null) { Toast.makeText(PasswordChange.this, "请先选择对象",
		 * Toast.LENGTH_LONG).show(); } else { Intent intent = new Intent();
		 * intent.setClass(PasswordChange.this, SosAlert.class);
		 * intent.putExtra("HOSTSURE", host); startActivity(intent);
		 * PasswordChange.this.finish(); } break;
		 * 
		 * case 1: if (host == null) { Toast.makeText(PasswordChange.this, "请先选择对象",
		 * Toast.LENGTH_LONG).show(); } else { Intent intent1 = new Intent();
		 * intent1.setClass(PasswordChange.this, TempreAlert.class);
		 * intent1.putExtra("HOSTSURE", host); startActivity(intent1);
		 * PasswordChange.this.finish(); } break;
		 * 
		 * case 2: if (host == null) { Toast.makeText(PasswordChange.this, "请先选择对象",
		 * Toast.LENGTH_LONG).show(); } else { Intent intent2 = new Intent();
		 * intent2.setClass(PasswordChange.this, FallAlert.class);
		 * intent2.putExtra("HOSTSURE", host); startActivity(intent2);
		 * PasswordChange.this.finish(); } break;
		 * 
		 * default: break; } break; case 2: switch ((int)
		 * sela.getChildId(groupPosition, childPosition)) { case 0: if (host ==
		 * null) { Toast.makeText(PasswordChange.this, "请先选择对象",
		 * Toast.LENGTH_LONG).show(); } else { Intent intent = new Intent();
		 * intent.setClass(PasswordChange.this, CycleRemind.class);
		 * intent.putExtra("HOSTSURE", host); startActivity(intent);
		 * PasswordChange.this.finish(); } break;
		 * 
		 * case 1: if (host == null) { Toast.makeText(PasswordChange.this, "请先选择对象",
		 * Toast.LENGTH_LONG).show(); } else { Intent intent1 = new Intent();
		 * intent1.setClass(PasswordChange.this, OnceRemind.class);
		 * intent1.putExtra("HOSTSURE", host); startActivity(intent1);
		 * PasswordChange.this.finish(); } break;
		 * 
		 * case 2: if (host == null) { Toast.makeText(PasswordChange.this, "请先选择对象",
		 * Toast.LENGTH_LONG).show(); } else { Intent intent2 = new Intent();
		 * intent2.setClass(PasswordChange.this, WeekRemind.class);
		 * intent2.putExtra("HOSTSURE", host); startActivity(intent2);
		 * PasswordChange.this.finish(); } break;
		 * 
		 * case 3: if (host == null) { Toast.makeText(PasswordChange.this, "请先选择对象",
		 * Toast.LENGTH_LONG).show(); } else { Intent intent3 = new Intent();
		 * intent3.setClass(PasswordChange.this, use_3_4.class);
		 * intent3.putExtra("HOSTSURE", host); startActivity(intent3);
		 * PasswordChange.this.finish(); } break;
		 * 
		 * case 4: if (host == null) { Toast.makeText(PasswordChange.this, "请先选择对象",
		 * Toast.LENGTH_LONG).show(); } else { Intent intent4 = new Intent();
		 * intent4.setClass(PasswordChange.this, use_3_5.class);
		 * intent4.putExtra("HOSTSURE", host); startActivity(intent4);
		 * PasswordChange.this.finish(); } break;
		 * 
		 * case 5: if (host == null) { Toast.makeText(PasswordChange.this, "请先选择对象",
		 * Toast.LENGTH_LONG).show(); } else { Intent intent5 = new Intent();
		 * intent5.setClass(PasswordChange.this, use_3_6.class);
		 * intent5.putExtra("HOSTSURE", host); startActivity(intent5);
		 * PasswordChange.this.finish(); } break;
		 * 
		 * default: break; }
		 * 
		 * break;
		 * 
		 * case 3: switch ((int) sela.getChildId(groupPosition, childPosition))
		 * { case 0: if (host == null) { Toast.makeText(PasswordChange.this, "请先选择对象",
		 * Toast.LENGTH_LONG).show(); } else { Intent intent1 = new Intent();
		 * intent1.setClass(PasswordChange.this, CareInformation.class);
		 * intent1.putExtra("HOSTSURE", host); startActivity(intent1);
		 * PasswordChange.this.finish(); } break;
		 * 
		 * case 1: if (host == null) { Toast.makeText(PasswordChange.this, "请先选择对象",
		 * Toast.LENGTH_LONG).show(); } else { Intent intent2 = new Intent();
		 * intent2.setClass(PasswordChange.this, use_4_2.class);
		 * intent2.putExtra("HOSTSURE", host); startActivity(intent2);
		 * PasswordChange.this.finish(); } break;
		 * 
		 * case 2: if (host == null) { Toast.makeText(PasswordChange.this, "请先选择对象",
		 * Toast.LENGTH_LONG).show(); } else { Intent intent3 = new Intent();
		 * intent3.setClass(PasswordChange.this, use_4_3.class);
		 * intent3.putExtra("HOSTSURE", host); startActivity(intent3);
		 * PasswordChange.this.finish(); } break;
		 * 
		 * case 3: if (host == null) { Toast.makeText(PasswordChange.this, "请先选择对象",
		 * Toast.LENGTH_LONG).show(); } else { Intent intent4 = new Intent();
		 * intent4.setClass(PasswordChange.this, use_4_4.class);
		 * intent4.putExtra("HOSTSURE", host); startActivity(intent4);
		 * PasswordChange.this.finish(); } break;
		 * 
		 * default: break; } break;
		 * 
		 * case 4: switch ((int) sela.getChildId(groupPosition, childPosition))
		 * { case 0: if (host == null) { Toast.makeText(PasswordChange.this, "请先选择对象",
		 * Toast.LENGTH_LONG).show(); } else { Intent intent1 = new Intent();
		 * intent1.setClass(PasswordChange.this, MapActivity.class);
		 * intent1.putExtra("HOSTSURE", host); startActivity(intent1);
		 * PasswordChange.this.finish(); } break;
		 * 
		 * case 1: if (host == null) { Toast.makeText(PasswordChange.this, "请先选择对象",
		 * Toast.LENGTH_LONG).show(); } else { Intent intent2 = new Intent();
		 * intent2.setClass(PasswordChange.this, use_5_2.class);
		 * intent2.putExtra("HOSTSURE", host); startActivity(intent2);
		 * PasswordChange.this.finish(); } break;
		 * 
		 * case 2: if (host == null) { Toast.makeText(PasswordChange.this, "请先选择对象",
		 * Toast.LENGTH_LONG).show(); } else { Intent intent3 = new Intent();
		 * intent3.setClass(PasswordChange.this, use_5_3.class);
		 * intent3.putExtra("HOSTSURE", host); startActivity(intent3);
		 * PasswordChange.this.finish(); } break;
		 * 
		 * default: break; } break;
		 * 
		 * case 5: switch ((int) sela.getChildId(groupPosition, childPosition))
		 * { case 0: if (host == null) { Toast.makeText(PasswordChange.this, "请先选择对象",
		 * Toast.LENGTH_LONG).show(); } else { Intent intent1 = new Intent();
		 * intent1.setClass(PasswordChange.this, QuickSet.class);
		 * intent1.putExtra("HOSTSURE", host); startActivity(intent1);
		 * PasswordChange.this.finish(); } break;
		 * 
		 * case 1: Uri uri = Uri.parse("tel:6581890");
		 * 
		 * Intent intent = new Intent(Intent.ACTION_DIAL, uri);
		 * 
		 * startActivity(intent);
		 * 
		 * break;
		 * 
		 * default: break; } break;
		 * 
		 * case 6: switch ((int) sela.getChildId(groupPosition, childPosition))
		 * { case 0: if (host == null) { Toast.makeText(PasswordChange.this, "请先选择对象",
		 * Toast.LENGTH_LONG).show(); } else { Intent intent1 = new Intent();
		 * intent1.setClass(PasswordChange.this, UseInformation.class);
		 * intent1.putExtra("HOSTSURE", host); startActivity(intent1);
		 * PasswordChange.this.finish(); } break;
		 * 
		 * case 1: if (host == null) { Toast.makeText(PasswordChange.this, "请先选择对象",
		 * Toast.LENGTH_LONG).show(); } else { Intent intent2 = new Intent();
		 * intent2.setClass(PasswordChange.this, PasswordChange.class);
		 * intent2.putExtra("HOSTSURE", host); startActivity(intent2);
		 * PasswordChange.this.finish(); } break;
		 * 
		 * case 2: Intent intent = new Intent(); intent.setClass(PasswordChange.this,
		 * home.class); intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 * PasswordChange.this.startActivity(intent); PasswordChange.this.finish(); break;
		 * 
		 * default: break; } break;
		 * 
		 * default: break; } return false; } });
		 */
	}
}
