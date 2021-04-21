package com.studentattendancesystem.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studentattendancesystem.model.Department;
import com.studentattendancesystem.model.Subject;
import com.studentattendancesystem.model.fronend.DepartmentFrontEndModel;
import com.studentattendancesystem.model.fronend.FacultyDepartmentDetails;
import com.studentattendancesystem.model.fronend.LectureTimeTable;
import com.studentattendancesystem.model.fronend.SubjectDepartmentDetails;
import com.studentattendancesystem.service.DepartmentService;

@RestController
@RequestMapping("/department")
public class DepartmentRestController {

	@Autowired
	private DepartmentService departmentService;
	
	@PostMapping("/addDepartment/{aId}")
	public ResponseEntity<Department> saveDepartment(@RequestBody Department department, @PathVariable("aId") Long aId) {
		
		if(aId==null)
			return null;
		department = departmentService.saveDepartment(department, aId);
		return ResponseEntity.ok().body(department);
	}
	
	@DeleteMapping("/delete/{dId}")
	public ResponseEntity<Map<String, Boolean>> deleteDepartmentWithId(@PathVariable("dId") Long dId){
		Map<String, Boolean> response= new HashMap<String, Boolean>();
		response.put("Value deleted", departmentService.deleteDepartmentWithId(dId));
		return ResponseEntity.ok().body(response);
	}
	
	@CrossOrigin(origins = {"http://127.0.0.1:5500/"}, allowedHeaders = {"Accept"})
	@GetMapping("/getAllDepartments")
	public List<DepartmentFrontEndModel> getAllDepartmentsFronEnd(){
		return departmentService.getAllDepartmentsFronEnd();
	}
	
	@GetMapping("/getAllDepartmentsSubjects/{dId}")
	public List<Subject> getAllDepartmentsSubjects(@PathVariable("dId") Long dId){
		
		return departmentService.getAllDepartmentsSubjects(dId);
	}
	
	@GetMapping("/getDepartmentDetails/{dId}")
	public DepartmentFrontEndModel getDepartmentDetails(@PathVariable("dId") Long dId) {
		return departmentService.getDepartmentDetails(dId);
	}
	
	@GetMapping("/getSubjectsDepartmentDetails/{dId}")
	public List<SubjectDepartmentDetails> getSubjectDepartmentDetails(@PathVariable("dId") Long dId){
		return departmentService.getSubjectDepartmentDetails(dId);
	}
	
	@GetMapping("/getFacultiesDepartmentDetails/{dId}")
	public List<FacultyDepartmentDetails> getFacultiesDepartmentDetails(@PathVariable("dId") Long dId){
		return departmentService.getFacultiesDepartmentDetails(dId);
	}
	
	@GetMapping("/getLecturesTimeTable/{dId}")
	public List<List<LectureTimeTable>> getLecturesTimeTable(@PathVariable("dId") Long dId){
		return departmentService.getLecturesTimeTable(dId);
	}
	
	
	
}
