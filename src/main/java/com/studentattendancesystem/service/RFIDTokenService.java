package com.studentattendancesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentattendancesystem.model.Department;
import com.studentattendancesystem.model.RFIDToken;
import com.studentattendancesystem.repository.RFIDTokenRepository;

@Service
public class RFIDTokenService {

	@Autowired
	private RFIDTokenRepository rfidTokenRepository;
	
	@Autowired
	private DepartmentService departmentService;
	
	public RFIDToken saveRfidToken(RFIDToken rfidToken, Long dId){
		
		Department department = departmentService.getDepartmentWithId(dId);
		rfidToken.setDepartment(department);
		
		RFIDToken rfid = rfidTokenRepository.save(rfidToken);
		rfid.setDepartment(null);
		return rfid;
	}

	public Boolean deleteRFIDToken(Long rfidId){
	
		if(rfidTokenRepository.existsById(rfidId)) {
			rfidTokenRepository.deleteById(rfidId);
			return true;
		}else
			return false;
		
	}
	
	public List<RFIDToken> getAllRFIDTokens(){

		List<RFIDToken> Tokens =  rfidTokenRepository.findAll();
		return Tokens;
	}

	public List<RFIDToken> getAllNonAssignedTokens(Long dId){
		
		List<RFIDToken> tokens = rfidTokenRepository.getAllNonAssignedTokens(dId);
		
		for(RFIDToken token: tokens) {
			token.setDepartment(null);
		}
		
		return tokens;
	}

	public RFIDToken getRFIDTokenWithTokenId(String tokenId) {
		
		return rfidTokenRepository.getRFIDTokenWithTokenId(tokenId);
		
	}

	public List<RFIDToken> getAllTokens(Long dId) {
		
		List<RFIDToken> Tokens =  rfidTokenRepository.getRFIDTokenWithDepartmentId(dId);
		
		for(RFIDToken token: Tokens) {
			token.setDepartment(null);
			if(token.getStudent()!=null) {
				token.getStudent().setCurrentLog(null);
				token.getStudent().setDepartment(null);
				token.getStudent().setRfidToken(null);
				token.getStudent().setStudentInfo(null);
			}
			if(token.getFaculty()!=null) {
				token.getFaculty().setCurrentLog(null);
				token.getFaculty().setDepartment(null);
				token.getFaculty().setLectures(null);
				token.getFaculty().setLogin(null);
				token.getFaculty().setRfidToken(null);
			}
		}
		
		return Tokens;
	}

	public void saveRfidToken(RFIDToken rfid) {
		// TODO Auto-generated method stub
		this.rfidTokenRepository.save(rfid);
	}

	public List<RFIDToken> getAllTokens(Long dId, Integer currentPage, Integer pageSize) {
		List<RFIDToken> tokens = getAllTokens(dId);
		
		int start = pageSize * (currentPage-1);
        int end = start + pageSize;
        
        
        if(start>=tokens.size())
        	return null;
        	
        if(end < tokens.size())
        	return tokens.subList(start, end);
        else
        	return tokens.subList(start, tokens.size());
	
	}

	
}
