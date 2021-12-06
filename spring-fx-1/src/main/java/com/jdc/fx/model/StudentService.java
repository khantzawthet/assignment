package com.jdc.fx.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.fx.entity.Student;
import com.jdc.fx.repo.StudentRepo;

@Service
public class StudentService {

	@Autowired
	private StudentRepo repo;

	public List<Student> getAll() {
		return repo.findAll();
	}

	@Transactional
	public void save(Student s) {
		repo.save(s);
	}
}
