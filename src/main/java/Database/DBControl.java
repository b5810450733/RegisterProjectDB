package Database;

import Model.Student;

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
            String sqlText = "INSERT INTO Student VALUES (?,?,?,?,?)";
            PreparedStatement prepare = connection.prepareStatement(sqlText);
            prepare.setString(1, newStudent.getStudentID());
            prepare.setString(2, newStudent.getFirstName());
            prepare.setString(3, newStudent.getLastName());
            prepare.setString(4, newStudent.getLastName());

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


    public ArrayList<Student> readAccount() { // Review User //
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
                incomeArray.add(inFlow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeAllConfigure(resultSet, stmt, connection);
        }
        return incomeArray;
    }
}
//
//    public boolean updateList(Income list){
//        boolean updateResult = false;
//        try{
//            String sqlText = "UPDATE Income SET type=?,info=?,value=? WHERE id=?";
//            PreparedStatement prepare = connection.prepareStatement(sqlText);
//            prepare.setString(1,list.getType());
//            prepare.setString(2,list.getInformation());
//            prepare.setDouble(3,Double.parseDouble(list.getAmount()));
//            prepare.setInt(4,list.getID());
//
//            if (prepare.executeUpdate() == 1){
//                updateResult = true;
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }finally {
//            SQLConnect.closeAllConfigure(resultSet,stmt,connection);
//        }
//        return updateResult;
//    }
//
//    public boolean deleteList(Income list){
//        boolean updateResult = false;
//        try{
//            String sqlText = "DELETE FROM Income WHERE id=?";
//            PreparedStatement prepare = connection.prepareStatement(sqlText);
//            prepare.setInt(1,list.getID());
//
//            if (prepare.executeUpdate() == 1){
//                updateResult = true;
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }finally {
//            SQLConnect.closeAllConfigure(resultSet,stmt,connection);
//        }
//        return updateResult;
//    }
//
//    public int getLastID(){
//        int lastID = 0;
//        try{
//            stmt = connection.createStatement();
//            String query = "SELECT * FROM Income ORDER BY id DESC LIMIT 1;";
//            resultSet = stmt.executeQuery(query);
//            while (resultSet.next()){
//                lastID = resultSet.getInt(1);
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }finally {
//            SQLConnect.closeAllConfigure(resultSet,stmt,connection);
//        }
//        return lastID;
//
//    }
//
//}
