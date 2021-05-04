package com.studentattendancesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentattendancesystem.model.Admin;
import com.studentattendancesystem.repository.AdminRepository;
import com.studentattendancesystem.service.mail.MailService;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private MailService mailService;
	
	public Long verifyCredentials(Admin admin) {
		admin = adminRepository.getAdminWithUsernameAndPassword(admin.getUsername(), admin.getPassword());
		if(admin==null) {
			return -1l;
		}
		return admin.getId();
	}

	public Admin addAdmin(Admin admin) {
		System.out.println(admin);
		admin = this.saveAdmin(admin);
		
		String subject = "Welcome to Student Attendance Management";
		String message = "Your Profile has been created on Student Attendance Management\n"
						+"You Have Department Admin Level Access into Our system \n"
						+"Click on this link: http://localhost:8080 \n"
						+"Use Below Credentials to Log into Our System\n"
						+"Username: "+admin.getUsername()+"\n"
						+"Password: "+admin.getPassword()+"\n"
						+"NOTE: DO NOT SHARE YOUR CREDENTIALS WITH ANYONE!!";
		
		
		this.sendEmail(admin.getEmail(), subject, message);
		
		return admin;
	}

	public void sendEmail(String receiver, String subject, String message) {
		mailService.sendMail(receiver, subject, message);
	}
	
	public Admin saveAdmin(Admin admin) {
		admin = adminRepository.save(admin);
		return admin;
	}
	
	public Boolean deleteAdmin(Long adminId) {
		if(adminRepository.existsById(adminId)) {
			adminRepository.deleteById(adminId);
			return false;
		}
		return false;
	}

	public Admin getAdminById(Long adminId) {
		if(adminRepository.existsById(adminId)) {
			return adminRepository.getOne(adminId);
		}
		return null;
	}

	public List<Admin> getAllAdmins() {
		
		List<Admin> admins = adminRepository.findAll();
		admins.forEach((admin)->{
			if(admin.getDepartment()!=null) {
				admin.getDepartment().setSubjects(null);
				admin.getDepartment().setLectures(null);
				admin.getDepartment().setFaculties(null);
				admin.getDepartment().setStudents(null);
			}
		});
		return admins;
	}

	public Boolean isSuperAdmin(Admin admin) {
		
		if(admin.getUsername().equals("superAdmin"))
			if(admin.getPassword().equals("superAdmin"))
				return true;
				
		return false;
	}
	

}
