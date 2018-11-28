package Control;

import Database.DBConnect;
import Database.DBControl;
import Model.AllCourse;
import Model.Student;
import Model.Subject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Optional;

public class InformationController {
    @FXML
    protected TableView<Subject> passView;

    @FXML
    protected TableColumn<Subject, String> PassSubID;

    @FXML
    protected TableColumn<Subject, String> PassSubname;

    @FXML
    protected TableColumn<Subject, String> PassSubCredit;

    @FXML
    protected TableColumn<Subject, String> PassSubyear;

    @FXML
    protected TableColumn<Subject, String> PassSubStatus;

    @FXML
    protected TableView<Subject> notView;

    @FXML
    protected TableColumn<Subject, String> notSubID;

    @FXML
    protected TableColumn<Subject, String> notSubname;

    @FXML
    protected TableColumn<Subject, String> notSubCredit;

    @FXML
    protected TableColumn<Subject, String> notSubyear;

    @FXML
    protected TableColumn<Subject, String> notHard;

    @FXML
    protected TableView<Subject> ChooseTable;

    @FXML
    protected TableColumn<Subject, String> ChooseID;

    @FXML
    protected TableColumn<Subject, String> ChooseName;

    @FXML
    protected TableColumn<Subject, String> ChooseCredit;

    @FXML
    protected TableColumn<Subject, String> ChooseYear;

    @FXML
    protected Button addbt,viewAllCourse,canclebt,dropbt;

    @FXML
    protected Label fname,lname,Year,tcredit,preTcredit,bsubject,csubject;

    @FXML
    protected ImageView image;


    private AllCourse allCourse = new AllCourse();
    protected Student presentStudent;
    private  ArrayList<Subject> allSubjects = new ArrayList<>();
    private ObservableList<Subject> dataSubject = FXCollections.observableArrayList();
    private ObservableList<Subject> dataNotPassSubject = FXCollections.observableArrayList();
    private ObservableList<Subject> dataChooseSubject = FXCollections.observableArrayList();
    private int totalCredit;


    @FXML
    public void initialize(){
        updateColor();
    }

    @FXML
    public void setPresentStudent(Student presentStudent) {
        this.presentStudent = presentStudent;
        readStudent();
        showImage();
        fname.setText(presentStudent.getFirstName());
        lname.setText(presentStudent.getLastName());
        Year.setText(presentStudent.getYear());
        tcredit.setText(String.valueOf(totalCredit));
    }

    @FXML
    public void showImage() {
        try {
            if (presentStudent.getPath() == null){
                image.setImage(new Image("Person/default.png"));
            }else {
                image.setImage(new Image("Person/"+presentStudent.getPath()));
            }
        }catch (IllegalArgumentException exception){
            System.out.println(presentStudent.getPath());
            System.out.println("Not found picture.");
            image.setImage(new Image("Person/default.png"));
        }
    }

    // made by 5810450733
    @FXML
    public void updateColor(){
        notHard.setCellFactory(column -> {
            return new TableCell<Subject, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty); //This is mandatory

                    if (item == null || empty) { //If the cell is empty
                        setText(null);
                        setStyle("");
                    } else { //If the cell is not empty\
                        setText(item); //Put the String data in the cell , set to null
                        //We get here all the info of the Subject of this row
                        Subject auxPerson = getTableView().getItems().get(getIndex());
                        if (auxPerson.getHardness().equals("3")) {
                            setText("Hard");
                            setStyle("-fx-font-weight: bold;" + "-fx-background-color: rgba(223,0,11,0.60)");
                        }else if (auxPerson.getHardness().equals("2")) {
                            setText("Medium");
                            setStyle("-fx-font-weight: bold;" + "-fx-background-color: rgba(15,86,223,0.65)");
                        }else if (auxPerson.getHardness().equals("1")) {
                            setText("Easy");
                            setStyle("-fx-font-weight: bold;" + "-fx-background-color: rgba(0,202,24,0.65)");
                        }else{
                            setStyle("-fx-background-color: white");
                            if (getTableView().getSelectionModel().getSelectedItems().contains(auxPerson)) {
                                setTextFill(Color.WHITE);
                            } else
                                setTextFill(Color.BLACK);
                        }
                    }
                }
            };
        });
    }



    public void readStudent(){
        DBControl connect = DBConnect.openDB();
        this.allSubjects = connect.readSubject();
        try {
            String[] subjectforStudent = presentStudent.getRegistersubject().split("#");
            ArrayList<Subject> toShownotPass = this.allSubjects;
            for (Subject shownotPass : toShownotPass) {
                for (String s : subjectforStudent) {
                    if (shownotPass.getSubCode().equals(s)){
                        System.out.println(shownotPass.getSubCode());
                        Subject studentSubject = new Subject(shownotPass.getSubCode(),shownotPass.getSubName(),
                                shownotPass.getCreDit(),shownotPass.getHardness(),shownotPass.getYear());
                        dataSubject.add(studentSubject);
                        totalCredit += Integer.parseInt(shownotPass.getCreDit());
                        toShownotPass.remove(studentSubject);
                    }
                }
            }
            for (Subject shownotPass : toShownotPass) {
                dataNotPassSubject.add(shownotPass);
            }
            showStudentPassSubject();
        }catch (NullPointerException e){
            for (Subject allSubject : allSubjects) {
                dataNotPassSubject.add(allSubject);
            }
            showStudentPassSubject();
        }

    }

    public void showStudentPassSubject(){
        PassSubID.setCellValueFactory(cellData->cellData.getValue().subCodeProperty());
        PassSubname.setCellValueFactory(cellData->cellData.getValue().subNameProperty());
        PassSubCredit.setCellValueFactory(cellData->cellData.getValue().creDitProperty());
        PassSubyear.setCellValueFactory(cellData->cellData.getValue().yearProperty());
        PassSubStatus.setCellValueFactory(cellData-> new SimpleStringProperty("Pass"));
        passView.setItems(dataSubject);
        //////////////////////////////////////////////////////////////////////////////////////////
        notSubID.setCellValueFactory(cellData->cellData.getValue().subCodeProperty());
        notSubname.setCellValueFactory(cellData->cellData.getValue().subNameProperty());
        notSubCredit.setCellValueFactory(cellData->cellData.getValue().creDitProperty());
        notSubyear.setCellValueFactory(cellData->cellData.getValue().yearProperty());
        notHard.setCellValueFactory(cellData->cellData.getValue().hardnessProperty());
        notView.setItems(dataNotPassSubject);
        notView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        notView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Subject clicked1 = notView.getSelectionModel().getSelectedItem();
                if (event.getClickCount() == 1 ){
                    bsubject.setText(clicked1.getSubName());
                    csubject.setText(clicked1.getCreDit());
                }else if (event.getClickCount() == 2){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to add this subject to Pre-Register table?",ButtonType.YES,ButtonType.NO);
                    alert.setHeaderText("");
                    Optional optional = alert.showAndWait();
                    if (optional.get().equals(ButtonType.YES)){
                        dataChooseSubject.add(clicked1);
                        dataNotPassSubject.remove(clicked1);
                        updateTotolPreCredit();
                        addbt.setDisable(false);
                    }else {
                        return;
                    }
                }
            }
        });
        notView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to add all selected to Pre-Register table?",ButtonType.YES,ButtonType.NO);
                    alert.setHeaderText("");
                    Optional optional = alert.showAndWait();
                    if (optional.get().equals(ButtonType.YES)) {
                        ObservableList<Subject> subjectObservableList = notView.getSelectionModel().getSelectedItems();
                        dataChooseSubject.addAll(subjectObservableList);
                        dataNotPassSubject.removeAll(subjectObservableList);
                        updateTotolPreCredit();
                        addbt.setDisable(false);
                    }else {
                        return;
                    }
                }
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////
        ChooseID.setCellValueFactory(cellData->cellData.getValue().subCodeProperty());
        ChooseName.setCellValueFactory(cellData->cellData.getValue().subNameProperty());
        ChooseCredit.setCellValueFactory(cellData->cellData.getValue().creDitProperty());
        ChooseYear.setCellValueFactory(cellData->cellData.getValue().yearProperty());
        ChooseTable.setItems(dataChooseSubject);
    }

    @FXML
    public void viewAllBThandle(ActionEvent event){
        if (event.getSource().equals(viewAllCourse)){
        }
    }

    @FXML
    public void handleAddPass(ActionEvent event){ // เหลือแก้ให้ น้องลงวิชาพี่ไม่ได้ , มีปุ่ม ลบวิชาออกจาก Pass list ด้วย
        if (event.getSource().equals(addbt)){
            Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to add all the subject to your passed subject list?",
                    ButtonType.YES,ButtonType.NO);
            newAlert.setHeaderText("");
            Optional optional = newAlert.showAndWait();
            for (Subject subject : dataChooseSubject) {
                if (!dataSubject.contains(subject) && optional.get().equals(ButtonType.YES)){
                    dataSubject.add(subject);
                    DBControl open = DBConnect.openDB();
                    String newSubject = "";
                    for (Subject subjectnew : dataSubject) {
                        newSubject += subjectnew.getSubCode()+"#";
                    }
                    presentStudent.setRegistersubject(newSubject);
                    open.updateStudentSubject(presentStudent);
                    totalCredit += Integer.parseInt(subject.getCreDit());
                    tcredit.setText(String.valueOf(totalCredit));
                    System.out.println("Complete");
                }
            }
            dataChooseSubject.clear();
            addbt.setDisable(true);
        }else{
            return;
        }
    }

    public void updateTotolPreCredit(){
        int pre = 0;
        for (Subject subject : dataChooseSubject) {
            pre += Integer.parseInt(subject.getCreDit());
        }
        preTcredit.setText(String.valueOf(pre));
    }

}
