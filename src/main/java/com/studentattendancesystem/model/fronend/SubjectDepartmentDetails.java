package com.studentattendancesystem.model.fronend;

public class SubjectDepartmentDetails {

	private Long id;
	private String name;
	private Integer lecturesAttended;
	private Float averageAttended;
	private Integer lectureCount;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLectureCount() {
		return lectureCount;
	}
	public void setLectureCount(Integer lectureCount) {
		this.lectureCount = lectureCount;
	}
	public Integer getLecturesAttended() {
		return lecturesAttended;
	}
	public void setLecturesAttended(Integer lecturesAttended) {
		this.lecturesAttended = lecturesAttended;
	}
	public Float getAverageAttended() {
		return averageAttended;
	}
	public void setAverageAttended(Float averageAttended) {
		this.averageAttended = averageAttended;
	}
	
	
}
