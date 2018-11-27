package Control;

import Database.DBConnect;
import Database.DBControl;
import Model.AllCourse;
import Model.Student;
import Model.Subject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
    protected Button addbt;

    @FXML
    protected Label infoamationLabel;

    private AllCourse allCourse = new AllCourse();
    protected Student presentStudent;
    private  ArrayList<Subject> allSubjects = new ArrayList<>();
    private ObservableList<Subject> dataSubject = FXCollections.observableArrayList();
    private ObservableList<Subject> dataNotPassSubject = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        this.presentStudent = MainController.nowLogin;
        readStudent();
        updateColor();
    }

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
                        setText(null); //Put the String data in the cell , set to null
                        //We get here all the info of the Person of this row
                        Subject auxPerson = getTableView().getItems().get(getIndex());
                        if (auxPerson.getHardness().equals("3")) {
                            setStyle("-fx-background-color: rgba(223,32,39,0.65)");
                        }else if (auxPerson.getHardness().equals("2")) {
                            setStyle("-fx-background-color: rgba(15,86,223,0.65)");
                        }else if (auxPerson.getHardness().equals("1")) {
                            setStyle("-fx-background-color: rgba(77,202,0,0.65)");
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
            for (int i = 0; i < toShownotPass.size(); i++) {
                for (int j = 0; j < subjectforStudent.length; j++) {
                    if (subjectforStudent[j].equals(toShownotPass.get(i).getSubCode())){
                        System.out.println(subjectforStudent[j]);
                        Subject studentSubject = new Subject(toShownotPass.get(i).getSubCode(),toShownotPass.get(i).getSubName(),
                                toShownotPass.get(i).getCreDit(),toShownotPass.get(i).getHardness(),toShownotPass.get(i).getYear());
                        dataSubject.add(studentSubject);
                        toShownotPass.remove(i);
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
    }

    @FXML
    public void handleAddPass(ActionEvent event){ // เหลือแก้ให้ น้องลงวิชาพี่ไม่ได้ , มีปุ่ม ลบวิชาออกจาก Pass list ด้วย
        Subject addSubjecttoPass = notView.getSelectionModel().getSelectedItem();
        if (event.getSource().equals(addbt)){
            Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to add this subject to your passed subject list.",
                    ButtonType.YES,ButtonType.NO);
            newAlert.setHeaderText("");
            Optional optional = newAlert.showAndWait();
            if (dataNotPassSubject.contains(addSubjecttoPass) && !dataSubject.contains(addSubjecttoPass) && optional.get().equals(ButtonType.YES)){
                dataNotPassSubject.remove(addSubjecttoPass);
                dataSubject.add(addSubjecttoPass);
                DBControl open = DBConnect.openDB();
                String newSubject = "";
                for (Subject subject : dataSubject) {
                    newSubject += subject.getSubCode()+"#";
                }
                presentStudent.setRegistersubject(newSubject);
                open.updateStudentSubject(presentStudent);
                System.out.println("Complete");
            }
        }
    }
}
