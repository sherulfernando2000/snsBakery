package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.OrderProductDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderProductDetailRepo {


    public static boolean save(List<OrderProductDetail> odList) throws SQLException {
        for (OrderProductDetail od : odList) {
            boolean isSaved = save(od);
            if(!isSaved) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(OrderProductDetail od) throws SQLException {
        String sql = "INSERT INTO orderProductDetail VALUES(?, ?, ?, ?,?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, od.getOrderId());
        pstm.setString(2, od.getProductId());
        pstm.setDouble(3, od.getUnitPrice());
        pstm.setInt(4, od.getQty());
        pstm.setDouble(5, od.getTotal());

        return pstm.executeUpdate() > 0;
    }
}
