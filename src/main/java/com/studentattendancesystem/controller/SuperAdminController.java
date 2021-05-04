package com.studentattendancesystem.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SuperAdminController {

	@RequestMapping("/superAdmin")
	public String superAdmin(HttpSession session) {
		Boolean superAdmin = (Boolean)session.getAttribute("superAdmin");
		if(superAdmin)
			return "SuperAdmin";
		return "redirect:/errorPage";
	}
	
	
	@RequestMapping("/superAdminProcess")
	public String superAdminProcess(HttpSession session) {
		session.setAttribute("superAdmin", true);
		return "redirect:/superAdmin";
	}
	
}
