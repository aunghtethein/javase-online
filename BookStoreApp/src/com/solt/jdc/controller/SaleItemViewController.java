package com.solt.jdc.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.solt.jdc.entity.Book;
import com.solt.jdc.entity.Invoice;
import com.solt.jdc.entity.SaleItem;
import com.solt.jdc.service.BookService;
import com.solt.jdc.service.InvoiceService;
import com.solt.jdc.service.SaleItemService;
import com.solt.jdc.util.BookStoreException;
import com.solt.jdc.util.Security;
import com.solt.jdc.util.ShowAlert;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class SaleItemViewController implements Initializable{

    @FXML
    private TextField schId;

    @FXML
    private TextField schName;

    @FXML
    private Label user;

    @FXML
    private TableView<Book> bookView;

    @FXML
    private TableColumn<Book, Integer> colId;

    @FXML
    private TableColumn<Book, String> colName;

    @FXML
    private TableColumn<Book, Double> colPrice;

    @FXML
    private TableColumn<Book, Integer> colStock;

    @FXML
    private TableColumn<Book, ImageView> colImage;

    @FXML
    private TableView<SaleItem> saleItemView;

    @FXML
    private TableColumn<SaleItem, Integer> idCol;

    @FXML
    private TableColumn<SaleItem, String> nameCol;

    @FXML
    private TableColumn<SaleItem, Double> priceCol;

    @FXML
    private TableColumn<SaleItem, Integer> stockCol;

    @FXML
    private TableColumn<SaleItem, Double> totalCol;

    @FXML
    private Label subTotal;

    @FXML
    private Label tax;

    @FXML
    private Label total;
    
    private BookService bookSrv;
    private List<Book> bookList;
    private InvoiceService invSrv;
	private SaleItemService srv;
    @FXML
    void clear() {
		getAllBook();
		schId.clear();
		schName.clear();
		saleItemView.getItems().clear();
		subTotal.setText("0.0");
		tax.setText("0.0");
		total.setText("0.0");
    }

    @FXML
    void paid() {
		List<SaleItem> saleItems = saleItemView.getItems();

		Invoice inv = new Invoice();
		inv.setSaleDate(LocalDate.now());
		inv.setUser_id(Security.getUser().getId());
		inv.setSubTotal(Double.valueOf(subTotal.getText()));
		inv.setTax(Double.valueOf(tax.getText()));
		inv.setTotal(Double.valueOf(total.getText()));
		int rst = invSrv.add(inv);
		if (rst > 0) {
			for (SaleItem item : saleItems) {
				srv.add(item);
				Book b = bookSrv.findById(item.getBook_id());
				b.setStock( b.getStock() - item.getCount());
				bookSrv.update(b);

			}
			clear();
		}
    }
    
    public void getAllBook() {
    	bookList = bookSrv.findAll();
    	bookView.getItems().clear();
    	bookView.getItems().addAll(bookList);
    }
	public void search() {
		int id = 0;
		try {
			if(schId.getText() != null || !schId.getText().isEmpty() ) {
				id = Integer.valueOf(schId.getText());
			}
			bookList = bookSrv.find(id, schName.getText()) ;
			bookView.getItems().clear();
			bookView.getItems().addAll(bookList);
		}catch (NumberFormatException e) {
			ShowAlert.showAlert("Please Enter Number Only In Search ID.", AlertType.WARNING);

   		 }
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
		colImage.setCellValueFactory(new PropertyValueFactory<>("imageView"));
		
		// idCol
		idCol.setCellFactory(event -> new TableCell<SaleItem, Integer>(){
			@Override
			public void updateIndex(int i) {
				// TODO Auto-generated method stub
				super.updateIndex(i);
				if(isEmpty() || i < 0) {
					setText(null);
				}else {
					setText(String.valueOf(i + 1));
				}
			}
		});
		user.setText(Security.getUser().getName());
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
		stockCol.setCellValueFactory(new PropertyValueFactory<>("count"));
		totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
		
		invSrv = new InvoiceService();
		bookSrv = new BookService();
		bookList = new ArrayList<Book>();
		srv = new SaleItemService();

		schId.textProperty().addListener((a,b,c) -> search());
		schName.textProperty().addListener((a,b,c) -> search());
		getAllBook();
		
		
			bookView.setOnMouseClicked(event -> {

				try {
					if(event.getClickCount() == 2) {
						Book book = bookView.getSelectionModel().getSelectedItem();
						
						if(book != null) {
							if(book.getStock() <=0) {
								throw new BookStoreException("There is no Stock!");
							}else {
								book.setStock(book.getStock()-1);
							}
							
							SaleItem sItem = saleItemView.getItems().stream().
									filter(a -> a.getBook_id()==book.getId()).findAny().orElse(null);
						
							if(sItem == null) {
								SaleItem saleItem = new SaleItem();
								saleItem.setName(book.getName());
								saleItem.setUnitPrice(book.getPrice());
								saleItem.setCount(1);
								saleItem.setBook_id(book.getId());
								saleItem.setInvoice_id(invSrv.getMaxInvoiceId() + 1);
								saleItemView.getItems().add(saleItem);
							}else{
								sItem.setCount(sItem.getCount() + 1);
							}
							bookView.refresh();
							saleItemView.refresh();
							
							double sTotal = saleItemView.getItems().stream().mapToDouble(a -> a.getTotal()).sum();
							subTotal.setText(String.valueOf(sTotal));
							tax.setText(String.format("%.1f", calTax(sTotal)));
							total.setText(String.format("%.1f", calTot(sTotal)));
						}
					}
				} catch (BookStoreException e) {
					ShowAlert.showAlert(e.getMessage(), AlertType.WARNING);
				}
				
				
			});
		
		
	}
	
	public double calTax(double num) {
		return num * 0.05;
	}
	public double calTot(double num) {
		return calTax(num)+num;
	}

}
