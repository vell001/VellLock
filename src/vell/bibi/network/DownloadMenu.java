package vell.bibi.network;

import vell.bibi.setting.LockSetting;
import vell.bibi.velllock.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class DownloadMenu extends Activity {
	public static DownloadMenu instance = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download_menu);
		instance = this;
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

	// °´¼ü´¦Àí
	public void download1(View v) {
		Intent intent = new Intent(DownloadMenu.this, Downloading.class);
		intent.putExtra("ID", 0);
		startActivity(intent);
	}
	public void download2(View v) {
		Intent intent = new Intent(DownloadMenu.this, Downloading.class);
		intent.putExtra("ID", 1);
		startActivity(intent);
	}
	public void download3(View v) {
		Intent intent = new Intent(DownloadMenu.this, Downloading.class);
		intent.putExtra("ID", 2);
		startActivity(intent);
	}
	public void download4(View v) {
		Intent intent = new Intent(DownloadMenu.this, Downloading.class);
		intent.putExtra("ID", 3);
		startActivity(intent);
	}

	public void btn_back(View v) {
		Intent i = new Intent();
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.setClass(DownloadMenu.this, LockSetting.class);
		startActivity(i);
	}
}
