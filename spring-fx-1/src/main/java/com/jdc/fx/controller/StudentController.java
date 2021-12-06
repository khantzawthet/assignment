package com.jdc.fx.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jdc.fx.entity.Student;
import com.jdc.fx.model.StudentService;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

@Controller
@Scope("prototype")
public class StudentController implements Initializable{

	@Autowired
	private StudentService service;
	
	@FXML
	private JFXTextField name;
	@FXML
	private JFXTextField phone;
	@FXML
	private JFXTextField email;
	
	@FXML
	private TableView<Student> table;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Student> list = service.getAll();
		table.getItems().addAll(list);
	}
	
	public void save() {
		Student s = new Student();
		s.setName(name.getText());
		s.setPhone(phone.getText());
		s.setEmail(email.getText());
		
		service.save(s);
		
		name.clear();
		phone.clear();
		email.clear();
		
		table.getItems().clear();
		table.getItems().addAll(service.getAll());
	}

}
