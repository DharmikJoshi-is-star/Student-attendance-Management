package com.studentattendancesystem.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lecture_attendances")
public class LectureAttendance {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	private Lecture lecture;
	
	@OneToOne
	private Student student;
	
	private Date date;
	
	private Time time;
	
	private Boolean isPresent;
	
	private Boolean isBuffered;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lecture getLecture() {
		return lecture;
	}

	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
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

	public Boolean getIsPresent() {
		return isPresent;
	}

	public void setIsPresent(Boolean isPresent) {
		this.isPresent = isPresent;
	}

	public Boolean getIsBuffered() {
		return isBuffered;
	}

	public void setIsBuffered(Boolean isBuffered) {
		this.isBuffered = isBuffered;
	}

	public LectureAttendance(Lecture lecture, Student student, Date date, Time time, Boolean isPresent,
			Boolean isBuffered) {
		super();
		this.lecture = lecture;
		this.student = student;
		this.date = date;
		this.time = time;
		this.isPresent = isPresent;
		this.isBuffered = isBuffered;
	}

	public LectureAttendance() {
		super();
		
	}

	
	
}
