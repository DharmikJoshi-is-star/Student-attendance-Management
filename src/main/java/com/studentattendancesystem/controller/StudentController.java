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
	
		return "redirect:/errorPage";
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
	
	@RequestMapping("/departmentStudentDetailsProcess")
	public String departmentStudentDetailsProcess(Model model, HttpSession session, @RequestParam("sId") Long sId) {
		
		Long departmentId = (Long) session.getAttribute("departmentId");
		if(departmentId==null || sId==null) {
			return "redirect:/errorPage";
		}
		
		session.setAttribute("departmentId", departmentId);
		session.setAttribute("studentId", sId);
	
		return "redirect:/departmentStudentDetails";
	}
	
	@RequestMapping("/departmentStudentDetails")
	public String departmentStudentDetails(Model model, HttpSession session) {
		Long studentId = (Long) session.getAttribute("studentId");
		model.addAttribute("studentId", studentId);
		if( studentId==null) {
			return "redirect:/errorPage";
		}
		System.out.println("studentId= "+studentId);
		return "DepartmentStudentDetails";
	}
	
	@RequestMapping("/studentMonthlyReportsProcess")
	public String studentMonthlyReportsProcess(Model model, HttpSession session, @RequestParam("sId") Long sId) {
		
		Long departmentId = (Long) session.getAttribute("departmentId");
		if(departmentId==null || sId==null) {
			return "redirect:/errorPage";
		}
		
		session.setAttribute("departmentId", departmentId);
		session.setAttribute("studentId", sId);
	
		return "redirect:/studentMonthlyReports";
	}
	
	@RequestMapping("/studentMonthlyReports")
	public String studentMonthlyReports(Model model, HttpSession session) {
		Long studentId = (Long) session.getAttribute("studentId");
		if( studentId==null) {
			return "redirect:/errorPage";
		}
		model.addAttribute("studentId", studentId);
		return "StudentMonthlyReport";
	}
	
	@RequestMapping("/subjectDetailReportProcess")
	public String subjectDetailReportProcess(Model model, HttpSession session, @RequestParam("sId") Long sId, @RequestParam("subId") Long subId) {
		
		Long departmentId = (Long) session.getAttribute("departmentId");
		if(departmentId==null || sId==null || subId==null)  {
			return "redirect:/errorPage";
		}
		
		session.setAttribute("departmentId", departmentId);
		session.setAttribute("studentId", sId);
		session.setAttribute("subjectId", subId);
	
		return "redirect:/subjectDetailReport";
	}
	
	@RequestMapping("/subjectDetailReport")
	public String subjectDetailReport(Model model, HttpSession session) {
		Long studentId = (Long) session.getAttribute("studentId");
		Long subjectId = (Long) session.getAttribute("subjectId");
		if( studentId==null || subjectId==null) {
			return "redirect:/errorPage";
		}
		model.addAttribute("pageSize", 10);
		model.addAttribute("currentPage", 1);
		model.addAttribute("studentId", studentId);
		model.addAttribute("subjectId", subjectId);
		return "SubjectDetailReport";
	}
}
