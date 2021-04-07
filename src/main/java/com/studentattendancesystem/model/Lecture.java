package com.studentattendancesystem.model;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lecture")
public class Lecture {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	private Subject subject;
	
	@ManyToOne
	@JoinColumn(name="class_id")
	private Class classObj;
	   
	//one to one 
	@ManyToOne
	@JoinColumn(name="department_id")
	private Department department;
	
	@ManyToOne
	@JoinColumn(name = "faculty_teaches")
	private Faculty faulty;
	
	@Column(name = "lecture_date")
	private String day;

	@Column(name = "lecture_no_of_day")
	private Integer lectureNumber;
	
	@Column(name = "lecture_start_time")
	private Time startTime;
	
	@Column(name = "lecture_end_time")
	private Time endTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Class getClassObj() {
		return classObj;
	}

	public void setClassObj(Class classObj) {
		this.classObj = classObj;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Faculty getFaulty() {
		return faulty;
	}

	public void setFaulty(Faculty faulty) {
		this.faulty = faulty;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Integer getLectureNumber() {
		return lectureNumber;
	}

	public void setLectureNumber(Integer lectureNumber) {
		this.lectureNumber = lectureNumber;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	

	
	
}
