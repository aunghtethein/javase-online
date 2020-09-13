package com.solt.jdc.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.solt.jdc.entity.User;
import com.solt.jdc.service.UserService;
import com.solt.jdc.util.BookStoreException;
import com.solt.jdc.util.Reloader;
import com.solt.jdc.util.ShowAlert;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CheckPasswordViewController implements Initializable{

    @FXML
    private TextField passwordTf;

    @FXML
    private Label userName;
    private User user;
    private UserService srv;
    private Reloader reloader;
    
 
    public void close() {
    	userName.getScene().getWindow().hide();
    }

    public void ok() {
    	try {
			User usr = srv.findById(user.getLogin_id());
			String password = passwordTf.getText();
			if (password == null || password.isEmpty()) {
				throw new BookStoreException("Password is Empty!");
			}
			if (usr.getPassword().equals(password)) {
				EditUserViewController.showView(usr, reloader);
				close();
			} else {
				ShowAlert.showAlert("Password is Invalid", AlertType.ERROR);
			} 
		} catch (BookStoreException e) {
			ShowAlert.showAlert(e.getMessage(), AlertType.ERROR);
		}
    	
    }
    
    public static void showView(User user, Reloader reloader) {
    	FXMLLoader loader = new FXMLLoader(CheckPasswordViewController.class.getResource("CheckPasswordView.fxml"));
    	try {
			Parent root = loader.load();
			CheckPasswordViewController controller = loader.getController();
			controller.user = user;
			controller.reloader = reloader;
			controller.userName.setText(user.getName());
			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		srv = new UserService();
		user = new User();
		reloader = new UserViewController();
		
	}

}
