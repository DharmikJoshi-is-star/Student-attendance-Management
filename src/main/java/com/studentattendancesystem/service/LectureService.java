package com.studentattendancesystem.service;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentattendancesystem.enums.DAY;
import com.studentattendancesystem.model.Class;
import com.studentattendancesystem.model.Department;
import com.studentattendancesystem.model.Faculty;
import com.studentattendancesystem.model.Lecture;
import com.studentattendancesystem.model.Subject;
import com.studentattendancesystem.model.fronend.LectureTimeTable;
import com.studentattendancesystem.model.fronend.StudentDetailedAttendanceOfSubject;
import com.studentattendancesystem.repository.LectureRepository;

@Service
public class LectureService {

	@Autowired
	private LectureRepository lectureRepository;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private FacultyService facultyService;
	
	@Autowired
	private ClassService classService;
	
	@Autowired
	private DepartmentService departmentService;
	
	public Lecture saveLecture(Lecture lecture) {
		return lectureRepository.save(lecture);
	}

	public List<Lecture> getAllLectures(){
		return lectureRepository.findAll();
	}
//	
//	public Boolean connectLecturesWithClass() {
//		Class classObj  = classService.getAllClasses().get(0);
//		List<Lecture> lectures = this.getAllLectures();
//		List<Lecture> newLectures = new ArrayList<>();
//		for(Lecture lec: lectures) {
//			lec.setClassObj(classObj);
//			newLectures.add( lectureRepository.save(lec) );	
//		}
//		classObj.setLectures(newLectures);
//		classService.saveClass(classObj);
//		return null;
//	}
//	
	public Boolean connectLecturesWithSubjectFaculty() {
		
		List<Lecture> lectures = lectureRepository.findLectureWithDayName(DAY.FRIDAY.toString());
		List<Lecture> newLectures = new ArrayList<>();
		
		List<Faculty> faculties = facultyService.getAllFaculty();
		List<Faculty> newFaculties =  new ArrayList<>();
		
		List<Subject> subjects = subjectService.getAllSubjects();
		
		for(int i=0; i<5; i++) {
			Lecture lecture = lectures.get(i);
			Faculty faculty = faculties.get(i);
			Subject subject = subjects.get(i);
			
			lecture.setFaulty(faculty);
			lecture.setSubject(subject);
			
			newLectures.add(this.saveLecture(lecture));
			
			if(faculty.getLectures()==null) {
				faculty.setLectures(new ArrayList<Lecture>());
			}
			
			faculty.getLectures().add(lecture);
			
			newFaculties.add( facultyService.saveFaculty(faculty));
			
		}
		
		return null;
	}
	
//	public Boolean connectLecturesWithDepartmentId() {
//		Department department = departmentService.getAllDepartments().get(0);
//		List<Lecture> lectures = this.getAllLectures();
//		List<Lecture> newLectures = new ArrayList<>();
//		for(Lecture lec: lectures) {
//			lec.setDepartment(department);
//			newLectures.add( lectureRepository.save(lec) );	
//		}
//		department.setLectures(newLectures);
//		departmentService.saveDepartment(department);
//		return null;
//	}
	
//	public Boolean setUpLectures() {
////		
////		List<Subject> subjects = subjectService.getAllSubjects();
////		List<Faculty> faculties = facultyService.getAllFaculty();
////		List<Class> classes = classService.getAllClasses();
////		List<Department> departments = departmentService.getAllDepartments();
////		System.currentTimeMillis();
//		
//		
//		
//		Lecture lecture1 = new Lecture();
//		lecture1.setTime(Time.valueOf("09:00:00"));
//		
//		Lecture lecture2 = new Lecture();
//		lecture2.setTime(Time.valueOf("10:00:00"));
//		
//		Lecture lecture3 = new Lecture();
//		lecture3.setTime(Time.valueOf("11:00:00"));
//		
//		Lecture lecture4 = new Lecture();
//		
//		lecture4.setTime(Time.valueOf("12:00:00"));
//
//		Lecture lecture5 = new Lecture();
//		
//		lecture5.setTime(Time.valueOf("13:00:00"));
//		
//		
//		List<Lecture> lectures = new ArrayList<>();
//		lectures.add(lecture1);lectures.add(lecture2);lectures.add(lecture3);lectures.add(lecture4);lectures.add(lecture5);
//		
//		for(Lecture lec : lectures) {
//			lec.setDay(DAY.SATURDAY.toString());
//			this.saveLecture(lec);
//		}
//		
//		return null;
//	}
//	
	public Boolean deleteLectures(Long lId) {
		
		Lecture lec = lectureRepository.getOne(lId);
		if(lec!=null) {
			
		deleteFacultyLectures(lec);
		
		deleteLectureFromDepartment(lec);
		
		deleteLectureClass(lec);
		
		lectureRepository.delete(lec);
		
		return true;
		}else return false;
	}

	public List<Lecture> getAllLecturesOfDayWithDepartmentId(Long departmentId, String day) {
		return lectureRepository.getAllLecturesOfDayWithDepartmentId(departmentId, day);
	
	}

	public Lecture addLecture(Lecture lecture, Long classId, Long departmentId, Long facultyId, Long subjectId) {
		
		Class classObj = classService.getClassWithId(classId);
		Department department = departmentService.getDepartmentWithId(departmentId);
		Faculty faculty = facultyService.getFacultyWithId(facultyId);
		Subject subject = subjectService.getSubjectWithId(subjectId);
		
		lecture.setClassObj(classObj);
		lecture.setDepartment(department);
		lecture.setFaulty(faculty);
		lecture.setSubject(subject);
		
		lecture = this.saveLecture(lecture);
		
		if(department.getLectures()==null)
			department.setLectures( new ArrayList<Lecture>() );
		department.getLectures().add(lecture);
		
		departmentService.saveDepartment(department);
		
		if(faculty.getLectures()==null)
			faculty.setLectures(new ArrayList<Lecture>());
		faculty.getLectures().add(lecture);
		
		facultyService.saveFaculty(faculty);
		
		lecture = this.saveLecture(lecture);
				
		lecture.setClassObj(null);
		lecture.setDepartment(null);
		lecture.setFaulty(null);
		lecture.setSubject(null);
		
		return lecture;
	}

	public Lecture addLecture(LectureTimeTable lectureTimeTable, Long classId, Long departmentId, Long facultyId,
			Long subjectId) {
		
		Lecture lecture = new Lecture();
		
		Class classObj = classService.getClassWithId(classId);
		Department department = departmentService.getDepartmentWithId(departmentId);
		Faculty faculty = facultyService.getFacultyWithId(facultyId);
		Subject subject = subjectService.getSubjectWithId(subjectId);
		
		lecture.setClassObj(classObj);
		lecture.setDepartment(department);
		lecture.setFaulty(faculty);
		lecture.setSubject(subject);
		lecture.setDay( lectureTimeTable.getDay() );
		lecture.setLectureNumber( lectureTimeTable.getLectureNumber() );
		
		int startHrs = Integer.parseInt(
					lectureTimeTable.getStartTime().split(":")[0]
							);
		int startMin = Integer.parseInt(
					lectureTimeTable.getStartTime().split(":")[1]
							);
		
		int endHrs = Integer.parseInt(
				lectureTimeTable.getEndTime().split(":")[0]
						);
		int endMin = Integer.parseInt(
				lectureTimeTable.getEndTime().split(":")[1]
						);
	
		
		Time startTime = new Time(startHrs, startMin, 0);
		Time endTime = new Time(endHrs, endMin, 0);
		
		lecture.setStartTime(startTime);
		lecture.setEndTime(endTime);
		
		lecture = this.saveLecture(lecture);
		
		if(department.getLectures()==null)
			department.setLectures( new ArrayList<Lecture>() );
		department.getLectures().add(lecture);
		
		departmentService.saveDepartment(department);
		
		if(faculty.getLectures()==null)
			faculty.setLectures(new ArrayList<Lecture>());
		faculty.getLectures().add(lecture);
		
		facultyService.saveFaculty(faculty);
		
		lecture = this.saveLecture(lecture);
				
		lecture.setClassObj(null);
		lecture.setDepartment(null);
		lecture.setFaulty(null);
		lecture.setSubject(null);
		
		return lecture;
		
		
	}

	public void deleteAllLecturesOfSubject(Subject subject) {
		
		List<Lecture> lectures = lectureRepository.deleteAllLecturesOfSubject(subject.getId());
		
		for(Lecture lec:lectures) {
			System.out.println(lec.getId());
			
			deleteFacultyLectures(lec);
			
			deleteLectureFromDepartment(lec);
			
			deleteLectureClass(lec);
			
			lectureRepository.delete(lec);
		
		}
		
		
		System.out.println("Lectures Are Deleted");
	}
	
	public void deleteLectureClass(Lecture lec) {
		lec.setClassObj(null);
		
		lectureRepository.save(lec);
		
	}
	

	public void deleteLectureFromDepartment(Lecture lec) {
		Department department = lec.getDepartment();
		while(department.getLectures().contains(lec))
			department.getLectures().remove(lec);
		
		departmentService.saveDepartment(department);
	}
	
	public void deleteFacultyLectures(Lecture lec) {
		Faculty faculty = lec.getFaulty();
		
		while(faculty.getLectures().contains(lec))
			faculty.getLectures().remove(lec);
		
		facultyService.saveFaculty(faculty);
	}
}
