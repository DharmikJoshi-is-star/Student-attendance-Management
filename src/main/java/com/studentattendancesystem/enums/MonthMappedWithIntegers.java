package com.studentattendancesystem.enums;

public class MonthMappedWithIntegers {

public static String getmonthWithIntValue(int month) {
		
		if(month == 0)
			return MONTHS.JANUARY.toString();
		else if(month == 1)
			return MONTHS.FEBRUARY.toString();
		else if(month == 2)
			return MONTHS.MARCH.toString();
		else if(month == 3)
			return MONTHS.APRIL.toString();
		else if(month == 4)
			return MONTHS.MAY.toString();
		else if(month == 5)
			return MONTHS.JUNE.toString();
		else if(month == 6)
			return MONTHS.JULY.toString();
		else if(month == 7)
			return MONTHS.AUGUST.toString();
		else if(month == 8)
			return MONTHS.SEPTEMBER.toString();
		else if(month == 9)
			return MONTHS.OCTOBER.toString();
		else if(month == 10)
			return MONTHS.NOVEMBER.toString();
		else if(month == 11)
			return MONTHS.DECEMBER.toString();
		
		else return "";
	}
	
public static int getmonthWithIntValue(String month) {
	
	
	if(month.equals( MONTHS.JANUARY.toString() ))
		return 0;
	else if(month.equals( MONTHS.FEBRUARY.toString() ))
		return 1;
	else if(month.equals( MONTHS.MARCH.toString() ))
		return 2;
	else if(month.equals( MONTHS.APRIL.toString() ))
		return 3;
	else if(month.equals( MONTHS.MAY.toString() ))
		return 4;
	else if(month.equals( MONTHS.JUNE.toString() ))
		return 5;
	else if(month.equals( MONTHS.JULY.toString() ))
		return 6;
	else if(month.equals( MONTHS.AUGUST.toString() ))
		return 7;
	else if(month.equals( MONTHS.SEPTEMBER.toString() ))
		return 8;
	else if(month.equals( MONTHS.OCTOBER.toString() ))
		return 9;
	else if(month.equals( MONTHS.NOVEMBER.toString() ))
		return 10;
	else if(month.equals( MONTHS.DECEMBER.toString() ))
		return 11;
	else 
		return -1;
	
}

}