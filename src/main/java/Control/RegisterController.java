package Control;

import Database.DBConnect;
import Database.DBControl;
import Model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.Optional;

public class RegisterController {
    @FXML
    private TextField idfield;

    @FXML
    private TextField fname;

    @FXML
    private TextField lname;

    @FXML
    private TextField yearfield;

    @FXML
    private Button clearbt;

    @FXML
    private Button submitbt;

    protected Stage stage;

    public void handleRegisterbutton(ActionEvent event){
        Button register = (Button) event.getSource();
        String id;
        String firstName;
        String lastName;
        String year;
        if (register.equals(submitbt)){
            DBConnect db = new DBConnect();
            Connection connection = db.openDatabase();
            DBControl dbControl = new DBControl(connection);
            id = idfield.getText();
            firstName = fname.getText();
            lastName = lname.getText();
            year = yearfield.getText();

            Alert ConfirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to submit this information?",
                    ButtonType.YES, ButtonType.NO);
            ConfirmationAlert.setHeaderText("");
            Optional optional = ConfirmationAlert.showAndWait();
            if (optional.get().equals(ButtonType.YES) && id.length() == 10) {
                System.out.println(dbControl.addStudent(new Student(id,firstName,lastName,year)));
                idfield.setStyle("-fx-border-color: green");
                System.out.println("Complete");
                stage.close();
            }else if (id.length() <10){
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Warning");
                warning.setHeaderText("");
                warning.setContentText("ID must contains 10 digits");
                idfield.setStyle("-fx-border-color: red");
                warning.showAndWait();
            }
            if (optional.get().equals(ButtonType.NO)){
                ConfirmationAlert.close();
            }
        }
    }
}
