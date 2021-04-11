package com.studentattendancesystem.model.fronend;

public class DepartmentFrontEndModel {

	private Long id;
	private String name;
	private Integer facultyCount;
	private Integer subjectCount;
	private Integer studentCount;
	private Integer nfcCount;
	private Integer classCount;
	
	
	private String currentLecture;
	private Integer studentsPresentInLecture;
	private String lectureInClass;
	private String facultyLogDate;
	private String facultyLogTime;
	
	
	
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
	public Integer getFacultyCount() {
		return facultyCount;
	}
	public void setFacultyCount(Integer facultyCount) {
		this.facultyCount = facultyCount;
	}
	public Integer getSubjectCount() {
		return subjectCount;
	}
	public void setSubjectCount(Integer subjectCount) {
		this.subjectCount = subjectCount;
	}
	public Integer getStudentCount() {
		return studentCount;
	}
	public void setStudentCount(Integer studentCount) {
		this.studentCount = studentCount;
	}
	public DepartmentFrontEndModel(Long id, String name, Integer facultyCount, Integer subjectCount,
			Integer studentCount) {
		super();
		this.id = id;
		this.name = name;
		this.facultyCount = facultyCount;
		this.subjectCount = subjectCount;
		this.studentCount = studentCount;
	}
	public DepartmentFrontEndModel() {
		super();
	}
	public String getCurrentLecture() {
		return currentLecture;
	}
	public void setCurrentLecture(String currentLecture) {
		this.currentLecture = currentLecture;
	}
	public Integer getStudentsPresentInLecture() {
		return studentsPresentInLecture;
	}
	public void setStudentsPresentInLecture(Integer studentsPresentInLecture) {
		this.studentsPresentInLecture = studentsPresentInLecture;
	}
	public String getLectureInClass() {
		return lectureInClass;
	}
	public void setLectureInClass(String lectureInClass) {
		this.lectureInClass = lectureInClass;
	}
	public String getFacultyLogDate() {
		return facultyLogDate;
	}
	public void setFacultyLogDate(String facultyLogDate) {
		this.facultyLogDate = facultyLogDate;
	}
	public String getFacultyLogTime() {
		return facultyLogTime;
	}
	public void setFacultyLogTime(String facultyLogTime) {
		this.facultyLogTime = facultyLogTime;
	}
	public Integer getNfcCount() {
		return nfcCount;
	}
	public void setNfcCount(Integer nfcCount) {
		this.nfcCount = nfcCount;
	}
	public Integer getClassCount() {
		return classCount;
	}
	public void setClassCount(Integer classCount) {
		this.classCount = classCount;
	}
	
	
}
