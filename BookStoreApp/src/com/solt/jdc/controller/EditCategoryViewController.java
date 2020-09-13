package com.solt.jdc.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.solt.jdc.entity.Category;
import com.solt.jdc.service.CategoryService;
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

public class EditCategoryViewController  implements Initializable{
	
	private Category category;
	private Reloader reloader;
	
    @FXML
    private TextField categoryNameTf;

    private CategoryService srv;
    
    public void close() {
    	categoryNameTf.getScene().getWindow().hide();
    }


    public void ok() {
    	try {
			String categoryName = categoryNameTf.getText();
			if (categoryName == null || categoryName.isEmpty()) {
				throw new BookStoreException("Category Name is Empty");
			}
			category.setCategoryName(categoryName);
			int rst = srv.update(category);
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
    
    public static void showView(Category category, Reloader reloader) {
    	try {
			FXMLLoader load = new FXMLLoader(EditCategoryViewController.class.getResource("EditCategoryView.fxml"));
			Parent root = load.load();
			EditCategoryViewController controller = load.getController();
			controller.category = category;
			controller.reloader = reloader;
			controller.showInfo(category);
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
    
    public void showInfo(Category category) {
    	categoryNameTf.setText(category.getCategoryName());
    }
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		srv = new CategoryService();
		category = new Category();
		reloader = new CategoryViewController();
	}


	

}
