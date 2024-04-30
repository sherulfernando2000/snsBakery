package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;
import lk.ijse.model.Product;
import lk.ijse.model.Supplier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static boolean update(Supplier supplier) throws SQLException {
        String sql = "UPDATE supplier SET sName = ?, address = ?, contact = ? WHERE supplierId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,supplier.getName());
        pstm.setObject(2,supplier.getAddress());
        pstm.setObject(3,supplier.getTel());
        pstm.setObject(4,supplier.getId());

        return pstm.executeUpdate()>0 ;


    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM supplier WHERE supplierId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate()>0;

    }

    public static Supplier searchId(String id) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE supplierId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,id);
        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String sup_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);


            Supplier supplier = new Supplier(sup_id,name,tel,address);
            return supplier;
        }
        return null;
    }

    public static List<Supplier> getAll() throws SQLException {
        String sql = "SELECT * FROM supplier";;
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<Supplier> supList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);

            Supplier supplier = new Supplier(id,name,tel,address);
            supList.add(supplier);
        }
        return supList;

    }

    public static List<String> getName() throws SQLException {
        String sql = "SELECT sName FROM supplier";
        ResultSet resultSet = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql)
                .executeQuery();

        List<String> nameList = new ArrayList<>();
        while (resultSet.next()) {
            nameList.add(resultSet.getString(1));
        }
        return nameList;
    }

    public static Supplier searchByName(String nameValue) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE sName = ?";
        PreparedStatement pstm = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,nameValue);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return new Supplier(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
        }

        return null;
    }

    public static String getName(String supplierId) throws SQLException {
        String sql = "SELECT sName FROM supplier WHERE supplierId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, supplierId);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String sName = resultSet.getString(1);

            return sName;
        }
        return null;
    }


}
