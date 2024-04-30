package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.OrderProductDetail;
import lk.ijse.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepo {

    public static boolean save(Product product) throws SQLException {
        String sql = "INSERT INTO product VALUES(?, ?, ?, ?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,product.getId());
        pstm.setObject(2,product.getName());
        pstm.setObject(3,product.getCategory());
        pstm.setObject(4,product.getQty());
        pstm.setObject(5,product.getPrice());

        return pstm.executeUpdate()>0;


    }

    public static boolean update(Product product) throws SQLException {
        String sql = "UPDATE product SET pName = ?, category = ?, qtyInSale = ? ,price = ? WHERE productId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,product.getName());
        pstm.setObject(2,product.getCategory());
        pstm.setObject(3,product.getQty());
        pstm.setObject(4,product.getPrice());
        pstm.setObject(5,product.getId());

        return pstm.executeUpdate()>0;


    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM product WHERE ProductId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;

    }

    public static Product searchById(String id) throws SQLException {
        String sql = "SELECT * FROM product WHERE productId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String product_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String category = resultSet.getString(3);
            int qty = resultSet.getInt(4);
            int price = resultSet.getInt(5);

            Product product = new Product(product_id,name,category,qty,price);

            return product;
        }

        return null;

    }

    public static List<Product> getAll() throws SQLException {
        String sql = "SELECT * FROM product";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Product> proList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String category = resultSet.getString(3);
            int qty = Integer.parseInt(resultSet.getString(4));
            int price = Integer.parseInt(resultSet.getString(5));

            Product product = new Product(id, name,category,qty,price);
            proList.add(product);
        }
        return proList;


    }

    public static List<String> getDescription() throws SQLException {
        String sql = "SELECT pName FROM product";
        ResultSet resultSet = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql)
                .executeQuery();

        List<String> codeList = new ArrayList<>();
        while (resultSet.next()) {
            codeList.add(resultSet.getString(1));
        }
        return codeList;
    }

    public static Product searchByDescription(String description) throws SQLException {
        String sql = "SELECT * FROM product WHERE pName = ?";
        PreparedStatement pstm = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,description);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return new Product(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4),resultSet.getInt(5));
        }

        return null;
    }

    public static boolean update(List<OrderProductDetail> odList) throws SQLException {
        for (OrderProductDetail od : odList) {
            boolean isUpdateQty = updateQty(od.getProductId(), od.getQty());
            if(!isUpdateQty) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(String productId, int qty) throws SQLException {
        String sql = "UPDATE product SET qtyInSale = qtyInSale - ? WHERE productId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, productId);

        return pstm.executeUpdate() > 0;
    }

    public static List<String> getName() throws SQLException {
        String sql = "SELECT pName FROM product";
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

    public static String getName(String productId) throws SQLException {
        String sql = "SELECT pName FROM product WHERE productId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,productId);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            String pName = resultSet.getString(1);

            return pName;
        }
        return null;

    }

    public static Product searchByName(String nameValue) throws SQLException {
        String sql = "SELECT * FROM product WHERE pName = ?";
        PreparedStatement pstm = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,nameValue);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return new Product(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4),resultSet.getInt(5));
        }

        return null;
    }
}
