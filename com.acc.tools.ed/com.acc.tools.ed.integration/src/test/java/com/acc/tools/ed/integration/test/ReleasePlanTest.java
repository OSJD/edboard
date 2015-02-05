package com.acc.tools.ed.integration.test;
import java.text.DateFormatSymbols;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;


public class ReleasePlanTest {

	public static void main(String[] args) {
		LocalDate dateStart = new LocalDate("2014-12-07");
		System.out.println(CalendarEnum.getMonthName(dateStart.getMonthOfYear()));
		LocalDate dateEnd = new LocalDate("2015-01-04");
		Map<String,Map<String,Map<String,String>>> resourceWeeksMap=new LinkedHashMap<String,Map<String,Map<String,String>>>();
		Map<String,Map<String,String>> weeksMap=new LinkedHashMap<String,Map<String,String>>();
		int weekIdCount=0;
		int weekIdRunningCount=0;
		String firstDayInWeek="Mon";
		while(dateStart.isBefore(dateEnd)){
			String dayType=dateStart.dayOfWeek().getAsShortText();
			int dayId=CalendarEnum.getDayId(dayType);
			if(weekIdCount==0 && !StringUtils.containsIgnoreCase("SunSat", dayType)){
				weekIdCount=1;
				firstDayInWeek=dayType;
			} 
			Map<String,String> weekDaysMap=weeksMap.get(weekIdCount+"-Week");
			if(weekDaysMap==null){
				weekDaysMap=new LinkedHashMap<String,String>();
			}
			weekDaysMap.put(dayType, dateStart.dayOfMonth().getAsString());
			weeksMap.put(weekIdCount+"-Week",weekDaysMap);
		    dateStart = dateStart.plusDays(1);
			if(weekIdCount==0 && (dayId==6 || dayId==7)){
				weekIdRunningCount++;
				if(dayId==7){
					weekIdCount++;
					weekIdRunningCount=0;
				}
			} else if(weekIdRunningCount==CalendarEnum.getDaysInWeek(firstDayInWeek)-1){
				weekIdCount++;
				weekIdRunningCount=0;
				firstDayInWeek=dateStart.dayOfWeek().getAsShortText();
			} else {
				weekIdRunningCount++;
			}
			
		}
		resourceWeeksMap.put("Developer A", weeksMap);
		resourceWeeksMap.put("Developer B", weeksMap);
		System.out.println("------------------------------------------------");
		for(Map.Entry<String,Map<String,String>> week:weeksMap.entrySet()){
			System.out.println(week.getKey());
			for(Map.Entry<String,String> weekDay:week.getValue().entrySet()){
				System.out.print(weekDay.getKey() +"["+weekDay.getValue()+"] |");
			}
			System.out.println("");
		}

	}
	
	enum CalendarEnum{
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

}
