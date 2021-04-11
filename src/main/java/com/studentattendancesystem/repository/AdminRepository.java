package com.studentattendancesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.studentattendancesystem.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>{

	@Query("Select admin from Admin admin where admin.username=?1 and admin.password=?2")
	Admin getAdminWithUsernameAndPassword(String username, String password);

}
