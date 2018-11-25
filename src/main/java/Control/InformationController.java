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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.ArrayList;

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
                        System.out.println(studentSubject);
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
    public void handleAddPass(ActionEvent event){


    }
}
