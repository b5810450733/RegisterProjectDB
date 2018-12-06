package Control;

import Database.DBConnect;
import Database.DBControl;
import Model.Student;
import Model.Subject;
import animatefx.animation.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

public class MainController{
    @FXML
    private Button loginbt;

    @FXML
    private TextField idlogin;

    @FXML
    private Button regisbt;

    @FXML
    protected Button allcoursebt;

    @FXML
    protected Button exitbtn;


    @FXML
    private AnchorPane mainPane;
    private double xOffset = 0;
    private double yOffset = 0;

    protected Student login;

    protected InformationController informationController;




    public void initialize(){
        makeStageDrageable(mainPane);
        exitbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) exitbtn.getScene().getWindow();
                stage.close();
            }
        });
    }

    private void makeStageDrageable(AnchorPane pane) {
        pane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Launcher.Main.stage.setX(event.getScreenX() - xOffset);
                Launcher.Main.stage.setY(event.getScreenY() - yOffset);
                Launcher.Main.stage.setOpacity(0.7f);
            }
        });
        pane.setOnDragDone((e) -> {
            Launcher.Main.stage.setOpacity(1.0f);
        });
        pane.setOnMouseReleased((e) -> {
            Launcher.Main.stage.setOpacity(1.0f);
        });
    }


    @FXML
    public void handleRegister(ActionEvent event){
        if (event.getSource().equals(regisbt)){
            openRegister(null);
        }
    }

    public static void openRegister(Student student){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/View/RegisterPage.fxml")) ;
        try {
            stage.setScene(new Scene(loader.load(),670,440));
            stage.setTitle("Register Page");
            stage.setResizable(false);
            RegisterController controller = (RegisterController) loader.getController();
            stage.initStyle(StageStyle.UNDECORATED);
            if (student != null){
                controller.setNowLogin(student);
            }
            controller.stage = stage;
            stage.show();
            new BounceInDown(stage.getScene().getRoot()).play();
        } catch (IOException e1){
            e1.printStackTrace();
        }
    }

    @FXML
    public void handlelogin(ActionEvent event){
        if (event.getSource().equals(loginbt)){
            DBControl opendb = DBConnect.openDB();
            ArrayList<Student> studentList = opendb.readStudent();
            String inID = idlogin.getText();
            Boolean found = false;
            for (Student student : studentList) {
                if (inID.equals(student.getStudentID())){
                    found = true;
                    this.login = student;
                    break;
                }
            }
            if (found == true){
                idlogin.setStyle("-fx-border-color: null");
                Stage stage = (Stage) mainPane.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/InformationPage.fxml")) ;
                try {
                    stage.setX(75);
                    stage.setY(70);
                    stage.setScene(new Scene(loader.load(),1001,650));
                    stage.setTitle("Your Information");
                    stage.setResizable(false);
                    InformationController controller = (InformationController) loader.getController();
                    controller.setPresentStudent(login);
                    informationController = controller;
                    stage.show();
                    openGuideStage();
                    openBaseSub();
                } catch (IOException e1){
                    e1.printStackTrace();
                }
            }else {
                new Shake(idlogin.getScene().getRoot()).play();
                Alert newAlert = new Alert(Alert.AlertType.WARNING);
                idlogin.setStyle("-fx-border-color: red");
                newAlert.setTitle("Warning, Please check the Information.");
                newAlert.setContentText("This ID not exist, Please check your ID or Register if you never registered.");
                newAlert.setHeaderText("");
                newAlert.showAndWait();
                idlogin.clear();
            }
        }
    }

    @FXML
    public void handleAll(ActionEvent event){
        if (event.getSource().equals(allcoursebt)){
            openAllCourse(null,null);
        }
    }

    @FXML
    public static void openAllCourse(Student student, ObservableList<Subject> pass ){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/View/Allcourse.fxml")) ;
        try {
            stage.setScene(new Scene(loader.load(),1116,484));
            stage.setTitle("All Course Schedule");
            stage.setResizable(true);
            stage.initStyle(StageStyle.UNDECORATED);
            AllCourseController controller = (AllCourseController) loader.getController();
            if (student != null){
                controller.setLoginStudent(student);
                controller.setPassedSubject(pass);
                System.out.println(student);
            }
            controller.setThisStage(stage);
            stage.show();
            new FadeInDown(stage.getScene().getRoot()).play();
        } catch (IOException e1){
            e1.printStackTrace();
        }
    }

    @FXML
    public void openGuideStage(){
        Stage stage2 = new Stage();
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/View/Course.fxml")) ;
        try {
            stage2.setX(1076);
            stage2.setY(71);
            stage2.setScene(new Scene(loader2.load(),331,360));
            stage2.setTitle("Should Register in this term");
            stage2.setResizable(false);
            CourseController controller = (CourseController) loader2.getController();
            stage2.initStyle(StageStyle.UNDECORATED);
            controller.setNowLogin(login);
            informationController.courseController = stage2;
            stage2.show();
            new FadeInLeft(stage2.getScene().getRoot()).play();
        } catch (IOException e2){
            e2.printStackTrace();
        }
    }

    @FXML
    public void openBaseSub(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/BaseAndCon.fxml")) ;
        try {
            stage.setX(1076);
            stage.setY(431);
            stage.setScene(new Scene(loader.load(),331,289));
            stage.setResizable(false);
            BaseAndConController controller = (BaseAndConController) loader.getController();
            stage.initStyle(StageStyle.UNDECORATED);
            controller.setShowBase();
            informationController.baseAndConController = stage;
            stage.show();
            new FadeInLeft(stage.getScene().getRoot()).play();
        } catch (IOException e2){
            e2.printStackTrace();
        }

    }

}
