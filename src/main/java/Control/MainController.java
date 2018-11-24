package Control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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
    private Label alertlabel;

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

}
