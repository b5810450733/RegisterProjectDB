package Control;

import Database.DBConnect;
import Database.DBControl;
import Launcher.Main;
import Model.Student;
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
    protected Button allcoursebt,exitbtn;


    @FXML
    private AnchorPane mainPane;
    private static double xOffset = 0;
    private static double yOffset = 0;

    protected Student login;


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

    public static void makeStageDrageable(AnchorPane pane) {
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
                Main.stage.setX(event.getScreenX() - xOffset);
                Main.stage.setY(event.getScreenY() - yOffset);
                Main.stage.setOpacity(0.7f);
            }
        });
        pane.setOnDragDone((e) -> {
            Main.stage.setOpacity(1.0f);
        });
        pane.setOnMouseReleased((e) -> {
            Main.stage.setOpacity(1.0f);
        });
    }


    @FXML
    public void handleRegister(ActionEvent event){
        if (event.getSource().equals(regisbt)){
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/RegisterPage.fxml")) ;
            try {
                stage.setScene(new Scene(loader.load(),670,440));
                stage.setTitle("Register Page");
                stage.setResizable(false);
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
                    stage.setX(40);
                    stage.setY(70);
                    stage.setScene(new Scene(loader.load(),1000,650));
                    stage.setTitle("Your Information");
                    stage.setResizable(false);
                    InformationController controller = (InformationController) loader.getController();
                    controller.setPresentStudent(login);
                    stage.show();
                    openGuideStage();
                } catch (IOException e1){
                    e1.printStackTrace();
                }
            }else {
                Alert newAlert = new Alert(Alert.AlertType.WARNING);
                idlogin.setStyle("-fx-border-color: red");
                newAlert.setTitle("Warning, Please check the Information.");
                newAlert.setContentText("This ID not exist, Please check your ID or Register if you never registered.");
                newAlert.setHeaderText("");
                newAlert.showAndWait();
            }
        }
    }

    @FXML
    public void handleAll(ActionEvent event){
        if (event.getSource().equals(allcoursebt)){
            openAllCourse();
        }
    }

    @FXML
    public static void openAllCourse(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/View/Allcourse.fxml")) ;
        try {
            stage.setScene(new Scene(loader.load(),840,540));
            stage.setTitle("All Course Schedule");
            stage.setResizable(false);
            AllCourseController controller = (AllCourseController) loader.getController();
            stage.show();
        } catch (IOException e1){
            e1.printStackTrace();
        }
    }

    @FXML
    public void openGuideStage(){
        Stage stage2 = new Stage();
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/View/Course.fxml")) ;
        try {
            stage2.setX(1041);
            stage2.setY(70);
            stage2.setScene(new Scene(loader2.load(),370,360));
            stage2.setTitle("Should Register in this term");
            stage2.setResizable(false);
            CourseController controller = (CourseController) loader2.getController();
            controller.setNowLogin(login);
            stage2.show();
        } catch (IOException e2){
            e2.printStackTrace();
        }
    }

}
