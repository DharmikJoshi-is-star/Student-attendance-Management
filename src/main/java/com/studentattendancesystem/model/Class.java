package com.studentattendancesystem.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Class {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "class_number")
	private String classNo;
	
	@Column(name="device_code")
	private String deviceCode;
	
	@OneToOne
	private Department department;
	
//	@OneToMany
//	private List<Student> students;
//	
//	@OneToMany
//	private List<ClassLog> classlogs;

//	@OneToMany
//	private List<Lecture> lectures;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClassNo() {
		return classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	

//	public List<Student> getStudents() {
//		return students;
//	}
//
//	public void setStudents(List<Student> students) {
//		this.students = students;
//	}

//	public List<ClassLog> getClasslogs() {
//		return classlogs;
//	}
//
//	public void setClasslogs(List<ClassLog> classlogs) {
//		this.classlogs = classlogs;
//	}

//	public List<Lecture> getLectures() {
//		return lectures;
//	}
//
//	public void setLectures(List<Lecture> lectures) {
//		this.lectures = lectures;
//	}
	
	
	
}
