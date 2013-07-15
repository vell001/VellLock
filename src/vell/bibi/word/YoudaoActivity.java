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
	// 查询按钮申明
	private ImageButton myButton01;
	// 输入框申明
	private EditText mEditText1;
	// 加载数据的WebView申明
	private WebView mWebView1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.youdao_search);
		// 获得布局的几个控件
		myButton01 = (ImageButton) findViewById(R.id.search_btn);
		mEditText1 = (EditText) findViewById(R.id.search_et);
		mWebView1 = (WebView) findViewById(R.id.youdao_web);

		// 查询按钮添加事件
		myButton01.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				//收起软键盘
				InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
				imm.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
				String strURI = (mEditText1.getText().toString());
				strURI = strURI.trim();
				// 如果查询内容为空提示
				if (strURI.length() == 0) {
					Toast.makeText(YoudaoActivity.this, "查询内容不能为空!", Toast.LENGTH_LONG)
							.show();
				}
				// 否则则以参数的形式从http://dict.youdao.com/m取得数据，加载到WebView里.
				else {
					String strURL = "http://dict.youdao.com/m/search?keyfrom=dict.mindex&q="
							+ strURI;
					mWebView1.loadUrl(strURL);
				}
			}
		});
	}
}
