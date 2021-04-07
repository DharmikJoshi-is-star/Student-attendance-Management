package com.studentattendancesystem.repository;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.studentattendancesystem.model.LectureAttendance;

@Repository   
public interface LectureAttendanceRepository extends JpaRepository<LectureAttendance, Long>{

	@Query("Select lectureAtt from LectureAttendance lectureAtt where lectureAtt.student.id=?1 and lectureAtt.lecture.classObj.id=?2 and lectureAtt.date=?3 and lectureAtt.isBuffered=true")
	List<LectureAttendance> getStudentBufferedAttendaceFromClassOnDate(Long studentId, Long classId, Date date);

	@Query("Select lectureAtt FROM LectureAttendance lectureAtt where lectureAtt.isBuffered=true and lectureAtt.student.department.id=?1")
	List<LectureAttendance> getAllTheBufferedAttendance(Long dId);

	@Query("Select lectureAtt from LectureAttendance lectureAtt where lectureAtt.student.id=?1 and lectureAtt.lecture.subject.id=?2 and lectureAtt.isPresent=true")
	List<LectureAttendance> getListOfAttendanceOfSubjectWhereStudentWasPresent(Long sId, Long subId);

	@Query("Select lectureAtt from LectureAttendance lectureAtt where lectureAtt.lecture.subject.id=?1 and lectureAtt.lecture.department.id=?2")
	List<LectureAttendance> getListOfLectureCountOfSubject(Long subId, Long deptId);

	@Query("Select lectureAtt from LectureAttendance lectureAtt where lectureAtt.lecture.department.id=?1")
	List<LectureAttendance> getCountOfLecturesWithDepartmentId(Long dId);

	@Query("Select lectureAtt from LectureAttendance lectureAtt where lectureAtt.student.id=?1 and lectureAtt.lecture.department.id=?2 and lectureAtt.isPresent=true")
	List<LectureAttendance> getCountLecturesAttendedByStudentOfDepartment(Long sId, Long dId);

	@Query("Select lectureAtt from LectureAttendance lectureAtt "
			+ "where "
			+ "lectureAtt.lecture.department.id=?1")
	List<LectureAttendance> getTotalLecturesConductedInDepartment(Long dId);

	@Query("Select lectureAtt from LectureAttendance lectureAtt "
			+ "where "
			+ "lectureAtt.student.id=?1 and "
			+ "lectureAtt.lecture.department.id=?2")
	List<LectureAttendance> getTotalLecturesAttendedByStudent(Long sid, Long dId);

	@Query("Select lectureAtt from LectureAttendance lectureAtt where lectureAtt.student.id=?1 and lectureAtt.lecture.subject.id=?2")
	List<LectureAttendance> getStudentDetailedAttendanceOfSubject(Long sId, Long subId);

	@Query("select lectureAtt from LectureAttendance lectureAtt where lectureAtt.lecture.subject.id=?1 and lectureAtt.date=?2")
	List<LectureAttendance> getAttendnaceOfSubjectOnDate(Long subId, Date date);

}
    