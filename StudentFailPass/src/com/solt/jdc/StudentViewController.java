package com.solt.jdc;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentViewController implements Initializable {

    @FXML
    private TextField nameTf;

    @FXML
    private TextField rollTf;

    @FXML
    private TextField myaTf;

    @FXML
    private TextField engTf;

    @FXML
    private TextField mathTf;

    @FXML
    private TextField cheTf;

    @FXML
    private TextField phyTf;

    @FXML
    private TextField bioTf;

    @FXML
    private TextField schName;

    @FXML
    private TextField schRoll;

    @FXML
    private ComboBox<String> schResult;

    @FXML
    private TableView<Student> tableView;

    @FXML
    private TableColumn<Student, String> colRoll;

    @FXML
    private TableColumn<Student, String> colName;

    @FXML
    private TableColumn<Student, Integer> colMya;

    @FXML
    private TableColumn<Student, Integer> colEng;

    @FXML
    private TableColumn<Student, Integer> colMath;

    @FXML
    private TableColumn<Student, Integer> colChe;

    @FXML
    private TableColumn<Student, Integer> colPhy;

    @FXML
    private TableColumn<Student, Integer> colBio;

    @FXML
    private TableColumn<Student, Integer> colTotal;

    @FXML
    private TableColumn<Student, String> colResult;
    
    @FXML
    private Label title;
    
    private StudentService srv;
    
    private List<Student> list;
    
    public void clear() {
    	nameTf.clear();
    	rollTf.clear();
    	myaTf.clear();
    	engTf.clear();
    	mathTf.clear();
    	cheTf.clear();
    	phyTf.clear();
    	bioTf.clear();
    	
    }

    public void ok() {
    	
			
    		try {
				if(rollTf.getText().isEmpty() || rollTf.getText() == null) {
					throw new RuntimeException("Roll is Empty!");
				}
				
				if(nameTf.getText().isEmpty() || nameTf.getText() == null) {
					throw new RuntimeException("Name is Empty!");
				}
				if(myaTf.getText().isEmpty() || rollTf.getText() == null) {
					throw new StudentException("Myanmar is Empty!");
				}
				
				if(engTf.getText().isEmpty() || engTf.getText() == null) {
					throw new RuntimeException("English is Empty!");
				}
				if(mathTf.getText().isEmpty() || mathTf.getText() == null) {
					throw new RuntimeException("Mathematic is Empty!");
				}
				if(cheTf.getText().isEmpty() || cheTf.getText() == null) {
					throw new RuntimeException("Chemistry is Empty!");
				}
				if(phyTf.getText().isEmpty() || phyTf.getText() == null) {
					throw new RuntimeException("Physics is Empty!");
				}
				if(bioTf.getText().isEmpty() || bioTf.getText() == null) {
					throw new RuntimeException("Biology is Empty!");
				}
//        	if(title.getText().equals("Add Student")) {
//        		
//        	}
				int MyanInt = 0;
				try {
					MyanInt = Integer.valueOf(myaTf.getText());
				} catch (NumberFormatException e) {
					throw new StudentException
						("Please enter number only in Myanmar!");
				}
				
				int EngInt = 0;
				try {
					EngInt = Integer.valueOf(engTf.getText());
				} catch (NumberFormatException e) {
					throw new StudentException
						("Please enter number only in English!");
				}
				
				int MathInt = 0;
				try {
					MathInt = Integer.valueOf(mathTf.getText());
				} catch (NumberFormatException e) {
					throw new StudentException
						("Please enter number only in Mathmatic!");
				}
				

				int CheInt = 0;
				try {
					CheInt = Integer.valueOf(cheTf.getText());
				} catch (NumberFormatException e) {
					throw new StudentException
						("Please enter number only in Chemistry!");
				}
				
				int PhyInt = 0;
				try {
					PhyInt = Integer.valueOf(phyTf.getText());
				} catch (NumberFormatException e) {
					throw new StudentException
						("Please enter number only in Physics!");
				}
				
				int BioInt = 0;
				try {
					BioInt = Integer.valueOf(bioTf.getText());
				} catch (NumberFormatException e) {
					throw new StudentException
						("Please enter number only in Biology!");
				}
				
				
				Student student = new Student();
				student.setRoll(rollTf.getText());
				student.setName(nameTf.getText());
				student.setMya(MyanInt);
				student.setEng(EngInt);
				student.setMath(MathInt);
				student.setChe(CheInt);
				student.setPhy(PhyInt);
				student.setBio(BioInt);
				String result = checkResult(student);
				student.setResult(result);
				Integer total = checkTotal(student);
				student.setTotal(total);
				
				srv.add(student);
				clear();
				reload();
				
			} catch (StudentException e) {
				ShowAlert.showAlert(e.getMessage(), AlertType.WARNING);
			}
    }     
   
    private String checkResult(Student student) {
    	String result = "Pass";
    	int [] marks = {student.getMya(),
    					student.getEng(),
    					student.getMath(),
    					student.getChe(),
    					student.getPhy(),
    					student.getBio()};
    	
    	for (int mark : marks) {
			if(mark < 40 ) {
				return "Fail";
		}
		}
    	return result;
    	 
      }
    
    private Integer checkTotal(Student student) {
    	int total = 0;
    	
    	int [] marks = {student.getMya(),
				student.getEng(),
				student.getMath(),
				student.getChe(),
				student.getPhy(),
				student.getBio()};

    	for( int m : marks) {
    		total += m;
    	}
		return total;
    }
    
    private void reload() {
    	list = srv.findAll();
    
    	tableView.getItems().clear();
    	tableView.getItems().addAll(list);
     
    }
    
    private void searchByName() {
    	if(!schName.getText().isEmpty() 
    		&& schName.getText() != null) {
    		list = srv.findByName(schName.getText());
    		tableView.getItems().clear();
    		tableView.getItems().addAll(list);
    	}else {
    		reload();
    	}
    }
    
    private void searchByRoll() {
    	if(!schRoll.getText().isEmpty()
    		&& schRoll.getText() != null) {
    			list = srv.findByRoll(schRoll.getText());
    			tableView.getItems().clear();
        		tableView.getItems().addAll(list);
    		}else {
    			reload();
    		}
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colRoll.setCellValueFactory(new PropertyValueFactory<>("roll"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colMya.setCellValueFactory(new PropertyValueFactory<>("mya"));
		colEng.setCellValueFactory(new PropertyValueFactory<>("eng"));
		colMath.setCellValueFactory(new PropertyValueFactory<>("math"));
		colChe.setCellValueFactory(new PropertyValueFactory<>("che"));
		colPhy.setCellValueFactory(new PropertyValueFactory<>("phy"));
		colBio.setCellValueFactory(new PropertyValueFactory<>("bio"));
		colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
		colResult.setCellValueFactory(new PropertyValueFactory<>("result"));
		
		srv = new StudentService();
		list = new ArrayList<Student>();
		schName.textProperty().addListener((a,b,c) -> searchByName());
		schRoll.textProperty().addListener((a,b,c) -> searchByRoll());
		
		
		
		reload();
	
	}

}
