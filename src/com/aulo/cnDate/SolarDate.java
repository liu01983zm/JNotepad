package com.aulo.cnDate;

// 阳历日期类,继承自定义日期
class SolarDate extends MyDate {

	private static int checkMonth(int iMonth) {
		if (iMonth > 12) {
			System.out.println("Month out of range, I think you want 12 ");
			return 12;
		} else if (iMonth < 1) {
			System.out.println("Month out of range, I think you want 1 ");
			return 1;
		} else
			return iMonth;
	}

	private static int checkDay(int iYear, int iMonth, int iDay) {
		int iMonthDays = ChineseCalendar.iGetSYearMonthDays(iYear, iMonth);

		if (iDay > iMonthDays) {
			System.out.println("Day out of range, I think you want "
					+ iMonthDays + " ");
			return iMonthDays;
		} else if (iDay < 1) {
			System.out.println("Day out of range, I think you want 1 ");
			return 1;
		} else
			return iDay;
	}

	public SolarDate(int iYear, int iMonth, int iDay) {
		super(iYear);
		this.iMonth = checkMonth(iMonth);
		this.iDay = checkDay(this.iYear, this.iMonth, iDay);
	}

	public SolarDate(int iYear, int iMonth) {
		super(iYear);
		this.iMonth = checkMonth(iMonth);
	}

	public SolarDate(int iYear) {
		super(iYear);
	}

	public SolarDate() {
		super();
	}

	public String toString() {
		return "" + this.iYear
				+ (this.iMonth > 9 ? "-" + this.iMonth : "-0" + this.iMonth)
				+ (this.iDay > 9 ? "-" + this.iDay : "-0" + this.iDay);
	}

	public Week toWeek() {
		int iOffsetDays = 0;
		for (int i = 1901; i < iYear; i++) {
			if (ChineseCalendar.bIsSolarLeapYear(i))
				iOffsetDays += 366;
			else
				iOffsetDays += 365;
		}

		iOffsetDays += ChineseCalendar.iGetSNewYearOffsetDays(iYear, iMonth,
				iDay);
		return new Week((iOffsetDays + 2) % 7);
	}

	public LunarDate toLunarDate() {
		int iYear, iMonth, iDay, iDate;
		LunarDate ld;
		iDate = Integer.parseInt(ChineseCalendar.sCalendarSolarToLundar(
				this.iYear, this.iMonth, this.iDay));
		iYear = iDate / 10000;
		iMonth = iDate % 10000 / 100;
		iDay = iDate % 100;
		ld = new LunarDate(iYear, iMonth, iDay);
		return ld;
	}
}
