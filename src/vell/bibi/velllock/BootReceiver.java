package vell.bibi.velllock;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {

	static final String ACTION = "android.intent.action.BOOT_COMPLETED";
	private Intent mIntent;
	private Context mContext;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		mIntent = new Intent(context, MyLockScreenService.class);
		mContext = context;
		if (intent.getAction().equals(ACTION) && getSet()) {
			Toast.makeText(context, "VellLock服务启动。。。", Toast.LENGTH_LONG)
					.show();
			mContext.startService(mIntent);
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					mContext.startService(mIntent);
					this.cancel();
				}
			}, 30000);
		}
	}

	// 读取设置
	private boolean getSet() {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences dataPreferences = mContext.getSharedPreferences("lock_setting",
				mode);
		return dataPreferences.getBoolean("poweron", true);
	}
}
