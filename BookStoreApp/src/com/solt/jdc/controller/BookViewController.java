package com.solt.jdc.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.solt.jdc.entity.Book;
import com.solt.jdc.service.BookService;
import com.solt.jdc.util.Reloader;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class BookViewController implements Initializable, Reloader{

    @FXML
    private TextField schName;

    @FXML
    private TextField schAuthor;

    @FXML
    private TableView<Book> tableView;

    @FXML
    private TableColumn<Book, Integer> colId;

    @FXML
    private TableColumn<Book, String> colBookName;

    @FXML
    private TableColumn<Book, Double> colPrice;

    @FXML
    private TableColumn<Book, Integer> colStock;

    @FXML
    private TableColumn<Book, String> colCategory;

    @FXML
    private TableColumn<Book, String> colAuthor;

    @FXML
    private TableColumn<Book, String> colDate;

    @FXML
    private TableColumn<Book, ImageView> colImage;
    private BookService srv;
    private List<Book>list;
    
    public void add() {
    	AddBookViewController.showView(this);
    }

   
   
    private void search() {
    	String searchN	= schName.getText();
    	String searchA = schAuthor.getText();
    	
    		list = srv.find(searchN,searchA);
    		tableView.getItems().clear();
    		tableView.getItems().addAll(list);
    	
    }
    
  
    public void clear() {
    	schName.clear();
    	schAuthor.clear();
    	reload();
    }
    
    public void reload() {
    	list = srv.findAll();
    	tableView.getItems().clear();
    	tableView.getItems().addAll(list);
    	
    }
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colBookName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
		colCategory.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
		colAuthor.setCellValueFactory(new PropertyValueFactory<>("authorName"));
		colDate.setCellValueFactory(new PropertyValueFactory<>("dateStr"));
		colImage.setCellValueFactory(new PropertyValueFactory<>("imageView"));
		
		srv = new BookService();
		list = new ArrayList<Book>();
		schAuthor.textProperty().addListener((a,b,c) -> search());
		schName.textProperty().addListener((a,b,c) -> search());
		
		MenuItem edit = new MenuItem("Edit");
		edit.setOnAction( e -> {
			Book book = tableView.getSelectionModel().getSelectedItem();
			if(book != null) {
				EditBookViewController.showView(this, book);
			}
		});
		
		tableView.setContextMenu(new ContextMenu(edit));
		reload();
	}


	@Override
	public void reloader() {
		reload();
	}

}
