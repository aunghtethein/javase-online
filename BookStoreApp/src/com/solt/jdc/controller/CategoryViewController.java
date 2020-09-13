package com.solt.jdc.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.solt.jdc.entity.Category;
import com.solt.jdc.service.CategoryService;
import com.solt.jdc.util.BookStoreException;
import com.solt.jdc.util.Reloader;
import com.solt.jdc.util.ShowAlert;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class CategoryViewController implements Initializable, Reloader{

    @FXML
    private TextField addNameTf;

    @FXML
    private TextField schNameTf;

    @FXML
    private TableView<Category> tableView;

    @FXML
    private TableColumn<Category, Integer> colID;

    @FXML
    private TableColumn<Category, String> colName;
    
    private CategoryService srv;
   
    private List<Category> list;
    
    public void add() {
    	try {
			String addNameStr = addNameTf.getText();
			if (addNameStr == null || addNameStr.isEmpty()) {
				throw new BookStoreException("Category Name is empty!");
			}
			Category category = new Category();
			category.setCategoryName(addNameStr);
			int rst = srv.addCategory(category);
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
	
	public void reloader() {
		reload();
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colID.setCellValueFactory(new PropertyValueFactory<>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
		srv = new CategoryService();
		list = new ArrayList<>();
	
		schNameTf.textProperty().addListener((a,b,c) -> search());
		
		MenuItem edit = new MenuItem("Edit");
		edit.setOnAction(event ->{
			Category category = tableView.getSelectionModel().getSelectedItem();
			if(category != null) {
				EditCategoryViewController.showView(category, this);
			}
		});
		tableView.setContextMenu(new ContextMenu(edit));
		reload();
	}


}
