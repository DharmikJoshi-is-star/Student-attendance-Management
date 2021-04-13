package com.studentattendancesystem.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentattendancesystem.model.Admin;
import com.studentattendancesystem.model.Faculty;
import com.studentattendancesystem.service.AdminService;
import com.studentattendancesystem.service.FacultyService;
import com.studentattendancesystem.service.LoginService;

@Controller
public class LoginController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private FacultyService facultyService;
	
	
	@RequestMapping("/")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/login-process-admin")
	public String loginProcess(HttpSession session, @RequestParam("adminId") Long adminId) {
		
		Admin admin = adminService.getAdminById(adminId);
	
		if(admin==null)
			return "redirect:/errorPage";
		
		
		session.setAttribute("adminId", adminId);
		
		if(admin.getDepartment()==null) {
			return "redirect:/createDepartment";
		}
		
		session.setAttribute("departmentId", admin.getDepartment().getId());
		return "redirect:/departmentDashboard";
	}
	
	@RequestMapping("/login-process-faculty")
	public String loginProcessFaculty(HttpSession session, @RequestParam("facultyId") Long facultyId) {
		
		Faculty faculty = facultyService.getFacultyWithId(facultyId);
				
		if(faculty==null)
			return "redirect:/errorPage";
		
		
		session.setAttribute("facultyId", facultyId);
		
		session.setAttribute("departmentId", faculty.getDepartment().getId());
		return "redirect:/markAttendance";
	}
	
	
}
