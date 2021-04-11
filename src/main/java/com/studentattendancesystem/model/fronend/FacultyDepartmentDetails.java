package com.studentattendancesystem.model.fronend;

import com.studentattendancesystem.model.LogIn;

public class FacultyDepartmentDetails {

	private Long id;
	private String name;
	private String rfid;
	private LogIn login;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRfid() {
		return rfid;
	}
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}
	public LogIn getLogin() {
		return login;
	}
	public void setLogin(LogIn login) {
		this.login = login;
	}
	
	
}
