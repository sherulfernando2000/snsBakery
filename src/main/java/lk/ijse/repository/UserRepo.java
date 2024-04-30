package lk.ijse.repository;

import javafx.scene.control.Alert;
import lk.ijse.db.DbConnection;
import lk.ijse.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepo {
        public static boolean saveUser(User user)  throws SQLException {
            String sql = "INSERT INTO user VALUES(?, ?, ?, ?)";

            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setObject(1,user.getUserName());
            pstm.setObject(2,user.getPassword());
            pstm.setObject(3,user.getPhoneNo());
            pstm.setObject(4,user.getRole());

            return pstm.executeUpdate()>0;


        }

    public static boolean checkCredential(String userName, String pw) throws SQLException {
        String sql = "SELECT userName, password FROM user WHERE userName = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userName);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String dbPw = resultSet.getString("password");

            if(pw.equals(dbPw)) {
                return true;
               // navigateToTheDashboard();
            } else {

                new Alert(Alert.AlertType.ERROR, "sorry! password is incorrect!").show();
                return false;
            }

        } else {
            new Alert(Alert.AlertType.INFORMATION, "sorry! user name can't be find!").show();
            return false;
        }
    }
}
