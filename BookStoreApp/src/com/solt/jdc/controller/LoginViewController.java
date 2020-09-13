package com.solt.jdc.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.solt.jdc.entity.User;
import com.solt.jdc.service.UserService;
import com.solt.jdc.util.BookStoreException;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController implements Initializable{

    @FXML
    private TextField loginIdTf;

    @FXML
    private PasswordField passwordTf;

    @FXML
    private Label errorMessage;
    
    private UserService srv;

   
    public void close() {
    	errorMessage.getScene().getWindow().hide();
    }

   
    public void login() throws IOException {
    	String loginId = loginIdTf.getText();
    	String password = passwordTf.getText();
    	try {
			if(loginId == null || loginId.isEmpty()	) {
				throw new BookStoreException("LoginId is Empty!");
			}
			if(password == null || password.isEmpty()) {
				throw new  BookStoreException("Password is Empty!");
			}
			User user = srv.findById(loginId);
			
			if(user == null) {
				throw new BookStoreException("LoginID is Invalid");
			}
			if(!user.getPassword().equals(password)) {
				throw new BookStoreException("Password is Invalid");
			}
			
			MainFrameViewController.showView();
			errorMessage.getScene().getWindow().hide();
		} catch (BookStoreException e) {
			errorMessage.setText(e.getMessage());
		}
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		srv = new UserService();
	}

}
