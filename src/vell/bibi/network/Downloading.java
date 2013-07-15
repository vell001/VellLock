package vell.bibi.network;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipException;

import vell.bibi.fileservice.ZipUtils;
import vell.bibi.velllock.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Downloading extends Activity {
	// private MyDialog dialog;
	private LinearLayout layout;
	private TextView resultView, downloadText;
	private ProgressBar downloadbar;
	private String ROOT_PATH = "/sdcard/velllock/";
	private String DOWNLOAD_URL = "http://vell001.clanmark.com/data/";
	private int DownloadID;
	private String[] FileName = new String[] { "cet_4_db.zip", "cet_6_db.zip",
			"cet_4_sounds.zip", "cet_6_sounds.zip" };
	// ��ѹ��
	private String[] File = new String[] { "cet_4.db", "cet_6.db" };
	private Handler handler = new Handler() {
		@Override
		// ��Ϣ
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				int size = msg.getData().getInt("size");
				downloadbar.setProgress(size);
				float result = (float) downloadbar.getProgress()
						/ (float) downloadbar.getMax();
				int p = (int) (result * 100);
				resultView.setText(p + "%");
				if (downloadbar.getProgress() == downloadbar.getMax())// �������
				{
					Toast.makeText(Downloading.this, R.string.success, 1)
							.show();
					// ��ѹ
					Toast.makeText(Downloading.this, "��ѹ�С�����ʱ������е㳤Ŷ������",
							Toast.LENGTH_SHORT).show();
					File file = new File(ROOT_PATH, FileName[DownloadID]);
					try {
						ZipUtils.upZipFile(file, ROOT_PATH);
					} catch (ZipException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					finish();
				}
				break;

			case -1:
				Toast.makeText(Downloading.this, R.string.error, 1).show();
				break;
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.downloading);
		// ���ܴ��������id
		DownloadID = this.getIntent().getIntExtra("ID", 3);
		// dialog=new MyDialog(this);
		layout = (LinearLayout) findViewById(R.id.downloading);
		downloadbar = (ProgressBar) findViewById(R.id.downloadbar);
		resultView = (TextView) findViewById(R.id.result);
		resultView.setText(FileName[DownloadID]);
		downloadText = (TextView) findViewById(R.id.download_text);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "��ʾ����������ⲿ�رմ��ڣ�",
						Toast.LENGTH_SHORT).show();
			}
		});
		// �ж��Ƿ�������ذ�
		if (DownloadID < 2 && isFileExist(File[DownloadID], ROOT_PATH)) {
			Toast.makeText(this, "�ʿ��Ѿ�������Ŷ������", Toast.LENGTH_SHORT);
			this.finish();
		}
		String path = DOWNLOAD_URL + FileName[DownloadID];
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File dir = new File(ROOT_PATH);//
			Environment.getExternalStorageDirectory();//
			// �ļ�����Ŀ¼
			download(path, dir);

		} else {
			Toast.makeText(Downloading.this, R.string.sdcarderror, 1).show();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	public Boolean isFileExist(String filename, String filedir) {
		File[] files = new File(filedir).listFiles();
		if (!files[0].exists())
			return null;
		for (File f : files) {
			// �ж��ļ���f���Ƿ����keyword
			if (f.getName().indexOf(filename) == 0) {
				// f.getPath()�����ļ���·��
				return true;
			}
		}
		return false;
	}

	// ɾ���ļ�
	public void deleteFile(File file) {
		if (file.exists()) { // �ж��ļ��Ƿ����
			if (file.isFile()) { // �ж��Ƿ����ļ�
				file.delete(); // delete()���� ��Ӧ��֪�� ��ɾ������˼;
			} else if (file.isDirectory()) { // �����������һ��Ŀ¼
				File files[] = file.listFiles(); // ����Ŀ¼�����е��ļ� files[];
				for (int i = 0; i < files.length; i++) { // ����Ŀ¼�����е��ļ�
					this.deleteFile(files[i]); // ��ÿ���ļ� ������������е���
				}
			}
			file.delete();
		} else {
			Toast.makeText(getBaseContext(), "ɾ��ʧ�ܣ��ļ�������", Toast.LENGTH_SHORT);
		}
	}

	private void download(final String path, final File dir) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					SmartFileDownloader loader = new SmartFileDownloader(
							Downloading.this, path, dir, 3);
					int length = loader.getFileSize();// ��ȡ�ļ��ĳ���
					downloadbar.setMax(length);
					loader.download(new SmartDownloadProgressListener() {
						@Override
						public void onDownloadSize(int size) {// ����ʵʱ�õ��ļ����صĳ���
							Message msg = new Message();
							msg.what = 1;
							msg.getData().putInt("size", size);
							handler.sendMessage(msg);
						}
					});
				} catch (Exception e) {
					Message msg = new Message();// ��Ϣ��ʾ
					msg.what = -1;
					msg.getData().putString("error", "����ʧ��");// ������ش�����ʾ��ʾʧ�ܣ�
					handler.sendMessage(msg);
				}
			}
		}).start();// ��ʼ
	}

	public void cancel_btn(View v) {
		System.exit(0);
	}
}
