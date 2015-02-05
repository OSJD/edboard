package com.acc.tools.ed.integration.util;

import java.text.DateFormatSymbols;

public enum CalendarEnum {
	Mon(1),
	Tue(2),
	Wed(3),
	Thu(4),
	Fri(5),
	Sat(6),
	Sun(7);
	private int dayId;
	CalendarEnum(int dayId){
		this.dayId=dayId;
	}
	
	public static int getDayId(String day){
		for(CalendarEnum weekday:values()){
			if(weekday.name().equalsIgnoreCase(day)){
				return weekday.dayId;
			}
		}
		return 0;
	}
	public static int getDaysInWeek(String day){
		if(CalendarEnum.Mon.name().equalsIgnoreCase(day))
			return 7;
		else if(CalendarEnum.Tue.name().equalsIgnoreCase(day))
			return 6;
		else if(CalendarEnum.Wed.name().equalsIgnoreCase(day))
			return 5;
		else if(CalendarEnum.Thu.name().equalsIgnoreCase(day))
			return 4;
		else if(CalendarEnum.Fri.name().equalsIgnoreCase(day))
			return 3;
		else if(CalendarEnum.Sat.name().equalsIgnoreCase(day))
			return 2;
		else 
			return 1;
	}
	
	public static String getMonthName(int month){
		String[] months = new DateFormatSymbols().getMonths();
		return months[month-1];
	}
}
