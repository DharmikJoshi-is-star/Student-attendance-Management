package com.studentattendancesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentattendancesystem.model.Department;
import com.studentattendancesystem.model.Subject;
import com.studentattendancesystem.model.Class;
import com.studentattendancesystem.repository.SubjectRepository;

@Service
public class SubjectService {

	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private LectureService lectureService;
	
	public Subject saveSubject(Subject subject, Long dId) {
		Department department = departmentService.getDepartmentWithId(dId);
		subject = subjectRepository.save(subject);
		
		department.getSubjects().add(subject);
		
		department = departmentService.saveDepartment(department);
		
		subject.setDepartment(department);
		
		subject = subjectRepository.save(subject);

		subject.setDepartment(null);
		
		return subject;
	}
	
	public List<Subject> getAllSubjects() {
		return subjectRepository.findAll();
	}    
	
	public Boolean deleteSubjectWithId(Long sId) {
		if(subjectRepository.existsById(sId)) {
			
			Subject subject = subjectRepository.getOne(sId);
			Department department = subject.getDepartment();
			
			department.getSubjects().remove(subject);
			departmentService.saveDepartment(department);
			System.out.println("subject is removed from department");
			
			
			lectureService.deleteAllLecturesOfSubject(subject);
			
			subjectRepository.delete(subject);
			System.out.println("subject is deleted");
			
			return true;
		}else
			return false;
	}

	public Subject getSubjectWithId(Long subjectId) {
		
		return subjectRepository.getOne(subjectId);
	}

	public Subject editSubject(Subject newSubject, Long subId) {
		
		if(!subjectRepository.existsById(subId)) 
			return newSubject;
	
		Subject subject = subjectRepository.getOne(subId);
		subject.setName(newSubject.getName());
		subjectRepository.save(subject);
			
		subject.setDepartment(null);
		
		return subject;
		
	
	}


	
}
