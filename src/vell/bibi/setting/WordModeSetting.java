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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class WordModeSetting extends Activity{
	private CheckBox[] CBN = new CheckBox[2];
	private int BODY_OR_BODYZH;
	private LinearLayout layout;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_mode);
		CBN[0] = (CheckBox) findViewById(R.id.select_body);
		CBN[1] = (CheckBox) findViewById(R.id.select_bodyzh);
		getSetInfo();
		setCBN(BODY_OR_BODYZH);
		CBN[0].setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (buttonView.isChecked()) {
					BODY_OR_BODYZH = 0;
					setCBN(0);
				}
			}
		});
		CBN[1].setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (buttonView.isChecked()) {
					BODY_OR_BODYZH = 1;
					setCBN(1);
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
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		saveSet();
		finish();
		return true;
	}

	public void setCBN(int n){
		if(n == 0){
			CBN[0].setChecked(true);
			CBN[1].setChecked(false);
		}else{
			CBN[0].setChecked(false);
			CBN[1].setChecked(true);
		}
	}
	
	public void ensure_btn(View v) {
		saveSet();
		finish();
	}
	
	public void getSetInfo(){
		SharedPreferences prefs = getSharedPreferences("lock_setting", MODE_PRIVATE);
		BODY_OR_BODYZH = prefs.getInt("body_or_bodyzh", 1);
	}
	
	public void saveSet() {
		SharedPreferences.Editor editer = getSharedPreferences("lock_setting",
				MODE_PRIVATE).edit();
		editer.putInt("body_or_bodyzh", BODY_OR_BODYZH);
		editer.commit();
		Toast.makeText(getBaseContext(), "已保存设置。。。", Toast.LENGTH_SHORT).show();
	}
}
