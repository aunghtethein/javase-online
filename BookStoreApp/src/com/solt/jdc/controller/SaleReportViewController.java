package com.solt.jdc.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.solt.jdc.entity.Invoice;
import com.solt.jdc.service.InvoiceService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SaleReportViewController implements Initializable {

    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    @FXML
    private TextField schEmployee;

    @FXML
    private TableView<Invoice> tableView;

    @FXML
    private TableColumn<Invoice	,Integer> colInvoiceId;

    @FXML
    private TableColumn<Invoice, String> colSaleDate;

    @FXML
    private TableColumn<Invoice, Double> colSubTotal;

    @FXML
    private TableColumn<Invoice, Double> colTax;

    @FXML
    private TableColumn<Invoice, Double> colTotal;

    @FXML
    private TableColumn<Invoice, String> colUserName;

    @FXML
    private Label totSub;

    @FXML
    private Label totTax;

    @FXML
    private Label totTotal;
    
    private List<Invoice> list;
    private InvoiceService srv;

   
    public void clear() {
    	totSub.setText("0.0");
    	totTax.setText("0.0");
    	totTotal.setText("0.0");
    	
    	reload();
    }
    
    private void calculate() {
    	double totS = 0d;
    	double totT = 0d;
    	double totTT= 0d;
    	for(Invoice i : tableView.getItems()) {
    		totS += i.getSubTotal();
    		totT += i.getTax();
    		totTT += i.getTotal();
    	}
    	totSub.setText(String.format("%.1f", totS));
    	totTax.setText(String.format("%.1f", totT));
    	totTotal.setText(String.format("%.1f", totTT));
    }
    
    private void search() {
		list = srv.find(dateFrom.getValue(), dateTo.getValue(), schEmployee.getText()) ;
		tableView.getItems().clear();
    	tableView.getItems().addAll(list);
	    calculate();
	}

    private void reload() {
    	list = srv.findAll();
    	tableView.getItems().clear();
    	tableView.getItems().addAll(list);
    	calculate();
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colInvoiceId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colSaleDate.setCellValueFactory(new PropertyValueFactory<>("saleDateStr"));
		colSubTotal.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
		colTax.setCellValueFactory(new PropertyValueFactory<>("tax"));
		colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
		colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
	
		list = new ArrayList<>();
		srv = new InvoiceService();
		dateFrom.valueProperty().addListener((a,b,c) -> search());
		dateTo.valueProperty().addListener((a,b,c) -> search());
		schEmployee.textProperty().addListener((a,b,c) -> search());
		
		reload();
	
	}

}
