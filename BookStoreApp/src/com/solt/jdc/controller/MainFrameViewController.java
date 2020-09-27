package com.solt.jdc.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MainFrameViewController implements Initializable{

    @FXML
    private HBox saleItemId;

    @FXML
    private SVGPath saleItemIcon;

    @FXML
    private Label saleItemLabel;

    @FXML
    private HBox saleReportId;

    @FXML
    private SVGPath saleReportIcon;

    @FXML
    private Label saleReportLabel;
   
    @FXML
    private HBox bookId;

    @FXML
    private SVGPath bookIcon;

    @FXML
    private Label bookLabel;

    @FXML
    private HBox categoryId;

    @FXML
    private SVGPath categoryIcon;

    @FXML
    private Label categoryLabel;
    
    @FXML
    private HBox authorId;

    @FXML
    private SVGPath authorIcon;

    @FXML
    private Label authorLabel;

    @FXML
    private HBox memberId;

    @FXML
    private SVGPath memberIcon;

    @FXML
    private Label memberLabel;

    @FXML
    private HBox exitId;

    @FXML
    private SVGPath exitIcon;

    @FXML
    private Label exitLabel;


    @FXML
    private Label title;

    @FXML
    private Label hourId;

    @FXML
    private Label minuteId;

    @FXML
    private Label secondId;

    @FXML
    private StackPane stackPane;
    
    @FXML
    void author(MouseEvent event) {
    	colorDefault();
    	fill(authorLabel, authorIcon, authorId, "#ace7ef", "black");
    	loadView("Author Management", "AuthorView.fxml");
    }
    
    @FXML
    void book(MouseEvent event) {
    	colorDefault();
    	fill(bookLabel, bookIcon, bookId, "#ace7ef", "black");
    	loadView("Book Management", "BookView.fxml");
    	
    }
    
    @FXML
    void category(MouseEvent event) {
    	colorDefault();
    	fill(categoryLabel, categoryIcon, categoryId, "#ace7ef", "black");
    	loadView("Category Management", "CategoryView.fxml");
    }
    
    @FXML
    void exit(MouseEvent event) {
    	colorDefault();
    	fill(exitLabel, exitIcon, exitId, "#ace7ef", "black");
    	Platform.exit();
    }
    
    @FXML
     void member(MouseEvent event) {
    	colorDefault();
    	fill(memberLabel, memberIcon, memberId, "#ace7ef", "black");
    	loadView("User Management", "UserView.fxml");
    }

    @FXML
    void saleItem(MouseEvent event) {
    	loadSaleItem();
    }

    @FXML
    void saleReport(MouseEvent event) {
    	colorDefault();
    	fill(saleReportLabel, saleReportIcon, saleReportId, "#ace7ef", "black");

    }
    public void loadView(String title, String view) {
    	stackPane.getChildren().clear();
    	this.title.setText("");
    	try {
			Parent viewFx = FXMLLoader.load(getClass().getResource(view));
			stackPane.getChildren().addAll(viewFx);
		} catch (IOException e) {
			this.title.setText(title);
			e.printStackTrace();
		}
    }
    public void loadSaleItem() {
        colorDefault();
        fill(saleItemLabel, saleItemIcon, saleItemId,"#ace7ef", "black");
        loadView("Sale Items", "SaleItemView.fxml");
    }
    public static void showView() throws IOException {
    	FXMLLoader loader = new FXMLLoader(MainFrameViewController.class.getResource("MainFrameView.fxml"));
        Parent root = loader.load();
    	Stage stage = new Stage();
        MainFrameViewController controller = loader.getController();
        controller.loadSaleItem();
        stage.setFullScreen(true);
    	stage.setResizable(false);
    	stage.initStyle(StageStyle.UNDECORATED);
    	stage.setScene(new Scene(root));
    	stage.show();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Timeline tl = new Timeline(new KeyFrame(Duration.ZERO, event -> {
			LocalTime lt = LocalTime.now();
			insert(lt.getHour(), hourId);
			insert(lt.getMinute(), minuteId);
			insert(lt.getSecond(), secondId);
			
		}), new KeyFrame(Duration.seconds(1)));
		tl.setCycleCount(Animation.INDEFINITE);
		tl.play();
	}
	
	public void insert(int time, Label label) {
		if(time < 10) {
			label.setText("0".concat(String.valueOf(time)));
		}else {
			label.setText(String.valueOf(time));
		}
	}
	
	public void fill(Label label, SVGPath svg, HBox hbox, String hboxColor, String textColor) {
		hbox.setStyle("-fx-background-color:" + hboxColor);
    	label.setStyle("-fx-text-fill:" + textColor);
    	svg.setStyle("-fx-fill:" + textColor);
	}
	
	public void colorDefault() {
		String hboxColor = "#1e6262";
		String textColor = "White";
		fill(saleItemLabel, saleItemIcon, saleItemId,hboxColor, textColor );
		fill(saleReportLabel,saleReportIcon,saleReportId, hboxColor, textColor);
		fill(bookLabel, bookIcon, bookId, hboxColor, textColor);
		fill(categoryLabel, categoryIcon, categoryId, hboxColor, textColor);
		fill(authorLabel, authorIcon, authorId, hboxColor, textColor);
		fill(memberLabel, memberIcon, memberId, hboxColor, textColor);
		fill(exitLabel, exitIcon, exitId, hboxColor, textColor);
	
	
	}
	
}
