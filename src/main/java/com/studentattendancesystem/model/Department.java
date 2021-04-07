package com.studentattendancesystem.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "department")
public class Department {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "department_name")
	private String name;
	
	@OneToMany
	private List<Lecture> lectures;
	
	@OneToMany
	private List<Subject> subjects;
	
	@OneToMany
	private List<Faculty> faculties;

	@OneToMany
	private List<Student> students;
	
	private Integer todaysCompletedLectureCount;
	
	private Integer currentLectureNumber;
	
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

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public List<Faculty> getFaculties() {
		return faculties;
	}

	public void setFaculties(List<Faculty> faculties) {
		this.faculties = faculties;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Integer getTodaysCompletedLectureCount() {
		return todaysCompletedLectureCount;
	}

	public void setTodaysCompletedLectureCount(Integer todaysCompletedLectureCount) {
		this.todaysCompletedLectureCount = todaysCompletedLectureCount;
	}

	public Integer getCurrentLectureNumber() {
		return currentLectureNumber;
	}

	public void setCurrentLectureNumber(Integer currentLectureNumber) {
		this.currentLectureNumber = currentLectureNumber;
	}
	
	
	
}
