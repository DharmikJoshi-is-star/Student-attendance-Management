package com.studentattendancesystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentattendancesystem.model.fronend.DepartmentFrontEndModel;
import com.studentattendancesystem.service.DepartmentService;

@Controller
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	
	@RequestMapping("/department")
	public String showDepartment(Model model) {
		List<DepartmentFrontEndModel> departments = departmentService.getAllDepartmentsFronEnd();
		model.addAttribute(departments);
		return "department";
	}  
	
	@RequestMapping("/createDepartment")
	public String createDepartment(Model model) {
		
		return "CreateDepartment";
	}
	
	@RequestMapping("/departmentDetails")
	public String showDepartmentDetails(
				@RequestParam("dId") Long dId, Model model
			) {
		model.addAttribute("departmentId", dId);
		return "departmentDetails";
	}
	
	@RequestMapping("/findSubjectAttendanceByDay")
	public String findSubjectAttendanceByDay(
				@RequestParam("dId") Long dId,
			Model model) {
		model.addAttribute("departmentId", dId);
		return "findSubjectAttendanceByDay";
	}
	
	@RequestMapping("/timeTableDepartment")
	public String timeTableDepartment(
			@RequestParam("dId") Long dId, Model model) {
		model.addAttribute("departmentId", dId);
		return "timetableDepartment";
	}
}
