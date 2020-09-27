package com.solt.jdc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import com.solt.jdc.entity.Author;
import com.solt.jdc.entity.Book;
import com.solt.jdc.entity.Category;
import com.solt.jdc.service.AuthorService;
import com.solt.jdc.service.BookService;
import com.solt.jdc.service.CategoryService;
import com.solt.jdc.util.BookStoreException;
import com.solt.jdc.util.ImageManager;
import com.solt.jdc.util.Reloader;
import com.solt.jdc.util.ShowAlert;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AddBookViewController implements Initializable{

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
    private CategoryService categorySrv;
    private AuthorService authorSrv;
    private BookService srv;
    private Reloader reloader;
    
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
    	stockTf.getScene().getWindow().hide();
    }

  
    @SuppressWarnings("unused")
	public void ok() {
    	try {
			if(categoryCB.getValue() == null) {
				throw new BookStoreException("Category is Empty!");
			}
			if(authorCB.getValue() == null) {
				throw new BookStoreException("Author is Empty!");
			}
			if(issuseDateDP.getValue() == null) {
				throw new BookStoreException("Issuse Date is not Selected!");
			}
			if(bookNameTf.getText().isEmpty() || bookNameTf.getText() == null) {
				throw new BookStoreException("Book Name is Empty!");
			}
			if(priceTf.getText().isEmpty() || priceTf.getText() == null) {
				throw new BookStoreException("Price is Empty!");
			}
			if(stockTf.getText().isEmpty() || stockTf.getText() == null) {
				throw new BookStoreException("Stock is Empty!");
			}
			Double price = 0.0;
			Double stock = 0.0;
			try {
				price = Double.valueOf(priceTf.getText());
				} catch (Exception e) {
				throw new BookStoreException("Please Enter Number only in Price");
			}
			
			try {
				stock = Double.valueOf(stockTf.getText());	
			} catch (Exception e) {
				throw new BookStoreException("Please Enter Number only in Stock");
			}
			String ImageName = bookNameTf.getText().concat(".png");
			Book b = new Book();
			b.setName(bookNameTf.getText());
			b.setPrice(price);
			b.setStock(Integer.valueOf(stockTf.getText()));
			b.setIssueDate(issuseDateDP.getValue());
			b.setImage(ImageName);
			b.setAuthorId(authorCB.getValue().getId());
			b.setCategoryId(categoryCB.getValue().getId());
			
			int rst = srv.addBook(b);
					
			if(rst != 0) {
				ShowAlert.showAlert("Add Complete!", AlertType.CONFIRMATION);
				File file = new File(ImageManager.createDirectory(), ImageName);
				ImageManager.saveImage(imageView, file);
				close();
				reloader.reloader();
				
			}else {
				ShowAlert.showAlert("Add UnComplete!", AlertType.ERROR);
			}
			
		} catch (BookStoreException e) {
			ShowAlert.showAlert(e.getMessage(), AlertType.WARNING);
		}
    }
    
    public static void showView(Reloader reloader) {
    	try {
			FXMLLoader loader = new FXMLLoader
					(AddBookViewController.class.getResource("AddBookView.fxml"));
			Parent root = loader.load();
			AddBookViewController controller = loader.getController();
			controller.reloader = reloader;
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    	
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		srv = new BookService();
		categorySrv = new CategoryService();
		authorSrv = new AuthorService();
		List<Author> autList = authorSrv.findAll();
		List<Category> catList = categorySrv.findAll();
		Collections.sort(autList);
		Collections.sort(catList);
		
		categoryCB.getItems().addAll(categorySrv.findAll());
		authorCB.getItems().addAll(authorSrv.findAll());
		
		categoryCB.setVisibleRowCount(4);
		authorCB.setVisibleRowCount(4);
		
		reloader = new BookViewController();
	}

}
