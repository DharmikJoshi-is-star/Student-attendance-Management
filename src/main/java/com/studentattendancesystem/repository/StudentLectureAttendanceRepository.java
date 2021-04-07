package com.studentattendancesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentattendancesystem.model.StudentLectureAttendance;

@Repository
public interface StudentLectureAttendanceRepository extends JpaRepository<StudentLectureAttendance, Long>{

}
