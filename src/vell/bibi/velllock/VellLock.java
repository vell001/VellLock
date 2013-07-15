package vell.bibi.velllock;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

@SuppressLint({ "SdCardPath", "NewApi" })
@SuppressWarnings("deprecation")
public class VellLock extends Activity {

	private DrawLockScreen mDLS;

	private Activity mParentActivity;
	static public int LOCK_N;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// try to disable home key.
		Window win = getWindow();
		WindowManager.LayoutParams lp = win.getAttributes();
		lp.flags |= 0x80000000; // 0x80000000 should be replaced by
								// WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED
		win.setAttributes(lp);
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//∂¡»°…Ë÷√
		SharedPreferences prefs = getSharedPreferences("lock_setting", MODE_PRIVATE);
		LOCK_N = prefs.getInt("lock_n", 3);
		
		// Close System KeyguardLock
		KeyguardManager manager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		KeyguardManager.KeyguardLock lock1 = manager
				.newKeyguardLock("KeyguardLock");
		lock1.disableKeyguard();

		// Get the screen properties
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);

		// draw the new custom view.
		mDLS = new DrawLockScreen(this, dm.widthPixels, dm.heightPixels);
		mDLS.setParentActivity(this);
		setContentView(mDLS);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		// return super.dispatchKeyEvent(event);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_HOME:
			return true;
		case KeyEvent.KEYCODE_BACK:
			return true;
		case KeyEvent.KEYCODE_CALL:
			return true;
		case KeyEvent.KEYCODE_SYM:
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			return true;
		case KeyEvent.KEYCODE_VOLUME_UP:
			return true;
		case KeyEvent.KEYCODE_STAR:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onAttachedToWindow() {
		// TODO Auto-generated method stub

		// try to disable home key.
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
		super.onAttachedToWindow();
	}

	void setParentActivity(Activity parentAct) {
		mParentActivity = parentAct;
	}

	public void closeParentActivity() {
		mParentActivity.finish();
	}
}