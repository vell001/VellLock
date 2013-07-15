package vell.bibi.word;

import vell.bibi.velllock.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class YoudaoActivity extends Activity {
	// ��ѯ��ť����
	private ImageButton myButton01;
	// ���������
	private EditText mEditText1;
	// �������ݵ�WebView����
	private WebView mWebView1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.youdao_search);
		// ��ò��ֵļ����ؼ�
		myButton01 = (ImageButton) findViewById(R.id.search_btn);
		mEditText1 = (EditText) findViewById(R.id.search_et);
		mWebView1 = (WebView) findViewById(R.id.youdao_web);

		// ��ѯ��ť����¼�
		myButton01.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				//���������
				InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
				imm.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
				String strURI = (mEditText1.getText().toString());
				strURI = strURI.trim();
				// �����ѯ����Ϊ����ʾ
				if (strURI.length() == 0) {
					Toast.makeText(YoudaoActivity.this, "��ѯ���ݲ���Ϊ��!", Toast.LENGTH_LONG)
							.show();
				}
				// �������Բ�������ʽ��http://dict.youdao.com/mȡ�����ݣ����ص�WebView��.
				else {
					String strURL = "http://dict.youdao.com/m/search?keyfrom=dict.mindex&q="
							+ strURI;
					mWebView1.loadUrl(strURL);
				}
			}
		});
	}
}
