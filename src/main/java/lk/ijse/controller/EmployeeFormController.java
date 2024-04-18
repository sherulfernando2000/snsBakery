package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.model.Employee;
import lk.ijse.model.Tm.EmployeeTm;
import lk.ijse.repository.EmployeeRepo;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {


    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPhoneno;

    @FXML
    private TableColumn<?, ?> colPosition;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableView<EmployeeTm> tblEmployee;
    @FXML
    private Button btnCustomer;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtEmployeeAddress;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtEmployeePosition;

    @FXML
    private TextField txtEmployeeSalary;

    @FXML
    private TextField txtEmployeeTel;

    public void initialize(){
        setCellValueFactory();
        loadAllEmployees();
    }

    private void loadAllEmployees() {
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();
        try {
            List<Employee> employeeList = EmployeeRepo.getAll();
            for (Employee employee : employeeList) {
                EmployeeTm tm = new EmployeeTm(
                       employee.getEmployeeId(),
                        employee.getEmployeeName(),
                        employee.getEmployeeTel(),
                        employee.getEmployeeAddress(),
                        employee.getEmployeePosition(),
                        employee.getEmployeeSalary()
                );

                obList.add(tm);
            }

            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colPhoneno.setCellValueFactory(new PropertyValueFactory<>("employeeTel"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("employeeAddress"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("employeePosition"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("employeeSalary"));
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/customer_from.fxml.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Customer Form");
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();

        try {
            boolean isDeleted = EmployeeRepo.delete(id);
            new Alert(Alert.AlertType.CONFIRMATION,"Employee Deleted.").show();
            clearFields();
            loadAllEmployees();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();
        String name = txtEmployeeName.getText();
        String address = txtEmployeeAddress.getText();
        String tel = txtEmployeeTel.getText();
        String position = txtEmployeePosition.getText();
        double salary = Double.parseDouble(txtEmployeeSalary.getText());

        Employee employee = new Employee(id,name,address,tel,position,salary);

        try {
            boolean isSaved = EmployeeRepo.save(employee);
            if (isSaved ) {
                new Alert(Alert.AlertType.CONFIRMATION,"Employee saved").show();
                clearFields();
                loadAllEmployees();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtEmployeeId.setText("");
        txtEmployeeName.setText("");
        txtEmployeeAddress.setText("");
        txtEmployeeTel.setText("");
        txtEmployeePosition.setText("");
        txtEmployeeSalary.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();
        String name = txtEmployeeName.getText();
        String address = txtEmployeeAddress.getText();
        String tel = txtEmployeeTel.getText();
        String position = txtEmployeePosition.getText();
        double salary = Double.parseDouble(txtEmployeeSalary.getText());

        Employee employee = new Employee(id,name,address,tel,position,salary);

        try {
            boolean isUpdated = EmployeeRepo.update(employee);
            if (isUpdated ) {
                new Alert(Alert.AlertType.CONFIRMATION,"Employee updated Successfully.").show();
                clearFields();
                loadAllEmployees();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtEmpIdOnAction(ActionEvent actionEvent) {
        String id = txtEmployeeId.getText();

        try {
            Employee employee = EmployeeRepo.searchId(id);
            if (employee != null) {
                txtEmployeeId.setText(employee.getEmployeeId());
                txtEmployeeName.setText(employee.getEmployeeName());
                txtEmployeeAddress.setText(employee.getEmployeeAddress());
                txtEmployeeTel.setText(employee.getEmployeeTel());
                txtEmployeePosition.setText(employee.getEmployeePosition());
                txtEmployeeSalary.setText(String.valueOf(employee.getEmployeeSalary()));
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Employee not found!").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
