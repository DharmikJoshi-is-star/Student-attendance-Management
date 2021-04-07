package com.studentattendancesystem.model.fronend;

public class StudentMonthlyAttendaceReport {

	private String month;
	private String year;
	private Integer totalLecturesAttended;
	private Integer totalLecturesTaken;
	private Float averageAttendance;
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Integer getTotalLecturesAttended() {
		return totalLecturesAttended;
	}
	public void setTotalLecturesAttended(Integer totalLecturesAttended) {
		this.totalLecturesAttended = totalLecturesAttended;
	}
	public Integer getTotalLecturesTaken() {
		return totalLecturesTaken;
	}
	public void setTotalLecturesTaken(Integer totalLecturesTaken) {
		this.totalLecturesTaken = totalLecturesTaken;
	}
	public Float getAverageAttendance() {
		return averageAttendance;
	}
	public void setAverageAttendance(Float averageAttendance) {
		this.averageAttendance = averageAttendance;
	}
	
	
}
