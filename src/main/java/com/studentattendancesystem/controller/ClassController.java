package com.studentattendancesystem.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClassController {

	@RequestMapping("/showClasses")
	public String showClasses(@RequestParam("dId") Long dId, Model model) {
		model.addAttribute("departmentId", dId);
		return "showClasses";
	}
	
	@RequestMapping("/classes")
	public String classes(@RequestParam("dId") Long dId, Model model) {
		model.addAttribute("departmentId", dId);
		return "classes";
	}
	
	@RequestMapping("/departmentClasses")
	public String DepartmentClasses(HttpSession session, Model model) {
		
		Long departmentId = (Long) session.getAttribute("departmentId");
		
		if(departmentId!=null) {
			model.addAttribute("departmentId", departmentId);
			return "DepartmentClasses";
		}
		
		return "redirect:/errorPage";
	}
	
	
}
