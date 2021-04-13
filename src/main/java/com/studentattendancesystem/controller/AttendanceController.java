package com.studentattendancesystem.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AttendanceController {

	@RequestMapping("/departmentAttendance")
	public String DepartmentClasses(HttpSession session, Model model) {
		
		Long departmentId = (Long) session.getAttribute("departmentId");
		
		if(departmentId!=null) {
			model.addAttribute("departmentId", departmentId);
			return "DepartmentAttendance";
		}
		
		return "redirect:/errorPage";
	}
	
}
