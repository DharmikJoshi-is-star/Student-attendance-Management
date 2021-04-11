package com.studentattendancesystem.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.studentattendancesystem.model.Faculty;
import com.studentattendancesystem.model.LogIn;
import com.studentattendancesystem.model.fronend.FacultyDepartmentDetails;
import com.studentattendancesystem.service.FacultyService;

@RestController
@RequestMapping("/faculty")
public class FacultyRestController {

	@Autowired
	private FacultyService facultyService;
	
	@PostMapping("/addFaculty/{dId}")
	public ResponseEntity<Faculty> saveFaculty(@RequestBody FacultyDepartmentDetails faculty, @PathVariable("dId") Long dId) {
		Faculty responseFaculty = facultyService.saveFaculty(faculty, dId);
		return ResponseEntity.ok().body(responseFaculty);
	}
	
	@DeleteMapping("/deleteFaculty/{fId}")
	public ResponseEntity<Map<String, Boolean>> deleteFacultyWithId(@PathVariable("fId") Long fId){
		Map<String, Boolean> response= new HashMap<String, Boolean>();
		response.put("Value deleted", facultyService.deleteFacultyWithId(fId));
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping("/saveCredentials/{fId}")
	public Boolean saveCredentials(@PathVariable("fId") Long fId, @RequestBody LogIn login){
		return facultyService.saveCredentials(fId, login);
	}
	
	
//	@GetMapping("/updateFaculty")
//	public Boolean facultyConnectToRFID() {
//		return facultyService.facultyConnectToRFID();
//	}
	
	
	@PostMapping("/editFaculty/{fId}")
	public ResponseEntity<Faculty> editFaculty(@RequestBody FacultyDepartmentDetails faculty, @PathVariable("fId") Long fId) {
		Faculty responseFaculty = facultyService.editFaculty(faculty, fId);
		return ResponseEntity.ok().body(responseFaculty);
	}
	
}
