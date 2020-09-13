package com.solt.jdc.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import com.solt.jdc.entity.Author;
import com.solt.jdc.entity.Book;
import com.solt.jdc.entity.Category;
import com.solt.jdc.util.Reloader;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EditBookViewController implements Initializable {

    @FXML
    private ImageView imageView;

    @FXML
    private ComboBox<Category> categoryCB;

    @FXML
    private ComboBox<Author> authorCB;

    @FXML
    private DatePicker issuseDateDP;

    @FXML
    private TextField bookNameTf;

    @FXML
    private TextField priceTf;

    @FXML
    private TextField stockTf;
    private Reloader reloader;
    private Book book;
   
    public void addImage() {
    	try {
			FileChooser choose = new FileChooser();
			choose.setTitle("Select Image");
			File file = choose.showOpenDialog(new Stage());
			if(file != null) {
				Image image = new Image(new FileInputStream(file),200,200,true,true	);
				imageView.setImage(image);
			}
			
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
		}
    
    }

   
    public void close() {
    	priceTf.getScene().getWindow().hide();
    }

   
    public void ok() {

    }
    
    public static void showView(Reloader reloader, Book book) {
    	try {
			FXMLLoader loader = new FXMLLoader(EditBookViewController.class.getResource("EditBookView.fxml"));
			Parent root = loader.load();
			EditBookViewController controller = loader.getController();
			controller.reloader = reloader;
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    }
    
    public void showInfo(Book book) {
    	//authorCB.setValue(book.getAuthorName());
    	bookNameTf.setText(book.getName());
    	priceTf.setText(String.valueOf(book.getPrice()));
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		reloader = new BookViewController();
		Book book = new Book();
	}

}
