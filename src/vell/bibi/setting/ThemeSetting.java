package vell.bibi.setting;

import vell.bibi.velllock.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

public class ThemeSetting extends Activity {
	private int ThemeNumMax = 2;
	private ImageView[] ThemeImage = new ImageView[ThemeNumMax];
	private CheckBox[] ThemeCheckBox = new CheckBox[ThemeNumMax];
	private int n;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_theme);
		ThemeImage[0] = (ImageView) findViewById(R.id.theme01_image);
		ThemeImage[1] = (ImageView) findViewById(R.id.theme02_image);
		ThemeCheckBox[0] = (CheckBox) findViewById(R.id.theme01_checkBox);
		ThemeCheckBox[1] = (CheckBox) findViewById(R.id.theme02_checkBox);
		// 读取设置
		getThemeSet();
		setCheck(n);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		for (int i = 0; i < ThemeNumMax; i++) {
			if (ThemeCheckBox[i].isChecked())
				n = i + 1;
		}
		saveThemeSet();
		super.onStop();
	}

	public void getThemeSet() {
		SharedPreferences prefs = getSharedPreferences("lock_setting",
				MODE_PRIVATE);
		n = prefs.getInt("theme_num", 2);
	}

	public void saveThemeSet() {
		SharedPreferences.Editor editor = getSharedPreferences("lock_setting",
				MODE_PRIVATE).edit();
		editor.putInt("theme_num", n);
		editor.commit();
		Toast.makeText(getBaseContext(), "保存成功", Toast.LENGTH_SHORT).show();
	}

	public void setCheck(int k) {
		for (int i = 0; i < ThemeNumMax; i++) {
			ThemeCheckBox[i].setChecked(false);
		}
		ThemeCheckBox[k - 1].setChecked(true);
	}

	/*
	 * button
	 */
	public void btn_theme01(View v) {
		if (!ThemeCheckBox[0].isChecked())
			setCheck(1);
	}

	public void btn_theme02(View v) {
		if (!ThemeCheckBox[1].isChecked())
			setCheck(2);
	}

	public void btn_ensure(View v) {
		for (int i = 0; i < ThemeNumMax; i++) {
			if (ThemeCheckBox[i].isChecked())
				n = i + 1;
		}
		saveThemeSet();
		finish();
	}

	public void btn_back(View v) {
		for (int i = 0; i < ThemeNumMax; i++) {
			if (ThemeCheckBox[i].isChecked())
				n = i + 1;
		}
		saveThemeSet();
		finish();
	}
}
