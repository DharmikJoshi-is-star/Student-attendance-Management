package com.studentattendancesystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.studentattendancesystem.model.Lecture;
import com.studentattendancesystem.model.fronend.StudentDetailedAttendanceOfSubject;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long>{

	@Query("select lecture from Lecture lecture where lecture.day = ?1")
	public List<Lecture> findLectureWithDayName(String day);

	@Query("select lecture from Lecture lecture where lecture.department.id=?1 and lecture.day=?2")
	public List<Lecture> getAllLecturesOfDayWithDepartmentId(Long departmentId, String day);

	@Query("select lecture from Lecture lecture where lecture.subject.id=?1")
	public List<Lecture> deleteAllLecturesOfSubject(Long id);

	
	
}
 