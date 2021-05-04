package com.studentattendancesystem.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentattendancesystem.model.Admin;
import com.studentattendancesystem.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

	@Autowired
	private AdminService adminService;
	
	@PostMapping("/verifyCredentials")
	public Long verifyCredentials(@RequestBody Admin admin) {
		return adminService.verifyCredentials(admin);
	}
	
	@PostMapping("/addAdmin")
	public Admin addAdmin(@RequestBody Admin admin) {
		System.out.println(admin);
		return adminService.addAdmin(admin);
	}
	
	@DeleteMapping("/deleteAdmin/{adminId}")
	public Boolean deleteAdmin(@PathVariable("/adminId") Long adminId) {
		return adminService.deleteAdmin(adminId);
	}

	@GetMapping("/getAllAdmins")
	public List<Admin> getAllAdmins(){
		return adminService.getAllAdmins();
	}
	

	@PostMapping("/isSuperAdmin")
	public Boolean isSuperAdmin(@RequestBody Admin admin) {
		
		return adminService.isSuperAdmin(admin);
	}
	
	
}
