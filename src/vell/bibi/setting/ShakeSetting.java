package vell.bibi.setting;

import vell.bibi.velllock.R;
import vell.bibi.velllock.R.id;
import vell.bibi.velllock.R.layout;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

public class ShakeSetting extends Activity {
	private int Sensitiveness;
	private int Time;
	private LinearLayout layout;
	private SeekBar SensorSeekbar, TimeSeekbar;
	private int SensorMax = 20,SensorMin = 10;
	private int TimeMax = 15, TimeMin = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_shake);
		//读取设置
		getShakeSet();
		layout = (LinearLayout) findViewById(R.id.set_shake);
		layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
						Toast.LENGTH_SHORT).show();
			}
		});
		SensorSeekbar = (SeekBar) findViewById(R.id.sensor);
		TimeSeekbar = (SeekBar) findViewById(R.id.time);
		SensorSeekbar.setMax(SensorMax-SensorMin);
		TimeSeekbar.setMax(TimeMax-TimeMin);
		SensorSeekbar.setProgress(Sensitiveness - SensorMin);
		TimeSeekbar.setProgress(Time - TimeMin);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		saveProgress();
		finish();
		return true;
	}

	private void saveProgress(){
		Sensitiveness = SensorSeekbar.getProgress() + SensorMin;
		Time = TimeSeekbar.getProgress() + TimeMin;
		saveShakeSet();
	}

	// 读取摇一摇设置
	private void getShakeSet() {
		SharedPreferences prefs = getSharedPreferences("lock_setting", MODE_PRIVATE);
		Sensitiveness = prefs.getInt("sensor", 18);
		Time = prefs.getInt("time", 6);
	}

	// 保存摇一摇设置
	private void saveShakeSet() {
		SharedPreferences.Editor editor = getSharedPreferences(
				"lock_setting", MODE_PRIVATE).edit();
		editor.putInt("sensor", Sensitiveness);
		editor.putInt("time", Time);
		editor.commit();
		Toast.makeText(getBaseContext(), "已保存设置。。。", Toast.LENGTH_SHORT).show();
	}
	
	/*
	 * button
	 * */
	public void ensure_btn(View v){
		saveProgress();
		finish();
	}
}



