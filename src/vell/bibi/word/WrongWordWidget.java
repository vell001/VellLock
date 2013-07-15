package vell.bibi.word;

import vell.bibi.database.DatabaseOperate;
import vell.bibi.velllock.R;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WrongWordWidget extends AppWidgetProvider {
	// private TextView WordText,SoundmarkText,UsageZh,UsageEn;
	private Word word;
	private DatabaseOperate DBO;
	private Context mContext;
	private String broadCastString = "vell.AppWidgetBroadCast";
	

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
		mContext = context;
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		mContext = context;
		appWidgetManager.updateAppWidget(appWidgetIds, setRemoteViews());
		// test 按键
		Intent i = new Intent(context, WrongWordList.class);
		// 设置pendingIntent的作用
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				i, 0);
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.wrong_word_widget);
		// 绑定事件
		remoteViews
				.setOnClickPendingIntent(R.id.wrong_word_view, pendingIntent);
		// 更新Appwidget
		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
	}

	/**
	 * 接受广播事件
	 * */
	@Override
	public void onReceive(Context context, Intent intent) {
		mContext = context;
		if (intent.getAction().equals(broadCastString)) {
			// 只能通过远程对象来设置appwidget中的控件状态

			// 获得appwidget管理实例，用于管理appwidget以便进行更新操作
			AppWidgetManager appWidgetManager = AppWidgetManager
					.getInstance(context);

			// 相当于获得所有本程序创建的appwidget
			ComponentName componentName = new ComponentName(context,
					WrongWordWidget.class);

			// 更新appwidget
			appWidgetManager.updateAppWidget(componentName, setRemoteViews());
		}
		super.onReceive(context, intent);
	}

	public RemoteViews setRemoteViews() {
		DBO = new DatabaseOperate(mContext);
		word = DBO.getMaxWorngWord();
		RemoteViews remoteviews = new RemoteViews(mContext.getPackageName(),
				R.layout.wrong_word_widget);
		remoteviews.setTextViewText(R.id.body_text, word.getBODY());
		remoteviews.setTextViewText(R.id.body_zh_text, word.getBODY_ZH());
		remoteviews.setTextViewText(R.id.soundmark_text,
				"[" + word.getSOUNDMARK() + "]");
		remoteviews.setTextViewText(R.id.usage_en_text, word.getUSAGE_EN());
		remoteviews.setTextViewText(R.id.usage_zh_text, word.getUSAGE_ZH());
		String str = "(" + Integer.toString(word.getRIGHTNUM()) + "/"
				+ Integer.toString(word.getRIGHTNUM() + word.getWRONGNUM())
				+ ")";
		remoteviews.setTextViewText(R.id.wrong_num, str);
		return remoteviews;
	}
}
