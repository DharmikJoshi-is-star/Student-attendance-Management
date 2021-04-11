package com.studentattendancesystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SuperAdminController {

	@RequestMapping("/superAdmin")
	public String superAdmin() {
		return "SuperAdmin";
	}
	
}
