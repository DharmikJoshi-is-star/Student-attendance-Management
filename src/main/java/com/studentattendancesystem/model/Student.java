package com.studentattendancesystem.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="student")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "student_name")
	private String name;
	
	@Column(name="student_rollno")
	private Integer roll_no;
	
	private String gender;
	
	//one to one
	@OneToOne(cascade = CascadeType.ALL) //Removal of  student will remove student-info
	private StudentInfo studentInfo;
	
	//one to one
	@OneToOne
	private RFIDToken rfidToken;

//	@ManyToOne
//	@JoinColumn(name="class_id")
//	private Class classObj;
	
	@ManyToOne
	@JoinColumn(name="belongs_to_department")
	private Department department;
	
	private String currentStatus;
	
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

	public Integer getRoll_no() {
		return roll_no;
	}

	public void setRoll_no(Integer roll_no) {
		this.roll_no = roll_no;
	}

	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public StudentInfo getStudentInfo() {
		return studentInfo;
	}

	public void setStudentInfo(StudentInfo studentInfo) {
		this.studentInfo = studentInfo;
	}

	public RFIDToken getRfidToken() {
		return rfidToken;
	}

	public void setRfidToken(RFIDToken rfidToken) {
		this.rfidToken = rfidToken;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
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
	
	
}
