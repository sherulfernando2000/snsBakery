package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {


    public static boolean save(Customer customer) throws SQLException {


        String sql = "INSERT INTO customer VALUES (?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,customer.getId());
        pstm.setObject(2,customer.getName());
        pstm.setObject(3,customer.getTel());
        pstm.setObject(4,customer.getAddress());

        return pstm.executeUpdate()>0 ;



    }

    public static boolean update(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET cName = ?, email = ?, phoneNo = ? WHERE customerId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,customer.getName());
        pstm.setObject(2,customer.getAddress());
        pstm.setObject(3,customer.getTel());
        pstm.setObject(4,customer.getId());

        return pstm.executeUpdate()>0;

    }


    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM customer WHERE customerId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate()>0;
    }

    public static Customer searchId(String id) throws SQLException {
        String sql = "SELECT * FROM customer WHERE customerId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,id);
        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String cus_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String tel = resultSet.getString(3);
            String address = resultSet.getString(4);

            Customer customer = new Customer(cus_id, name, tel, address);
            return customer;
        }
            return null;
    }

    public static Customer searchTel(String tel) throws SQLException {
        String sql = "SELECT * FROM customer WHERE phoneNo= ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,tel);
        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String cus_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String cus_tel = resultSet.getString(3);
            String address = resultSet.getString(4);

            Customer customer = new Customer(cus_id, name, cus_tel, address);
            return customer;
        }
        return null;
    }

    public static List<Customer> getAll() throws SQLException {
        String sql = "SELECT * FROM customer";;
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<Customer> cusList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String tel = resultSet.getString(3);
            String address = resultSet.getString(4);

            Customer customer = new Customer(id, name, tel, address);
            cusList.add(customer);
        }
        return cusList;

    }

    /*public static List<Customer> getNameEmail() throws SQLException {
        String sql = "SELECT * FROM customer";;
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<Customer> cusList = new ArrayList<>();

        while (resultSet.next()) {
            String name = resultSet.getString(2);
            String address = resultSet.getString(4);

            Customer customer = new Customer(id, name, tel, address);
            cusList.add(customer);
        }
        return cusList;
    }*/
}
