package com.studentattendancesystem.model.fronend;

public class StudentFrontEndModel {

	/*
	studentName
	studentRollNo
	studentGender
	studentDepartmentName
	studentTotalLecturesPresent
	studentAverageAttendance
	studentRFID
	studentCurrentStatus
	studentCurrentClass
	studentCurrentLogDate
	studentCurrentLogTime
	*/
	
	private Long id;
	private Integer rollno;
	private String name;
	private String deptName;
	private String gender;
	private String status;
	
	private Integer totalLecturesPresent;
	
	private Integer totalLecturesTaken;
	
	private Float averageAttendance;
	
	private String rfid;
	
	private String currentStatus;
	
	private String currentClass;
	
	private String currentLogDate;
	
	private String currentLogTime;
	
	private String mailAddress;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getRollno() {
		return rollno;
	}
	public void setRollno(Integer rollno) {
		this.rollno = rollno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public StudentFrontEndModel(Long id, Integer rollno, String name, String deptName, String gender, String status) {
		super();
		this.id = id;
		this.rollno = rollno;
		this.name = name;
		this.deptName = deptName;
		this.gender = gender;
		this.status = status;
	}
	public StudentFrontEndModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getTotalLecturesPresent() {
		return totalLecturesPresent;
	}
	public void setTotalLecturesPresent(Integer totalLecturesPresent) {
		this.totalLecturesPresent = totalLecturesPresent;
	}
	public Float getAverageAttendance() {
		return averageAttendance;
	}
	public void setAverageAttendance(Float averageAttendance) {
		this.averageAttendance = averageAttendance;
	}
	public String getRfid() {
		return rfid;
	}
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getCurrentClass() {
		return currentClass;
	}
	public void setCurrentClass(String currentClass) {
		this.currentClass = currentClass;
	}
	public String getCurrentLogDate() {
		return currentLogDate;
	}
	public void setCurrentLogDate(String currentLogDate) {
		this.currentLogDate = currentLogDate;
	}
	public String getCurrentLogTime() {
		return currentLogTime;
	}
	public void setCurrentLogTime(String currentLogTime) {
		this.currentLogTime = currentLogTime;
	}
	
	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public Integer getTotalLecturesTaken() {
		return totalLecturesTaken;
	}
	public void setTotalLecturesTaken(Integer totalLecturesTaken) {
		this.totalLecturesTaken = totalLecturesTaken;
	}
	
	
	
}
