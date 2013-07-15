package vell.bibi.setting;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import vell.bibi.ads.LoadingPopAd;
import vell.bibi.velllock.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import cn.waps.AppConnect;

public class Appstart extends Activity {
	// 设置界面跳转时间为5秒
	private int time = 5;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appstart);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN); //全屏显示
		// Toast.makeText(getApplicationContext(), "孩子！好好背诵！",
		// Toast.LENGTH_LONG).show();
		// overridePendingTransition(R.anim.hyperspace_in,
		// R.anim.hyperspace_out);
		// 广告
		AppConnect.getInstance("cb85351bbbdf4f3765f011903786831c", "360", this);

		// 使用自定义的OffersWebView
		AppConnect.getInstance(this).setAdViewClassName(
				this.getPackageName() + ".MyAdView");

		// 初始化自定义广告数据
		AppConnect.getInstance(this).initAdInfo();
		// 初始化插屏广告数据
		AppConnect.getInstance(this).initPopAd(this);

		// 开屏广告
		View loadingAdView = LoadingPopAd.getInstance().getContentView(this,
				time);
		if (loadingAdView != null) {
			// 将开屏广告的布局View设置到当前Activity的整体布局中
			this.setContentView(loadingAdView);
		}

		// 设置延时跳转到主界面
		scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(Appstart.this, LockSetting.class);
				startActivity(intent);
				Appstart.this.finish();
				AppConnect.getInstance(Appstart.this).finalize();
			}
		}, time, TimeUnit.SECONDS);

	}
}