package com.studentattendancesystem.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studentattendancesystem.model.Subject;
import com.studentattendancesystem.service.SubjectService;

@RestController
@RequestMapping("/subject")
public class SubjectRestController {

	@Autowired
	private SubjectService subjectService;
	
	@PostMapping("/addSubject/{dId}")
	public ResponseEntity<Subject> saveSubject(@RequestBody Subject subject, @PathVariable("dId") Long dId){
		
		subject = subjectService.saveSubject(subject, dId);
		return ResponseEntity.ok().body(subject);
	}

	@DeleteMapping("/deleteSubject/{sId}")
	public ResponseEntity<Map<String, Boolean>> deleteSubjectWithId(@PathVariable("sId") Long sId){
		
		Map<String, Boolean> response = new HashMap();
		response.put("Value Deleted", subjectService.deleteSubjectWithId(sId));
		
		return ResponseEntity.ok().body(response);
		
	}
	
	@PostMapping("/editSubject/{subId}")
	public Subject editSubject(@RequestBody Subject subject, @PathVariable("subId") Long subId) {
		subject = subjectService.editSubject(subject, subId);
		return subject;
	}
	
	
}

