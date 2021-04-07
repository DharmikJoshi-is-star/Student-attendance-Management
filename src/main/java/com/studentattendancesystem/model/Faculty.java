package com.studentattendancesystem.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Faculty {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "faculty_name")
	private String name;
	
	@OneToMany
	private List<Lecture> lectures;
	
	@ManyToOne
	@JoinColumn(name = "belongs_to_department")
	private Department department;
	
	@OneToOne
	private RFIDToken rfidToken;

	private String currentStatus;
	
	@OneToOne
	private LogIn login;
	
	@OneToOne
	private ClassLog currentLog;
	
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

	public List<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(List<Lecture> lectures) {
		this.lectures = lectures;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public RFIDToken getRfidToken() {
		return rfidToken;
	}

	public void setRfidToken(RFIDToken rfidToken) {
		this.rfidToken = rfidToken;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}  

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public ClassLog getCurrentLog() {
		return currentLog;
	}

	public void setCurrentLog(ClassLog currentLog) {
		this.currentLog = currentLog;
	}

	public LogIn getLogin() {
		return login;
	}

	public void setLogin(LogIn login) {
		this.login = login;
	}
	
	
	
}
