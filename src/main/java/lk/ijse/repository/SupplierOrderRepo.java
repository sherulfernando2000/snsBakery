package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Ingredient;
import lk.ijse.model.SupplierOrder;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierOrderRepo {

    public static boolean save(SupplierOrder supplierOrder) throws SQLException {
        String sql = "INSERT INTO supplyOrder VALUES (?,?,?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,supplierOrder.getIngredientId());
        pstm.setObject(2,supplierOrder.getSupplierId());
        pstm.setObject(3,supplierOrder.getDate());
        pstm.setObject(4,supplierOrder.getQty());
        pstm.setObject(5,supplierOrder.getPrice());
        pstm.setObject(6,supplierOrder.getTotal());


        return pstm.executeUpdate()>0 ;
    }

    public static List<SupplierOrder> getAll() throws SQLException {
        String sql = "SELECT * FROM supplyOrder";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<SupplierOrder> orderList = new ArrayList<>();

        while (resultSet.next()) {
            String ingredientId = resultSet.getString(1);
            String supplierId = resultSet.getString(2);
            Date date = Date.valueOf(resultSet.getString(3));
            int qty = resultSet.getInt(4);
            double price = Double.valueOf(resultSet.getString(5));
            double total = Double.parseDouble(resultSet.getString(6));



            SupplierOrder supplierOrder = new SupplierOrder(ingredientId,supplierId,date,qty,price,total);
            orderList.add(supplierOrder);
        }
        return orderList;

    }

    public static boolean detele(String sId, String iId, Date date) throws SQLException {
        String sql = "DELETE FROM supplyOrder WHERE supplierId = ? AND ingredientId = ? AND  date = ? ;\n";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,sId);
        pstm.setObject(2,iId);
        pstm.setObject(3,date);

        return pstm.executeUpdate()>0;

    }
}
