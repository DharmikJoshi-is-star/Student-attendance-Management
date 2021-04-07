package com.studentattendancesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentattendancesystem.model.Faculty;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long>{

}
