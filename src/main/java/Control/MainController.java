package Control;

import Database.DBConnect;
import Database.DBControl;
import Model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

public class MainController{
    @FXML
    private Button loginbt;

    @FXML
    private TextField idlogin;

    @FXML
    private Button regisbt;

    @FXML
    private Button allcoursebt;


    @FXML
    public void handleRegister(ActionEvent event){
        if (event.getSource().equals(regisbt)){
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/RegisterPage.fxml")) ;
            try {
                stage.setScene(new Scene(loader.load(),670,440));
                stage.setTitle("Register Page");
                RegisterController controller = (RegisterController) loader.getController();
                controller.stage = stage;
                stage.show();
            } catch (IOException e1){
                e1.printStackTrace();
            }
        }
    }

    @FXML
    public void handlelogin(ActionEvent event){
        if (event.getSource().equals(loginbt)){
            DBControl opendb = DBConnect.openDB();
            ArrayList<Student> studentList = opendb.readAccount();
            String inID = idlogin.getText();
            Boolean found = false;
            for (Student student : studentList) {
                if (inID.equals(student.getStudentID())){
                    found = true;
                }
            }
            if (found == true){
                idlogin.setStyle("-fx-border-color: null");
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/InformationPage.fxml")) ;
                try {
                    stage.setScene(new Scene(loader.load(),840,540));
                    stage.setTitle("Your Information");
                    InformationController controller = (InformationController) loader.getController();
                    stage.show();
                } catch (IOException e1){
                    e1.printStackTrace();
                }
            }else {
                Alert newAlert = new Alert(Alert.AlertType.WARNING);
                idlogin.setStyle("-fx-border-color: red");
                newAlert.setTitle("Warning, please check the Information.");
                newAlert.setContentText("This ID not exist, Please check your ID or Register if you never registered.");
                newAlert.setHeaderText("");
                newAlert.showAndWait();
            }
        }
    }

    @FXML
    public void handleAll(ActionEvent event){
        if (event.getSource().equals(allcoursebt)){
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Allcourse.fxml")) ;
            try {
                stage.setScene(new Scene(loader.load(),840,540));
                stage.setTitle("All Course Schedule");
                AllCourseController controller = (AllCourseController) loader.getController();
                stage.show();
            } catch (IOException e1){
                e1.printStackTrace();
            }
        }
    }

}
