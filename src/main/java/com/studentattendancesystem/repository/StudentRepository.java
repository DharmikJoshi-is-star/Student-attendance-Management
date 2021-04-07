package com.studentattendancesystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.studentattendancesystem.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	@Query("select student from Student student where student.rfidToken.token_id=?1")
	Student getStudentWithRFIDToken(String token_id);

	@Query("select student from Student student where student.department.id=?1")
	List<Student> getAllStudentsDepartmentId(Long dId);

}
