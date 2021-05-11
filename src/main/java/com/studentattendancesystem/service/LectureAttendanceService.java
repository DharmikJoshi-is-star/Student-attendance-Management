package com.studentattendancesystem.service;

import java.sql.Date;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.studentattendancesystem.enums.DaysMappedWithEnumDAY;
import com.studentattendancesystem.enums.MonthMappedWithIntegers;
import com.studentattendancesystem.enums.STATUS;
import com.studentattendancesystem.model.Class;
import com.studentattendancesystem.model.Department;
import com.studentattendancesystem.model.Faculty;
import com.studentattendancesystem.model.Lecture;
import com.studentattendancesystem.model.LectureAttendance;
import com.studentattendancesystem.model.Student;
import com.studentattendancesystem.model.Subject;
import com.studentattendancesystem.model.fronend.MarkAttendanceFronEndStudent;
import com.studentattendancesystem.model.fronend.MarkAttendanceFrontEndFaculty;
import com.studentattendancesystem.model.fronend.StudentDetailedAttendanceOfSubject;
import com.studentattendancesystem.model.fronend.StudentFrontEndModel;
import com.studentattendancesystem.model.fronend.StudentMonthlyAttendaceReport;
import com.studentattendancesystem.model.fronend.SubjectAttendanceOnDate;
import com.studentattendancesystem.model.fronend.SubjectDepartmentDetails;
import com.studentattendancesystem.repository.LectureAttendanceRepository;
import com.studentattendancesystem.service.mail.MailService;

@Service
public class LectureAttendanceService {

	@Autowired
	private ClassService classService;

	@Autowired
	private ClassLogService classLogService;

	@Autowired
	private LectureAttendanceRepository attendanceRepository;

	@Autowired
	private FacultyService facultyService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private LectureService lectureService;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private MailService mailService;
	
	public MarkAttendanceFrontEndFaculty getMarkAttendanceFronEndFaculty(Long fId) {
		
		MarkAttendanceFrontEndFaculty mafef = new MarkAttendanceFrontEndFaculty();
		Faculty faculty = facultyService.getFacultyWithId(fId);
		Class classObj = faculty.getCurrentLog().getClassObj();
		Date date = faculty.getCurrentLog().getDate();
		Time time = faculty.getCurrentLog().getTime();
		
		String day = DaysMappedWithEnumDAY.getDayWithIntValue(
				faculty.getCurrentLog().getDate().getDay()
		);
		List<Student> presentStudents = classLogService.getAllStudentsPresentInsideOfClass(
						classObj.getId(), 
						faculty.getCurrentLog().getDate(), 
						STATUS.IN.toString()
					);
		Department department = presentStudents.get(0).getDepartment();
		List<Lecture> lectures = lectureService.getAllLecturesOfDayWithDepartmentId(department.getId(), day);
		  
		if(department.getTodaysCompletedLectureCount() == null)
			department.setTodaysCompletedLectureCount(0);
		
		Lecture lecture = lectures.get( department.getTodaysCompletedLectureCount() );
	
		mafef.setFacultyName(faculty.getName());
		mafef.setSubjectName( lecture.getSubject().getName() );
		mafef.setClassRoom( classObj.getClassNo() );
		mafef.setDate(date.toString());
		mafef.setTime(time.toString());
	
		return mafef;
	}
	
	
	//Faculty data is give to a function
	//using this data we have find lecture and present student of class
	
	//How?
	/*
	 * Faculties current log will give us the class object
	 * so our task is to mark attendance of student who are present in the class(can be found from classLogs )
	 * now we don't have the lecture information
	 * from student will get Department data
	 * from department will get all the lecture of a present Day
	 * from department todaysCompletedLectureCount will get next lecture data
	 * 
	 */
	public List<MarkAttendanceFronEndStudent> markTodaysAttendance(Long fId) {
		
		System.out.println("Mark Todays Attendance is called!!");
		
		List<MarkAttendanceFronEndStudent> listOfAllStudentsWithMarkAttendance = new ArrayList<>();
		
		Faculty faculty = facultyService.getFacultyWithId(fId);
		
		List<Lecture> departmentLectures = faculty.getDepartment().getLectures();
		
		Integer lectureNumber = faculty.getDepartment().getCurrentLectureNumber();
		
		Lecture currentLecture = null;
		
		Department department = faculty.getDepartment();
		
		if(lectureNumber==null || lectureNumber==0 || departmentLectures.size() <= lectureNumber) {
			lectureNumber = 0;
		}
		
		lectureNumber = lectureNumber + 1 ;
		
		for(Lecture lec: departmentLectures) {
			
			if(lec.getLectureNumber()==null) {
				lec.setLectureNumber(1);
			}
			
			if(Integer.compare(lectureNumber, lec.getLectureNumber())==0) {
				currentLecture = lec;
				break;
			}
		}
			
		if(currentLecture == null) {
			return null;
		}
		
		List<Student> studentsOfDepartment = faculty.getDepartment().getStudents();
		
		for(Student student: studentsOfDepartment) {
			
			if(student.getCurrentLog()==null)
				continue;
			
			String facultyPresentInClass = faculty.getCurrentLog().getClassObj().getClassNo();
			String studentPresentInClass = student.getCurrentLog().getClassObj().getClassNo();
			
			if(studentPresentInClass.equals(facultyPresentInClass)) {
				
				LectureAttendance lectureAttendance;
				
				if(student.getCurrentLog().getStatus().equals( STATUS.IN.toString() ))
					lectureAttendance = this.createLectureAttendance(currentLecture, student, null, true);
				else
					lectureAttendance = this.createLectureAttendance(currentLecture, student, false, false);
				
				lectureAttendance = this.saveAttendance(lectureAttendance);
				listOfAllStudentsWithMarkAttendance.add(
							this.mapLectureAttendanceToMarkAttendanceFrontEndStudent(lectureAttendance)
						);
				
			}
			
		}
		
		
		department.setCurrentLectureNumber(lectureNumber);
		
		departmentService.saveDepartment(department);
		
		
		/*  Previous Method of taking attendance 
		System.out.println(faculty.getName());
		Class classObj = faculty.getCurrentLog().getClassObj();
		String day = DaysMappedWithEnumDAY.getDayWithIntValue(
										faculty.getCurrentLog().getDate().getDay()
								);
		
		
		List<Student> presentStudents = classLogService.getAllStudentsPresentInsideOfClass(
												classObj.getId(), 
												faculty.getCurrentLog().getDate(), 
												STATUS.IN.toString()
											);
		
		//classLogService.getSingleStudentDataPresentInClass(classObj.getId(), faculty.getCurrentLog().getDate(), STATUS.IN.toString());
		
		
//		System.out.println("classObj.getId() "+classObj.getId());
//		System.out.println("faculty.getCurrentLog().getDate() "+faculty.getCurrentLog().getDate());
//		System.out.println(student);
//		
		Department department = presentStudents.get(0).getDepartment();
//		System.out.println("department "+department);
//		System.out.println(1);
		List<Student> allStudents = departmentService.getAllStudentOfDepartment(department.getId());
		List<Lecture> lectures = lectureService.getAllLecturesOfDayWithDepartmentId(department.getId(), day);
//		System.out.println("Size of lec "+lectures.size());
//		System.out.println(2);
		
		Lecture lecture = lectures.get( department.getTodaysCompletedLectureCount() );
//		System.out.println(3);
		
		for(Student student: allStudents) {
			LectureAttendance lectureAttendance;
			if(presentStudents.contains(student)) {
				lectureAttendance = this.createLectureAttendance(lecture, student, null, true);
			}else {
				lectureAttendance = this.createLectureAttendance(lecture, student, false, false);
			}
			lectureAttendance = this.saveAttendance(lectureAttendance);
			listOfAllStudentsWithMarkAttendance.add(
						this.mapLectureAttendanceToMarkAttendanceFrontEndStudent(lectureAttendance)
					);
		}
		
		department.setTodaysCompletedLectureCount( department.getTodaysCompletedLectureCount() + 1 );

		if(department.getTodaysCompletedLectureCount() == lectures.size()) {
			department.setTodaysCompletedLectureCount(0);	
		}
		
		
		departmentService.saveDepartment(department);
		*/
		//department.getTodaysCompletedLectureCount() is greater than lectures.size() then mark all buffered attendance to final attendance
		
		//return all the present students i.e. presentStudents
		return listOfAllStudentsWithMarkAttendance;
	}
	
	
	
	private MarkAttendanceFronEndStudent mapLectureAttendanceToMarkAttendanceFrontEndStudent(LectureAttendance lectAttendance) {
		MarkAttendanceFronEndStudent mafs = new MarkAttendanceFronEndStudent();
		
		mafs.setLectureAttendanceId( lectAttendance.getId() );
		mafs.setRollNo( lectAttendance.getStudent().getRoll_no() );
		mafs.setStudentName( lectAttendance.getStudent().getName() );
		mafs.setDepartmentName( lectAttendance.getStudent().getDepartment().getName() );
		mafs.setGender( lectAttendance.getStudent().getGender() );
		mafs.setIsPresent( lectAttendance.getIsBuffered() );
		
		return mafs;
	}
	
	public void markStudentAttendanceAsPresent(Student student, Class classObj, Date date) {
		
		List<LectureAttendance> lectureAttendances = 
				attendanceRepository.getStudentBufferedAttendaceFromClassOnDate(student.getId(), classObj.getId(), date);
		
		for(LectureAttendance attendance : lectureAttendances) {
			attendance.setIsBuffered(false);
			attendance.setIsPresent(true);
			this.saveAttendance(attendance);
		}

	}
	
	private LectureAttendance saveAttendance(LectureAttendance lectureAttendance) {
		return attendanceRepository.save(lectureAttendance);
	}
	
	private LectureAttendance createLectureAttendance(Lecture lecture, Student student, Boolean isPresent, Boolean isBuffered) {
		
		Long datetime = System.currentTimeMillis();
		
		Date date = new Date(datetime);
		Time time = new Time(datetime);
		
		LectureAttendance lectureAttendance = new LectureAttendance(lecture, student, date, time, isPresent, isBuffered);
		return lectureAttendance;
	}


	public void deleteAllTheBufferedAttendance(Long dId) {
		
		List<LectureAttendance> bufferList = attendanceRepository.getAllTheBufferedAttendance(dId);
	
		for(LectureAttendance la: bufferList) {
			attendanceRepository.delete(la);
		}
		
	}

	public void deleteBufferAttendance(Long attendanceId) {
		
		attendanceRepository.deleteById(attendanceId);
		
	}

	public Map<Subject, Integer> allSubjectsTotalPresentLectureCount(Long sId) {
		
		Map<Subject, Integer> subjectWithPresenceOfStudent = new HashMap<>();
		
		Student student =  studentService.getStudentWithId(sId);
		
		List<Subject> subjects = student.getDepartment().getSubjects();
		
		for(Subject subject : subjects) {
			
			List<LectureAttendance> attendance =  attendanceRepository.getListOfAttendanceOfSubjectWhereStudentWasPresent(sId, subject.getId());
			subjectWithPresenceOfStudent.put(subject, attendance.size());
			
		}
		
		return subjectWithPresenceOfStudent;
	}
	
	public Map<Subject, Integer> allSubjectLectureCount(Long sId) {
		
		Map<Subject, Integer> subjectLecturesCount = new HashMap<>();
		
		Student student =  studentService.getStudentWithId(sId);
		
		List<Subject> subjects = student.getDepartment().getSubjects();
		
		for(Subject subject : subjects) {
			   
			List<LectureAttendance> attendance = attendanceRepository.getListOfLectureCountOfSubject(subject.getId(), student.getDepartment().getId());
												
			System.out.println("Before Filter: " + attendance.size());
			attendance = this.filteredList( attendance );
			System.out.println("After Filter: " + attendance.size());
			subjectLecturesCount.put(subject, attendance.size());
			
		}
		
		return subjectLecturesCount;
	}


	public Map<Subject, Float> averageAttendanceOfStudentInAllSubjects(Long sId) {
		
		Map<Subject, Integer> subjectWithPresenceOfStudent  = this.allSubjectsTotalPresentLectureCount(sId);
		Map<Subject, Integer> subjectLectureCount = this.allSubjectLectureCount(sId);
		Map<Subject, Float> averageAttendanceOfStudentInAllSubjects = new HashMap<>();
		for(Subject subject : subjectWithPresenceOfStudent.keySet()) {
			
			if(subjectLectureCount.get(subject)==0) {
				averageAttendanceOfStudentInAllSubjects.put(subject, 0.0f);
			}else {
				averageAttendanceOfStudentInAllSubjects.put(subject, 
						
						((float)subjectWithPresenceOfStudent.get(subject) /(float) subjectLectureCount.get(subject))*100
						
						);
			}
			
			
		}
		return averageAttendanceOfStudentInAllSubjects;
	}

	public List<SubjectDepartmentDetails> averageAttendanceInAllSubjects(Long sId){

		Map<Subject, Integer> subjectWithPresenceOfStudent  = this.allSubjectsTotalPresentLectureCount(sId);
		Map<Subject, Integer> subjectLectureCount = this.allSubjectLectureCount(sId);
		List<SubjectDepartmentDetails> allSubjectAttendance = new ArrayList<>();
		
		for(Subject subject: subjectWithPresenceOfStudent.keySet()) {
			
			if(subjectLectureCount.containsKey(subject)) {
				
				SubjectDepartmentDetails subjectDetails = new SubjectDepartmentDetails();
				
				subjectDetails.setId( subject.getId() );
				subjectDetails.setName( subject.getName() );
				subjectDetails.setLectureCount( subjectLectureCount.get(subject) );
				subjectDetails.setLecturesAttended( subjectWithPresenceOfStudent.get(subject) );
				
				subjectDetails.setAverageAttended( 
							this.findAverageAttedance(subjectDetails.getLecturesAttended(), subjectDetails.getLectureCount())
				);

				allSubjectAttendance.add(subjectDetails);
			}
			
		}
		
		return allSubjectAttendance;
	}
	
	
	private Float findAverageAttedance(Integer lectureAttended, Integer lectureTaken) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		if(lectureTaken==0)
			return 0.0f;
		return (lectureAttended/(float)lectureTaken)*100;
	}
	
	//tested
	public Float averageAttendanceOfStudent(Long sId) {
		
		Long dId = studentService.getStudentWithId(sId).getDepartment().getId();
		
		List<LectureAttendance> totalLecturesConducted = this.filteredList(  
																	attendanceRepository.getCountOfLecturesWithDepartmentId(dId)
															);
		
		List<LectureAttendance> totalLecturesAttendedByStudent = attendanceRepository.getCountLecturesAttendedByStudentOfDepartment(sId, dId);
		
		
		for(LectureAttendance l: totalLecturesConducted)
			System.out.println(l.getId());
		System.out.println("\n\n");
		for(LectureAttendance l: totalLecturesAttendedByStudent)
			System.out.println(l.getId());
		
		return this.findAverageAttedance( totalLecturesAttendedByStudent.size() , totalLecturesConducted.size());
		
//		if(totalLecturesConducted.size()==0)
//			return 0.0f;
//		
//		return ((float)totalLecturesAttendedByStudent.size()/ (float)totalLecturesConducted.size() )*100;
	}

	//tested
	private List<LectureAttendance> filteredList(List<LectureAttendance> LecturesConducted){
		List<LectureAttendance> filteredList = new ArrayList<>();
		for(LectureAttendance lec: LecturesConducted) {
			boolean contains = false;
			for(LectureAttendance filLec : filteredList) {
				if(lec.getDate().getDate() == filLec.getDate().getDate() && lec.getTime().getHours() == filLec.getTime().getHours()) {
					contains = true; break;
				}
			}
			if(!contains)
				filteredList.add(lec);
		}
		return filteredList;
	}
	
	private List<LectureAttendance> totalLecturesConductedInMonth(Long dId,Integer month){
		return this.filteredList(  
				this.filterOnMonth( 
						attendanceRepository.getTotalLecturesConductedInDepartment(dId), 
						month
					)
				);
	}
	
	private Map<String,List<LectureAttendance>> mappedMonthAttendanceWithYear(List<LectureAttendance> monthlyAttendance){
		
		Map<String,List<LectureAttendance>> mappedByYear = new HashMap<>();
		
		for(LectureAttendance lecAtt: monthlyAttendance) {
			Integer year = lecAtt.getDate().getYear()+1900;
			if(!mappedByYear.containsKey(year.toString())) {
				mappedByYear.put(year.toString(), new ArrayList<LectureAttendance>());
			}
			mappedByYear.get(year.toString()).add(lecAtt);
		}
		return mappedByYear;
	}
	
	private List<LectureAttendance> totalLecturesAttendedByStudentInMonth(Long sId, Long dId, Integer month){
		return this.filterOnMonth( 
				this.onlyTakeIsPresent( attendanceRepository.getTotalLecturesAttendedByStudent(sId, dId)), 
				month
			);
	}
	
	private List<LectureAttendance> onlyTakeIsPresent(List<LectureAttendance> attendances){
		List<LectureAttendance> filteredList = new ArrayList<LectureAttendance>();
		for(LectureAttendance la: attendances) {
			if(la.getIsPresent()!=null)
				if(la.getIsPresent())
					filteredList.add(la);
		}
		return filteredList;  
	}
	//tested
	public Float averageAttendanceOfStudentInMonth(Long sId, Integer month) {
		
		Student student = studentService.getStudentWithId(sId);
		Long dId = student.getDepartment().getId();
		List<LectureAttendance> totalLecturesConductedInMonth = this.totalLecturesConductedInMonth(dId, month);
			
		List<LectureAttendance> totalLecturesAttendedByStudentInMonth = this.totalLecturesAttendedByStudentInMonth(student.getId(), dId, month);
		
		for(LectureAttendance l: totalLecturesConductedInMonth)
			System.out.println(l.getId());
		System.out.println("\n\n");
		for(LectureAttendance l: totalLecturesAttendedByStudentInMonth)
			System.out.println(l.getId());
		
		if(totalLecturesConductedInMonth.size()==0)
			return 0.0f;
		
		
		return (totalLecturesAttendedByStudentInMonth.size()/(float)totalLecturesConductedInMonth.size())*100;
	}
		
	
	//tested
	private List<LectureAttendance> filterOnMonth(List<LectureAttendance> totalLecturesConductedInDepartment, int month){
		List<LectureAttendance> totalLecturesConductedInMonth = new ArrayList<>();
		for(LectureAttendance lec :totalLecturesConductedInDepartment) {
			if(lec.getDate().getMonth() == month) {
				totalLecturesConductedInMonth.add(lec);
			}
		}
		return totalLecturesConductedInMonth;
	}


	public Integer getTotalLecturesAttendedByStudent(Long sId, Long dId) {
		
		List<LectureAttendance> totalLecturesAttendedByStudent = attendanceRepository.getCountLecturesAttendedByStudentOfDepartment(sId, dId);
		
		
		return totalLecturesAttendedByStudent.size();
	}


	public List<StudentMonthlyAttendaceReport> getStudentMonthlyReport(Long sId) {
		
		List<StudentMonthlyAttendaceReport> studentMonthlyAttendaceReports = new ArrayList<>();
		Student student = studentService.getStudentWithId(sId);
		Long dId = student.getDepartment().getId();
		
		for(int month=0; month<12; month++) {
			
			StudentMonthlyAttendaceReport smar = new StudentMonthlyAttendaceReport();
			
			Map<String,List<LectureAttendance>>  totalLecturesConductedInMonth = this.mappedMonthAttendanceWithYear(this.totalLecturesConductedInMonth(dId, month));
			Map<String,List<LectureAttendance>> totalLecturesAttendedByStudentInMonth = this.mappedMonthAttendanceWithYear(this.totalLecturesAttendedByStudentInMonth(student.getId(), dId, month));
			
			
			for(String year : totalLecturesConductedInMonth.keySet()) {
				if(totalLecturesAttendedByStudentInMonth.containsKey(year)) {
					
					int conducted = totalLecturesConductedInMonth.get(year).size();
					int attended = totalLecturesAttendedByStudentInMonth.get(year).size();
					
					if(conducted==0)
						continue;
					
					smar.setMonth( MonthMappedWithIntegers.getmonthWithIntValue(month) );
					smar.setYear(year);
					smar.setAverageAttendance(
							this.findAverageAttedance(attended, conducted)
							);//(attended/(float)conducted)*100
					smar.setTotalLecturesAttended(attended);
					smar.setTotalLecturesTaken(conducted);
					studentMonthlyAttendaceReports.add(smar);
				}
			}
		
		}
		
		studentMonthlyAttendaceReports.sort( Comparator.comparing(StudentMonthlyAttendaceReport::getYear));
		
		return studentMonthlyAttendaceReports;
	}


	public List<StudentDetailedAttendanceOfSubject> getStudentDetailedAttendanceOfSubject(Long sId, Long subId) {
		
		List<LectureAttendance> detailedSubjectAttendanceList = this.getStudentDetailedAttendanceOfSubjectFromDB(sId, subId);
		
		List<StudentDetailedAttendanceOfSubject> studentDetailedAttendanceOfSubject = new ArrayList<>();
		
		for(LectureAttendance detailedSubjectAttendance : detailedSubjectAttendanceList) {
			studentDetailedAttendanceOfSubject.add(
					mapLectureAttendanceToStudentDetailedAttendanceOfSubject(detailedSubjectAttendance)
					);
		}
		
		return studentDetailedAttendanceOfSubject;
	}
	
	

	private List<LectureAttendance> getStudentDetailedAttendanceOfSubjectFromDB(Long sId, Long subId) {
		return attendanceRepository.getStudentDetailedAttendanceOfSubject(sId, subId);
	}


	private StudentDetailedAttendanceOfSubject mapLectureAttendanceToStudentDetailedAttendanceOfSubject(LectureAttendance lectureAttendance) {
		StudentDetailedAttendanceOfSubject sdaos = new StudentDetailedAttendanceOfSubject();
		sdaos.setId(lectureAttendance.getId());
		sdaos.setClassNo(lectureAttendance.getLecture().getClassObj().getClassNo());
		sdaos.setIsPresent(lectureAttendance.getIsPresent());
		sdaos.setFacultyName(lectureAttendance.getLecture().getFaulty().getName());
		sdaos.setDate(lectureAttendance.getDate().toString());
		sdaos.setTime(lectureAttendance.getTime().toString());
		
		return sdaos;
	}


	public List<SubjectAttendanceOnDate> getAttendnaceOfSubjectOnDate(Long subId, Date date) {
		
		List<LectureAttendance> attendances =  attendanceRepository.getAttendnaceOfSubjectOnDate(subId, date);
		
		Map<Long, List<LectureAttendance>> forMoreThanOneLectureOfSubjectOnDay = 
										this.MapForMoreThanLectureForSubject(attendances);
		
		 List<SubjectAttendanceOnDate> subjectAttendanceOnDate = new ArrayList<>();
		
		for(Long sId : forMoreThanOneLectureOfSubjectOnDay.keySet()) {
			subjectAttendanceOnDate.add(
					mapLectureAttendanceToSubjectAttendanceOnDate(
							forMoreThanOneLectureOfSubjectOnDay.get(sId)
							)
					);
		}
		
		subjectAttendanceOnDate.sort(
				Comparator.comparing(SubjectAttendanceOnDate::getRollNno));
		
		return subjectAttendanceOnDate;
	}
	
	
	private Map<Long, List<LectureAttendance>> MapForMoreThanLectureForSubject(List<LectureAttendance> attendances) {
		
		Map<Long, List<LectureAttendance>> map = new HashMap<>();
		
		for(LectureAttendance attend : attendances) {
			
			Student student = attend.getStudent();
			
			if(!map.containsKey(student.getId())) 
				map.put(student.getId(), new ArrayList<LectureAttendance>());
			
			map.get(student.getId()).add(attend);
		
		}
		
		
		return map;
	}


	private SubjectAttendanceOnDate mapLectureAttendanceToSubjectAttendanceOnDate(
										List<LectureAttendance> attendance) {
		
		SubjectAttendanceOnDate saod = new SubjectAttendanceOnDate();
		saod.setRollNno( attendance.get(0).getStudent().getRoll_no() );
		saod.setStudentName( attendance.get(0).getStudent().getName() );
		saod.setSubjectName(  attendance.get(0).getLecture().getSubject().getName() );
		
		Boolean[] lecs = new Boolean[attendance.size()];
		int idx = 0;
		for(LectureAttendance attend: attendance) {
			lecs[idx++] = attend.getIsPresent();
		}
		
		saod.setIsPresentLectures(lecs);
		return saod;
	}


	public void sendEmail(String receiver, String subject, String message) {
		mailService.sendMail(receiver, subject, message);
	}

	public Boolean sendAlertMailToGuardian(Float minAttendance, Long dId) {
		Department department = departmentService.getDepartmentWithId(dId);
		List<Student> students = department.getStudents();
		
		for(Student student : students) {
			if(student.getStudentInfo()!=null) {
				
				//DecimalFormat df = new DecimalFormat();
//				df.setMaximumFractionDigits(2);
//				
//				Float averageAttendance =  Float.parseFloat(df.format(
//							averageAttendanceOfStudent(student.getId())
//						));
				
				Float averageAttendance =  averageAttendanceOfStudent(student.getId());
				
				if(averageAttendance < minAttendance) {
					String message = "Dear Guardian, \n "
							+ "	Your child name "+student.getName()+" has an average Attendance of "+averageAttendance+"% \n "
							+ "This attendance is lower than we expected. Your child should have Minimum "+minAttendance+"% Attendance.\n"
							+ "For we are counting "+student.getName()+" as defaulter.\n\n\n"
							+ "This Value Has been Calculated By our Attendance Management System..!!";
					
					String receiver = student.getStudentInfo().getMailAddress();
					String subject = "Attendance Alert!! Defaulter Student";
					try {
						this.sendEmail(receiver, subject, message);
					}catch(Exception e) {
						continue;
					}
				}
				
			}
		}
		return true;
	}
	
	public Boolean sendAverageAttendanceGuardian(Long dId) {
		
		
			Department department = departmentService.getDepartmentWithId(dId);
			List<Student> students = department.getStudents();
			
			for(Student student : students) {
				if(student.getStudentInfo()!=null) {
//					DecimalFormat df = new DecimalFormat();
//					df.setMaximumFractionDigits(2);
//					
//					Float averageAttendance = Float.parseFloat(df.format(
//								averageAttendanceOfStudent(student.getId())
//							));
					
					Float averageAttendance = averageAttendanceOfStudent(student.getId());
							
					
					String message = "Dear Guardian, \n Your child name "+student.getName()+" has an average Attendance of "+averageAttendance+"% \n This Value Has been Calculated "
							+ "By our Attendance Management System..!!";
					
					String receiver = student.getStudentInfo().getMailAddress();
					String subject = "Average Attendance of Student";
					try {
						this.sendEmail(receiver, subject, message);
					}catch(Exception e) {
						continue;
					}
					
				}
			}
			return true;
	}
	
	public Boolean sendMonthlyAverageAttendanceGuardian(Long dId) {
		
		
		Department department = departmentService.getDepartmentWithId(dId);
		List<Student> students = department.getStudents();
		
		for(Student student : students) {
			if(student.getStudentInfo()!=null) {
				//DecimalFormat df = new DecimalFormat();
				//df.setMaximumFractionDigits(2);
				
				List<StudentMonthlyAttendaceReport> monthlyAttendaceReports = getStudentMonthlyReport(student.getId());
				
				String report = "";
				
				for(StudentMonthlyAttendaceReport attendaceReport : monthlyAttendaceReports) {
					report += ("Month :"+attendaceReport.getMonth() + "\t Average Attendance : "+attendaceReport.getAverageAttendance()+"% \n");
				}
				
				String message = "Dear Guardian, \n Your child name "+student.getName()+ "All Months Attendance Report is Here!\n"
						+report
						+"\n This Value Has been Calculated By our Attendance Management System..!!";
				
				String receiver = student.getStudentInfo().getMailAddress();
				String subject = "Average Attendance of Student";
				try {
					this.sendEmail(receiver, subject, message);
				}catch(Exception e) {
					continue;
				}
				
			}
		}
		return true;
}

	
	public Boolean sendMonthlyAverageAttendanceGuardian(Long dId, String month, String year) {
		
		
		Department department = departmentService.getDepartmentWithId(dId);
		List<Student> students = department.getStudents();
		
		for(Student student : students) {
			if(student.getStudentInfo()!=null) {
//				DecimalFormat df = new DecimalFormat();
//				df.setMaximumFractionDigits(2);
				
				List<StudentMonthlyAttendaceReport> monthlyAttendaceReports = getStudentMonthlyReport(student.getId());
				
				String report = "";
				String message = "";
				for(StudentMonthlyAttendaceReport attendaceReport : monthlyAttendaceReports) {
					if(attendaceReport.getMonth().equals(month) && attendaceReport.getYear().equals(year)) {
						
						message = "Dear Guardian, \n "
								+ "	Your child name "+student.getName()+" has an average Attendance of "+attendaceReport.getAverageAttendance()+"% in month of "+month+"-"+year+"\n "
								
								+ "This Value Has been Calculated By our Attendance Management System..!!";
						break;
					
					}
				}
				
				
				String receiver = student.getStudentInfo().getMailAddress();
				String subject = "Average Attendance of Student";
				try {
					this.sendEmail(receiver, subject, message);
				}catch(Exception e) {
					continue;
				}
				
			}
		}
		return true;
	}

	public Boolean sendMonthlyAlertAverageAttendanceGuardian(Long dId, String month, String year, float averageAttendance) {
		
		
		Department department = departmentService.getDepartmentWithId(dId);
		List<Student> students = department.getStudents();
		
		for(Student student : students) {
			if(student.getStudentInfo()!=null) {
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				
				List<StudentMonthlyAttendaceReport> monthlyAttendaceReports = getStudentMonthlyReport(student.getId());
				
				String report = "";
				String message ="";
				
				for(StudentMonthlyAttendaceReport attendaceReport : monthlyAttendaceReports) {
					if(attendaceReport.getAverageAttendance() < averageAttendance &&   attendaceReport.getMonth().equals(month) && attendaceReport.getYear().equals(year)) {
						
						message = "Dear Guardian, \n "
								+ "	Your child name "+student.getName()+" has an average Attendance of "+attendaceReport.getAverageAttendance()+"% in month of "+month+"-"+year+"\n "
								+ "This attendance is lower than we expected. Your child should have Minimum "+df.format(averageAttendance)+"% Attendance.\n"
								+ "For we are counting "+student.getName()+" as defaulter.\n\n\n"
								+ "This Value Has been Calculated By our Attendance Management System..!!";
						break;
					}

				}
				
				String receiver = student.getStudentInfo().getMailAddress();
				String subject = "Average Attendance of Student";
				try {
					this.sendEmail(receiver, subject, message);
				}catch(Exception e) {
					continue;
				}
				
			}
		}
		return true;
	}


	public List<StudentFrontEndModel> getAverageAttendanceOfStudents(Long dId) {
		
		List<StudentFrontEndModel> studentAttendances = studentService.getAllDepartmentStudentForFronEnd(dId);
		
		//DecimalFormat df = new DecimalFormat();
		//df.setMaximumFractionDigits(2);
		
		for(StudentFrontEndModel student : studentAttendances) {
			
			int lecturesAttended = this.getTotalLecturesAttendedByStudent(student.getId(), dId);
			int lecturesTaken = this.filteredList( attendanceRepository.getCountOfLecturesWithDepartmentId(dId) ).size();
			
			float averageAttendance =  this.findAverageAttedance(lecturesAttended, lecturesTaken);// Float.parseFloat(df.format( (lecturesAttended/(float)lecturesTaken) *100 ));
			
			student.setTotalLecturesPresent(lecturesAttended);
			student.setTotalLecturesTaken(lecturesTaken);
			student.setAverageAttendance(averageAttendance);
			
		}
		
		return studentAttendances;
	}


	public List<StudentFrontEndModel> getAverageAttendanceOfStudentsPerMonth(Long dId, String month, String year) {
		
		List<StudentFrontEndModel> studentAttendances = studentService.getAllDepartmentStudentForFronEnd(dId);
		//DecimalFormat df = new DecimalFormat();
		//df.setMaximumFractionDigits(2);
		
		for(StudentFrontEndModel student : studentAttendances) {
			
			int lecturesAttended = 0;
			int lecturesTaken = 0;
			float averageAttendance= 0;
			 Map<String,List<LectureAttendance>> mappedByYear = this.mappedMonthAttendanceWithYear(
					 			this.totalLecturesAttendedByStudentInMonth(student.getId(), dId, MonthMappedWithIntegers.getmonthWithIntValue(month))
					 );
			
			 if(mappedByYear.containsKey(year)) {
				 lecturesAttended = mappedByYear.get(year).size();
			 }
			 
			 mappedByYear = this.mappedMonthAttendanceWithYear(
					 			this.totalLecturesConductedInMonth(dId, MonthMappedWithIntegers.getmonthWithIntValue(month))
					 		);
			 
			 if(mappedByYear.containsKey(year)) {
				 lecturesTaken = mappedByYear.get(year).size();
			 }
			 
			//int lecturesAttended =  this.totalLecturesAttendedByStudentInMonth(student.getId(), dId, MonthMappedWithIntegers.getmonthWithIntValue(month)).size();
			//int lecturesTaken = this.totalLecturesConductedInMonth(dId, MonthMappedWithIntegers.getmonthWithIntValue(month)).size();
			//if(lecturesAttended==0 || lecturesTaken==0)
			//		averageAttendance = 0;
			//else
			//	averageAttendance = Float.parseFloat(df.format( (lecturesAttended/(float)lecturesTaken) *100 ));
		
			averageAttendance = this.findAverageAttedance(lecturesAttended, lecturesTaken);
			
			student.setTotalLecturesPresent(lecturesAttended);
			student.setTotalLecturesTaken(lecturesTaken);
			student.setAverageAttendance(averageAttendance);
			
		}
		return studentAttendances;
	}


	public Map<String, Integer> getOneWeekAttedance(long dId) {
		
		Map<String, Integer> oneWeekAttedance = new HashMap<>();
		
		List<LectureAttendance> lectureAttendance= attendanceRepository.getLectureWithDepartmentId(dId);
		
		lectureAttendance.forEach( (attendance)->{
			
			if(attendance.getStudent().getDepartment().getId()==dId) {
				String date = attendance.getDate().toString();
				if(oneWeekAttedance.containsKey(date)) {
					oneWeekAttedance.put(date, oneWeekAttedance.get(date)+1);
				}else {
					oneWeekAttedance.put(date, 1);
				}
				
			}
			
		});
		System.out.println(lectureAttendance.size());
		return oneWeekAttedance;
	}


	public List<StudentDetailedAttendanceOfSubject> getSubjectDetailedAttendance(Long sId, Long subId, Integer currentPage,
			Integer pageSize) {
		
		List<StudentDetailedAttendanceOfSubject> attendanceList= this.getStudentDetailedAttendanceOfSubject(sId, subId);
		
		
        Collections.sort(attendanceList, new Comparator<StudentDetailedAttendanceOfSubject>() {
              public int compare(StudentDetailedAttendanceOfSubject o1, StudentDetailedAttendanceOfSubject o2) {
                  if (o1.getDate() == null || o2.getDate() == null)
                    return 0;     
                  return o2.getDate().compareTo(o1.getDate());
              }
            });
        
        Collections.sort(attendanceList, new Comparator<StudentDetailedAttendanceOfSubject>() {
            public int compare(StudentDetailedAttendanceOfSubject o1, StudentDetailedAttendanceOfSubject o2) {
                if (o1.getTime() == null || o2.getTime() == null)
                  return 0;     
                return o2.getTime().compareTo(o1.getTime());
            }
          });
        
        
        int start = pageSize * (currentPage-1);
        int end = start + pageSize;
        
        
        if(start>=attendanceList.size())
        	return null;
        	
        if(end < attendanceList.size())
        	return attendanceList.subList(start, end);
        else
        	return attendanceList.subList(start, attendanceList.size());
	}
	
}
