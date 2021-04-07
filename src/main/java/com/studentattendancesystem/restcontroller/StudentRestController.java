package com.studentattendancesystem.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentattendancesystem.model.Student;
import com.studentattendancesystem.model.StudentInfo;
import com.studentattendancesystem.model.fronend.StudentFrontEndModel;
import com.studentattendancesystem.model.fronend.StudentMonthlyAttendaceReport;
import com.studentattendancesystem.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentRestController {

	@Autowired
	private StudentService studentService;
	
	@PostMapping("/addStudent/{dId}")
	public Student saveStudent(@RequestBody StudentFrontEndModel student, @PathVariable("dId") Long dId){
		System.out.println("Roll NO: "+student.getRollno());
		Student savedStudent = studentService.saveStudent(student, dId);
		return savedStudent;
	}


	@PostMapping("/editStudent/{sId}")
	public StudentFrontEndModel editStudent(@RequestBody StudentFrontEndModel student, @PathVariable("sId") Long sId){
		System.out.println("Roll NO: "+student.getRollno());
		StudentFrontEndModel savedStudent = studentService.editStudent(student, sId);
		return savedStudent;
	}

	
	@PostMapping("/addStudentInfo/{sId}")
	public StudentInfo saveStudent(@RequestBody StudentInfo studentInfo, @PathVariable("sId") Long sId){
		System.out.println("studentInfo "+studentInfo);
		studentInfo = studentService.saveStudentInfo(studentInfo, sId);
		return studentInfo;
	}
	
	
	@GetMapping("/updateStudents")
	public Boolean studentConnectToDepartment() {
		return studentService.studentAddCurrentStatus();
	}
	

	@GetMapping("/getStudentInfo/{sId}")
	public StudentInfo getStudentInfo(@PathVariable("sId") Long sId) {
		return studentService.getStudentInfo(sId);
	}
	
	@DeleteMapping("/deleteStudent/{sId}")
	public Boolean deleteStudent(@PathVariable("sId") Long sId) {
		return studentService.deleteStudent(sId);
	}
	
	
	/*
	studentName
	studentRollNo
	studentGender
	studentDepartmentName
	studentTotalLecturesPresent
	studentAverageAttendance
	studentRFID
	studentCurrentStatus
	studentCurrentClass
	studentCurrentLogDate
	studentCurrentLogTime
	*/
	@GetMapping("/getAllStudents/{dId}")
	public List<StudentFrontEndModel> getAllStudentForFronEnd(@PathVariable("dId") Long dId){
		return studentService.getAllDepartmentStudentForFronEnd(dId);
	}
	
	@GetMapping("/getStudent/{sId}")
	public StudentFrontEndModel getStudentFronEnd(@PathVariable("sId") Long sId) {
		return studentService.getStudentFronEnd(sId);
	}
	
	
}
