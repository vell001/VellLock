package vell.bibi.setting;

import java.io.File;

import vell.bibi.velllock.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class DBSetting extends Activity {
	private CheckBox[] CBN = new CheckBox[2];
	//活动着的数据库id
	private int DBNum;
	private String DATABASE_PATH = "/sdcard/velllock";
	private String[] DB = new String[] { "cet_4.db", "cet_6.db" };
	private int[] DBLength = new int[] {4531,2080};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_database);
		//读取设置
		CBN[0] = (CheckBox) findViewById(R.id.select_cet_4);
		CBN[1] = (CheckBox) findViewById(R.id.select_cet_6);
		getSetInfo();
		if (isFileExist(DB[DBNum], DATABASE_PATH)) {
				setCBN(DBNum);
		} else {
			dialog();
		}
		CBN[0].setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (buttonView.isChecked()) {
					if (isFileExist(DB[0], DATABASE_PATH)) {
						DBNum = 0;
						setCBN(0);
					} else {
						DBNum = 1;
						setCBN(1);
						dialog();
					}
				}
			}
		});
		CBN[1].setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (buttonView.isChecked()) {
					if (isFileExist(DB[1], DATABASE_PATH)) {
						DBNum = 1;
						setCBN(1);
					} else {
						DBNum = 0;
						setCBN(0);
						dialog();
					}
				}
			}
		});
	}

	public void getSetInfo() {
		SharedPreferences prefs = getSharedPreferences("lock_setting", MODE_PRIVATE);
		DBNum = prefs.getInt("active_id", 1);
	}

	public Boolean isFileExist(String filename, String filedir) {
		File[] files = new File(filedir).listFiles();
		if (!files[0].exists())
			return null;
		for (File f : files) {
			// 判断文件名f中是否包含keyword
			if (f.getName().indexOf(filename) == 0) {
				// f.getPath()返回文件的路径
				return true;
			}
		}
		return false;
	}

	public void setCBN(int n) {
		if (n == 0) {
			CBN[0].setChecked(true);
			CBN[1].setChecked(false);
		} else if (n == 1) {
			CBN[0].setChecked(false);
			CBN[1].setChecked(true);
		}
	}

	public void dialog() {
		// AlertDialog.Builder builder = new Builder(getBaseContext());
		// builder.setMessage("啊哦，你还没有下载此词库哟。。。\n立即进入下载？？？");
		// builder.setTitle("VellWarn");
		// builder.setPositiveButton("进入下载", new OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// // TODO Auto-generated method stub
		// dialog.dismiss();
		// Intent intent = new Intent(getBaseContext(), DownloadMenu.class);
		// startActivity(intent);
		// }
		// });
		// builder.setNegativeButton("取消", new OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// // TODO Auto-generated method stub
		// dialog.dismiss();
		// }
		// });
		// builder.create().show();
	}

	/*
	 * button
	 */

	public void ensure_btn(View v) {
		saveSetInfo();
		finish();
	}

	public void saveSetInfo() {
		SharedPreferences.Editor editor = getSharedPreferences("lock_setting", MODE_PRIVATE).edit();
		editor.putInt("active_id", DBNum);
		editor.commit();
		editor = getSharedPreferences("db_info", MODE_PRIVATE).edit();
		editor.putInt("db_length", DBLength [DBNum]);
		editor.commit();
		///////////刷新lock_setting/////////
		LockSetting.ACTIVE_ID = DBNum;
	}
}
