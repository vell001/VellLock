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
	// �����沼��
	private View slidingDrawerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_vell001);
		instance = this;

		// ����ʽӦ��ǽ
		// 1,��drawable-hdpi�ļ����е�ͼƬȫ���������¹��̵�drawable-hdpi�ļ�����
		// 2,��layout�ļ����е�detail.xml��slidewall.xml�����ļ����������¹��̵�layout�ļ�����
		// ��ȡ������ʽ���Զ�����
		slidingDrawerView = SlideWall.getInstance().getView(this);
		// ��ȡ������ʽ���Զ�����,�Զ���handle����߱߾�Ϊ150
		// slidingDrawerView = SlideWall.getInstance().getView(this, 150);
		// ��ȡ������ʽ���Զ�����,�Զ����б���ÿ��Item�Ŀ��480,�߶�150
		// slidingDrawerView = SlideWall.getInstance().getView(this, 480, 150);
		// ��ȡ������ʽ���Զ�����,�Զ���handle����߱߾�Ϊ150,�б���ÿ��Item�Ŀ��480,�߶�150
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

				// �������ʽӦ��ǽչʾ�У���رճ���
				SlideWall.getInstance().closeSlidingDrawer();
			}else{
				this.finish();
			}

		}
		return true;
	}

	// �������onConfigurationChanged�ص�����
	// ע:�����ǰActivityû������android:configChanges����,�����ǹ̶�����������ģʽ,����Ҫ����
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// ʹ�ó���ʽӦ��ǽ,������״̬�л�ʱ,���¼��س���,��֤ListView���¼���,��֤ListView��Item�Ĳ���ƥ�䵱ǰ��Ļ״̬
		if (slidingDrawerView != null) {
			// ��remove��slidingDrawerView
			((ViewGroup) slidingDrawerView.getParent())
					.removeView(slidingDrawerView);
			slidingDrawerView = null;
			// ���»�ȡ������ʽ����,��ʱListView����������Adapter
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
