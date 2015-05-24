package org.cct.home.admin;

import org.cct.home.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.org.ifamily.entity.User;
import com.org.ifamily.implement.Userimpl;

public class Admin3UseQuery extends Activity {

	private EditText qeditText;
	private Button qbtn;
	private TextView qtextView22;
	private TextView qtextView33;
	private TextView qtextView44;
	private TextView qtextView55;
	private TextView qtextView66;
	private TextView qtextView77;
	private TextView qtextView88;
	private TextView qtextView99;
	private TextView qtextView0;
	private ProgressDialog dialog;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome_1_2_all);
		handler = new Admin2UseQueryHandler();

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_adminhome_1_2_all);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Admin3UseQuery.this.finish();
			}
		});

		qeditText = (EditText) findViewById(R.id.qeditText);
		qbtn = (Button) findViewById(R.id.qbtn);
		qtextView22 = (TextView) findViewById(R.id.qtextView22);
		qtextView33 = (TextView) findViewById(R.id.qtextView33);
		qtextView44 = (TextView) findViewById(R.id.qtextView44);
		qtextView55 = (TextView) findViewById(R.id.qtextView55);
		qtextView66 = (TextView) findViewById(R.id.qtextView66);
		qtextView77 = (TextView) findViewById(R.id.qtextView77);
		qtextView88 = (TextView) findViewById(R.id.qtextView88);
		qtextView99 = (TextView) findViewById(R.id.qtextView99);
		qtextView0 = (TextView) findViewById(R.id.qtextView0);

		qbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog = ProgressDialog.show(Admin3UseQuery.this, "提交中",
						"正在提交中,请稍后..");
				Thread workThread = new WorkThread();
				workThread.start();
			}
		});
	}

	class WorkThread extends Thread {

		@Override
		public void run() {
			Userimpl userimpl = new Userimpl();
			User user = userimpl.querybyusername(qeditText.getText().toString()
					.trim());
			if (user == null) {
				Message msg = handler.obtainMessage();
				msg.what = 0;
				handler.sendMessage(msg);

			} else {
				Message msg = handler.obtainMessage();
				msg.what = 1;
				msg.obj = user;
				handler.sendMessage(msg);
			}
		}
	}

	class Admin2UseQueryHandler extends Handler {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				User user = (User) msg.obj;
				qtextView22.setText(user.getUsername());
				qtextView33.setText(user.getName());
				qtextView44.setText(user.getPhonenum());
				qtextView55.setText(user.getIdnumber());
				qtextView66.setText(user.getEmail());
				if (user.getStatus()) {
					qtextView77.setText("已启用");
				} else {
					qtextView77.setText("已禁用");
				}
				qtextView88.setText(user.getAddress());
				qtextView99.setText(user.getCreatetime() + "");
				qtextView0.setText(user.getLastlogintime() + "");
				dialog.dismiss();
			} else {
				Toast.makeText(Admin3UseQuery.this, "对不起，没有相关信息！请核对用户名后再查询。",
						Toast.LENGTH_SHORT).show();
				qtextView22.setText("");
				qtextView33.setText("");
				qtextView44.setText("");
				qtextView55.setText("");
				qtextView66.setText("");
				qtextView77.setText("");
				qtextView88.setText("");
				qtextView99.setText("");
				qtextView0.setText("");
				dialog.dismiss();
			}
		}
	}
}
