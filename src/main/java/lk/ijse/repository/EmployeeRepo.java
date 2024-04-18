package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;
import lk.ijse.model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM employee WHERE employeeId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate()>0;


    }

    /*
     * employeeId varchar(15) PRIMARY KEY,
	eName VARCHAR(40),
	salary INT(40),
	postion VARCHAR(40)


);

ALTER TABLE employee
ADD COLUMN address VARCHAR(40),
ADD COLUMN phoneNo VARCHAR(40);
*/

    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET eName = ?, salary = ?, postion = ?, address = ?, phoneNo = ? WHERE employeeId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,employee.getEmployeeName());
        pstm.setObject(2,employee.getEmployeeSalary());
        pstm.setObject(3,employee.getEmployeePosition());
        pstm.setObject(4,employee.getEmployeeAddress());
        pstm.setObject(5,employee.getEmployeeTel());
        pstm.setObject(6,employee.getEmployeeId());

        return pstm.executeUpdate()>0;
    }

    public static Employee searchId(String id) throws SQLException {
        String sql = "SELECT * FROM employee WHERE employeeId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,id);
        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String emp_id = resultSet.getString(1);
            String name = resultSet.getString(2);
           double salary = Double.parseDouble(resultSet.getString(3));
            String position = resultSet.getString(4);
            String address = resultSet.getString(5);
            String phoneNo = resultSet.getString(6);

            Employee employee = new Employee(emp_id,name,address,phoneNo,position,salary);
           return employee;
        }
        return null;
    }

    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM employee";;
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<Employee> empList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            double salary = Double.parseDouble(resultSet.getString(3));
            String position = resultSet.getString(4);
            String address = resultSet.getString(5);
            String tel = resultSet.getString(6);

            Employee employee = new Employee(id, name, address, tel, position, salary);
            empList.add(employee);
        }
        return empList;

    }
}

