package vell.bibi.velllock;

import vell.bibi.ads.SlideWall;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import cn.waps.UpdatePointsNotifier;

public class AboutVell001 extends Activity implements View.OnClickListener, UpdatePointsNotifier {
	public static AboutVell001 instance = null;
	// 抽屉广告布局
	private View slidingDrawerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_vell001);
		instance = this;

		// 抽屉式应用墙
		// 1,将drawable-hdpi文件夹中的图片全部拷贝到新工程的drawable-hdpi文件夹中
		// 2,将layout文件夹中的detail.xml和slidewall.xml两个文件，拷贝到新工程的layout文件夹中
		// 获取抽屉样式的自定义广告
		slidingDrawerView = SlideWall.getInstance().getView(this);
		// 获取抽屉样式的自定义广告,自定义handle距左边边距为150
		// slidingDrawerView = SlideWall.getInstance().getView(this, 150);
		// 获取抽屉样式的自定义广告,自定义列表中每个Item的宽度480,高度150
		// slidingDrawerView = SlideWall.getInstance().getView(this, 480, 150);
		// 获取抽屉样式的自定义广告,自定义handle距左边边距为150,列表中每个Item的宽度480,高度150
		// slidingDrawerView = SlideWall.getInstance().getView(this, 150, 480,
		// 150);

		if (slidingDrawerView != null) {
			this.addContentView(slidingDrawerView, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (SlideWall.getInstance().slideWallDrawer != null
					&& SlideWall.getInstance().slideWallDrawer.isOpened()) {

				// 如果抽屉式应用墙展示中，则关闭抽屉
				SlideWall.getInstance().closeSlidingDrawer();
			}else{
				this.finish();
			}

		}
		return true;
	}

	// 建议加入onConfigurationChanged回调方法
	// 注:如果当前Activity没有设置android:configChanges属性,或者是固定横屏或竖屏模式,则不需要加入
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// 使用抽屉式应用墙,横竖屏状态切换时,重新加载抽屉,保证ListView重新加载,保证ListView中Item的布局匹配当前屏幕状态
		if (slidingDrawerView != null) {
			// 先remove掉slidingDrawerView
			((ViewGroup) slidingDrawerView.getParent())
					.removeView(slidingDrawerView);
			slidingDrawerView = null;
			// 重新获取抽屉样式布局,此时ListView重新设置了Adapter
			slidingDrawerView = SlideWall.getInstance().getView(this);
			if (slidingDrawerView != null) {
				this.addContentView(slidingDrawerView, new LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			}
		}
		super.onConfigurationChanged(newConfig);
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

	public void btn_back(View v) {
		this.finish();
	}

	@Override
	public void getUpdatePoints(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getUpdatePointsFailed(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
