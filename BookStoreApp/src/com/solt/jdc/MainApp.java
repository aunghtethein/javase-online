package com.solt.jdc;

import com.solt.jdc.controller.LoginViewController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {
	

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(LoginViewController.class.getResource("LoginView.fxml"));
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(new Scene(root));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}