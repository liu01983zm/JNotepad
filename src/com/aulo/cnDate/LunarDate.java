package com.aulo.cnDate;
// ����������,�̳��Զ���������
class LunarDate extends MyDate {

  private String sChineseNum[] = { "��", "һ", "��", "��", "��", "��", "��", "��", "��", "��", "ʮ" };

  private static int checkMonth(int iYear, int iMonth) {
    if ((iMonth > 12) && (iMonth == ChineseCalendar.iGetLLeapMonth(iYear) + 12)) {
      return iMonth;
    } else if (iMonth > 12) {
      System.out.println("Month out of range, I think you want 12 ");
      return 12;
    } else if (iMonth < 1) {
      System.out.println("Month out of range, I think you want 1 ");
      return 1;
    } else
      return iMonth;
  }

  private static int checkDay(int iYear, int iMonth, int iDay) {
    int iMonthDays = ChineseCalendar.iGetLMonthDays(iYear, iMonth);

    if (iDay > iMonthDays) {
      System.out.println("Day out of range, I think you want " + iMonthDays + " ");
      return iMonthDays;
    } else if (iDay < 1) {
      System.out.println("Day out of range, I think you want 1 ");
      return 1;
    } else
      return iDay;
  }

  public LunarDate(int iYear, int iMonth, int iDay) {
    super(iYear);
    this.iMonth = checkMonth(this.iYear, iMonth);
    this.iDay = checkDay(this.iYear, this.iMonth, iDay);
  }

  public LunarDate(int iYear, int iMonth) {
    super(iYear);
    this.iMonth = checkMonth(this.iYear, iMonth);
  }

  public LunarDate(int iYear) {
    super(iYear);
  }

  public LunarDate() {
    super();
  }
  public LunarDate(LunarDate ldate) {
    super();
    this.iYear = ldate.iYear;
	this.iMonth = checkMonth(this.iYear, ldate.iMonth);
	this.iDay = checkDay(this.iYear, this.iMonth, ldate.iDay);

  }
  public String toString() {
    String sCalendar = "ũ��";

    sCalendar += sChineseNum[iYear / 1000] + sChineseNum[iYear % 1000 / 
100] + sChineseNum[iYear % 100 / 10]
        + sChineseNum[iYear % 10] + "(" + toChineseEra() + ")��";
    if (iMonth > 12) {
      iMonth -= 12;
      sCalendar += "��";
    }
    if (iMonth == 12)
      sCalendar += "����";
    else if (iMonth == 11)
      sCalendar += "����";
    else if (iMonth == 1)
      sCalendar += "����";
    else
      sCalendar += sChineseNum[iMonth] + "��";
    if (iDay > 29)
      sCalendar += "��ʮ";
    else if (iDay > 20)
      sCalendar += "��ʮ" + sChineseNum[iDay % 20];
    else if (iDay == 20)
      sCalendar += "��ʮ";
    else if (iDay > 10)
      sCalendar += "ʮ" + sChineseNum[iDay % 10];
    else
      sCalendar += "��" + sChineseNum[iDay];

    return sCalendar;
  }

  public CnWeek toWeek() {
    int iOffsetDays = 0;
    for (int i = 1901; i < iYear; i++)
      iOffsetDays += ChineseCalendar.iGetLYearDays(i);

    iOffsetDays += ChineseCalendar.iGetLNewYearOffsetDays(iYear, iMonth, 
iDay);
    return new CnWeek((iOffsetDays + 2) % 7);
  }

  public ChineseEra toChineseEra() {
    return new ChineseEra(iYear);
  }

  public SolarDate toSolarDate() {
    int iYear, iMonth, iDay, iDate;
    SolarDate sd;
    iDate = Integer.parseInt(ChineseCalendar.sCalendarLundarToSolar
(this.iYear, this.iMonth, this.iDay));
    iYear = iDate / 10000;
    iMonth = iDate % 10000 / 100;
    iDay = iDate % 100;
    sd = new SolarDate(iYear, iMonth, iDay);
    return sd;
  }
}
