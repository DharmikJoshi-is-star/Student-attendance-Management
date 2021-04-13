package com.studentattendancesystem.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {

	@RequestMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
	}
	
	@RequestMapping("/departmentDashboard")
	public String departmentDashboard(HttpSession session, Model model) {
		
		Long departmentId = (Long)session.getAttribute("departmentId");
		System.out.println("departmentId="+departmentId);
		if(departmentId!=null) {
			model.addAttribute("departmentId", departmentId);
			return "DepartmentDashboard";
		}
		
		return "redirect:/errorPage";
	}
	
}
