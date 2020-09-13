package com.solt.jdc.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.solt.jdc.entity.Author;
import com.solt.jdc.service.AuthorService;
import com.solt.jdc.util.BookStoreException;
import com.solt.jdc.util.Reloader;
import com.solt.jdc.util.ShowAlert;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EditAuthorViewController  implements Initializable{
	
	private Author author;
	private Reloader reloader;
	
    @FXML
    private TextField authorNameTf;

    private AuthorService srv;
    
    public void close() {
    	authorNameTf.getScene().getWindow().hide();
    }


    public void ok() {
    	try {
			String authorName = authorNameTf.getText();
			if (authorName == null || authorName.isEmpty()) {
				throw new BookStoreException("Author Name is Empty");
			}
			author.setAuthorName(authorName);
			int rst = srv.update(author);
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
    
    public static void showView(Author author, Reloader reloader) {
    	try {
			FXMLLoader load = new FXMLLoader(EditAuthorViewController.class.getResource("EditAuthorView.fxml"));
			Parent root = load.load();
			EditAuthorViewController controller = load.getController();
			controller.author = author;
			controller.reloader = reloader;
			controller.showInfo(author);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		
		}
    }
    
    public void showInfo(Author author) {
    	authorNameTf.setText(author.getAuthorName());
    }
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		srv = new AuthorService();
		author = new Author();
		reloader = new AuthorViewController();
	}


	

}
