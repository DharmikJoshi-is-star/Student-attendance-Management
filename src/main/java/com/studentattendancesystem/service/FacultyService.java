package com.studentattendancesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentattendancesystem.model.Department;
import com.studentattendancesystem.model.Faculty;
import com.studentattendancesystem.model.Lecture;
import com.studentattendancesystem.model.LogIn;
import com.studentattendancesystem.model.RFIDToken;
import com.studentattendancesystem.model.fronend.FacultyDepartmentDetails;
import com.studentattendancesystem.repository.FacultyRepository;

@Service
public class FacultyService {

	@Autowired
	private FacultyRepository facultyRepository;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private RFIDTokenService rfidTokenService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private LectureService lectureService;
	
	public Faculty saveFaculty(FacultyDepartmentDetails departmentFaculty, Long dId) {
		
		RFIDToken rfid = rfidTokenService.getRFIDTokenWithTokenId(departmentFaculty.getRfid());//new RFIDToken();
		//rfid.setToken_id(departmentFaculty.getRfid());
		
		//rfid = rfidTokenService.saveRfidToken(rfid);
		
		Faculty faculty = new Faculty();
		faculty.setName(departmentFaculty.getName());
		faculty.setRfidToken(rfid);
		faculty = facultyRepository.save(faculty);
		
		rfid.setFaculty(faculty);
		//rfid = rfidTokenService.saveRfidToken(rfid);
		
		Department department = departmentService.getDepartmentWithId(dId);
		
		department.getFaculties().add(faculty);
		department = departmentService.saveDepartment(department);
		faculty.setDepartment(department);
		faculty = facultyRepository.save(faculty);
		
		faculty.setDepartment(null);
		faculty.setRfidToken(null);
		
		return faculty;
	}
	
	public Faculty saveFaculty(Faculty faculty) {
		return facultyRepository.save(faculty);
	}
	
	public List<Faculty> getAllFaculty(){
		return facultyRepository.findAll();
	}
	
//	public void connectFacultyWithDepartment() {
//		List<Department> departments = departmentService.getAllDepartments();
//		List<Faculty> faculties= this.getAllFaculty();
//		for(Faculty faculty : faculties) {
//			faculty.setDepartment(departments.get(0));
//			this.saveFaculty(faculty);
//		}
//		departments.get(0).setFaculties(faculties);
//		departmentService.saveDepartment(departments.get(0));
//	}
	
//	public void connectFacultiesWithSubject() {
//		List<Subject> subjects = subjectService.getAllSubjects();
//		List<Faculty> faculties = this.getAllFaculty();
//		
//		for(int i=0; i<faculties.size(); i++) {
//			subjects.get(i).setFaculty(faculties.get(0));
//			faculties.get(i).set
//		}
//	}

	public Boolean deleteFacultyWithId(Long fId) {
		if(facultyRepository.existsById(fId)) {
			
			Faculty faculty = facultyRepository.getOne(fId);
			Department department = faculty.getDepartment();
			
			department.getFaculties().remove(faculty);
			departmentService.saveDepartment(department);
			
		
			for(Lecture lec: faculty.getLectures())
				lectureService.deleteLectures(lec.getId());
			
			faculty.getRfidToken().setFaculty(null);
			
			faculty.setCurrentLog(null);
			this.saveFaculty(faculty);
			
			facultyRepository.deleteById(fId);
			return true;
		}
		return false;
	}
	
//	public Boolean facultyConnectToRFID() {
//		List<Faculty> faculties = this.getAllFaculty();
//		List<RFIDToken> rfidTokens = rfidTokenService.getAllNonAssignedTokens();
//		
//		for(int i=0 ; i<faculties.size(); i++) {
//			Faculty faculty = faculties.get(i);
//			RFIDToken rfid = rfidTokens.get(i);
//			faculty.setRfidToken(rfid);
//			faculty = this.saveFaculty(faculty);
//			rfid.setFaculty(faculty);
//			rfidTokenService.saveRfidToken(rfid);
//		}
//		
//		return null;
//	}

	public void changeCurrentStatus(Faculty faculty, String currentStatus) {
		faculty.setCurrentStatus(currentStatus);
		this.saveFaculty(faculty);
	}

	public Faculty getFacultyWithId(Long fId) {
		return facultyRepository.getOne(fId);
	}

	public Faculty editFaculty(FacultyDepartmentDetails newFaculty, Long fId) {
		
		Faculty faculty = facultyRepository.getOne(fId);
		
		faculty.setName(newFaculty.getName());
		faculty.getRfidToken().setToken_id( newFaculty.getRfid() );
		
		faculty = this.saveFaculty(faculty);
		
		faculty.setDepartment(null);
		faculty.setRfidToken(null);
		
		return faculty;
	}

	public Boolean saveCredentials(Long fId, LogIn login) {
		
		if(facultyRepository.existsById(fId)) {
			Faculty faculty = facultyRepository.getOne(fId);
			
			LogIn facultyLogin = faculty.getLogin();
			
			if(facultyLogin==null) {
				login.setFaculty(faculty);
				login = loginService.saveLogin(login);
				faculty.setLogin(login);
				faculty = saveFaculty(faculty);
			}else {
				
				facultyLogin.setUsername( login.getUsername() );

				facultyLogin.setPassword( login.getPassword() );
			}
			
			return true;
		}
		
		return false;
	}
	
}
