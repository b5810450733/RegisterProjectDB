package Database;

import java.sql.*;

public class DBConnect {
    Connection connect = null;
    static final private String dbURL = "jdbc:sqlite:registerdb.db";


    public Connection openDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            if(connect == null) {
                connect = DriverManager.getConnection(dbURL);
            }
            if (connect != null) {
                System.out.println("Connect to database!!!");
                DatabaseMetaData dm = (DatabaseMetaData) connect.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Product name: " + dm.getDatabaseProductName());
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this.connect;
    }

    public void closeDatabase() {
        try {
            this.connect.close();
            connect = null;
            System.out.println("Close DBConnector!!!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeAllConfigure(ResultSet resultSet, Statement stmt, Connection connection){
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
