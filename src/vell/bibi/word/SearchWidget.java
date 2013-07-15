package vell.bibi.word;

import vell.bibi.velllock.R;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class SearchWidget extends AppWidgetProvider{
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// test 按键
		Intent i = new Intent(context, YoudaoActivity.class);
		// 设置pendingIntent的作用
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				i, 0);
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.search_widget);
		// 绑定事件
		remoteViews
				.setOnClickPendingIntent(R.id.search_img, pendingIntent);
		// 更新Appwidget
		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
	}
}
