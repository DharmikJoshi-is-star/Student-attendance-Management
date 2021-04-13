package com.studentattendancesystem.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FacultyController {

	@RequestMapping("/markAttendance")
	public String markAttedance(Model model, HttpSession session) {
		
		Long facultyId = (Long)session.getAttribute("facultyId");
		
		if(facultyId==null)
			return "redirect:/errorPage";
		
		model.addAttribute("facultyId", facultyId);
		return "markAttendance";
	}
	
}
