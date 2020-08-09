package com.solt.jdc.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

    }
    
    @FXML
    void book(MouseEvent event) {

    }
    
    @FXML
    void category(MouseEvent event) {

    }
    
    @FXML
    void exit(MouseEvent event) {

    }
    
    @FXML
     void member(MouseEvent event) {

    }

    @FXML
    void saleItem(MouseEvent event) {
    	colorDefault();
    	fill(saleItemLabel, saleItemIcon, saleItemId,"#ace7ef", "black");
    }

    @FXML
    void saleReport(MouseEvent event) {
    	colorDefault();
    	fill(saleReportLabel, saleItemIcon, saleReportId, "#ace7ef", "black");

    }
    
    public static void showView() throws IOException {
    	Parent root = FXMLLoader.load(MainFrameViewController.class.getResource("MainFrameView.fxml"));
    	Stage stage = new Stage();
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
	}
	
}
