package com.solt.jdc.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.solt.jdc.entity.User;
import com.solt.jdc.service.UserService;
import com.solt.jdc.util.Reloader;
import com.solt.jdc.util.ShowAlert;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
public class UserViewController implements Initializable, Reloader{
	
	@FXML
	private TextField schNameTf;
	
    
    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<User, Integer> colID;

    @FXML
    private TableColumn<User, String> colName;

    @FXML
    private TableColumn<User, String> colLoginId;

    @FXML
    private TableColumn<User, String> colPhone;

    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TableColumn<User, String> colAddress;
    
    private UserService srv;
 
    private List<User> list;
    
  
    public void add() {
    	showView(this);
    	clear();
    	reload();
    }
    public void clear() {
    	schNameTf.clear();
    }
   
 
    public void reload() {
    	list = srv.findAll();
    	tableView.getItems().clear();
    	tableView.getItems().addAll(list);
    }
    private void search() {
    	String sch = schNameTf.getText();
    	if(!sch.isEmpty() || sch != null ) {
    		list = srv.findByName(sch);
    		tableView.getItems().clear();
    		tableView.getItems().addAll(list);
    	}
    }
   
	public  int delete(User user) {
		int rst = srv.delete(user);
		if(rst > 0) {
			ShowAlert.showAlert("Delete Cancelled Successfully!", AlertType.INFORMATION);
			reload();
		}else {
			ShowAlert.showAlert("Delete Successfully!!", AlertType.INFORMATION);
			reload();
		}
		return rst;
	}
	
	  public static void showView(Reloader reloader) {
	       	try {
				FXMLLoader load = new FXMLLoader(AddUserViewController.class.getResource("AddUserView.fxml"));
				Parent root = load.load();
				AddUserViewController controller = load.getController();
				controller.reloader = reloader;
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
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		colID.setCellValueFactory(new PropertyValueFactory<>("id"));
		colLoginId.setCellValueFactory(new PropertyValueFactory<>("login_id"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		colPhone.setCellValueFactory(new PropertyValueFactory<>("password"));
		
		srv = new UserService();
		list = new ArrayList<>();
		
		schNameTf.textProperty().addListener( (a,b,c) -> search());
		
		tableView.setOnMouseClicked(e -> {
			if(e.getClickCount() == 2 ) {
					User user = tableView.getSelectionModel().getSelectedItem();
					if(user != null) {
						CheckDeleteViewController.showView(user, this);
					
					//delete(user);
					reload();
					}else {
						ShowAlert.showAlert("Password is Invalid", AlertType.INFORMATION);
					}
				
			}
		});
		
		MenuItem edit = new MenuItem("Edit");
		edit.setOnAction(e -> {
			User user = tableView.getSelectionModel().getSelectedItem();
			if(user != null) {
				CheckPasswordViewController.showView(user, this);
				
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
