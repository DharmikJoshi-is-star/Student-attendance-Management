package com.studentattendancesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentattendancesystem.model.Admin;
import com.studentattendancesystem.repository.AdminRepository;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;
	
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
		return admin;
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
	
}
