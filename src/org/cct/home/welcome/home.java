package org.cct.home.welcome;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.cct.home.R;
import org.cct.home.admin.AdminLogin;
import org.cct.home.autoupdate.Config;
import org.cct.home.autoupdate.NetworkTools;
import org.cct.home.use.UseLogin;
import org.cct.home.worker.WorkerLogin;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class home extends Activity {
	private boolean is2CallBack = false;
	private Button uselogin = null;
	private Button adminlogin = null;
	private Button workerlogin = null;

	private static final String TAG = "Update";
	public ProgressDialog pBar;
	private Handler handler = new Handler();

	private int newVerCode = -1;
	private String newVerName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		uselogin = (Button) findViewById(R.id.uselogin);
		adminlogin = (Button) findViewById(R.id.adminlogin);
		workerlogin = (Button) findViewById(R.id.workerlogin);

		uselogin.setOnClickListener(new UseLoginListener());
		adminlogin.setOnClickListener(new AdminLoginListener());
		workerlogin.setOnClickListener(new WorkerLoginListener());

		if (getServerVerCode()) {
			int vercode = Config.getVerCode(this);
			if (newVerCode > vercode) {
				doNewVersionUpdate();
			} else {
				// notNewVersionShow();
			}
		}

	}

	class UseLoginListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (isNetworkConnected(home.this)) {
				Intent useintent = new Intent();
				useintent.setClass(home.this, UseLogin.class);
				home.this.startActivity(useintent);
			} else {
				Toast.makeText(home.this, "网络未连接,请先开启网络连接!", Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	class AdminLoginListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			if (isNetworkConnected(home.this)) {
				handler.post(new Runnable() {
					public void run() {
						Intent adminintent = new Intent();
						adminintent.setClass(home.this, AdminLogin.class);
						home.this.startActivity(adminintent);
					}
				});

			} else {
				Toast.makeText(home.this, "网络未连接,请先开启网络连接!", Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	class WorkerLoginListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (isNetworkConnected(home.this)) {
				handler.post(new Runnable() {
					public void run() {
						Intent adminintent = new Intent();
						adminintent.setClass(home.this, WorkerLogin.class);
						home.this.startActivity(adminintent);
					}
				});

			} else {
				Toast.makeText(home.this, "网络未连接,请先开启网络连接!", Toast.LENGTH_LONG)
						.show();
			}
		}

	}

	public boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	private boolean getServerVerCode() {
		try {
			String verjson = NetworkTools.getContent(Config.UPDATE_SERVER
					+ Config.UPDATE_VERJSON);
			JSONArray array = new JSONArray(verjson);
			if (array.length() > 0) {
				JSONObject obj = array.getJSONObject(0);
				try {
					newVerCode = Integer.parseInt(obj.getString("verCode"));
					newVerName = obj.getString("verName");
				} catch (Exception e) {
					newVerCode = -1;
					newVerName = "";
					return false;
				}
			}
		} catch (Exception e) {
			// Log.e(TAG, e.getMessage());
			return false;
		}
		return true;
	}

	private void doNewVersionUpdate() {
		int verCode = Config.getVerCode(this);
		String verName = Config.getVerName(this);
		StringBuffer sb = new StringBuffer();
		sb.append("当前版本:");
		sb.append(verName);
		sb.append(" Code:");
		sb.append(verCode);
		sb.append(", 发现新版本:");
		sb.append(newVerName);
		sb.append(" Code:");
		sb.append(newVerCode);
		sb.append(", 是否更新?");
		Dialog dialog = new AlertDialog.Builder(home.this)
				.setTitle("软件更新")
				.setMessage(sb.toString())
				// 设置内容
				.setPositiveButton("更新",// 设置确定按钮
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								pBar = new ProgressDialog(home.this);
								pBar.setTitle("正在下载");
								pBar.setMessage("请稍候...");
								pBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
								downFile(Config.UPDATE_SERVER
										+ Config.UPDATE_APKNAME);
							}

						})
				.setNegativeButton("暂不更新",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// 点击"取消"按钮之后退出程序
								// down();
							}
						}).create();// 创建
		// 显示对话框
		dialog.show();
	}

	void downFile(final String url) {
		pBar.show();
		new Thread() {
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(url);
				HttpResponse response;
				try {
					response = client.execute(get);
					HttpEntity entity = response.getEntity();
					long length = entity.getContentLength();
					InputStream is = entity.getContent();
					FileOutputStream fileOutputStream = null;
					if (is != null) {

						File file = new File(
								Environment.getExternalStorageDirectory(),
								Config.UPDATE_SAVENAME);
						fileOutputStream = new FileOutputStream(file);

						byte[] buf = new byte[1024];
						int ch = -1;
						int count = 0;
						while ((ch = is.read(buf)) != -1) {
							fileOutputStream.write(buf, 0, ch);
							count += ch;
							if (length > 0) {
							}
						}

					}
					fileOutputStream.flush();
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					down();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}.start();

	}

	void down() {
		handler.post(new Runnable() {
			public void run() {
				pBar.cancel();
				update();
			}
		});

	}

	void update() {

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), Config.UPDATE_SAVENAME)),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!is2CallBack) {
				is2CallBack = true;
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						is2CallBack = false;
					}
				}, 2500);

			} else {
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		}
		return true;
	}

}
