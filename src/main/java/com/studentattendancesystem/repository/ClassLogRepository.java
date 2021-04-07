package com.studentattendancesystem.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.studentattendancesystem.model.ClassLog;
import com.studentattendancesystem.model.Student;

@Repository
public interface ClassLogRepository extends JpaRepository<ClassLog, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM student_class_log classLog WHERE classLog.student_id is not null and classLog.student_id= :sId order by classLog.log_date, classLog.log_time DESC LIMIT 1")
	ClassLog getLatestClassLogOfStudent(Long sId);

	@Query(nativeQuery = true, value = "SELECT * FROM student_class_log classLog WHERE classLog.student_id is not null and classLog.class_obj_id=:cId and classLog.log_date=:date and classLog.status_in_out=:status  order by classLog.log_date, classLog.log_time DESC LIMIT 1")
	ClassLog getSingleStudentDataPresentInClass(Long cId, Date date, String status);

	@Query(nativeQuery = true, value = "SELECT * FROM student_class_log classLog WHERE classLog.student_id is not null and classLog.class_obj_id=:classId and classLog.log_date=:date and classLog.status_in_out=:status")
	List<ClassLog> getClassLogsForClassOnDay(Long classId, Date date, String status);
	
}
