package com.studentattendancesystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentattendancesystem.model.Faculty;
import com.studentattendancesystem.model.LogIn;
import com.studentattendancesystem.repository.LoginRepository;

@Service
public class LoginService {

	@Autowired
	private LoginRepository loginRepository;
	
	public Long verifyLoginCredentials(LogIn login) {
		
		Faculty faculty =  loginRepository.verifyLoginCredentials(login.getUsername(), login.getPassword());
		
		if(faculty==null)
			return -1l;
		
		return faculty.getId();
	}

	public LogIn saveLogin(LogIn login) {
		return loginRepository.save(login);
	}
	
	
}
