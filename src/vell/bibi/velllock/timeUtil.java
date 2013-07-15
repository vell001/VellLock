package vell.bibi.velllock;

import java.util.Calendar;

public class timeUtil {
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;

	// 计算相对时间(2013.1.1/0:0)
	public int sumRelativeDate() {
		int D = 0, M = 0;// 天数
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);

		for (int i = 2013; i < mYear; i++) {
			if (isLeapYear(mYear))
				D = D + 366;
			else
				D = D + 365;
		}
		for (int i = 1; i < mMonth; i++)
			D = D + monthdays(mYear, i);

		M = (D + mDay - 1) * 1440;
		M = M + mHour * 60 + mMinute;
		return M;
	}

	// 计算相对时间
	public int sumRelativeTime(int y, int mo, int d, int h, int mi) {
		int D = 0, M = 0;// 天数

		for (int i = 2013; i < y; i++) {
			if (isLeapYear(y))
				D = D + 366;
			else
				D = D + 365;
		}
		for (int i = 1; i < mo; i++)
			D = D + monthdays(y, i);

		M = (D + d) * 1440;
		M = M + h * 60 + mi;
		return M;
	}

	// 判断闰年
	public boolean isLeapYear(int year) {
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
			return true;
		else
			return false;
	}

	// 判断一个月的天数
	public int monthdays(int year, int month) {
		int N = 0;
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12)
			N = 31;
		else if (month == 4 || month == 6 || month == 9 || month == 11)
			N = 30;
		else if (month == 2)
			if (isLeapYear(year))
				N = 29;
			else
				N = 28;
		return N;
	}
}
