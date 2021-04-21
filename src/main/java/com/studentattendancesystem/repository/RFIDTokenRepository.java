package com.studentattendancesystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.studentattendancesystem.model.RFIDToken;

@Repository
public interface RFIDTokenRepository extends JpaRepository<RFIDToken, Long>{

	@Query("select rfid from RFIDToken rfid where rfid.student=null and rfid.faculty=null and rfid.department.id=?1")
	List<RFIDToken> getAllNonAssignedTokens(Long dId);

	@Query("select rfid from RFIDToken rfid where rfid.token_id=?1")
	RFIDToken getRFIDTokenWithTokenId(String tokenId);

	@Query("select rfid from RFIDToken rfid where rfid.department.id=?1")
	List<RFIDToken> getRFIDTokenWithDepartmentId(Long dId);

}
