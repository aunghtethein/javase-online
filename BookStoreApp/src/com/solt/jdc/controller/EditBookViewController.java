package com.solt.jdc.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
   
    private BookService srv;
    private AuthorService autSrv;
    private CategoryService catSrv;
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
			
			book.setName(bookNameTf.getText());
			book.setPrice(price);
			book.setStock(Integer.valueOf(stockTf.getText()));
			book.setIssueDate(issuseDateDP.getValue());
			book.setImage(ImageName);
			book.setAuthorId(authorCB.getValue().getId());
			book.setCategoryId(categoryCB.getValue().getId());
			
			int rst = srv.update(book);
					
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
    
    public static void showView(Reloader reloader, Book book) {
    	try {
			FXMLLoader loader = new FXMLLoader(EditBookViewController.class.getResource("EditBookView.fxml"));
			Parent root = loader.load();
			EditBookViewController controller = loader.getController();
			controller.reloader = reloader;
			controller.book = book;
			controller.showInfo(book);
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
    	authorCB.setValue(autSrv.findById(book.getAuthorId()));
    	categoryCB.setValue(catSrv.findByid(book.getCategoryId()));
    	issuseDateDP.setValue(book.getIssueDate());
    	stockTf.setText(String.valueOf(book.getStock()));
    	bookNameTf.setText(book.getName());
    	priceTf.setText(String.valueOf(book.getPrice()));
    	Image img = ImageManager.getImage(book.getImage());
    	imageView.setImage(img);
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		reloader = new BookViewController();
		Book book = new Book();
		srv = new BookService();
		autSrv = new AuthorService();
		catSrv = new CategoryService();
		
		List<Author> autList = autSrv.findAll();
		List<Category> catList = catSrv.findAll();
		Collections.sort(autList);
		Collections.sort(catList);
		
		categoryCB.getItems().addAll(catSrv.findAll());
		authorCB.getItems().addAll(autSrv.findAll());
		
		categoryCB.setVisibleRowCount(4);
		authorCB.setVisibleRowCount(4);
		
	}

}
