package vell.bibi.network;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import android.util.Log;

public class DownloadUtils {
	private static final String TAG = "DownloadService";

	public static boolean requestDownload(URL url, File file) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			return download(url, fos);
		} catch (Throwable t) {
			return false;
		} finally {
		}
	}

	public static void dump(InputStream is, OutputStream os) throws IOException {
		byte buffer[] = new byte[4096];
		int rc = is.read(buffer, 0, buffer.length);
		final Thread thread = Thread.currentThread();

		while (rc > 0) {
			os.write(buffer, 0, rc);
			rc = is.read(buffer, 0, buffer.length);
		}

		Thread.interrupted(); // consume the interrupt signal
	}

	public static boolean download(URL url, OutputStream output) {
		InputStream input = null;
		try {
			input = url.openStream();
			dump(input, output);
			return true;
		} catch (Throwable t) {
			Log.w(TAG, "fail to download", t);
			return false;
		} finally {
		}
	}
}