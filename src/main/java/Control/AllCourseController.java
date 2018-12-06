package Control;

import Database.DBConnect;
import Database.DBControl;
import Model.Student;
import Model.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AllCourseController {

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
    protected TableColumn<Subject,String> statuscolum;

    protected ObservableList<Subject> passedSubject = FXCollections.observableArrayList();

    @FXML
    protected AnchorPane allMainpane;

    protected Student loginStudent;

    protected Stage thisStage ;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    protected Button exitbtnFromAll;

    @FXML
    protected Button oneone,onetwo,twoone,twotwo,treeone,treetwo,fourone,fourtwo,seeall;

    protected ObservableList<Subject> subjectList = FXCollections.observableArrayList();
    protected ObservableList<Subject> showSubjectOfyear = FXCollections.observableArrayList();


    public void initialize(){
        exitbtnFromAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) exitbtnFromAll.getScene().getWindow();
                if (loginStudent != null){
                    loginStudent = null;
                }
                stage.close();
            }
        });
        start();
        makeStageDrageable();
    }

    public void setLoginStudent(Student loginStudent) {
        this.loginStudent = loginStudent;
    }

    public void setPassedSubject(ObservableList<Subject> passedSubject) {
        this.passedSubject = passedSubject;
        for (Subject subject : this.passedSubject) {
            System.out.println(subject.getIsPass()+"jkjk");
        }
        if (loginStudent != null){
            showAllSubject("all");
        }
    }



    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void start(){
        DBControl dbControl = DBConnect.openDB();
        subjectList.addAll(dbControl.readSubject());
        showAllSubject("all");
        setColor();
    }


    public void searchYear(String year){
        showSubjectOfyear.clear();
        for (Subject subject : subjectList) {
            if (subject.getYear().equals(year)){
                showSubjectOfyear.add(subject);
                for (Subject subject1 : passedSubject) {
                    if (subject1.getSubCode().equals(subject.getSubCode())){
                        subject.setIsPass(subject1.getIsPass());
                    }
                }
            }
        }
        showAllSubject("each");
    }

    public void handleButton(ActionEvent event){
        Button someButton = (Button) event.getSource();
        String clickBUtton = someButton.getId();
        switch (clickBUtton){
            case "oneone":
                searchYear("1.1");
                break;
            case "onetwo":
                searchYear("1.2");
                break;
            case "twoone":
                searchYear("2.1");
                break;
            case "twotwo":
                searchYear("2.2");
                break;
            case "treeone":
                searchYear("3.1");
                break;
            case "treetwo":
                searchYear("3.2");
                break;
            case "fourone":
                searchYear("4.1");
                break;
            case "fourtwo":
                searchYear("4.2");
                break;
            case "seeall":
                showAllSubject("all");
                break;

            default: showAllSubject("all");
                break;
        }
    }

    @FXML
    public void showAllSubject(String separate){
        idcolum.setCellValueFactory(cellData->cellData.getValue().subCodeProperty());
        namecolum.setCellValueFactory(cellData->cellData.getValue().subNameProperty());
        creditcolum.setCellValueFactory(cellData->cellData.getValue().creDitProperty());
        yearcolum.setCellValueFactory(cellData->cellData.getValue().yearProperty());
        basecolum.setCellValueFactory(cellData->cellData.getValue().baseSubjectProperty());
        contcolum.setCellValueFactory(cellData->cellData.getValue().contSubjectProperty());
        difficolum.setCellValueFactory(cellData->cellData.getValue().hardnessProperty());
        statuscolum.setCellValueFactory(cellData->cellData.getValue().isPassProperty());
        if (separate.equals("all")){
            showSubjectOfyear.clear();
            for (Subject subject : subjectList) {
                for (Subject subject1 : passedSubject) {
                    if (subject1.getSubCode().equals(subject.getSubCode())){
                        subject.setIsPass(subject1.getIsPass());
                    }
                }
            }
            allView.setItems(subjectList);
        }if (separate.equals("each")){
            allView.setItems(showSubjectOfyear);
        }
        setColor();
    }


    @FXML
    public void setColor(){
        difficolum.setCellFactory(column -> {
            return new TableCell<Subject, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty); //This is mandatory
                    if (item == null || empty) { //If the cell is empty
                        setText(null);
                        setStyle("");
                    } else { //If the cell is not empty\
                        setText(item); //Put the String data in the cell
                        Subject subject = getTableView().getItems().get(getIndex());
                        if (subject.getHardness().equals("3")) {
                            setText("Hard");
                            setStyle("-fx-font-weight: bold;" + "-fx-background-color: rgba(223,0,11,0.46)");
                        }else if (subject.getHardness().equals("2")) {
                            setText("Medium");
                            setStyle("-fx-font-weight: bold;" + "-fx-background-color: rgba(15,86,223,0.47)");
                        }else if (subject.getHardness().equals("1")) {
                            setText("Easy");
                            setStyle("-fx-font-weight: bold;" + "-fx-background-color: rgba(0,202,24,0.46)");
                        }else{
                            setStyle("-fx-background-color: white");
                            if (getTableView().getSelectionModel().getSelectedItems().contains(subject)) {
                                setTextFill(Color.WHITE);
                            } else
                                setTextFill(Color.BLACK);
                        } } }};
        });
        setStatusColor();
    }


    @FXML
    public void setStatusColor(){
        statuscolum.setCellFactory(column -> {
            return new TableCell<Subject, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty); //This is mandatory
                    if (item == null || empty) { //If the cell is empty
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                        Subject subject = getTableView().getItems().get(getIndex());
                        if (subject.getIsPass().equals("Passed")){
                            setStyle("-fx-background-color: rgba(0,202,24,0.5)");
                        }
                    }
                }
            };
        });

    }

    private void makeStageDrageable() {
        allMainpane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        allMainpane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                thisStage.setX(event.getScreenX() - xOffset);
                thisStage.setY(event.getScreenY() - yOffset);
                thisStage.setOpacity(0.7f);
            }
        });
        allMainpane.setOnDragDone((e) -> {
            thisStage.setOpacity(1.0f);
        });
        allMainpane.setOnMouseReleased((e) -> {
            thisStage.setOpacity(1.0f);
        });
    }



}
