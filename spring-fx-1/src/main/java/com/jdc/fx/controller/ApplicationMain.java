package com.jdc.fx.controller;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentView.fxml"));
		loader.setControllerFactory(this::call);
		Parent root = loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public Object call(Class<?> param) {
		
		try {
			return ctx.getBean(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("application.xml");

}
