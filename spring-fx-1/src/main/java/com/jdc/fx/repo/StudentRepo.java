package com.jdc.fx.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jdc.fx.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Integer> {

}
