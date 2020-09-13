package com.solt.jdc.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.solt.jdc.entity.Author;
import com.solt.jdc.service.AuthorService;
import com.solt.jdc.util.BookStoreException;
import com.solt.jdc.util.Reloader;
import com.solt.jdc.util.ShowAlert;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AuthorViewController implements Initializable, Reloader {

    @FXML
    private TextField addNameTf;

    @FXML
    private TextField schNameTf;

    @FXML
    private TableView<Author> tableView;

    @FXML
    private TableColumn<Author, Integer> colID;

    @FXML
    private TableColumn<Author, String> colName;
    
    private AuthorService srv;
  
    private List<Author> list;
    
    public void add() {
    	try {
			String addNameStr = addNameTf.getText();
			if (addNameStr == null || addNameStr.isEmpty()) {
				throw new BookStoreException("Author Name is empty!");
			}
			Author author = new Author();
			author.setAuthorName(addNameStr);
			int rst = srv.addAuthor(author);
			if (rst == 0) {
				throw new BookStoreException("Add Process is UnSuccessfull!");
			} else {
				ShowAlert.showAlert("Add Process is Successfull", AlertType.INFORMATION);
			} 
		} catch (BookStoreException e) {
			ShowAlert.showAlert(e.getMessage(), AlertType.ERROR);
		}
    	clear();
    	
    	
    }

    public void clear() {
    	
    	addNameTf.clear();
    	schNameTf.clear();
    	reload();
    }

    public void reload() {
    	list = srv.findAll();
    	tableView.getItems().clear();
    	tableView.getItems().addAll(list);
    	
    }
    private void search() {
    	String sch = schNameTf.getText();
    	if(!sch.isEmpty() 
    			&& sch != null) {
    		list = srv.findByName(sch);
    		tableView.getItems().clear();
    		tableView.getItems().addAll(list);
    	}else {
    		reload();
    	}
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colID.setCellValueFactory(new PropertyValueFactory<>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<>("authorName"));
		srv = new AuthorService();
		list = new ArrayList<>();
	
		schNameTf.textProperty().addListener((a,b,c) -> search());
		
		MenuItem edit = new MenuItem("Edit");
		edit.setOnAction(event ->{
			Author author = tableView.getSelectionModel().getSelectedItem();
			if(author != null) {
				EditAuthorViewController.showView(author, this);
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
