package org.cct.home.admin;

import org.cct.home.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.org.ifamily.entity.Admin;
import com.org.ifamily.implement.Adminimpl;

public class Admin1PasswordChange extends Activity {

	private EditText alterEditText1;
	private EditText alterEditText2;
	private EditText alterEditText3;
	private Button alterbtn;
	private Admin admin = null;
	private Adminimpl adminimpl = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome_4_2_all);
		

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_adminhome_4_2_all);
		button.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				//返回上一级
				Admin1PasswordChange.this.finish();
			}
		});
		
	
		alterEditText1 = (EditText) findViewById(R.id.alterEditText1);
		alterEditText2 = (EditText) findViewById(R.id.alterEditText2);
		alterEditText3 = (EditText) findViewById(R.id.alterEditText3);
		alterbtn = (Button) findViewById(R.id.alterbtn);
		adminimpl = new Adminimpl();
		admin = adminimpl.query(AdminLogin.adminId);
		alterbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String newpassword = alterEditText2.getText().toString()
						.trim();
				if (!alterEditText1.getText().toString().trim()
						.equals(admin.getPassword())) {
					Toast.makeText(Admin1PasswordChange.this, "输入的旧密码不正确，请重新输入",
							Toast.LENGTH_SHORT).show();
					alterEditText1.setText("");
					alterEditText2.setText("");
					alterEditText3.setText("");
				} else if (!alterEditText2.getText().toString().trim()
						.equals(alterEditText3.getText().toString().trim())) {
					Toast.makeText(Admin1PasswordChange.this, "两次输入的新密码不一致，请重新输入",
							Toast.LENGTH_SHORT).show();
					alterEditText1.setText("");
					alterEditText2.setText("");
					alterEditText3.setText("");
				} else if (alterEditText2.getText().toString().trim()
						.equals("")) {
					Toast.makeText(Admin1PasswordChange.this, "输入的新密码不能仅为空格，请重新输入",
							Toast.LENGTH_SHORT).show();
					alterEditText1.setText("");
					alterEditText2.setText("");
					alterEditText3.setText("");
				} else {
					new AlertDialog.Builder(Admin1PasswordChange.this)
							.setTitle("提示")
							.setMessage("确定保存修改？")
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int whichButton) {
											boolean check = adminimpl
													.changepassword(
															AdminLogin.adminId,
															admin.getPassword(),
															newpassword);
											if (!check) {
												Toast.makeText(
														Admin1PasswordChange.this,
														"密码修改失败...",
														Toast.LENGTH_SHORT)
														.show();
											} else {
												Toast.makeText(
														Admin1PasswordChange.this,
														"密码修改成功！",
														Toast.LENGTH_SHORT)
														.show();

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

	}

	

}
