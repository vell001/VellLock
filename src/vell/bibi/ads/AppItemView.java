package vell.bibi.ads;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.waps.AdInfo;
import cn.waps.AppConnect;
import cn.waps.SDKUtils;

public class AppItemView {

	private static AppItemView myAdapterView;
	public static AppItemView getInstance(){
		if(myAdapterView == null){
			myAdapterView = new AppItemView();
		}
		return myAdapterView;
	}
	
	public View getAdapterView(final Context context, final AdInfo adInfo, int itemWidth, int itemHeight){
		// 对小屏手机进行屏幕判�?
		int displaySize = SDKUtils.getDisplaySize(context);
		
		// 整体布局
		RelativeLayout whole_layout = null;
		
		GradientDrawable click_bg_grad = new GradientDrawable(Orientation.TOP_BOTTOM, 
			new int[] {Color.parseColor("#FFD700"), Color.parseColor("#FFB90F"), Color.parseColor("#FFD700")}); 
		
		// 广告数据布局
		RelativeLayout r_layout = null;
		try {
			whole_layout = new RelativeLayout(context);
			if(itemWidth == 0 || itemHeight == 0){
				itemWidth = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth();
				// item的默认高�?
				if(displaySize == 320){
					itemHeight = 55;
				}else if(displaySize == 240){
					itemHeight = 40;
				}else{
					itemHeight = 100;
				}
				
			}
			whole_layout.setLayoutParams(new ListView.LayoutParams(itemWidth, itemHeight));
			
			r_layout = new RelativeLayout(context);
			
			int item_whole_bg_id = context.getResources().getIdentifier("item_bg", "drawable", context.getPackageName());
			
			if(item_whole_bg_id != 0){
				r_layout.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
				r_layout.setBackgroundResource(item_whole_bg_id);
			}
			r_layout.setPadding(0, 0, 0, 0);
			
			// 图标
			ImageView app_icon = new ImageView(context);
			app_icon.setId(1);
			app_icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
			
			app_icon.setImageDrawable(new BitmapDrawable(adInfo.getAdIcon()));  
			app_icon.setPadding(5, 5, 5, 5);
			
			// 加载�?��文字内容的整体布�?
			LinearLayout layout  = new LinearLayout(context);
			layout.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			layout.setOrientation(LinearLayout.VERTICAL);
			
			int item_text_bg_id = context.getResources().getIdentifier("item_text_bg", "drawable", context.getPackageName());
			if(item_text_bg_id != 0){
//				layout.setBackgroundResource(item_text_bg_id);
			}
			layout.setId(2);
			
			// 加载广告名称和广告大小的布局
			RelativeLayout title_size_layout = new RelativeLayout(context);
			
			// 广告名称
			TextView app_name = new TextView(context);
			app_name.setId(2);
			app_name.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			
			app_name.setText(adInfo.getAdName());
			app_name.setTextSize(18);
			app_name.setTextColor(Color.BLACK);
			app_name.setPadding(10, 0, 0, 0);
			
			// 广告大小
			TextView app_size = new TextView(context);
			app_size.setText(adInfo.getFilesize() + "M");
			app_size.setTextSize(12);
			app_size.setTextColor(Color.GRAY);
			app_size.setPadding(5, 0, 5, 0);
			
			RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params4.addRule(RelativeLayout.ALIGN_TOP, app_name.getId());
			params4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			
			
			title_size_layout.addView(app_name);
			title_size_layout.addView(app_size, params4);
			
			// 广告�?
			TextView content = new TextView(context);
			content.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			content.setText(adInfo.getAdText());
			content.setPadding(10, 0, 0, 0);
			content.setTextColor(Color.GRAY);
			
			layout.addView(title_size_layout);
			layout.addView(content);
			
			// 下载按钮
			LinearLayout down_layout = new LinearLayout(context);
			down_layout.setOrientation(LinearLayout.HORIZONTAL);
			down_layout.setGravity(Gravity.CENTER);
			down_layout.setId(3);
			
			// 直接加载下载图标的布�?
			RelativeLayout down_img_layout = new RelativeLayout(context);
			int down_layout_width = 75;
			if(displaySize == 320){
				down_layout_width = 45;
			}else if(displaySize == 240){
				down_layout_width = 30;
			}
			down_img_layout.setLayoutParams(new LinearLayout.LayoutParams(down_layout_width, LayoutParams.FILL_PARENT));
			
			ImageView downImage = new ImageView(context);
			int down_bg_id = context.getResources().getIdentifier("down_ico", "drawable", context.getPackageName());
			
			if(down_bg_id != 0){
				downImage.setImageResource(down_bg_id);
			}
			
			// 下载按钮的点击半透明遮罩效果布局
			final LinearLayout d_layout = new LinearLayout(context);
			d_layout.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
//			d_layout.setBackgroundColor(Color.argb(80, 0, 0, 0));
//			d_layout.setBackgroundColor(Color.parseColor("#AAFFD700"));
			d_layout.setBackgroundDrawable(click_bg_grad);
			d_layout.setVisibility(View.INVISIBLE);
			
			RelativeLayout.LayoutParams down_img_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			down_img_params.addRule(RelativeLayout.CENTER_IN_PARENT);
			
			down_img_layout.addView(d_layout);
			down_img_layout.addView(downImage, down_img_params);
			
			// 竖直分隔�?
			LinearLayout line_layout_2 = new LinearLayout(context);
			line_layout_2.setLayoutParams(new LinearLayout.LayoutParams(4, LayoutParams.FILL_PARENT));
			
			//颜色渐变
			GradientDrawable grad = new GradientDrawable(Orientation.LEFT_RIGHT, 
				new int[] {Color.parseColor("#cccccc"), Color.parseColor("#ffffff"), Color.parseColor("#cccccc")}); 
			line_layout_2.setBackgroundDrawable(grad);
			
			down_layout.addView(line_layout_2);
			down_layout.addView(down_img_layout);
			
			down_img_layout.setOnTouchListener(new OnTouchListener(){
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					// 点击时，ListView的item产生被点击效果（半�?明遮罩）
					case MotionEvent.ACTION_DOWN:
						d_layout.setVisibility(View.VISIBLE);
						break;
					// 离开点击区域，ListView的item点击效果消失
					case MotionEvent.ACTION_CANCEL:
						d_layout.setVisibility(View.INVISIBLE);
						break;
					// 点击后抬起时，ListView的item点击效果消失
					case MotionEvent.ACTION_UP:
						d_layout.setVisibility(View.INVISIBLE);
						break;
					}
					return false;
				}
			});
			
			down_img_layout.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					AppConnect.getInstance(context).downloadAd(adInfo.getAdId());
				}
				
			});
			
			
			RelativeLayout.LayoutParams params1 = null;
			if(displaySize == 320){
				params1 = new RelativeLayout.LayoutParams(50, 50);
			}else if(displaySize == 240){
				params1 = new RelativeLayout.LayoutParams(36, 36);
			}else{
				params1 = new RelativeLayout.LayoutParams(75, 75);
			}
			params1.addRule(RelativeLayout.CENTER_VERTICAL);
			params1.leftMargin=5;
			
			RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			params2.addRule(RelativeLayout.RIGHT_OF, app_icon.getId());
			params2.addRule(RelativeLayout.LEFT_OF, down_layout.getId());
			params2.addRule(RelativeLayout.CENTER_VERTICAL);
			
			RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
//			params3.addRule(RelativeLayout.RIGHT_OF, layout.getId());
			params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			
			
//			int width = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth()-83;
			// 点击效果的半透明遮罩布局
			final LinearLayout l_layout = new LinearLayout(context);
			l_layout.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
//			l_layout.setBackgroundColor(Color.argb(80, 0, 0, 0));//黑色
//			l_layout.setBackgroundColor(Color.argb(80, 30, 144, 255));//淡蓝�?
//			l_layout.setBackgroundColor(Color.parseColor("#AAFFD700"));
			l_layout.setBackgroundDrawable(click_bg_grad);
			l_layout.setVisibility(View.INVISIBLE);
			
			r_layout.addView(l_layout);
			r_layout.addView(app_icon, params1);
			r_layout.addView(down_layout, params3);
			r_layout.addView(layout, params2);
			
			
			whole_layout.addView(r_layout);
			
			
			r_layout.setOnTouchListener(new OnTouchListener(){
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					// 点击时，ListView的item产生被点击效果（半�?明遮罩）
					case MotionEvent.ACTION_DOWN:
						l_layout.setVisibility(View.VISIBLE);
						break;
					// 离开点击区域，ListView的item点击效果消失
					case MotionEvent.ACTION_CANCEL:
						l_layout.setVisibility(View.INVISIBLE);
						break;
					// 点击后抬起时，ListView的item点击效果消失
					case MotionEvent.ACTION_UP:
						l_layout.setVisibility(View.INVISIBLE);
						break;
					}
					return false;
				}
			});
			
			
			r_layout.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					AppDetail.getInstanct().showAdDetail(context, adInfo);
				}
				
			});
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        return whole_layout;
	}
}
