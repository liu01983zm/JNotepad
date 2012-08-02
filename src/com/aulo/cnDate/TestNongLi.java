package com.aulo.cnDate;
public class TestNongLi {
  public static void main(String[] args) {
    // 调用农历日期转换阳历日期方法
    SolarDate normalDate = new SolarDate(2008, 1, 1);
    LunarDate cn_Date = new LunarDate(ChineseCalendar.CalendarSolarToLundar(2008, 1, 1));
	System.out.println(ChineseCalendar.sCalendarSolarToLundar(2008, 1, 1));
    //System.out.println(ChineseCalendar.sCalendarLundarToSolar(2008, 1, 1));
    //LunarDate cn_Date = new LunarDate(2008, 1, 1);
    System.out.println("cn date="+cn_Date.toString());
    
  }
}