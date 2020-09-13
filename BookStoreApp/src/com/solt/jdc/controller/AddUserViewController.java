package com.solt.jdc.controller;

import java.net.URL;
import java.util.ResourceBundle;
import com.solt.jdc.entity.User;
import com.solt.jdc.service.UserService;
import com.solt.jdc.util.BookStoreException;
import com.solt.jdc.util.Reloader;
import com.solt.jdc.util.ShowAlert;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;


public class AddUserViewController implements Initializable{
	
	  
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
    private PasswordField passwordTf;
    private UserService srv;
    public Reloader reloader;

	public User user;

    @SuppressWarnings("unused")
	public void add() {
    	try {
			if (nameTf.getText().isEmpty() || nameTf.getText() == null) {
				throw new BookStoreException("Name is Empty!");
			}
			if (phoneTf.getText().isEmpty() || phoneTf.getText() == null) {
				throw new BookStoreException("Phone is Empty!");
			}
			
			if (loginIdTf.getText().isEmpty() || loginIdTf.getText() == null) {
				throw new BookStoreException("LoginID is Empty!");
			}
			
			if (passwordTf.getText().isEmpty() || passwordTf.getText() == null) {
				throw new BookStoreException("Password is Empty!");
			}
			
			User user = new User();
			user.setLogin_id(loginIdTf.getText());
			user.setName(nameTf.getText());
			user.setPhone(phoneTf.getText());
			user.setEmail(emailTf.getText());
			user.setAddress(addressTa.getText());
			user.setPassword(passwordTf.getText());
		
			int rst = srv.addUser(user);
			if (rst != 0) {
				ShowAlert.showAlert("Add Process is Successfull", AlertType.CONFIRMATION);
				close();
			} else {
				ShowAlert.showAlert("Add Process is UnSuccessfull", AlertType.ERROR);
				
			} 
			reloader.reloader();
		} catch (Exception e) {
			ShowAlert.showAlert(e.getMessage(), AlertType.WARNING);
			
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
		reloader = new UserViewController();
		
	}


	
}
