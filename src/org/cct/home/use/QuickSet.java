package org.cct.home.use;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.cct.home.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.org.ifamily.entity.Callto;
import com.org.ifamily.entity.Host;
import com.org.ifamily.implement.Calltoimpl;
import com.org.ifamily.vo.CalltoVO;

public class QuickSet extends Activity {
	private Button use_6_1submit;
	private EditText[] phones;
	private EditText[] names;
	private Host host;
	int i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.use_6_1);

		ImageButton button = (ImageButton) findViewById(R.id.imageButton_use_6_1);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 按钮按下，返回上一界面
				QuickSet.this.finish();

			}
		});

		phones = new EditText[4];
		names = new EditText[4];

		phones[0] = (EditText) findViewById(R.id.quick1_phone);
		phones[1] = (EditText) findViewById(R.id.quick2_phone);
		phones[2] = (EditText) findViewById(R.id.quick3_phone);
		phones[3] = (EditText) findViewById(R.id.quick4_phone);

		names[0] = (EditText) findViewById(R.id.quick1_name);
		names[1] = (EditText) findViewById(R.id.quick2_name);
		names[2] = (EditText) findViewById(R.id.quick3_name);
		names[3] = (EditText) findViewById(R.id.quick4_name);
		use_6_1submit = (Button) findViewById(R.id.use_6_1submit);
		use_6_1submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calltoimpl calltoimpl = new Calltoimpl();
				CalltoVO<Callto> calltoVO = new CalltoVO<Callto>();
				List<Callto> list = new ArrayList<Callto>();
				calltoVO.setHostnum(host.getHostnum());
				for (int i = 0; i < 4; i++) {
					if (!phones[i].equals("")) {
						Callto callto = new Callto();
						callto.setButtonid(i + 1);
						callto.setCallname(names[i].getText().toString());
						callto.setHostnum(host.getHostnum());
						callto.setCallnum(phones[i].getText().toString());
						list.add(callto);
					}
				}
				calltoVO.setCalltolists(list);
				calltoimpl.update(calltoVO);
				Toast.makeText(QuickSet.this, "更新成功", Toast.LENGTH_SHORT)
						.show();

			}
		});
		Intent intent = getIntent();
		host = (Host) intent.getSerializableExtra("HOSTSURE");

		final Calltoimpl calltoimpl = new Calltoimpl();
		List<Callto> lists = calltoimpl.query(host.getHostnum());
		final Callto[] calltos = new Callto[4];
		if (lists == null || lists.isEmpty()) {
			Toast.makeText(QuickSet.this, "未查询到任何记录，请添加。", Toast.LENGTH_SHORT)
					.show();
		} else {
			for (Iterator<Callto> iterator = lists.iterator(); iterator
					.hasNext();) {
				Callto callto = iterator.next();
				calltos[callto.getButtonid() - 1] = callto;
			}

			for (int c = 0; c < 4; c++) {
				if (calltos[c] == null) {
					continue;
				}
				phones[c].setText(calltos[c].getCallnum() + "");
				names[c].setText(calltos[c].getCallname() + "");
			}
		}

	}
}
