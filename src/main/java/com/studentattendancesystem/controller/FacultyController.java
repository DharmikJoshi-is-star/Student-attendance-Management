package com.studentattendancesystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FacultyController {

	@RequestMapping("/markAttendance")
	public String markAttedance(@RequestParam("fId") Long fId, Model model) {
		model.addAttribute("facultyId", fId);
		return "markAttendance";
	}
	
}
