package com.studentattendancesystem.controller;

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
}
