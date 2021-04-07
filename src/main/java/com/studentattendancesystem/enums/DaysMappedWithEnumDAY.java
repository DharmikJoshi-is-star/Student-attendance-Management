package com.studentattendancesystem.enums;

import java.sql.Date;
import java.sql.Time;

import org.springframework.stereotype.Service;

public class DaysMappedWithEnumDAY {

	public static String getDayWithIntValue(int day) {
		
		if(day == 0)
			return DAY.SUNDAY.toString();
		else if(day == 1)
			return DAY.MONDAY.toString();
		else if(day == 2)
			return DAY.TUESDAY.toString();
		else if(day == 3)
			return DAY.WEDNESDAY.toString();
		else if(day == 4)
			return DAY.THURSDAY.toString();
		else if(day == 5)
			return DAY.FRIDAY.toString();
		else if(day == 6)
			return DAY.SATURDAY.toString();
		else return "";
	}
	
	public static int getDayWithIntValue(String day) {
		
		if(day.equals(DAY.MONDAY.toString()))
			return 0;
		else if(day.equals(DAY.TUESDAY.toString()))
			return 1;
		else if(day.equals(DAY.WEDNESDAY.toString()))
			return 2;
		else if(day.equals(DAY.THURSDAY.toString()))
			return 3;
		else if(day.equals(DAY.FRIDAY.toString()))
			return 4;
		else if(day.equals(DAY.SATURDAY.toString()))
			return 5;
		else if(day.equals(DAY.SUNDAY.toString()))
			return 6;
		else return -1;
	}
	
}
