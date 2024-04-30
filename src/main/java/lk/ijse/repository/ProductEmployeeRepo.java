package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.ProductEmployee;
import lk.ijse.model.SupplierOrder;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductEmployeeRepo {
    public static boolean save(ProductEmployee productEmployee) throws SQLException {
        String sql = "INSERT INTO productemployeedetail VALUES (?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,productEmployee.getEmployeeId());
        pstm.setObject(2,productEmployee.getProductId());
        pstm.setObject(3,productEmployee.getAssignmentType());


        return pstm.executeUpdate()>0 ;
    }

    public static List<ProductEmployee> getAll() throws SQLException {
        String sql = "SELECT * FROM productemployeedetail";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<ProductEmployee> orderList = new ArrayList<>();

        while (resultSet.next()) {
            String employeeID = resultSet.getString(1);
            String productId = resultSet.getString(2);
            String assignmentType = resultSet.getString(3);



            ProductEmployee productEmployee = new ProductEmployee(employeeID,productId,assignmentType);
            orderList.add(productEmployee);
        }
        return orderList;
    }

    public static boolean detele(String eId, String pId, String assignmentType) throws SQLException {
        String sql = "DELETE FROM productemployeedetail WHERE employeeId = ? AND productId = ? AND  assignment_type = ? ;\n";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,eId);
        pstm.setObject(2,pId);
        pstm.setObject(3,assignmentType);

        return pstm.executeUpdate()>0;
    }
}
