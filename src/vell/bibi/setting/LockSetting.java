package vell.bibi.setting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import cn.waps.AppConnect;

import vell.bibi.network.DownloadMenu;
import vell.bibi.velllock.AboutVell001;
import vell.bibi.velllock.MyLockScreenService;
import vell.bibi.velllock.R;
import vell.bibi.word.WrongWordList;
import vell.bibi.word.YoudaoActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

@SuppressLint({ "SdCardPath", "ShowToast" })
@SuppressWarnings("deprecation")
public class LockSetting extends Activity {
	public static LockSetting instance = null;

	private CheckBox mSetOnOff, mSetPoweron, mSetShake, mSetPlaySound;

	private boolean mLockScreenOn, mPoweron, mShake, mPlaySound;

	public static String DATABASE_PATH = "/sdcard/velllock";

	public static int ACTIVE_ID;
	public static String[] DATABASE_FILENAME = new String[] {
			"cet_4.db", "cet_6.db","set_info.db" };
	private int[] DB_ID = new int[] {R.raw.cet_4, R.raw.cet_6,R.raw.set_info };
	private boolean RunFirstTime = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_settings);
		instance = this;
		// read saved setting.
		getSetInfo();
		if (RunFirstTime) {
			// InitDB
			initDB();
			SharedPreferences.Editor editor = getSharedPreferences("lock_setting", MODE_PRIVATE).edit();
			editor.putBoolean("run_first_time", false);
			editor.commit();
		}
		// set checkbox with saved value.
		mSetOnOff = (CheckBox) findViewById(R.id.set_onoff);
		mSetPoweron = (CheckBox) findViewById(R.id.set_poweron);
		mSetShake = (CheckBox) findViewById(R.id.set_shake);
		mSetPlaySound = (CheckBox) findViewById(R.id.set_play_sound);

		mSetOnOff.setChecked(mLockScreenOn);
		mSetPoweron.setChecked(mPoweron);
		mSetShake.setChecked(mShake);
		mSetPlaySound.setChecked(mPlaySound);
		EnableSystemKeyguard(false);
	}

	private void getSetInfo() {
		// TODO Auto-generated method stub
		SharedPreferences prefs = getSharedPreferences("lock_setting",
				MODE_PRIVATE);
		mLockScreenOn = prefs.getBoolean("onoff", true);
		mPoweron = prefs.getBoolean("poweron", true);
		mShake = prefs.getBoolean("shake", true);
		mPlaySound = prefs.getBoolean("play_sound", false);
		ACTIVE_ID = prefs.getInt("active_id", 1);
		RunFirstTime = prefs.getBoolean("run_first_time", true);
	}

	/**/
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// 开始锁屏服务
		startService(new Intent(this, MyLockScreenService.class));
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		mLockScreenOn = mSetOnOff.isChecked();
		mPoweron = mSetPoweron.isChecked();
		mShake = mSetShake.isChecked();
		mPlaySound = mSetPlaySound.isChecked();

		// save the setting before leaving.
		SharedPreferences.Editor editor = getSharedPreferences("lock_setting",
				MODE_PRIVATE).edit();
		editor.putBoolean("onoff", mLockScreenOn);
		editor.putBoolean("poweron", mPoweron);
		editor.putBoolean("shake", mShake);
		editor.putBoolean("play_sound", mPlaySound);
		// 提交
		editor.commit();
		// 重启锁屏服务
		stopService(new Intent(this, MyLockScreenService.class));

		if (mLockScreenOn) {
			// keep on disabling the system Keyguard
			startService(new Intent(this, MyLockScreenService.class));
			EnableSystemKeyguard(false);
		} else {
			// recover original Keyguard
			EnableSystemKeyguard(true);
		}
	}

	void EnableSystemKeyguard(boolean bEnable) {
		KeyguardManager mKeyguardManager = null;
		KeyguardLock mKeyguardLock = null;

		mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		mKeyguardLock = mKeyguardManager.newKeyguardLock("");
		if (bEnable)
			mKeyguardLock.reenableKeyguard();
		else
			mKeyguardLock.disableKeyguard();
	}

	public SQLiteDatabase openDatabase() {
		try {
			// 获得velllockdb.db文件的绝对路径
			String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME[ACTIVE_ID];
			File dir = new File(DATABASE_PATH);
			// 如果/sdcard/velllock目录中存在，创建这个目录
			if (!dir.exists())
				dir.mkdir();
			// 如果在/sdcard/velllock目录中不存在
			// dictionary.db文件，则从res\raw目录中复制这个文件到
			// SD卡的目录（/sdcard/velllock）
			if (!(new File(databaseFilename)).exists()) {
				// 获得封装dictionary.db文件的InputStream对象
				InputStream is = getResources().openRawResource(DB_ID[ACTIVE_ID]);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[8192];
				int count = 0;
				// 开始复制dictionary.db文件
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
		} catch (Exception e) {
		}
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH + "/" + DATABASE_FILENAME[ACTIVE_ID], null);
		return db;
	}

	public void initDB(){
		for(int i=0; i<3; i++){
			try {
				// 获得velllockdb.db文件的绝对路径
				String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME[i];
				File dir = new File(DATABASE_PATH);
				// 如果/sdcard/velllock目录中存在，创建这个目录
				if (!dir.exists())
					dir.mkdir();
				// 如果在/sdcard/velllock目录中不存在
				// dictionary.db文件，则从res\raw目录中复制这个文件到
				// SD卡的目录（/sdcard/velllock）
				if (!(new File(databaseFilename)).exists()) {
					// 获得封装dictionary.db文件的InputStream对象
					InputStream is = getResources().openRawResource(DB_ID[i]);
					FileOutputStream fos = new FileOutputStream(databaseFilename);
					byte[] buffer = new byte[8192];
					int count = 0;
					// 开始复制dictionary.db文件
					while ((count = is.read(buffer)) > 0) {
						fos.write(buffer, 0, count);
					}
					fos.close();
					is.close();
				}
			} catch (Exception e) {
			}
		}
	}

	/*
	 * button
	 */

	public void btn_DownloadSounds(View v) {
		Intent intent = new Intent(LockSetting.this, DownloadMenu.class);
		startActivity(intent);
	}

	public void exit(View v) {
		this.finish();
	}

	public void search_btn(View v) {
		Intent intent = new Intent(LockSetting.this, YoudaoActivity.class);
		startActivity(intent);
	}

	public void wrong_word(View v) {
		Intent intent = new Intent(LockSetting.this, WrongWordList.class);
		startActivity(intent);
	}

	public void aboutVell001(View v) {
		Intent intent = new Intent(LockSetting.this, AboutVell001.class);
		startActivity(intent);
	}

	public void btn_advise(View v) {
		Intent it = new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://vell001.clanmark.com/forum/forum.php"));
		startActivity(it);
	}

	public void btn_LockNumSetting(View v) {
		Intent intent = new Intent(LockSetting.this, LockNumSetting.class);
		startActivity(intent);
	}

	public void btn_ShakeSetting(View v) {
		Intent intent = new Intent(LockSetting.this, ShakeSetting.class);
		startActivity(intent);
	}

	public void btn_ThemeSetting(View v) {
		Intent intent = new Intent(LockSetting.this, ThemeSetting.class);
		startActivity(intent);
	}

	public void btn_ReviewSetting(View v) {
		Intent intent = new Intent(LockSetting.this, ReviewSetting.class);
		startActivity(intent);
	}

	public void btn_WodeModeSetting(View v) {
		Intent intent = new Intent(LockSetting.this, WordModeSetting.class);
		startActivity(intent);
	}
	
	public void btn_DBSetting(View v){
		Intent intent = new Intent(LockSetting.this, DBSetting.class);
		startActivity(intent);
	}
	
	public void btn_english_corner(View v){
		Intent it = new Intent(
				Intent.ACTION_VIEW,
				Uri.parse("http://vell001.clanmark.com/forum/forum.php?mod=forumdisplay&fid=39"));
		startActivity(it);
	}
}
