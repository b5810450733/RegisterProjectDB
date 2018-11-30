package Control;

import Database.DBConnect;
import Database.DBControl;
import Model.Student;
import Model.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class CourseController {
    protected Student nowLogin;
    protected ObservableList<Subject> notPassAndCanRegister = FXCollections.observableArrayList();

    @FXML
    private ListView<Subject> list;

    public void setNowLogin(Student nowLogin) {
        this.nowLogin = nowLogin;
        readUser();
        list.setItems(notPassAndCanRegister);
    }

    public void readUser(){
        DBControl dbControl = DBConnect.openDB();
        ArrayList<Subject> allSubject = dbControl.readSubject();
        ArrayList<Subject> checkPass = new ArrayList<>();
        double Useryear = Double.parseDouble(nowLogin.getYear());
        checkPass.addAll(allSubject);
        String[] userRegister = nowLogin.getRegistersubject().split("#");
        try {
            for (Subject subject : allSubject) {
                for (String userRegis : userRegister) {
                    if (subject.getSubCode().equals(userRegis)){
                        checkPass.remove(subject);
                    }
                }
            }
            for (Subject can : checkPass) {
                if (Double.parseDouble(can.getYear()) <= Useryear){
                    notPassAndCanRegister.add(can);
                    System.out.println(can);
                }
            }
        }catch (NullPointerException | IndexOutOfBoundsException e){
            e.getStackTrace();
        }

    }
}
