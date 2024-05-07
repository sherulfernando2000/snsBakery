package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Payment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentRepo {
    public static String getCurrentPaymentId() throws SQLException {
        String sql = "SELECT CONCAT('P-', MAX(CAST(SUBSTRING_INDEX(paymentId, '-', -1) AS UNSIGNED))) AS max_payment_id FROM payment;\n";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String paymentId = resultSet.getString(1);
            return paymentId;
        }
        return null;
    }

    public static boolean save(Payment payment) throws SQLException {
        String sql = "INSERT INTO payment VALUES(?, ?, ?, ?,?,?,?,?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1,payment.getPaymentId() );
        pstm.setString(2, payment.getPaymentMethod());
        pstm.setString(3, payment.getDate());
        pstm.setDouble(4, payment.getDiscountAmount());
        pstm.setDouble(5, payment.getTotalAmount());
        pstm.setString(6,payment.getOrderId());
        pstm.setString(7,payment.getDiscountType());
        pstm.setDouble(8,payment.getDiscountPrecentage());

        return pstm.executeUpdate() > 0;    //false ->  |

    }
}
