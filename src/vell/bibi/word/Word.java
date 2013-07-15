package vell.bibi.word;

import java.io.File;
import java.io.IOException;

import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

public class Word {
	private int WORD_ID;
	private String BODY;
	private String BODY_ZH;
	private String BODY_EN;
	private String USAGE_ZH;
	private String USAGE_EN;
	private int WRONGNUM;
	private int RIGHTNUM;
	private int LEARNWEIGHT;
	private String SOUNDMARK;
	private int UNIT_NUMBER;
	private int POLYPHONE;
	private String SOUNDMARK2;

	public static String SOUND_PATH = "/sdcard/velllock/sounds";
	MediaPlayer mMediaPlayer = new MediaPlayer();

	public Word(int id, String body, String body_zn, String body_en,
			String usage_zn, String usage_en, int WrongNum, int RightNum,
			int LearnWeight, String soundmark, int unit_number, int polyphone,
			String soundmark2) {
		setWORD_ID(id);
		setBODY(body);
		setBODY_ZH(body_zn);
		setBODY_EN(body_en);
		setUSAGE_ZH(usage_zn);
		setUSAGE_EN(usage_en);
		setWRONGNUM(WrongNum);
		setRIGHTNUM(RightNum);
		setLEARNWEIGHT(LearnWeight);
		setSOUNDMARK(soundmark);
		setUNIT_NUMBER(unit_number);
		setPOLYPHONE(polyphone);
		setSOUNDMARK2(soundmark2);
	}

	public Word(String body, String body_zn) {
		setBODY(body);
		setBODY_ZH(body_zn);
	}

	// 查找声音文件
	public String getSoundPath() {
		String keyword = BODY;
		String result = null;
		/* File f = new File("/")指在当前盘符路径下 */
		/* listFiles()可以把目录下面的文件和子目录都打出来 */
		// 列出所在文件夹；
		File dir = new File(SOUND_PATH + "/" + keyword.substring(0, 1));
		if (!dir.exists()){
			return null;
		}
		File[] files = dir.listFiles();
		if (!files[0].exists())
			return null;
		for (File f : files) {
			// 判断文件名f中是否包含keyword
			if (f.getName().indexOf("-$-" + keyword + ".mp3") == 0) {
				// f.getPath()返回文件的路径
				result = f.getAbsolutePath();
			}
		}
		return result;
	}

	public void playSound() {
		String path = getSoundPath();
		if (path == null){
			return;
		}

		try {
			/* 重置MediaPlayer */
			mMediaPlayer.reset();
			/* 设置要播放的文件的路径 */
			mMediaPlayer.setDataSource(path);
			/* 准备播放 */
			mMediaPlayer.prepare();
			/* 开始播放 */
			mMediaPlayer.start();
		} catch (IOException e) {
		}
	}

	public String getBODY_EN() {
		return BODY_EN;
	}

	public void setBODY_EN(String bODY_EN) {
		BODY_EN = bODY_EN;
	}

	public String getUSAGE_ZH() {
		return USAGE_ZH;
	}

	public void setUSAGE_ZH(String uSAGE_ZH) {
		USAGE_ZH = uSAGE_ZH;
	}

	public String getUSAGE_EN() {
		return USAGE_EN;
	}

	public void setUSAGE_EN(String uSAGE_EN) {
		USAGE_EN = uSAGE_EN;
	}

	public String getSOUNDMARK() {
		return SOUNDMARK;
	}

	public void setSOUNDMARK(String sOUNDMARK) {
		SOUNDMARK = sOUNDMARK;
	}

	public int getUNIT_NUMBER() {
		return UNIT_NUMBER;
	}

	public void setUNIT_NUMBER(int uNIT_NUMBER) {
		UNIT_NUMBER = uNIT_NUMBER;
	}

	public int getPOLYPHONE() {
		return POLYPHONE;
	}

	public void setPOLYPHONE(int pOLYPHONE) {
		POLYPHONE = pOLYPHONE;
	}

	public String getSOUNDMARK2() {
		return SOUNDMARK2;
	}

	public void setSOUNDMARK2(String sOUNDMARK2) {
		SOUNDMARK2 = sOUNDMARK2;
	}

	public int getWORD_ID() {
		return WORD_ID;
	}

	public void setWORD_ID(int wORD_ID) {
		WORD_ID = wORD_ID;
	}

	public String getBODY() {
		return BODY;
	}

	public void setBODY(String bODY) {
		BODY = bODY;
	}

	public String getBODY_ZH() {
		return BODY_ZH;
	}

	public void setBODY_ZH(String bODY_ZH) {
		BODY_ZH = bODY_ZH;
	}

	public int getWRONGNUM() {
		return WRONGNUM;
	}

	public void setWRONGNUM(int wRONGNUM) {
		WRONGNUM = wRONGNUM;
	}

	public int getLEARNWEIGHT() {
		return LEARNWEIGHT;
	}

	public void setLEARNWEIGHT(int lEARNWEIGHT) {
		LEARNWEIGHT = lEARNWEIGHT;
	}

	public int getRIGHTNUM() {
		return RIGHTNUM;
	}

	public void setRIGHTNUM(int rIGHTNUM) {
		RIGHTNUM = rIGHTNUM;
	}
}
