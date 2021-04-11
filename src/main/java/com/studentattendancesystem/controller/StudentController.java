package com.studentattendancesystem.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {

	@RequestMapping("/departmentStudents")
	public String departmentStudents(HttpSession session, Model model) {
		
		Long departmentId = (Long) session.getAttribute("departmentId");
		
		if(departmentId!=null) {
			model.addAttribute("departmentId", departmentId);
			return "DepartmentStudent";
		}
	
		return "redirect:/error";
	}  
	
	
	@RequestMapping("/showAllDepartmentStudents")
	public String showAllStudents(@RequestParam("dId") Long dId, Model model) {
		model.addAttribute("departmentId", dId);
		return "showAllDepartmentStudents";
	}
	
	@RequestMapping("/showStudentDetails")
	public String studentDetails(@RequestParam("sId") Long sId, Model model) {
		model.addAttribute("studentId", sId);
		return "studentDetails";
	}
	
	@RequestMapping("/studentMonthlyReport")
	public String studentMonthlyReport(@RequestParam("sId") Long sId) {
		return "studentMonthlyReport";
	}
	
	@RequestMapping("/studentDetailedAttendanceOfSubject")
	public String studentDetailedAttendanceOfSubject(@RequestParam("sId") Long sId, @RequestParam("subId") Long subId,Model model) {
		model.addAttribute("studentId", sId);
		model.addAttribute("subjectId", subId);
		return "studentDetailedAttendanceOfSubject";
	}
	
	@RequestMapping("/studentsAverageAttendance")
	public String studentsAverageAttendance(@RequestParam("dId") Long dId, Model model) {
		model.addAttribute("departmentId", dId);
		return "studentsAverageAttendance";
	}
	
}
