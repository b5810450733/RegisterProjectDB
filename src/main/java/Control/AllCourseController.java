package Control;

import Database.DBConnect;
import Database.DBControl;
import Model.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class AllCourseController {

    ////////////////////////////////////////

    @FXML
    protected TableView<Subject> allView;

    @FXML
    protected TableColumn<Subject,String> idcolum;

    @FXML
    protected TableColumn<Subject,String> namecolum;

    @FXML
    protected TableColumn<Subject,String> creditcolum;

    @FXML
    protected TableColumn<Subject,String> yearcolum;

    @FXML
    protected TableColumn<Subject,String> basecolum;

    @FXML
    protected TableColumn<Subject,String> contcolum;

    @FXML
    protected TableColumn<Subject,String> difficolum;


    @FXML
    protected Button exitbtnFromAll;

    protected ObservableList<Subject> subjectList = FXCollections.observableArrayList();

    public void initialize(){
        exitbtnFromAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) exitbtnFromAll.getScene().getWindow();
                stage.close();
            }
        });
        start();
    }

    public void start(){
        DBControl dbControl = DBConnect.openDB();
        subjectList.addAll(dbControl.readSubject());
        showAllSubject();
    }

    @FXML
    public void showAllSubject(){
        idcolum.setCellValueFactory(cellData->cellData.getValue().subCodeProperty());
        namecolum.setCellValueFactory(cellData->cellData.getValue().subNameProperty());
        creditcolum.setCellValueFactory(cellData->cellData.getValue().creDitProperty());
        yearcolum.setCellValueFactory(cellData->cellData.getValue().yearProperty());
        basecolum.setCellValueFactory(cellData->cellData.getValue().baseSubjectProperty());
        contcolum.setCellValueFactory(cellData->cellData.getValue().contSubjectProperty());
        difficolum.setCellValueFactory(cellData->cellData.getValue().hardnessProperty());
        allView.setItems(subjectList);
    }


}
