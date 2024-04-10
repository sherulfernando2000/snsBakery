package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

}
