package vell.bibi.database;

import vell.bibi.setting.LockSetting;
import vell.bibi.velllock.VellLock;
import vell.bibi.word.Word;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

@SuppressLint("SdCardPath")
public class DatabaseOperate {

	private SQLiteDatabase DB;
	private int DBLength;
	private int Rate;
	private Context mContext;

	public DatabaseOperate(Context context) {
		mContext = context;
	}

	// 按ID获取单词
	public Word getWordById(int id) {
		Word w;
		// 打开数据库
		DB = new LockSetting().openDatabase();
		Cursor cursor = DB.rawQuery("select * from WORD_INFO where WORD_ID=?",
				new String[] { Integer.toString(id) });// Cursor 游标和 ResultSet
														// 很像
		w = getWordFromCursor(cursor, 0);
		DB.close();
		return w;
	}

	// 记录正确错误计算权值
	public void saveRecordToDb(Word[] word, int n, boolean isright) {
		String[] sql = new String[VellLock.LOCK_N];
		for (int i = 0; i < VellLock.LOCK_N; i++) {
			sql[i] = "update WORD_INFO set WRONG_NUM = ?,RIGHT_NUM = ?,LEARN_WEIGHT = ? where WORD_ID = "
					+ Integer.toString(word[i].getWORD_ID());
		}
		// 打开数据库
		DB = new LockSetting().openDatabase();
		int[] WN = new int[VellLock.LOCK_N];
		int[] RN = new int[VellLock.LOCK_N];

		for (int i = 0; i < VellLock.LOCK_N; i++) {
			WN[i] = word[i].getWRONGNUM();
			RN[i] = word[i].getRIGHTNUM();
		}
		int[] LW = new int[VellLock.LOCK_N];
		for (int i = 0; i < VellLock.LOCK_N; i++)
			LW[i] = 0;// 初始化
		if (isright) {
			RN[n]++;
			LW[n] = ((WN[n] * 100) / ((WN[n] + RN[n])));
			DB.execSQL(
					sql[n],
					new String[] { Integer.toString(WN[n]),
							Integer.toString(RN[n]), Integer.toString(LW[n]) });
		} else {
			for (int i = 0; i < VellLock.LOCK_N; i++)
				// 每一个都错误加一
				WN[i]++;
			// 计算权值
			for (int i = 0; i < VellLock.LOCK_N; i++)
				LW[i] = (WN[i]*2 / (WN[i]*2 + RN[i])) * 100;

			for (int i = 0; i < VellLock.LOCK_N; i++)
				DB.execSQL(sql[i], new String[] { Integer.toString(WN[i]),
						Integer.toString(RN[i]), Integer.toString(LW[i]) });
		}
		DB.close();
	}

	public Word[] selectWords() {
		int n = VellLock.LOCK_N;
		Word[] word = new Word[n];
		// 获取设置信息
		getReviewSet();
		getDBLength();
		DB = new LockSetting().openDatabase();
		Cursor cursor;
		// 比重
		cursor = DB
				.rawQuery(
						"select * from WORD_INFO where LEARN_WEIGHT>?",// 学习比重
						new String[] { Integer.toString((int) (Rate + Math
								.random() * 20)) });
		// 前面的全是比重
		for (int i = 0; i < n - 1; i++) {
			word[i] = getWordFromCursor(cursor, i);
			if (word[i] == null) {
				cursor = DB
						.rawQuery("select * from WORD_INFO where WORD_ID=?",
								new String[] { Integer.toString((int) (Math
										.random() * DBLength)) });
				word[i] = getWordFromCursor(cursor, 0);
			}
		}
		// 随机
		cursor = DB
				.rawQuery(
						"select * from WORD_INFO where WORD_ID=?",
						new String[] { Integer.toString((int) (Math.random() * DBLength)) });
		word[n - 1] = getWordFromCursor(cursor, 0);
		DB.close();
		return word;
	}
	//找到错的最多的单词
	public Word getMaxWorngWord() {
		DB = new LockSetting().openDatabase();
		Cursor cursor = DB.rawQuery(
				"select * from WORD_INFO order by LEARN_WEIGHT desc", null);
		
		return getWordFromCursor(cursor, 0);
	}

	public Word getWordFromCursor(Cursor cursor, int N) {
		if (!cursor.moveToFirst())
			return null;
		else {
			for (int i = 0; i < N; i++) {
				if (!cursor.moveToNext())
					return null;
			}
			int wordid = cursor.getInt(0);
			String body = cursor.getString(1);
			String body_zn = cursor.getString(2);
			String body_en = cursor.getString(3);
			String usage_zn = cursor.getString(4);
			String usage_en = cursor.getString(5);
			int WrongNum = cursor.getInt(6);
			int RightNum = cursor.getInt(7);
			int LearnWeight = cursor.getInt(8);
			String soundmark = cursor.getString(9);
			int unit_number = cursor.getInt(10);
			int polyphone = cursor.getInt(11);
			String soundmark2 = cursor.getString(12);

			return new Word(wordid, body, body_zn, body_en, usage_zn, usage_en,
					WrongNum, RightNum, LearnWeight, soundmark, unit_number,
					polyphone, soundmark2);
		}
	}

	/*
	 * 读取设置
	 */
	public void getReviewSet() {
		SharedPreferences prefs = mContext.getSharedPreferences("lock_setting",
				Activity.MODE_PRIVATE);
		int Progress = prefs.getInt("review", 40);
		Rate = 100 - Progress;
	}
	public void getDBLength(){
		SharedPreferences prefs = mContext.getSharedPreferences("db_info", Activity.MODE_PRIVATE);
		DBLength = prefs.getInt("db_length", 0);
	}
}