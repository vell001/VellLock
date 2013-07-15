package vell.bibi.vellnote;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;

public class VellNote extends AppWidgetProvider {
	/** Called when the activity is first created. */
	
	final String mPerfName = "vell_note_conf";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
	}
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		final int N = appWidgetIds.length;
		int appWidgetId;
		//删除保存的数据
		for (int i = 0; i < N; i++) {
			appWidgetId = appWidgetIds[i];
			SharedPreferences.Editor editor = context.getSharedPreferences(mPerfName, Activity.MODE_PRIVATE).edit();
			editor.remove("DAT" + appWidgetId);
			editor.commit();
		}
	}

}