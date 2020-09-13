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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

public class EditUserViewController implements Initializable{

    @FXML
    private TextField nameTf;

    @FXML
    private TextField phoneTf;

    @FXML
    private TextField emailTf;

    @FXML
    private TextField loginIdTf;

    @FXML
    private TextArea addressTa;
    
    @FXML
    private Label title;

    @FXML
    private PasswordField passwordTf;
    private UserService srv;
    private User user;
    private Reloader reloader;

    public void update() {
    	try {
    		
			if(loginIdTf.getText().isEmpty() || loginIdTf.getText() == null) {
				throw new BookStoreException("User LoginID is Empty!");
			}
			
			if(nameTf.getText() == null || nameTf.getText().isEmpty()) {
				throw new BookStoreException("User Name is Empty!");
			}
			
			if(phoneTf.getText().isEmpty() || phoneTf.getText() == null	) {
				throw new BookStoreException("Phone is Empty!");
			}
			
			if(passwordTf.getText().isEmpty() || passwordTf.getText() == null) {
				throw new BookStoreException("Password is Empty!");
			}
			
			
			user.setLogin_id(loginIdTf.getText());
			user.setName(nameTf.getText());
			user.setPhone(phoneTf.getText());
			user.setEmail(emailTf.getText());
			user.setAddress(addressTa.getText());
			user.setPassword(passwordTf.getText());
			int rst = srv.update(user);
			if (rst == 0) {
				throw new BookStoreException("Edit Process is not complete!");
			} else {
				ShowAlert.showAlert("Edit Complete!", AlertType.CONFIRMATION);
				close();
			}
			reloader.reloader();
			
		} catch (BookStoreException e) {
			ShowAlert.showAlert(e.getMessage(), AlertType.ERROR);
		}
    	
    }
    
    public static void showView(User user, Reloader reloader) {
    	try {
			FXMLLoader load = new FXMLLoader(EditUserViewController.class.getResource("EditUserView.fxml"));
			Parent root = load.load();
			EditUserViewController controller = load.getController();
			controller.user = user;
			controller.reloader = reloader;
			controller.showInfo(user);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
					
		} catch (IOException e) {
			e.getMessage();
		}
    }
    
    public void showInfo(User user) {
    	if(user != null) {
    		loginIdTf.setText(user.getLogin_id());
        	nameTf.setText(user.getName());
        	phoneTf.setText(user.getPhone());
        	emailTf.setText(user.getEmail());
        	addressTa.setText(user.getAddress());
        	passwordTf.setText(user.getPassword());
        	
    	}
    	
    }
    
    public void close() {
    	loginIdTf.getScene().getWindow().hide();
    	nameTf.getScene().getWindow().hide();
    	phoneTf.getScene().getWindow().hide();
    	emailTf.getScene().getWindow().hide();
    	addressTa.getScene().getWindow().hide();
    	passwordTf.getScene().getWindow().hide();
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		srv = new UserService();
		user = new User();
		reloader = new UserViewController();
		
		addressTa.setWrapText(true);
	}

}
