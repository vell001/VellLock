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
	// 解压后
	private String[] File = new String[] { "cet_4.db", "cet_6.db" };
	private Handler handler = new Handler() {
		@Override
		// 信息
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				int size = msg.getData().getInt("size");
				downloadbar.setProgress(size);
				float result = (float) downloadbar.getProgress()
						/ (float) downloadbar.getMax();
				int p = (int) (result * 100);
				resultView.setText(p + "%");
				if (downloadbar.getProgress() == downloadbar.getMax())// 下载完成
				{
					Toast.makeText(Downloading.this, R.string.success, 1)
							.show();
					// 解压
					Toast.makeText(Downloading.this, "解压中。。。时间可能有点长哦。。。",
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
		// 接受传入的下载id
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
				Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
						Toast.LENGTH_SHORT).show();
			}
		});
		// 判断是否存在下载包
		if (DownloadID < 2 && isFileExist(File[DownloadID], ROOT_PATH)) {
			Toast.makeText(this, "词库已经存在了哦。。。", Toast.LENGTH_SHORT);
			this.finish();
		}
		String path = DOWNLOAD_URL + FileName[DownloadID];
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File dir = new File(ROOT_PATH);//
			Environment.getExternalStorageDirectory();//
			// 文件保存目录
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
			// 判断文件名f中是否包含keyword
			if (f.getName().indexOf(filename) == 0) {
				// f.getPath()返回文件的路径
				return true;
			}
		}
		return false;
	}

	// 删除文件
	public void deleteFile(File file) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
		} else {
			Toast.makeText(getBaseContext(), "删除失败：文件不存在", Toast.LENGTH_SHORT);
		}
	}

	private void download(final String path, final File dir) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					SmartFileDownloader loader = new SmartFileDownloader(
							Downloading.this, path, dir, 3);
					int length = loader.getFileSize();// 获取文件的长度
					downloadbar.setMax(length);
					loader.download(new SmartDownloadProgressListener() {
						@Override
						public void onDownloadSize(int size) {// 可以实时得到文件下载的长度
							Message msg = new Message();
							msg.what = 1;
							msg.getData().putInt("size", size);
							handler.sendMessage(msg);
						}
					});
				} catch (Exception e) {
					Message msg = new Message();// 信息提示
					msg.what = -1;
					msg.getData().putString("error", "下载失败");// 如果下载错误，显示提示失败！
					handler.sendMessage(msg);
				}
			}
		}).start();// 开始
	}

	public void cancel_btn(View v) {
		System.exit(0);
	}
}
