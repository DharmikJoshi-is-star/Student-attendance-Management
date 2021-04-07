package com.studentattendancesystem.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentattendancesystem.model.Lecture;
import com.studentattendancesystem.model.fronend.LectureTimeTable;
import com.studentattendancesystem.service.LectureService;

@RestController
@RequestMapping("/lecture")
public class LectureRestController {

	@Autowired
	private LectureService lectureService;
	
	@PostMapping("/save")
	public ResponseEntity<Lecture> saveLecture(@RequestBody Lecture lecture){
	
		
		lecture = lectureService.saveLecture(lecture);
		return ResponseEntity.ok().body(lecture);
	}
	
	@GetMapping("/setUpLectures")
	public Boolean setUpLectures() {
		
		return lectureService.connectLecturesWithSubjectFaculty();
		
	}
	
	@PostMapping("/addLecture/{classId}/{departmentId}/{facultyId}/{subjectId}")
	public Lecture addLecture(@RequestBody LectureTimeTable lecture,
									@PathVariable("classId") Long classId,
									@PathVariable("departmentId") Long departmentId,
									@PathVariable("facultyId") Long facultyId,
									@PathVariable("subjectId") Long subjectId) {
		
		
		System.out.println("lecture="+lecture);
		return lectureService.addLecture(lecture, classId, departmentId, facultyId, subjectId);
		//return null;
	}
	
	@DeleteMapping("/deleteLecture/{lectureId}")
	public Boolean deleteLecture(@PathVariable("lectureId") Long lectureId) {
		
		
		System.out.println("lecture="+lectureId);
		return lectureService.deleteLectures(lectureId);
		//return null;
	}
	
}
