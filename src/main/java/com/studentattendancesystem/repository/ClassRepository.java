package com.studentattendancesystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.studentattendancesystem.model.Class;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long>{

	@Query("select c from Class c where c.deviceCode=?1")
	Class getClassWithdeviceCode(String deviceCode);

	@Query("select c from Class c where c.department.id=?1")
	List<Class> getAllClasses(Long dId);


}
