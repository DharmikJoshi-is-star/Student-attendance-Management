package com.studentattendancesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.studentattendancesystem.model.Faculty;
import com.studentattendancesystem.model.LogIn;

@Repository
public interface LoginRepository extends JpaRepository<LogIn, Long> {

	@Query("Select login.faculty from LogIn login where login.username=?1 and password=?2")
	Faculty verifyLoginCredentials(String username, String password);

	
}
