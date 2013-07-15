package vell.bibi.vellnote;

import vell.bibi.velllock.R;
import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.TextView;

public class VellNoteConf extends Activity {

	int mAppWidgetId;
	ImageButton mImBtn1, mImBtn2, mImBtn3, mImBtn4;

	final String mPerfName = "vell_note_conf";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vell_note_conf);
		Log.i("myLog", " on WidgetConf ... ");

		setResult(RESULT_CANCELED);
		// Find the widget id from the intent.
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}

		// If they gave us an intent without the widget id, just bail.
		if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			finish();
		}

		mImBtn1 = (ImageButton) findViewById(R.id.ImageButton01);
		mImBtn2 = (ImageButton) findViewById(R.id.ImageButton02);
		mImBtn3 = (ImageButton) findViewById(R.id.ImageButton03);
		mImBtn4 = (ImageButton) findViewById(R.id.ImageButton04);

		mImBtn1.setOnClickListener(mBtnClick);
		mImBtn2.setOnClickListener(mBtnClick);
		mImBtn3.setOnClickListener(mBtnClick);
		mImBtn4.setOnClickListener(mBtnClick);
	}

	OnClickListener mBtnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

			TextView mTextView = (TextView) VellNoteConf.this
					.findViewById(R.id.EditText02);
			SharedPreferences.Editor prefs = VellNoteConf.this
					.getSharedPreferences(mPerfName, 0).edit();
			prefs.putString("DAT" + mAppWidgetId, mTextView.getText()
					.toString());
			prefs.commit();

			int srcId = R.drawable.sketchy_paper_008;
			switch (v.getId()) {
			case R.id.ImageButton01:
				srcId = R.drawable.sketchy_paper_003;
				break;
			case R.id.ImageButton02:
				srcId = R.drawable.sketchy_paper_004;
				break;
			case R.id.ImageButton03:
				srcId = R.drawable.sketchy_paper_007;
				break;
			case R.id.ImageButton04:
				srcId = R.drawable.sketchy_paper_011;
				break;
			}
			Log.i("myLog", "mAppWidgetId is: " + mAppWidgetId);

			RemoteViews views = new RemoteViews(VellNoteConf.this
					.getPackageName(), R.layout.vell_note_widget);
			views.setImageViewResource(R.id.my_widget_img, srcId);
			
			Intent intent = new Intent(VellNoteConf.this, VellNoteEdit.class);
			intent.setAction(mPerfName + mAppWidgetId);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,mAppWidgetId);
			PendingIntent pendingIntent = PendingIntent.getActivity(VellNoteConf.this, 0,
					intent, 0);
			views.setOnClickPendingIntent(R.id.my_widget_img, pendingIntent);

			AppWidgetManager appWidgetManager = AppWidgetManager
					.getInstance(VellNoteConf.this);
			appWidgetManager.updateAppWidget(mAppWidgetId, views);

			// return OK
			Intent resultValue = new Intent();
			resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
					mAppWidgetId);

			setResult(RESULT_OK, resultValue);
			finish();
		}
	};
}