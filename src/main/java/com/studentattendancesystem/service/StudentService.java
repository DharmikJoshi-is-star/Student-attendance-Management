package com.studentattendancesystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentattendancesystem.enums.STATUS;
import com.studentattendancesystem.model.ClassLog;
import com.studentattendancesystem.model.Department;
import com.studentattendancesystem.model.RFIDToken;
import com.studentattendancesystem.model.Student;
import com.studentattendancesystem.model.StudentInfo;
import com.studentattendancesystem.model.fronend.StudentFrontEndModel;
import com.studentattendancesystem.model.fronend.StudentMonthlyAttendaceReport;
import com.studentattendancesystem.repository.StudentInfoRepository;
import com.studentattendancesystem.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private RFIDTokenService rfidTokenService;
	
	@Autowired
	private LectureAttendanceService attendanceService;
	
	@Autowired
	private StudentInfoRepository studentInfoRepository;
	
	@Autowired
	ClassLogService classLogService;
	
	public Student saveStudent(StudentFrontEndModel studentModel, Long dId) {
		
		Department department = departmentService.getDepartmentWithId(dId);
		
		RFIDToken rfid = rfidTokenService.getRFIDTokenWithTokenId(studentModel.getRfid());//new RFIDToken();
		//rfid.setToken_id( studentModel.getRfid() );
		//rfid = rfidTokenService.saveRfidToken(rfid);
		
		
		Student student = new Student();
		student.setName( studentModel.getName() );
		student.setRoll_no( studentModel.getRollno() );
		student.setGender( studentModel.getGender() );
		student.setRfidToken( rfid );
		
		student = this.save(student);
		
		rfid.setStudent(student);
		//rfid = rfidTokenService.saveRfidToken(rfid);
		
		student.setDepartment(department);
		student = this.save(student);
		
		if(department.getStudents()==null)
			department.setStudents( new ArrayList<Student>() );
		
		department.getStudents().add(student);
		departmentService.saveDepartment(department);
		
		student.setCurrentLog(null);
		student.setDepartment(null);
		student.setRfidToken(null);
		student.setStudentInfo(null);
		
		return student;
	}
	
	public Student save(Student student) {
		return studentRepository.save(student);
	}
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}
	
	
	public Boolean studentConnectToDepartment() {
		Department department = departmentService.getAllDepartments().get(0);
		List<Student> students = this.getAllStudents();
		List<Student> newStudents = new ArrayList<>();
		for(Student student: students) {
			student.setDepartment(department);
			newStudents.add(this.save(student));
		}
		department.setStudents(newStudents);
		departmentService.saveDepartment(department);
		return null;
	}
	
	public Boolean studentConnectToRFID() {
		List<Student> students = this.getAllStudents();
		List<RFIDToken> rfidTokens = rfidTokenService.getAllRFIDTokens();
		
		for(int i=0 ; i<students.size(); i++) {
			Student student = students.get(i);
			RFIDToken rfid = rfidTokens.get(i);
			student.setRfidToken(rfid);
			student = this.save(student);
			rfid.setStudent(student);
			//rfidTokenService.saveRfidToken(rfid);
		}
		
		return null;
	}

	public Student getStudentWithRFIDToken(RFIDToken rfid) {
		return studentRepository.getStudentWithRFIDToken(rfid.getToken_id());
	}

	public void changeCurrentStatus(Student student, String currentStatus) {
		student.setCurrentStatus(currentStatus);
		this.save(student);
	}

	public Boolean studentAddCurrentStatus() {
		
		List<Student> students = this.getAllStudents();
		
		for(Student student: students) {
			student.setCurrentStatus(STATUS.OUT.toString());
			this.save(student);
		}
		
		return null;
	}

	public Student getStudentWithId(Long sId) {
		return studentRepository.getOne(sId);
	}


	
	public List<StudentFrontEndModel> getAllDepartmentStudentForFronEnd(Long dId){
		
		List<StudentFrontEndModel> frontEndStudents = new ArrayList<>();
		List<Student> backEndStudents =  studentRepository.getAllStudentsDepartmentId(dId);//this.getAllStudents();
		
		for(Student student: backEndStudents) {
			frontEndStudents.add( this.mapStudentToStudentFrontEndModel(student));
		}
		
		return frontEndStudents;
	}
	
	private StudentFrontEndModel mapStudentToStudentFrontEndModel(Student student) {
		
		StudentFrontEndModel studentFrontEndModel = new StudentFrontEndModel();
		
		studentFrontEndModel.setId( student.getId() );
		studentFrontEndModel.setRollno( student.getRoll_no() );
		studentFrontEndModel.setName( student.getName() );
		studentFrontEndModel.setDeptName(  student.getDepartment().getName() );
		studentFrontEndModel.setGender( student.getGender() );
		studentFrontEndModel.setCurrentStatus( student.getCurrentStatus() );
		if(student.getRfidToken()!=null)
		studentFrontEndModel.setRfid( student.getRfidToken().getToken_id() );
		if(student.getCurrentLog()!=null) {
			studentFrontEndModel.setCurrentClass( student.getCurrentLog().getClassObj().getClassNo() );
			studentFrontEndModel.setCurrentLogDate( student.getCurrentLog().getDate().toString() );
			studentFrontEndModel.setCurrentLogTime( student.getCurrentLog().getTime().toString() );
		}
		
		studentFrontEndModel.setAverageAttendance( attendanceService.averageAttendanceOfStudent(student.getId()) );
		studentFrontEndModel.setTotalLecturesPresent(attendanceService.getTotalLecturesAttendedByStudent(student.getId(), student.getDepartment().getId()) );
		
		return studentFrontEndModel;
	}

	public StudentFrontEndModel getStudentFronEnd(Long sId) {
		return this.mapStudentToStudentFrontEndModel( this.getStudentWithId(sId) );
	}

	public List<StudentMonthlyAttendaceReport> getStudentMonthlyReport(Long sId) {
		
		
		
		return null;
	}

	public StudentInfo saveStudentInfo(StudentInfo studentInfo, Long sId) {
		// TODO Auto-generated method stub
		
		Student student = this.getStudentWithId(sId);
		studentInfo  =  studentInfoRepository.save(studentInfo);
		
		student.setStudentInfo(studentInfo);
		student =  this.save(student);
		
		
		return studentInfo;
	}

	public StudentInfo getStudentInfo(Long sId) {
		return this.getStudentWithId(sId).getStudentInfo();
	}

	public Boolean deleteStudent(Long sId) {
		
		Student student = this.getStudentWithId(sId);
		if(student!=null) {
			Department department = student.getDepartment();
			RFIDToken rfid = student.getRfidToken();
			StudentInfo studentInfo =  student.getStudentInfo();
			ClassLog classLog = student.getCurrentLog();
			
			if(department!=null) {
				department.getStudents().remove(student);
				departmentService.saveDepartment(department);
			}
			
			if(rfid!=null) {
				rfid.setStudent(null);
				rfidTokenService.saveRfidToken(rfid);
				student.setRfidToken(null);
				student =  this.save(student);
			}
			
			
			if(studentInfo!=null)
				studentInfoRepository.delete(studentInfo);
				
			if(classLog!=null)
				classLogService.deleteClassLog(classLog);
			
			studentRepository.delete(student);
			
			return true;
		}else {
			return false;
		}
		
		
		
		
	}

	public StudentFrontEndModel editStudent(StudentFrontEndModel newStudent, Long sId) {
		
		Student student = this.getStudentWithId(sId);
		
		student.setGender( newStudent.getGender());
		student.setName(newStudent.getName());
		student.setRoll_no(newStudent.getRollno());

		
		RFIDToken assignedToken = student.getRfidToken();
		
		if(!assignedToken.getToken_id().equals( newStudent.getRfid() )) {
			
			assignedToken.setStudent(null);
			rfidTokenService.saveRfidToken(assignedToken);
			
			RFIDToken rfid = rfidTokenService.getRFIDTokenWithTokenId(newStudent.getRfid());//new RFIDToken();
			student.setRfidToken(rfid);
			student = this.save(student);
			rfid.setStudent(student);
			rfidTokenService.saveRfidToken(rfid);
			
		}
		
		student = this.save(student);
		
		return this.mapStudentToStudentFrontEndModel(student);
	}

	
}

