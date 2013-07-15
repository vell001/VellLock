package vell.bibi.word;

import java.util.ArrayList;
import java.util.List;

import vell.bibi.setting.LockSetting;
import vell.bibi.velllock.R;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class WrongWordList extends Activity {
	private ListView mListView;
	private List<Word> mDataArrays = new ArrayList<Word>();
	private WrongWordAdapter mAdapter;
	private SQLiteDatabase DB;

	// private SlidingDrawer mDrawer;
	// private Button mDrawerBtn;

	// private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wrong_word_list);

		mListView = (ListView) findViewById(R.id.wrong_word_view);
		getData();
		mAdapter = new WrongWordAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
		// È¥µô·Ö¸îÏß
		mListView.setDivider(null);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

			}

		});
	}

	public void getData() {
		DB = new LockSetting().openDatabase();
		Cursor cursor = DB.rawQuery(
				"select * from WORD_INFO order by LEARN_WEIGHT desc", null);
		int wordid;
		String body;
		String body_zn;
		String body_en;
		String usage_zn;
		String usage_en;
		int WrongNum;
		int RightNum;
		int LearnWeight = 100;
		String soundmark;
		int unit_number;
		int polyphone;
		String soundmark2;

		if (cursor.moveToFirst()) {
			while (true) {
				wordid = cursor.getInt(0);
				body = cursor.getString(1);
				body_zn = cursor.getString(2);
				body_en = cursor.getString(3);
				usage_zn = cursor.getString(4);
				usage_en = cursor.getString(5);
				WrongNum = cursor.getInt(6);
				RightNum = cursor.getInt(7);
				LearnWeight = cursor.getInt(8);
				soundmark = cursor.getString(9);
				unit_number = cursor.getInt(10);
				polyphone = cursor.getInt(11);
				soundmark2 = cursor.getString(12);
				if (LearnWeight <= 0)
					break;
				mDataArrays.add(new Word(wordid, body, body_zn, body_en,
						usage_zn, usage_en, WrongNum, RightNum, LearnWeight,
						soundmark, unit_number, polyphone, soundmark2));
				if (!cursor.moveToNext())
					break;
			}
		}
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
}
