package com.studentattendancesystem.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DepartmentMailController {
//"departmentAverageAttendance"
	
	@RequestMapping("/departmentMail")
	public String DepartmentClasses(HttpSession session, Model model) {
		
		Long departmentId = (Long) session.getAttribute("departmentId");
		
		if(departmentId!=null) {
			model.addAttribute("departmentId", departmentId);
			return "DepartmentSendMail";
		}
		
		return "redirect:/errorPage";
	}
	
}
