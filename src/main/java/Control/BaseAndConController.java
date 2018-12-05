package Control;

import Model.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BaseAndConController {

    @FXML
    protected ListView<Subject> baseList,conList;

    @FXML
    protected static AnchorPane BCAnchor;


    protected static ObservableList<Subject> baseSub = FXCollections.observableArrayList();
    protected static ObservableList<Subject> contSub = FXCollections.observableArrayList();

    public void setShowBase(){
        baseList.setItems(baseSub);
        conList.setItems(contSub);
    }
}
