package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;
import lk.ijse.model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRepo {
    public static boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee VALUES (?,?,?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,employee.getEmployeeId());
        pstm.setObject(2,employee.getEmployeeName());
        pstm.setObject(3,employee.getEmployeeSalary());
        pstm.setObject(4,employee.getEmployeePosition());
        pstm.setObject(5,employee.getEmployeeAddress());
        pstm.setObject(6,employee.getEmployeeTel());

        return pstm.executeUpdate()>0 ;
        }

    }

