package com.studentattendancesystem.model.fronend;

public class SubjectAttendanceOnDate {

	private Integer rollNno;
	private String subjectName;
	private String studentName;
	private Boolean[] isPresentLectures;
	public Integer getRollNno() {
		return rollNno;
	}
	public void setRollNno(Integer rollNno) {
		this.rollNno = rollNno;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Boolean[] getIsPresentLectures() {
		return isPresentLectures;
	}
	public void setIsPresentLectures(Boolean[] isPresentLectures) {
		this.isPresentLectures = isPresentLectures;
	}
	
	
}
