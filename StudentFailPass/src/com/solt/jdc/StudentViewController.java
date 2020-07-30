package com.solt.jdc;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    	title.setText("Add Student");
    	rollTf.setEditable(true);
    	
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
				
				if (title.getText().equals("Add Student")) {
					add();
				} else {
					edit();
				}
			
				
				reload();
				
			} catch (StudentException e) {
				e.printStackTrace();
			}
    }     
   
    private void edit() {
    	Student student = new Student();
    	student.setRoll(rollTf.getText());
    	student.setName(nameTf.getText());
    	student.setMya(Integer.valueOf(myaTf.getText()));
    	student.setEng(Integer.valueOf(engTf.getText()));
    	student.setMath(Integer.valueOf(mathTf.getText()));
    	student.setChe(Integer.valueOf(cheTf.getText()));
    	student.setPhy(Integer.valueOf(phyTf.getText()));
        student.setBio(Integer.valueOf(bioTf.getText()));
        String result = checkResult(student);
        student.setResult(result);
        
        srv.edit(student);
        clear();
        
        
	}

	private void add() {
    	Student student = new Student();
    	student.setRoll(rollTf.getText());
    	student.setName(nameTf.getText());
    	student.setMya(Integer.valueOf(myaTf.getText()));
    	student.setEng(Integer.valueOf(engTf.getText()));
    	student.setMath(Integer.valueOf(mathTf.getText()));
    	student.setChe(Integer.valueOf(cheTf.getText()));
    	student.setPhy(Integer.valueOf(phyTf.getText()));
        student.setBio(Integer.valueOf(bioTf.getText()));
        String result = checkResult(student);
        student.setResult(result);
        
        srv.add(student);
        clear();
        
        
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
 
    private void reload() {
    	list = srv.findAll();
    
    	tableView.getItems().clear();
    	tableView.getItems().addAll(list);
     
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
		schResult.getItems().addAll("Pass & Fail","Pass","Fail");
		
		schName.textProperty().addListener((a,b,c) -> search());
		schRoll.textProperty().addListener((a,b,c) -> search());
		schResult.valueProperty().addListener((a,b,c) -> search());
		
		tableView.setOnMouseClicked(e -> {
			if(e.getClickCount() == 2) {
				Student stu = tableView.getSelectionModel().getSelectedItem();
				delete(stu);
			}
		});
		MenuItem menu = new MenuItem("Edit");
		menu.setOnAction(e -> {
			Student stu = tableView.getSelectionModel().getSelectedItem();
			if(stu != null ) {
				showText(stu);
				title.setText("Edit Student");
			}
		});
		tableView.setContextMenu(new ContextMenu(menu));
		reload();
	
	}

	private void showText(Student stu) {
		rollTf.setText(stu.getRoll());
		nameTf.setText(stu.getName());
		myaTf.setText(String.valueOf(stu.getMya()));
		engTf.setText(String.valueOf(stu.getEng()));
		mathTf.setText(String.valueOf(stu.getMath()));
		cheTf.setText(String.valueOf(stu.getChe()));
		phyTf.setText(String.valueOf(stu.getPhy()));
		bioTf.setText(String.valueOf(stu.getBio()));
		rollTf.setEditable(false);
	}

	private void delete(Student stu) {
		srv.delete(stu);
		reload();
	}

	private void search() {
		String stringRoll = schRoll.getText();
		String stringName = schName.getText();
		String stringResult = schResult.getValue();
		list = srv.find(stringRoll, stringName, stringResult);
		tableView.getItems().clear();
		tableView.getItems().addAll(list);
		
		
	}

}
