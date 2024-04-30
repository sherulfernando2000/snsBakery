package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class WasteRepo {
    public static boolean save(List<Product> product) throws SQLException {
        PreparedStatement pstm = null;
        boolean isExecute = false;
        int lastId = getLastId();
        if (lastId != -1) {
            for (int i = 0; i < product.size(); i++) {
                String id = product.get(i).getId();
                int qty = product.get(i).getQty();
                LocalDate now = LocalDate.now();

                String sql = "INSERT INTO wasteManage VALUES(?, ?, ?, ?,?)";

                Connection connection = DbConnection.getInstance().getConnection();
                pstm = connection.prepareStatement(sql);
                pstm.setObject(1, lastId +i + 1);
                pstm.setObject(2, qty);
                pstm.setObject(3, "left overs");
                pstm.setObject(4, id);
                pstm.setObject(5, now);

                isExecute = pstm.executeUpdate() > 0;

            }

            return isExecute;
        }

       return false;

        
    }

    private static int getLastId() throws SQLException {
        /*String sql = "SELECT wastetId FROM wasteManage ORDER BY wastetId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            int lastId = resultSet.getInt(1);
            return lastId;
        }
        return 0;*/

        String sql = "SELECT wastetId FROM wasteManage ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        int count = 0;
        while (resultSet.next()) {
            count++;

        }
        return count;
    }
}
