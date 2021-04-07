package com.studentattendancesystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {

	@RequestMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
	}
}
