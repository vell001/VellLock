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
	// ���ý�����תʱ��Ϊ5��
	private int time = 5;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appstart);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��������
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN); //ȫ����ʾ
		// Toast.makeText(getApplicationContext(), "���ӣ��úñ��У�",
		// Toast.LENGTH_LONG).show();
		// overridePendingTransition(R.anim.hyperspace_in,
		// R.anim.hyperspace_out);
		// ���
		AppConnect.getInstance("cb85351bbbdf4f3765f011903786831c", "360", this);

		// ʹ���Զ����OffersWebView
		AppConnect.getInstance(this).setAdViewClassName(
				this.getPackageName() + ".MyAdView");

		// ��ʼ���Զ���������
		AppConnect.getInstance(this).initAdInfo();
		// ��ʼ�������������
		AppConnect.getInstance(this).initPopAd(this);

		// �������
		View loadingAdView = LoadingPopAd.getInstance().getContentView(this,
				time);
		if (loadingAdView != null) {
			// ���������Ĳ���View���õ���ǰActivity�����岼����
			this.setContentView(loadingAdView);
		}

		// ������ʱ��ת��������
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