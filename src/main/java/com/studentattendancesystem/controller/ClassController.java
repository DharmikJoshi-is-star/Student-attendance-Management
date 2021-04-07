package com.studentattendancesystem.controller;

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
	
}
