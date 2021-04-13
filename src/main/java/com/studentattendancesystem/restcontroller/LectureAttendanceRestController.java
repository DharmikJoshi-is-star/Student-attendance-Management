package com.studentattendancesystem.restcontroller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentattendancesystem.model.Student;
import com.studentattendancesystem.model.Subject;
import com.studentattendancesystem.model.fronend.MarkAttendanceFronEndStudent;
import com.studentattendancesystem.model.fronend.MarkAttendanceFrontEndFaculty;
import com.studentattendancesystem.model.fronend.StudentDetailedAttendanceOfSubject;
import com.studentattendancesystem.model.fronend.StudentFrontEndModel;
import com.studentattendancesystem.model.fronend.StudentMonthlyAttendaceReport;
import com.studentattendancesystem.model.fronend.SubjectAttendanceOnDate;
import com.studentattendancesystem.model.fronend.SubjectDepartmentDetails;
import com.studentattendancesystem.service.LectureAttendanceService;

@RestController
@RequestMapping("/lectureattendance")
public class LectureAttendanceRestController {

	@Autowired
	private LectureAttendanceService lectureAttendanceService;
	
	//will return all the present students
	@GetMapping("/markAttendance/{fId}")
	public List<MarkAttendanceFronEndStudent> markAttendance(@PathVariable("fId") Long fId) {
		return lectureAttendanceService.markTodaysAttendance(fId);
	}
	
	@GetMapping("/facultyDetailsForMarkAttendance/{fId}")
	public MarkAttendanceFrontEndFaculty facultyDetailsForMarkAttendance(@PathVariable("fId") Long fId) {
		return lectureAttendanceService.getMarkAttendanceFronEndFaculty(fId);
	}
	

	@DeleteMapping("/deleteAllBufferedAttendance/{dId}")
	public Boolean deleteAllBufferedAttendance(@PathVariable("dId") Long dId) {
		lectureAttendanceService.deleteAllTheBufferedAttendance(dId);
		return true;
	}

	@DeleteMapping("/deleteBufferedAttendanceOfStudent/{attId}")
	public Boolean deleteBufferedAttendanceOfStudent(@PathVariable("attId") Long attendanceId) {
		lectureAttendanceService.deleteBufferAttendance(attendanceId);
		return true;
	}
	//tested
	// how many lcture student was present /  how many lecture were conducted *100
	@GetMapping("/averageAttendanceOfStudent/{sId}")
	public Float averageAttendanceOfStudent(@PathVariable("sId") Long sId) {
		
		return lectureAttendanceService.averageAttendanceOfStudent(sId);
	}
	
	
	//tested
	@GetMapping("/averageAttendanceOfStudentInMonth/{sId}/{month}")
	public Float averageAttendanceOfStudentInMonth(@PathVariable("sId") Long sId, 
														@PathVariable("month") Integer month) {
		
		return lectureAttendanceService.averageAttendanceOfStudentInMonth(sId, month);
	}
	
	@GetMapping("/getStudentMonthlyReport/{sId}")
	public List<StudentMonthlyAttendaceReport> getStudentMonthlyReport(@PathVariable("sId") Long sId){
		return lectureAttendanceService.getStudentMonthlyReport(sId);
	}
	
	
	
	@GetMapping("/averageAttendanceOfStudentInSubject/{sId}/{subId}")
	public Boolean averageAttendanceOfStudentInSubject() {
		
		return null;
	}
	
	
	//tested
	@GetMapping("/averageAttendanceOfAllSubjects/{sId}")
	public Map<Subject, Float> averageAttendanceOfAllSubjects(@PathVariable("sId") Long sId){
		return lectureAttendanceService.averageAttendanceOfStudentInAllSubjects(sId);
		
	}
	
	@GetMapping("/averageAttedndanceInAllSubjects/{sId}")
	public  List<SubjectDepartmentDetails> averageAttendanceInAllSubjects(@PathVariable("sId") Long sId){
		return lectureAttendanceService.averageAttendanceInAllSubjects(sId);
		
	}
	
	
	
	
	//tested
	//return the mapped list of subject and keys as number of lecture attendaed by student
	@GetMapping("/allSubjectsTotalPresentLectureCount/{sId}")
	public Map<Subject, Integer> allSubjectsTotalPresentLectureCount(@PathVariable("sId") Long sId){
		return lectureAttendanceService.allSubjectsTotalPresentLectureCount(sId);
	}
	
	@GetMapping("/getStudentDetailedAttendanceOfSubject/{sId}/{subId}")
	public List<StudentDetailedAttendanceOfSubject> 
					getStudentDetailedAttendanceOfSubject(
						@PathVariable("sId") Long sId,
							@PathVariable("subId") Long  subId){
		return lectureAttendanceService.getStudentDetailedAttendanceOfSubject(sId, subId);
	}
	
	@GetMapping("/getSubjectDetailedAttendance/{sId}/{subId}/{currentPage}/{pageSize}")
	public List<StudentDetailedAttendanceOfSubject> 
					getSubjectDetailedAttendance(
						@PathVariable("sId") Long sId,
							@PathVariable("subId") Long  subId,
								@PathVariable("currentPage") Integer currentPage,
									@PathVariable("pageSize") Integer pageSize){
		
		return lectureAttendanceService.getSubjectDetailedAttendance(sId, subId, currentPage, pageSize);
	}

	@GetMapping("/getAttendnaceOfSubjectOnDate/{subId}/{date}")
	public List<SubjectAttendanceOnDate> getAttendnaceOfSubjectOnDate(
											@PathVariable("subId") Long subId,
											@PathVariable("date") Date date
										){
		
		return lectureAttendanceService.getAttendnaceOfSubjectOnDate(subId, date);
	}
	
	@GetMapping("/sendEmail")
	public Boolean sendEmail() {
		//lectureAttendanceService.sendEmail();
		return true;
	}
	
	
	//To send Average Attendance to students
	@GetMapping("/sendAverageAttendanceGuardian/{dId}")
	public Boolean sendAverageAttendanceGuardian(@PathVariable("dId") Long dId) {
		return lectureAttendanceService.sendAverageAttendanceGuardian(dId);
	}
	
	//To send ALert to Students Whose Average Attendance is Low
	@GetMapping("/sendAlertMailToGuardian/{dId}/{minAttendance}")
	public Boolean sendAlertMailToGuardian(@PathVariable("dId") Long dId, @PathVariable("minAttendance") Float minAttendance) {
		return lectureAttendanceService.sendAlertMailToGuardian(minAttendance, dId);
	}
	
	//To send All Months Average Attendance Report 
	@GetMapping("/sendMonthlyAverageAttendanceGuardian/{dId}")
	public Boolean sendMonthlyAverageAttendanceGuardian(@PathVariable("dId") Long dId) {
		return lectureAttendanceService.sendMonthlyAverageAttendanceGuardian(dId);
	}
	
	//To send Monthly Average Attendance To Parent
	@GetMapping("/sendMonthlyAverageAttendanceGuardian/{dId}/{month}/{year}")
	public Boolean sendMonthlyAverageAttendanceGuardian(@PathVariable("dId") Long dId, @PathVariable("month") String month, @PathVariable("year") String year) {
		return lectureAttendanceService.sendMonthlyAverageAttendanceGuardian(dId, month, year);
	}
	
	//To send Monthly Alert For Average Attendance To Parent
	@GetMapping("/sendMonthlyAverageAttendanceGuardian/{dId}/{month}/{year}/{minAttendance}")
	public Boolean sendMonthlyAlertAverageAttendanceGuardian(@PathVariable("dId") Long dId, @PathVariable("month") String month, @PathVariable("year") String year, @PathVariable("minAttendance") Float minAttendance) {
		return lectureAttendanceService.sendMonthlyAlertAverageAttendanceGuardian(dId, month, year, minAttendance);
	}
	
	@GetMapping("/getAverageAttendanceOfStudents/{dId}")
	public List<StudentFrontEndModel> getAverageAttendanceOfStudents(@PathVariable("dId") Long dId) {
		return lectureAttendanceService.getAverageAttendanceOfStudents(dId);
	}
	
	@GetMapping("/getAverageAttendanceOfStudentsPerMonth/{dId}/{month}/{year}")
	public List<StudentFrontEndModel> getAverageAttendanceOfStudentsPerMonth(@PathVariable("dId") Long dId, @PathVariable("month") String month, @PathVariable("year") String year) {
		return lectureAttendanceService.getAverageAttendanceOfStudentsPerMonth(dId, month, year);
	}
	
	@GetMapping("/testChart")
	public Map<String, String> testCghart(){
		Map<String, String> testChart = new HashMap<>();
		testChart.put("MOnth1", "10");
		testChart.put("MOnth1", "10");
		testChart.put("MOnth1", "10");
		testChart.put("MOnth1", "10");
		
		return testChart;
	}
	
	@GetMapping("/getOneWeekAttedance/{dId}")
	public Map<String, Integer> getOneWeekAttedance(@PathVariable("dId") Long dId){
		return lectureAttendanceService.getOneWeekAttedance(dId);
	}
	
//	//return the mapped list of subject and keys as number of lecture have take in class 
//	@GetMapping("/allSubjectsTotalLectureCount")
//	public Map<Subject, Integer> allSubjectsTotalLectureCount(){
//		return lectureAttendanceService.allSubjectsTotalLectureCount();
//	}
}
