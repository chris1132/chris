package org.cct.home.savecontext;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SaveService {
	private Context context;

	public SaveService(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	/**
	 * 保存配置
	 * @param fileName
	 * @param map
	 * @return
	 */
	public boolean saveSharePreference(String fileName, Map<String, Object> map) {
		boolean flag = false;
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object object = entry.getValue();
			if (object instanceof Boolean) {
				Boolean new_name = (Boolean) object;
				editor.putBoolean(key, new_name);
			} else if (object instanceof Integer) {
				Integer integer = (Integer) object;
				editor.putInt(key, integer);
			} else if (object instanceof Float) {
				Float f = (Float) object;
				editor.putFloat(key, f);
			} else if (object instanceof Long) {
				Long l = (Long) object;
				editor.putLong(key, l);
			} else if (object instanceof String) {
				String s = (String) object;
				editor.putString(key, s);
			}
		}
		flag = editor.commit();
		return flag;
	}

	/**
	 * 得到配置数据
	 * @param fileName
	 * @return
	 */
	public Map<String, ?> getSharePreference(String fileName) {
		Map<String, ?> map = null;
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		map = preferences.getAll();
		return map;
	}
}
