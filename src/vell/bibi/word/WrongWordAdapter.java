package vell.bibi.word;

import java.lang.reflect.Field;
import java.util.List;

import vell.bibi.velllock.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WrongWordAdapter extends BaseAdapter {

	private List<Word> WordList;

	private LayoutInflater layoutInflater;

	private Context mContext;

	public WrongWordAdapter(Context context, List<Word> list) {
		// TODO Auto-generated constructor stub
		this.WordList = list;
		this.mContext = context;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return WordList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return WordList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	// ������ʾ��
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// ��̬������Ϣ
		convertView = layoutInflater.inflate(R.layout.list_theme, null);
		final TextView tv = (TextView) convertView.findViewById(R.id.word_body);
		tv.setText(WordList.get(position).getBODY() + "     "
				+ Integer.toString(WordList.get(position).getLEARNWEIGHT())
				+ "%");
		tv.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					// �������º���ʾ��ϸ��Ϣ
					new AlertDialog.Builder(mContext)
							.setTitle(
									WordList.get(position).getBODY()
											+ "  ("
											+ Integer.toString(WordList.get(
													position).getRIGHTNUM())
											+ "/"
											+ Integer.toString(WordList.get(
													position).getRIGHTNUM()
													+ WordList.get(position)
															.getWRONGNUM())
											+ ")")
							.setItems(
									new String[] {
											WordList.get(position).getBODY_ZH()
													+ " ["
													+ WordList.get(position)
															.getSOUNDMARK()
													+ "]",
											"���䣺"
													+ WordList.get(position)
															.getUSAGE_EN(),
											"���ͣ�"
													+ WordList.get(position)
															.getUSAGE_ZH() },
									null)
							.setPositiveButton("����",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											WordList.get(position).playSound();
											// ���رմ���
											try {
												Field field = dialog
														.getClass()
														.getSuperclass()
														.getDeclaredField(
																"mShowing");
												field.setAccessible(true);
												field.set(dialog, false);
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									})
							.setNegativeButton("ȷ��",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											// �رմ���
											try {
												Field field = dialog
														.getClass()
														.getSuperclass()
														.getDeclaredField(
																"mShowing");
												field.setAccessible(true);
												field.set(dialog, true);
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									}).show();
				}
				return true;
			}
		});
		return convertView;
	}
}
