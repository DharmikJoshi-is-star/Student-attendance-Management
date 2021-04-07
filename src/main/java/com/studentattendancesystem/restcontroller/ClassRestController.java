package com.studentattendancesystem.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentattendancesystem.model.Class;
import com.studentattendancesystem.service.ClassService;

@RestController
@RequestMapping("/class")
public class ClassRestController {

	@Autowired
	private ClassService classService;
	
	@PostMapping("/addClass/{dId}")
	public ResponseEntity<Class> saveClass(@RequestBody Class classObj, @PathVariable("dId") Long dId) {
		classObj = classService.saveClass(classObj, dId);
		return ResponseEntity.ok().body(classObj);
	}
	
	@PostMapping("/editClass/{cId}")
	public Class updateClass(@RequestBody Class classObj, @PathVariable("cId") Long cId) {
		return classService.updateClass(classObj, cId);
	}
	
	@DeleteMapping("/deleteClass/{cId}")
	public Boolean deleteClass(@PathVariable("cId") Long cId) {
		return classService.deleteClass(cId);
	}
	
	@GetMapping("/getAllClasses/{dId}")
	public List<Class> getAllClasses(@PathVariable("dId") Long dId){
		return classService.getAllClasses(dId);
	}
	
	@GetMapping("/getClasses")
	public List<Class> getAllClasses(){
		return classService.getAllClasses();
	}
	
}
