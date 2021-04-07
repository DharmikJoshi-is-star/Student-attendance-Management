package com.studentattendancesystem.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentattendancesystem.enums.STATUS;
import com.studentattendancesystem.model.Class;
import com.studentattendancesystem.model.ClassLog;
import com.studentattendancesystem.model.Faculty;
import com.studentattendancesystem.model.RFIDToken;
import com.studentattendancesystem.model.Student;
import com.studentattendancesystem.repository.ClassLogRepository;

@Service
public class ClassLogService {

	@Autowired
	private ClassLogRepository classLogRepository;
	
	@Autowired
	private ClassService classService;
	
	@Autowired
	private RFIDTokenService rfidTokenService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private FacultyService facultyService;
	
	@Autowired
	private LectureAttendanceService lectureAttendanceService;
	
	
	public Boolean setLogIn(String deviceCode, String tokenId) {
		
		Class classObj = classService.getClassWithdeviceCode(deviceCode);
		if(classObj == null)
			return false;
		else {
			RFIDToken rfid = rfidTokenService.getRFIDTokenWithTokenId(tokenId);
			if(rfid == null)
				return false;
			else {
				if(rfid.getStudent()!=null) {
					Student student = rfid.getStudent();
					String currentStatus = student.getCurrentStatus();
					
					
					//let us consider a case where a student didn't log out on a lecture and comes for another lecture 
					//we have to let him in and count for the log lecture 
					//how to differentiate?
					//if the latest log and current log has time difference of 30 min or grater then let student in
					ClassLog latestClassLog = this.getLatestClassLogOfStudent(student); 
					
					if(currentStatus==null || latestClassLog==null) {
						
						ClassLog log = this.generateLog(classObj, student, null, rfid, STATUS.IN.toString());
						classLogRepository.save(log);
						student.setCurrentLog(log);
						studentService.changeCurrentStatus(student, STATUS.IN.toString());
						
						System.out.print("Log is Counted");
						return true;	
						
					}else if(currentStatus.toString().equals(STATUS.OUT.toString()) || this.isTheTimeDifferenceisMoreThanThirtyMins(latestClassLog.getTime()) ) {

						ClassLog log = this.generateLog(classObj, student, null, rfid, STATUS.IN.toString());
						classLogRepository.save(log);
						student.setCurrentLog(log);
						studentService.changeCurrentStatus(student, STATUS.IN.toString());
						
						System.out.print("Log is Counted");
						
						return true;
						
					}else {
						
						System.out.print("Log is Not Counted");
						//insert into false log
						return false;
					}
					
				}else if(rfid.getFaculty()!=null) {
					Faculty faculty = rfid.getFaculty();
					String currentStatus = faculty.getCurrentStatus();
					
					
					if(currentStatus==null) {
						
						ClassLog log = this.generateLog(classObj, null, faculty, rfid, STATUS.IN.toString());
						classLogRepository.save(log);
						faculty.setCurrentLog(log);
						facultyService.changeCurrentStatus(faculty, STATUS.IN.toString());
						return true;	
						
					}else if(currentStatus.toString().equals(STATUS.OUT.toString())) {
							ClassLog log = this.generateLog(classObj, null, faculty, rfid, STATUS.IN.toString());
							classLogRepository.save(log);
							faculty.setCurrentLog(log);
							facultyService.changeCurrentStatus(faculty, STATUS.IN.toString());
							return true;	
						}
					else {
						
						//insert into false log
						return false;
					}
				}
			}
		}
		return false;
	}

	
	private ClassLog getLatestClassLogOfStudent(Student student) {
		
		return classLogRepository.getLatestClassLogOfStudent(student.getId());
		
	}


	public Boolean setLogOut(String deviceCode, String tokenId) {
		
		Class classObj = classService.getClassWithdeviceCode(deviceCode);
		if(classObj == null)
			return false;
		else {
			RFIDToken rfid = rfidTokenService.getRFIDTokenWithTokenId(tokenId);
			if(rfid == null)
				return false;
			else {
				if(rfid.getStudent()!=null) {
					Student student = rfid.getStudent();
					String currentStatus = student.getCurrentStatus();
					
					if(currentStatus.toString().equals(STATUS.IN.toString())) {

						//student out log should be from the same class where student was previously in
						ClassLog latestClassLog = this.getLatestClassLogOfStudent(student); 
						if(latestClassLog.getClassObj().getId() == classObj.getId()) {
							
							ClassLog log = this.generateLog(classObj, student, null, rfid, STATUS.OUT.toString());
							classLogRepository.save(log);
							student.setCurrentLog(log);
							studentService.changeCurrentStatus(student, STATUS.OUT.toString());
							
							//mark buffered attendance as false and present attendance as true
							//student object
							//class object
							//Date date
							lectureAttendanceService.markStudentAttendanceAsPresent(student, classObj, log.getDate());
							
							return true;
							
						}else {
							
							//insert into false logs
							return false;
						}
						
					}else {
						
						//insert into false log
						return false;
					}
					
				}else if(rfid.getFaculty()!=null) {
					Faculty faculty = rfid.getFaculty();
					String currentStatus = faculty.getCurrentStatus();
					
					if(currentStatus.toString().equals(STATUS.IN.toString())) {
						ClassLog log = this.generateLog(classObj, null, faculty, rfid, STATUS.OUT.toString());
						classLogRepository.save(log);
						faculty.setCurrentLog(log);
						facultyService.changeCurrentStatus(faculty, STATUS.OUT.toString());
						return true;	
					}else {
						
						//insert into false log
						return false;
					}
				}
			}
		}
		return false;
	}


	private ClassLog generateLog(Class classObj, Student student, Faculty faculty, RFIDToken rfid, String status) {
		ClassLog log = new ClassLog(classObj, student, faculty, rfid, status);
		
		log.setDate(new Date(System.currentTimeMillis()));
		log.setTime(new Time(System.currentTimeMillis()));
		
		return log;
	}
	
	private Boolean isTheTimeDifferenceisMoreThanThirtyMins(Time previousLogTime) {
		
		Time currentTime = new Time(System.currentTimeMillis());
		Long difference_In_Time =  currentTime.getTime() -  previousLogTime.getTime() ;

	    long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60; 
	
	    long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24; 
	
	    //return is zero if same day
	    //less than 0 if before
	    //more than 0 if after
	    Integer day = previousLogTime.compareTo(currentTime);
	    
	    System.out.println("difference_In_Minutes "+difference_In_Minutes);
	    
	    System.out.println("difference_In_Hours "+difference_In_Hours);
	    
	    
	    if( day>1 || difference_In_Hours>1 || difference_In_Minutes>30) {
	    	return true;
	    }else
	    	return false;
	    
	}
	
	public Student getSingleStudentDataPresentInClass(Long cId, Date date, String status) {
		ClassLog studentsPresentInClass = classLogRepository.getSingleStudentDataPresentInClass(cId, date, status);
		
		return studentsPresentInClass.getStudent();
	}


	public List<Student> getAllStudentsPresentInsideOfClass(Long classId, Date date, String status) {
		List<ClassLog> classLogs = classLogRepository.getClassLogsForClassOnDay(classId, date, status);
		List<Student> students = new ArrayList<Student>();
		for(ClassLog log : classLogs) {
			students.add( log.getStudent() );
		}
		return students;
	}


	public ClassLog getLogCurrentLogOfStudent(Long sId) {
		return studentService.getStudentWithId(sId).getCurrentLog();
	}


	public void deleteClassLog(ClassLog classLog) {
		if(classLog!=null)
		this.classLogRepository.delete(classLog);
	}
	

	
}

