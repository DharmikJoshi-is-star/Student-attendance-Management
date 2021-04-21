package com.studentattendancesystem.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentattendancesystem.enums.DaysMappedWithEnumDAY;
import com.studentattendancesystem.model.Admin;
import com.studentattendancesystem.model.Department;
import com.studentattendancesystem.model.Faculty;
import com.studentattendancesystem.model.Lecture;
import com.studentattendancesystem.model.Student;
import com.studentattendancesystem.model.Subject;
import com.studentattendancesystem.model.fronend.DepartmentFrontEndModel;
import com.studentattendancesystem.model.fronend.FacultyDepartmentDetails;
import com.studentattendancesystem.model.fronend.LectureTimeTable;
import com.studentattendancesystem.model.fronend.StudentMonthlyAttendaceReport;
import com.studentattendancesystem.model.fronend.SubjectDepartmentDetails;
import com.studentattendancesystem.repository.DepartmentRespository;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRespository departmentRespository;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private SubjectService subjectService;

	@Autowired
	private LectureService lectureService;
	
	@Autowired
	private RFIDTokenService rfidTokenService;
	
	@Autowired
	private ClassService classService;

	public Department saveDepartment(Department department) {
		
		department = departmentRespository.save(department);
	
		return department;
	}

	
	public Department saveDepartment(Department department, Long adminId) {
		
		Admin admin = adminService.getAdminById(adminId);
		if(admin==null)
			return null;
		
		department = departmentRespository.save(department);
		
		admin.setDepartment(department);
		
		adminService.saveAdmin(admin);
		
		return department;
	}

	public List<Department> getAllDepartments() {
		return departmentRespository.findAll();
	}
	
	public Boolean deleteDepartmentWithId(Long dId) {
		if(departmentRespository.existsById(dId)) {
			departmentRespository.deleteById(dId);
			return true;
		}else 
			return false;
			
	}

	public List<Student> getAllStudentOfDepartment(Long dId) {
		if(departmentRespository.existsById(dId)) {
			return departmentRespository.getOne(dId).getStudents();
		}else 
			return null;
	}

	public List<DepartmentFrontEndModel> getAllDepartmentsFronEnd() {
		List<Department> departments =  this.getAllDepartments();
		List<DepartmentFrontEndModel> departmentsForFronEnd = new ArrayList<>();
		for(Department dept : departments) {
			departmentsForFronEnd.add(
						this.mappedToDepartmentFrontEndModel(dept)
					);	
		}
		return departmentsForFronEnd;
	}
	
	private DepartmentFrontEndModel mappedToDepartmentFrontEndModel(Department dept) {
		return new DepartmentFrontEndModel(dept.getId(), dept.getName(), dept.getFaculties().size(), dept.getSubjects().size(), dept.getStudents().size());
	}

	public List<Subject> getAllDepartmentsSubjects(Long dId) {
		
		List<Subject> subjects = departmentRespository.getOne(dId).getSubjects();
		for(Subject sub: subjects ) {
			System.out.println(sub);
			sub.setDepartment(null);
		}
		return subjects;
	}
	
	public Department getDepartmentWithId(Long dId) {
		return departmentRespository.getOne(dId);
	}

	public DepartmentFrontEndModel getDepartmentDetails(Long dId) {
		
		Department department = this.getDepartmentWithId(dId);
		
		DepartmentFrontEndModel dfem = new DepartmentFrontEndModel();
		
		dfem.setId(department.getId());
		dfem.setName( department.getName() );
		dfem.setStudentCount( department.getStudents().size() );
		dfem.setFacultyCount( department.getFaculties().size() );
		dfem.setSubjectCount( department.getSubjects().size() );
		
		dfem.setNfcCount( rfidTokenService.getAllTokens(dId).size());
		
		dfem.setClassCount( classService.getAllClasses(dId).size());
		
		if(department.getTodaysCompletedLectureCount()!=null && department.getTodaysCompletedLectureCount()==0) {
			dfem.setCurrentLecture( null );
		}else {
			
			Lecture lecture = null;
			for(Lecture lec: department.getLectures()) {
				if(lec.getLectureNumber()!=null) {
					if(Integer.compare(lec.getLectureNumber() , department.getCurrentLectureNumber())==0) { 
						lecture = lec;
						break;
					}
				}
				
			}
			
//			Lecture lecture = department.getLectures().get( 
//					department.getTodaysCompletedLectureCount() - 1
//					);
			
			if(lecture!=null) {
				dfem.setCurrentLecture( 
						lecture.getSubject().getName() 
						);
				
				dfem.setLectureInClass( 
						lecture.getClassObj().getClassNo()
						);
			}
			
			int count = 0;
			if(department.getStudents()!=null) {
				for(Student student: department.getStudents()) {
					if(student.getCurrentLog()!=null)
					if(student.getCurrentLog().getClassObj().getClassNo().equals(lecture.getClassObj().getClassNo())) {
						count++;
					}
				}
				dfem.setStudentsPresentInLecture(count);
			}
			
		}		
		return dfem;
	}

	public List<SubjectDepartmentDetails> getSubjectDepartmentDetails(Long dId) {
		
		Department department = this.getDepartmentWithId(dId);
		List<Subject> subjects = department.getSubjects();
		List<Lecture> lectures = department.getLectures();
		List<SubjectDepartmentDetails> sdd = new ArrayList<SubjectDepartmentDetails>();
		for(Subject subject: subjects) {
			int count = 0;
			for(Lecture lecture : lectures) {
				if(lecture.getSubject().getId().toString().equals( subject.getId().toString() )) {
					count++;
				}
			}
			SubjectDepartmentDetails s =new SubjectDepartmentDetails();
			s.setId(subject.getId());
			s.setLectureCount(count);
			s.setName(subject.getName());
			sdd.add(s);
		}
		
		return sdd;
	}
	
	public List<FacultyDepartmentDetails> getFacultiesDepartmentDetails(Long dId) {
		
		Department department = this.getDepartmentWithId(dId);
		List<Faculty> faculties = department.getFaculties();
		List<FacultyDepartmentDetails> fdd = new ArrayList<FacultyDepartmentDetails>();
		for(Faculty faculty: faculties) {
			FacultyDepartmentDetails f = new FacultyDepartmentDetails();
			f.setId(faculty.getId());
			f.setName(faculty.getName());
			f.setRfid(faculty.getRfidToken().getToken_id());
			if(faculty.getLogin()!=null)
				faculty.getLogin().setFaculty(null);
			f.setLogin( faculty.getLogin() );
			fdd.add(f);
		}
		return fdd;
	}

	public List<List<LectureTimeTable>> getLecturesTimeTable(Long dId) {
		
		Department department = this.getDepartmentWithId(dId);
		
		List<Lecture> lectures= department.getLectures();
		
		List<List<LectureTimeTable>> lecturesTimeTable = new ArrayList<List<LectureTimeTable>>();
		//i=0 start form monday i=6 end at sunday  
		for(int i = 0; i<=6; i++) {
			List<LectureTimeTable> dayLecs = new ArrayList<LectureTimeTable>();
			for(Lecture lec: lectures) {
				
				if( DaysMappedWithEnumDAY.getDayWithIntValue(lec.getDay())==i) {
					dayLecs.add( this.mapLectureToLectureTimeTable(lec) );
				}
				dayLecs.sort(  Comparator.comparing(LectureTimeTable::getStartTime) );
			}
			lecturesTimeTable.add(dayLecs);
		}
		
		
		
		return lecturesTimeTable;
	}

	private LectureTimeTable mapLectureToLectureTimeTable(Lecture lecture) {
		
		LectureTimeTable lectureTimeTable = new LectureTimeTable();
		
		lectureTimeTable.setId( lecture.getId() );
		lectureTimeTable.setSubject( lecture.getSubject().getName() );
		lectureTimeTable.setFaculty( lecture.getFaulty().getName() );
		lectureTimeTable.setLectureNumber( lecture.getLectureNumber() );
		if(lecture.getStartTime()==null) {
			lectureTimeTable.setStartTime( "9:00" );
			lectureTimeTable.setEndTime( "10:00");
		}else {
			lectureTimeTable.setStartTime( lecture.getStartTime().toString() );
			lectureTimeTable.setEndTime( lecture.getEndTime().toString() );
		}
		
		return lectureTimeTable;
		
	}

	
	
//	public void connectDepartmentWithSubjects() {
//		List<Subject> subjects = subjectService.getAllSubjects();
//		List<Department> departments = this.getAllDepartments();
//		List<Subject> newSubjects = new ArrayList<>();
//		for(Subject subject: subjects) {
//			subject.setDepartment(departments.get(0));
//			newSubjects.add(subjectService.saveSubject(subject));
//		}
//		
//		departments.get(0).setSubjects(newSubjects);
//		this.saveDepartment(departments.get(0));
//	}
//	
}
