package com.studentattendancesystem.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentattendancesystem.model.LogIn;
import com.studentattendancesystem.service.LoginService;

@RestController
@RequestMapping("login")
public class LoginRestController {

	@Autowired
	private LoginService loginService;
	
	@PostMapping("/verifyLoginCredentials")
	public Long verifyLoginCredentials(@RequestBody LogIn login) {
		return loginService.verifyLoginCredentials(login);
	}
	
}
