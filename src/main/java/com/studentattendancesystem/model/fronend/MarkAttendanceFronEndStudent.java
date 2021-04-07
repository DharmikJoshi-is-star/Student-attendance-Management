package com.studentattendancesystem.model.fronend;

public class MarkAttendanceFronEndStudent {

	private Long lectureAttendanceId;
	private Integer rollNo;
	private String studentName;
	private String departmentName;
	private String gender;
	private Boolean isPresent;
	public Long getLectureAttendanceId() {
		return lectureAttendanceId;
	}
	public void setLectureAttendanceId(Long lectureAttendanceId) {
		this.lectureAttendanceId = lectureAttendanceId;
	}
	public Integer getRollNo() {
		return rollNo;
	}
	public void setRollNo(Integer rollNo) {
		this.rollNo = rollNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Boolean getIsPresent() {
		return isPresent;
	}
	public void setIsPresent(Boolean isPresent) {
		this.isPresent = isPresent;
	}
	
	
	
}
