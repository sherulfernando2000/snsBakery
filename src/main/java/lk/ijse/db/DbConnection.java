package lk.ijse.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection dbConnection;

    private Connection connection;

    private DbConnection() throws SQLException {
         connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/bakery",
                "root",
                "Ijse@123"
        );

    }

    public static DbConnection getInstance() throws SQLException {
        if ( dbConnection == null) {
                dbConnection = new DbConnection();
        }
        return dbConnection;
    }

    public Connection getConnection(){
        return connection;

    }

    /*public static void main(String[] args) throws SQLException {
        System.out.println(getInstance().getConnection());
    }*/
}
