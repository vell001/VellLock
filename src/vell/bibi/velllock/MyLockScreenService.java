package vell.bibi.velllock;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

@SuppressLint("NewApi")
public class MyLockScreenService extends Service// implements
// SensorEventListener
{
	protected static final String LOCK_TAG = null;
	private final String ACT_SCREEN_OFF = "android.intent.action.SCREEN_OFF";
	private final String ACT_SCREEN_ON = "android.intent.action.SCREEN_ON";

	private SensorManager mManager = null;
	private Sensor mSensor = null;
	private int Sensitiveness = 18;// ҡ�����ж�
	private int Time = 6;// ҡ��ʱ��
	private int N = 0; // test

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		IntentFilter intentFilterOff = new IntentFilter(ACT_SCREEN_OFF);
		registerReceiver(mScreenOff, intentFilterOff);
		IntentFilter intentFilterOn = new IntentFilter(ACT_SCREEN_ON);
		registerReceiver(mScreenOn, intentFilterOn);
		//��ȡ����
		getSet();
		mManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mManager.registerListener(mListener, mSensor,
				SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(mScreenOff);
		mManager.unregisterListener(mListener);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		// ��ȡҡһҡ��������
		SharedPreferences prefs = getSharedPreferences("lock_setting", MODE_PRIVATE);
		if (!prefs.getBoolean("shake", true))
			mManager.unregisterListener(mListener);
	}

	private BroadcastReceiver mScreenOn = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
		}
	};

	private BroadcastReceiver mScreenOff = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			{
				try {
					Intent i = new Intent();
					// i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					// i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					i.setClass(context, VellLock.class);
					context.startActivity(i);
				} catch (Exception e) {
					// TODO: handle exception
					Log.e("", "***********onReceive Error=" + e);
				}
			}
		}
	};

	private SensorEventListener mListener = new SensorEventListener() {
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		@Override
		@SuppressWarnings("deprecation")
		public void onSensorChanged(SensorEvent event) {
			float x = event.values[SensorManager.DATA_X];
			float y = event.values[SensorManager.DATA_Y];
			float z = event.values[SensorManager.DATA_Z];
			if (isLongTime()
					&& (Math.abs(x) > Sensitiveness
							|| Math.abs(y) > Sensitiveness || Math.abs(z) > Sensitiveness)) {
				lightScreen();
				try {
					Intent i = new Intent();
					i.setClass(getBaseContext(), VellLock.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					// i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

					getBaseContext().startActivity(i);

				} catch (Exception e) {
					// TODO: handle exception
					Log.e("", "***********onReceive Error=" + e);
				}
			}
		}
	};

	private boolean isLongTime() {
		N++;
		if (N > Time) {
			N = 0;
			return true;
		} else
			return false;
	}

	@SuppressLint("Wakelock")
	private void lightScreen() {
		PowerManager powerManager = (PowerManager) this
				.getSystemService(Context.POWER_SERVICE);
		@SuppressWarnings("deprecation")
		WakeLock wakeLock = powerManager.newWakeLock(
				PowerManager.FULL_WAKE_LOCK
						| PowerManager.ACQUIRE_CAUSES_WAKEUP, "");
		if (!powerManager.isScreenOn())
			wakeLock.acquire();
	}

	// ��ȡ����
	private void getSet() {
		SharedPreferences dataPreferences = getSharedPreferences("lock_setting",
				MODE_PRIVATE);
		Sensitiveness =  dataPreferences.getInt("sensor", 18);
		Time =  dataPreferences.getInt("time", 6);
	}
}
