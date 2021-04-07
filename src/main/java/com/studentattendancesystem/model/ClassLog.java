package com.studentattendancesystem.model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "student_class_log")
public class ClassLog  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//Remove
//	@ManyToOne
//	@JoinColumn(name = "class_id")
	@OneToOne
	private Class classObj;
	
	//One to one
	@OneToOne
	private Student student;
	
	//One to one
	@OneToOne
	private Faculty faculty;
	
	//One to one
	@OneToOne
	private RFIDToken rfidToken;
	
	@Column(name = "status_in_out")
	private String status;
	
	@Column(name = "log_date")
	private Date date;
	
	@Column(name = "log_time")
	private Time time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Class getClassObj() {
		return classObj;
	}

	public void setClassObj(Class classObj) {
		this.classObj = classObj;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public RFIDToken getRfidToken() {
		return rfidToken;
	}

	public void setRfidToken(RFIDToken rfidToken) {
		this.rfidToken = rfidToken;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public ClassLog() {
		super();
	}

	public ClassLog(Long id, Class classObj, Student student, Faculty faculty, RFIDToken rfidToken, String status,
			Date date, Time time) {
		super();
		this.id = id;
		this.classObj = classObj;
		this.student = student;
		this.faculty = faculty;
		this.rfidToken = rfidToken;
		this.status = status;
		this.date = date;
		this.time = time;
	}

	public ClassLog(Class classObj, Student student, Faculty faculty, RFIDToken rfidToken, String status) {
		super();
		this.classObj = classObj;
		this.student = student;
		this.faculty = faculty;
		this.rfidToken = rfidToken;
		this.status = status;
	}
	
	
	
}
