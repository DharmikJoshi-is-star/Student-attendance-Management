package com.studentattendancesystem.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RFIDController {

	@RequestMapping("/rfid")
	public String showRFID(@RequestParam("dId") Long dId, Model model) {
		model.addAttribute("departmentId",dId);
		return "rfid";
	}  
	
	@RequestMapping("/departmentNFCS")
	public String showRFID(HttpSession session, Model model) {
		
		Long departmentId = (Long) session.getAttribute("departmentId");
		
		if(departmentId!=null) {
			model.addAttribute("departmentId", departmentId);
			model.addAttribute("currentPage", 1);
			model.addAttribute("pageSize", 10);
			return "DepartmentNFCS";
		}
		
		return "redirect:/errorPage";
	}  
	
}
