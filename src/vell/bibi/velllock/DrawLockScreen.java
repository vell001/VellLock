package vell.bibi.velllock;

import java.text.SimpleDateFormat;
import java.util.Date;

import vell.bibi.database.DatabaseOperate;
import vell.bibi.word.Word;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("ViewConstructor")
public class DrawLockScreen extends View {
	private final String TAG = "DrawLockScreen";
	// ��߽�
	private final int LEFT_MARGIN = 20;
	// �ƶ�������
	private final int MOVE_X_PIXEL = 5;
	// �����С
	private final int DRAW_TEXT_SIZE = 24;
	private int BODY_OR_BODYZH;
	// һ����ʾ���ַ���
	// private int DRAW_TEXT_NUM = 20;
	// ����
	private Paint mPaint;
	private Context mContext;
	// ͼ��
	private Bitmap mBgImage, mSlideBtnUpImage, mSlideBtnDnImage, bgImage;

	Activity mParentActivity;
	// ��Ļ����320 x 569
	private int mScrWidth, mScrHeight;

	// ����Y������
	private int[] SLIDEBAR_Y;
	// ����x���ƶ�����
	private int[] movex;
	private boolean[] mMoveValid;
	private int[] fingerInBtn;
	private Bitmap[] mSlideBtnImage;
	private Bitmap bgImage1, bgImage2;
	// ����
	private Word[] word;
	private int ANSWER;
	// �Ƿ񲥷�����
	private boolean mPlaySound;
	// ����Y������
	private DatabaseOperate DBO;
	private int ThemeMax = 2;
	// ����ͼƬID
	private int[][] ThemeImageID = new int[ThemeMax][5];
	// ����
	private int ThemeNum = 1;
	private String broadCastString = "vell.AppWidgetBroadCast";

	public DrawLockScreen(Context context, int width, int height) {
		super(context);
		// ��ʼ������

		// TODO Auto-generated constructor stub
		setFocusable(true); // only this view is foucsed, onkeydown will be
							// called.
		mContext = context;

		mScrWidth = width;
		mScrHeight = height;

		// ��ȡ����
		getSetInfo();
		// ��������
		setTheme();
		// ��ʼ������
		initData();

		mPaint = new Paint();

		// start new thread to make the screen get refreshed by every 1 second.
		// ...
	}

	private void setTheme() {
		// ���ݿ������ʼ��
		DBO = new DatabaseOperate(mContext);
		ThemeImageID[0][0] = R.drawable.theme01_bg1;
		ThemeImageID[0][1] = R.drawable.theme01_bg2;
		ThemeImageID[0][2] = R.drawable.theme01_slidebg;
		ThemeImageID[0][3] = R.drawable.theme01_slidebtndn;
		ThemeImageID[0][4] = R.drawable.theme01_slidebtnup;

		ThemeImageID[1][0] = R.drawable.theme02_bg1;
		ThemeImageID[1][1] = R.drawable.theme02_bg2;
		ThemeImageID[1][2] = R.drawable.theme02_slidebg;
		ThemeImageID[1][3] = R.drawable.theme02_slidebtndn;
		ThemeImageID[1][4] = R.drawable.theme02_slidebtnup;
		for (int i = 0; i < ThemeMax; i++) {
			if (ThemeNum == i + 1) {
				bgImage1 = ((BitmapDrawable) this.getResources().getDrawable(
						ThemeImageID[i][0])).getBitmap();
				bgImage2 = ((BitmapDrawable) this.getResources().getDrawable(
						ThemeImageID[i][1])).getBitmap();
				bgImage = bgImage1;

				mBgImage = ((BitmapDrawable) this.getResources().getDrawable(
						ThemeImageID[i][2])).getBitmap();
				mSlideBtnDnImage = ((BitmapDrawable) this.getResources()
						.getDrawable(ThemeImageID[i][3])).getBitmap();
				mSlideBtnUpImage = ((BitmapDrawable) this.getResources()
						.getDrawable(ThemeImageID[i][4])).getBitmap();
			}
		}
	}

	private void initData() {
		SLIDEBAR_Y = new int[VellLock.LOCK_N];
		movex = new int[VellLock.LOCK_N];
		mMoveValid = new boolean[VellLock.LOCK_N];
		fingerInBtn = new int[VellLock.LOCK_N];
		mSlideBtnImage = new Bitmap[VellLock.LOCK_N];
		word = new Word[VellLock.LOCK_N];
		ANSWER = (int) (Math.random() * VellLock.LOCK_N);
		setWord();
		for (int i = 0; i < VellLock.LOCK_N; i++) {
			movex[i] = LEFT_MARGIN;
			fingerInBtn[i] = 0;
			mMoveValid[i] = false;
			mSlideBtnImage[i] = mSlideBtnUpImage;
			SLIDEBAR_Y[i] = mScrHeight - ((i + 1) * 70);
		}
	}

	private void getSetInfo() {
		int mode = Activity.MODE_PRIVATE;
		// ��ȡ��������
		SharedPreferences prefs = mContext.getSharedPreferences("lock_setting",
				mode);
		mPlaySound = prefs.getBoolean("play_sound", true);
		// 0��ʾ��ʾ����
		BODY_OR_BODYZH = prefs.getInt("body_or_bodyzh", 1);
		ThemeNum = prefs.getInt("theme_num", 1);
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		// clean the screen with specific color.
		canvas.drawColor(Color.WHITE);
		mPaint.setColor(Color.WHITE);
		// ����ݷ���
		mPaint.setAntiAlias(true);
		
		mPaint.setTextAlign(Align.CENTER);

		// draw background
		canvas.drawBitmap(bgImage, 0, 0, mPaint);
		mPaint.setTextAlign(Align.LEFT);
		// ��ʾ����
		for (int i = 0; i < VellLock.LOCK_N; i++) {
			canvas.drawBitmap(mBgImage, 0, SLIDEBAR_Y[i], mPaint);
			if (BODY_OR_BODYZH == 0){
				mPaint.setTextSize(DRAW_TEXT_SIZE);
				canvas.drawText(word[i].getBODY(), 115, SLIDEBAR_Y[i] + 40,
						mPaint);
			}
			else{
				mPaint.setTextSize(DRAW_TEXT_SIZE*3/4);
				canvas.drawText(word[i].getBODY_ZH(), 115, SLIDEBAR_Y[i] + 40,
						mPaint);
			}
		}
		// draw button
		for (int i = 0; i < VellLock.LOCK_N; i++) {
			DrawImage(
					canvas,
					mSlideBtnImage[i],
					0,
					0,
					mSlideBtnImage[i].getWidth(),
					mSlideBtnImage[i].getHeight(),
					movex[i],
					SLIDEBAR_Y[i]
							+ (mBgImage.getHeight() - mSlideBtnImage[i]
									.getHeight()) / 2);
		}
		mPaint.setTextAlign(Align.CENTER);
		// draw Date text
		SimpleDateFormat eFormatter = new SimpleDateFormat("E");
		SimpleDateFormat mFormatter = new SimpleDateFormat("MM��dd��");
		SimpleDateFormat hFormatter = new SimpleDateFormat("HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
		String eDate = eFormatter.format(curDate);
		String mDate = mFormatter.format(curDate);
		String hDate = hFormatter.format(curDate);
		DrawText(canvas, mDate + "  " + eDate, Color.WHITE, 40 / 2,
				Typeface.DEFAULT, Align.CENTER, 85, 160);
		DrawText(canvas, hDate, Color.WHITE, 40, Typeface.DEFAULT, Align.CENTER,
				70, 160);

		// draw interpretation
		if (BODY_OR_BODYZH == 0)
			DrawText(canvas, word[ANSWER].getBODY_ZH(), Color.WHITE,
					DRAW_TEXT_SIZE, Typeface.DEFAULT, Align.CENTER,
					SLIDEBAR_Y[VellLock.LOCK_N - 1] - 30, 160);
		else
			DrawText(canvas, word[ANSWER].getBODY(), Color.WHITE,
					DRAW_TEXT_SIZE, Typeface.DEFAULT, Align.CENTER,
					SLIDEBAR_Y[VellLock.LOCK_N - 1] - 30, 160);
		if (bgImage == bgImage2) {
			DrawText(canvas, "[" + word[ANSWER].getSOUNDMARK() + "]",
					Color.WHITE, DRAW_TEXT_SIZE, Typeface.DEFAULT, Align.CENTER,
					SLIDEBAR_Y[VellLock.LOCK_N - 1] - 30 + DRAW_TEXT_SIZE + 5,
					160);
		}

		// test screen pixel
		// w = 320; h = 569;
		// canvas.drawText(
		// "w = " + Integer.toString(mScrWidth) + "h = "
		// + Integer.toString(mScrHeight), 40, 160, mPaint);
		postInvalidate();
	}

	public void DrawText(Canvas canvas, String text, int color, int size,
			Typeface typeface, Align A, int h, int w) {
		mPaint.setColor(color);
		// ����ݷ���
		mPaint.setAntiAlias(true);
		mPaint.setTypeface(typeface);
		mPaint.setTextSize(size);
		mPaint.setTextAlign(A);

		FontMetrics fontMetrics = mPaint.getFontMetrics();
		// �������ָ߶�
		float fontHeight = fontMetrics.bottom - fontMetrics.top;

		float textBaseY = h - fontHeight / 2;
		canvas.drawText(text, w, textBaseY, mPaint);
	}

	public void DrawImage(Canvas canvas, Bitmap bitmap, int srcx, int srcy,
			int w, int h, int desx, int desy) {
		Rect src = new Rect(srcx, srcy, srcx + w, srcy + h);
		Rect dest = new Rect(desx, desy, desx + w, desy + h);

		// cut the picture from src, and then fill it to the dest fully by the
		// rect.
		canvas.drawBitmap(bitmap, src, dest, mPaint);

		src = null;
		dest = null;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		for (int i = 0; i < VellLock.LOCK_N; i++) {
			if (mMoveValid[i] && keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
				if (this.movex[i] >= LEFT_MARGIN + MOVE_X_PIXEL)
					this.movex[i] -= MOVE_X_PIXEL;
			} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
				if (this.movex[i] + this.mSlideBtnImage[i].getWidth() <= this.mBgImage
						.getWidth() - LEFT_MARGIN - MOVE_X_PIXEL)
					this.movex[i] += MOVE_X_PIXEL;
			}
		}
		// �ػ���Ļ
		this.postInvalidate();

		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
		// return true;
	}

	/*
	 * The first method to count unlock is that in onTouchEvent. Another method
	 * is using GestureDetector to deal with it.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// get touched position
		int action = event.getAction();
		if (action == MotionEvent.ACTION_MOVE) {
			for (int i = 0; i < VellLock.LOCK_N; i++) {
				if (mMoveValid[i]) {
					int x = (int) event.getX();
					x -= fingerInBtn[i];
					if (x > this.mBgImage.getWidth() - LEFT_MARGIN
							+ MOVE_X_PIXEL - this.mSlideBtnImage[i].getWidth())
						movex[i] = this.mBgImage.getWidth() - LEFT_MARGIN
								- this.mSlideBtnImage[i].getWidth();
					else if (x < LEFT_MARGIN)
						movex[i] = LEFT_MARGIN;
					else
						movex[i] = (int) event.getX() - fingerInBtn[i];
					break;
				}
			}
		} else if (action == MotionEvent.ACTION_DOWN) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			int[] topY = new int[VellLock.LOCK_N];
			int[] bottomY = new int[VellLock.LOCK_N];
			for (int i = 0; i < VellLock.LOCK_N; i++) {
				topY[i] = SLIDEBAR_Y[i]
						+ (mBgImage.getHeight() - mSlideBtnImage[i].getHeight())
						/ 2;
				bottomY[i] = topY[i] + mBgImage.getHeight();
			}
			boolean flag = false;// �Ƿ�����
			for (int i = 0; i < VellLock.LOCK_N; i++) {
				if ((y > topY[i]) && (y < bottomY[i]) && (x > movex[i])
						&& (x < movex[i] + mSlideBtnImage[i].getWidth())) {
					mSlideBtnImage[i] = mSlideBtnDnImage;
					fingerInBtn[i] = (int) event.getX() - movex[i];
					mMoveValid[i] = true;
					// ��������
					if (bgImage == bgImage2 && mPlaySound)
						word[i].playSound();
					flag = true;
					break;
				}
			}
			// û����
			if (!flag) {
				for (int i = 0; i < VellLock.LOCK_N; i++)
					mMoveValid[i] = false;
				if (bgImage == bgImage1)
					bgImage = bgImage2;
				else
					bgImage = bgImage1;
			}
		} else if (action == MotionEvent.ACTION_UP) {
			for (int i = 0; i < VellLock.LOCK_N; i++) {
				movex[i] = LEFT_MARGIN;
				mSlideBtnImage[i] = mSlideBtnUpImage;
				mMoveValid[i] = false;
			}
		}

		// �ػ���Ļ
		this.postInvalidate();

		for (int i = 0; i < VellLock.LOCK_N; i++) {
			// �����Ƿ�ɹ�
			if (movex[i] == this.mBgImage.getWidth() - LEFT_MARGIN
					- this.mSlideBtnImage[i].getWidth()
					&& mMoveValid[i] == true) {
				if (ANSWER == i) {
					DBO.saveRecordToDb(word, i, true);
					// �˳�����֮ǰ����һ������widget
					// /////////////test//vell.AppWidgetBroadCast////////////////////
					Intent intent = new Intent();
					intent.setAction(broadCastString);
					mContext.sendBroadcast(intent);
					// /////////////test//////////////////////

					mParentActivity.finish(); // exit the process only.
				} else {
					DBO.saveRecordToDb(word, i, false);
					virbate(); // ��һ��
				}
				// System.exit(0); // exit both process and clean user data
			}
		}
		// TODO Auto-generated method stub
		// return super.onTouchEvent(event);
		super.onTouchEvent(event);
		return true;
	}

	// ��
	private void virbate() {
		// ˳�㼤��ͷ��
		bgImage = bgImage2;
		Vibrator vibrator = (Vibrator) mContext
				.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(500);
	}

	// �������
	public void setParentActivity(Activity parentAct) {
		mParentActivity = parentAct;
	}

	// ���ð�ť��ʾ��Ϣ
	public void setWord() {
		// ����趨����
		Word[] w;
		w = DBO.selectWords();
		for (int i = 0; i < VellLock.LOCK_N; i++) {
			word[i] = w[i];
		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		// return super.dispatchKeyEvent(event);
		return true;
	}
}
