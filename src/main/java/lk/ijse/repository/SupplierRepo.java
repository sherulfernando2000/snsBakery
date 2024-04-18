package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Supplier;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SupplierRepo {

    public static boolean save(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO supplier VALUES (?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,supplier.getId());
        pstm.setObject(2,supplier.getName());
        pstm.setObject(3,supplier.getAddress());
        pstm.setObject(4,supplier.getTel());

        return pstm.executeUpdate()>0 ;

    }
}
