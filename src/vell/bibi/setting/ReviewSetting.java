package vell.bibi.setting;

import vell.bibi.velllock.R;
import vell.bibi.velllock.R.id;
import vell.bibi.velllock.R.layout;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

public class ReviewSetting extends Activity {
	private LinearLayout layout;
	private SeekBar ReviewSeekBar;
	private int Progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_review);
		getReviewSet();
		layout = (LinearLayout) findViewById(R.id.set_shake);
		layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
						Toast.LENGTH_SHORT).show();
			}
		});
		ReviewSeekBar = (SeekBar) findViewById(R.id.review);
		ReviewSeekBar.setMax(100);
		ReviewSeekBar.setProgress(Progress);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		saveReViewSet();
		finish();
		return true;
	}

	public void getReviewSet() {
		SharedPreferences prefs = getSharedPreferences("lock_setting",
				MODE_PRIVATE);
		Progress = prefs.getInt("review", 40);
	}

	public void saveReViewSet() {
		SharedPreferences.Editor editor = getSharedPreferences("lock_setting",
				MODE_PRIVATE).edit();
		editor.putInt("review", ReviewSeekBar.getProgress());
		editor.commit();
		Toast.makeText(getBaseContext(), "已保存设置。。。", Toast.LENGTH_SHORT).show();
	}

	/*
	 * button
	 */
	public void ensure_btn(View v) {
		saveReViewSet();
		finish();
	}
}
