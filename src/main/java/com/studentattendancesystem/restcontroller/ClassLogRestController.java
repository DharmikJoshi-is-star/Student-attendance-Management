package com.studentattendancesystem.restcontroller;

import java.sql.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentattendancesystem.model.ClassLog;
import com.studentattendancesystem.service.ClassLogService;

@RestController
@RequestMapping("/classlog")
public class ClassLogRestController {

	@Autowired
	private ClassLogService classLogService;
	
	
	//for testing
	//

	@GetMapping("/setLogIn/{deviceCode}/{tokenId}")
	public Boolean setLogIn(@PathVariable("deviceCode") String deviceCode, 
							@PathVariable("tokenId") String tokenId) {
		
		System.out.println("tokenId="+ tokenId);
		
		classLogService.setLogIn(deviceCode, tokenId);
		
		return true;
	}  
	
	@GetMapping("/setLogOut/{deviceCode}/{tokenId}")
	public Boolean setLogOut(@PathVariable("deviceCode") String deviceCode, 
							@PathVariable("tokenId") String tokenId) {
		
		classLogService.setLogOut(deviceCode, tokenId);
		System.out.println("Logout tokenId="+ tokenId);
		return true;
	}
	
	@GetMapping("/test")
	public Time test() {
		
		
		
		return null;
	}
	
	@GetMapping("/getLogCurrentLogOfStudent/{sId}")
	public ClassLog getLogCurrentLogOfStudent(@PathVariable("sId") Long sId) {
		return classLogService.getLogCurrentLogOfStudent(sId);
	}
	
}
