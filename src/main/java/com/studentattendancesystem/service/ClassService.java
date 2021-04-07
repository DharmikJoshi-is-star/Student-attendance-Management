package com.studentattendancesystem.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentattendancesystem.repository.ClassRepository;
import com.studentattendancesystem.model.Class;
import com.studentattendancesystem.model.Department;
import com.studentattendancesystem.model.Student;
@Service
public class ClassService {

	@Autowired
	private ClassRepository classRepository;
	
	@Autowired
	private DepartmentService departmentService;
	
	public Class saveClass(Class classObj, Long dId) {
		
		Department department = departmentService.getDepartmentWithId(dId);
		classObj.setDepartment(department);
		classObj =  classRepository.save(classObj);
		classObj.setDepartment(null);
		return classObj;
	}
	

	public Class updateClass(Class newClassObj, Long cId) {
		
		Class classObj = this.getClassWithId(cId);
		
		classObj.setClassNo( newClassObj.getClassNo() );
		classObj.setDeviceCode( newClassObj.getDeviceCode() );
		classObj =  classRepository.save(classObj);
		classObj.setDepartment(null);
		
		return classObj;
	}

	
	public List<Class> getAllClasses(){
		return classRepository.findAll();
	}

	public Boolean updateClass() {
		
		 this.getAllClasses().get(0).setDeviceCode("AB00940111");
		
		//this.saveClass( this.getAllClasses().get(0) );
		
		return null;
	}

	public Class getClassWithdeviceCode(String deviceCode) {
		return classRepository.getClassWithdeviceCode(deviceCode);
	}

	public Class getClassWithId(Long classId) {
		
		return classRepository.getOne(classId);
	}

	public List<Class> getAllClasses(Long dId) {
		List<Class> classes = classRepository.getAllClasses(dId);
		classes.forEach( (c)-> c.setDepartment(null) );
		return classes;
	}


	public Boolean deleteClass(Long cId) {
		
		if(classRepository.existsById(cId)) {
			
			Class classObj = this.getClassWithId(cId);
			
			classRepository.delete(classObj);
			
			return true;
		}
		
		return false;
	}

	
	
}

