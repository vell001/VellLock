package vell.bibi.setting;

import vell.bibi.velllock.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LockNumSetting extends Activity {
	private CheckBox[] CBN = new CheckBox[3];
	private int LOCK_N;
	private LinearLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_lock_num);
		CBN[0] = (CheckBox) findViewById(R.id.set_num1);
		CBN[1] = (CheckBox) findViewById(R.id.set_num2);
		CBN[2] = (CheckBox) findViewById(R.id.set_num3);
		// 读取配置文件
		SharedPreferences prefs = getSharedPreferences("lock_setting",
				MODE_PRIVATE);
		LOCK_N = prefs.getInt("lock_n", 2);
		setCBN(LOCK_N);
		CBN[0].setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (buttonView.isChecked()) {
					LOCK_N = 1;
					setCBN(1);
				}
			}
		});
		CBN[1].setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (buttonView.isChecked()) {
					LOCK_N = 2;
					setCBN(2);
				}
			}
		});
		CBN[2].setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (buttonView.isChecked()) {
					LOCK_N = 3;
					setCBN(3);
				}
			}
		});

		layout = (LinearLayout) findViewById(R.id.set_lock_num);
		layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void setCBN(int n) {
		if (n == 1) {
			CBN[0].setChecked(true);
			CBN[1].setChecked(false);
			CBN[2].setChecked(false);
		} else if (n == 2) {
			CBN[0].setChecked(false);
			CBN[1].setChecked(true);
			CBN[2].setChecked(false);
		} else if (n == 3) {
			CBN[0].setChecked(false);
			CBN[1].setChecked(false);
			CBN[2].setChecked(true);
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		saveSet();
		finish();
		return true;
	}

	public void ensure_btn(View v) {
		saveSet();
		finish();
	}

	public void saveSet() {
		SharedPreferences.Editor editer = getSharedPreferences("lock_setting",
				MODE_PRIVATE).edit();
		editer.putInt("lock_n", LOCK_N);
		editer.commit();
		Toast.makeText(getBaseContext(), "已保存设置。。。", Toast.LENGTH_SHORT).show();
	}
}
