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
    protected static ObservableList<Subject> notPassAndCanRegister = FXCollections.observableArrayList();

    @FXML
    private ListView<Subject> list;

    public void setNowLogin(Student nowLogin) {
        this.nowLogin = nowLogin;
        readUser();
        list.setItems(notPassAndCanRegister);
    }

    public static void updateList(Subject subject,int i){
        boolean can = false;
        ArrayList<Subject> backup = new ArrayList<>();
        for (Subject subject1 : notPassAndCanRegister) {
            if (i == 1){
                if (!subject1.getSubCode().equals(subject.getSubCode())) {
                    backup.add(subject1);
                }
            }
            if (i == 0){
                if (!subject1.getSubCode().equals(subject.getSubCode())){
                    can = true;
                }
            }
        }
        if (i == 0 && can) {
            notPassAndCanRegister.add(subject);
        }
        if (i == 1){
            notPassAndCanRegister.clear();
            notPassAndCanRegister.addAll(backup);
        }

    }

    public void readUser(){
        DBControl dbControl = DBConnect.openDB();
        ArrayList<Subject> allSubject = dbControl.readSubject();
        ArrayList<Subject> checkPass = new ArrayList<>();
        double Useryear = Double.parseDouble(nowLogin.getYear());
        checkPass.addAll(allSubject);
        String[] userRegister = nowLogin.getRegistersubject().split("#");
        try {
            if (userRegister.length > 0){
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

            }else{
                System.out.println(notPassAndCanRegister.size());
            }

        }catch (NullPointerException | IndexOutOfBoundsException e){
            System.out.println("NUll Pointer");
        }

    }
}
