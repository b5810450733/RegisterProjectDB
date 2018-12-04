package Database;

import Model.Student;
import Model.Subject;

import java.sql.*;
import java.util.ArrayList;

public class DBControl {
    private Connection connection;
    private ResultSet resultSet = null;
    private Statement stmt = null;

    public DBControl(Connection connection) {
        this.connection = connection;
    }

    public boolean addStudent(Student student) {
        boolean addResult = false;
        try {
            Student newStudent = student;
            String sqlText = "INSERT INTO Student VALUES (?,?,?,?,?,?,?)";
            PreparedStatement prepare = connection.prepareStatement(sqlText);
            prepare.setString(1, newStudent.getStudentID());
            prepare.setString(2, newStudent.getFirstName());
            prepare.setString(3, newStudent.getLastName());
            prepare.setString(4, newStudent.getYear());
            prepare.setString(6,newStudent.getRegistersubject());

            if (prepare.executeUpdate() == 1) {
                addResult = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeAllConfigure(resultSet, stmt, connection);
        }
        return addResult;
    }


    public ArrayList<Student> readStudent(){ // Review  //
        ArrayList<Student> incomeArray = new ArrayList<>();
        Student inFlow = null;
        try {
            stmt = connection.createStatement();
            String query = "SELECT * FROM Student";
            resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                inFlow = new Student(resultSet.getString(1)
                        , resultSet.getString(2)
                        , resultSet.getString(3),resultSet.getString(4));
                inFlow.setRegistersubject(resultSet.getString(6));
                inFlow.setPath(resultSet.getString(7));
                incomeArray.add(inFlow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeAllConfigure(resultSet, stmt, connection);
        }
        return incomeArray;
    }

    /////////////////////////////////////////////////////////////////////

    public ArrayList<Subject> readSubject() {
        ArrayList<Subject> subjectsList = new ArrayList<>();
        Subject inFlow = null;
        try {
            stmt = connection.createStatement();
            String query = "SELECT * FROM Subject";
            resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                inFlow = new Subject(resultSet.getString(1)
                        , resultSet.getString(2)
                        , resultSet.getString(3),resultSet.getString(4),resultSet.getString(5));
                inFlow.setBaseSubject(resultSet.getString(6));
                inFlow.setContSubject(resultSet.getString(7));
                subjectsList.add(inFlow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeAllConfigure(resultSet, stmt, connection);
        }
        return subjectsList;
    }

    public boolean addSubject(Subject subject) {
        boolean addResult = false;
        try {
            Subject newSubject = subject;
            String sqlText = "INSERT INTO Subject VALUES (?,?,?,?,?)";
            PreparedStatement prepare = connection.prepareStatement(sqlText);
            prepare.setString(1, newSubject.getSubCode());
            prepare.setString(2, newSubject.getSubName());
            prepare.setString(3, newSubject.getCreDit());
            prepare.setString(4, newSubject.getHardness());
            prepare.setString(5,newSubject.getYear());

            if (prepare.executeUpdate() == 1) {
                addResult = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeAllConfigure(resultSet, stmt, connection);
        }
        return addResult;
    }

        public boolean updateStudentSubject(Student subject){
        boolean updateResult = false;
        try{
            String sqlText = "UPDATE Student SET credit=?,allsubject=? WHERE studentID=?";
            PreparedStatement prepare = connection.prepareStatement(sqlText);
            prepare.setString(1,subject.getCredit());
            prepare.setString(2,subject.getRegistersubject());
            prepare.setString(3,subject.getStudentID());

            if (prepare.executeUpdate() == 1){
                updateResult = true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBConnect.closeAllConfigure(resultSet,stmt,connection);
        }
        return updateResult;
    }
}

