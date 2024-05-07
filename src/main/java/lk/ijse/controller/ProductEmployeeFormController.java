package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.*;
import lk.ijse.model.Tm.ProductEmployeeTm;
import lk.ijse.model.Tm.SupplierOrderTm;
import lk.ijse.repository.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ProductEmployeeFormController {

    @FXML
    private JFXButton btnIngredient;

    @FXML
    private ComboBox<String> cmbEmployeeName;

    @FXML
    private ComboBox<String> cmbProductName;

    @FXML
    private TableColumn<?, ?> colAssignmentType;

    @FXML
    private TableColumn<?, ?> colEmployeeID;

    @FXML
    private TableColumn<?, ?> colEmployeeName;

    @FXML
    private TableColumn<?, ?> colProductId;

    @FXML
    private TableColumn<?, ?> colProductName;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<ProductEmployeeTm> tblTask;


    @FXML
    private TextField txtAssignmentType;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtProductId;

    public void initialize(){
        setCellValueFactory();
        loadAllProductEmployee();
        getEmployeeName();
        getProductName();
    }

    private void loadAllProductEmployee() {
        ObservableList<ProductEmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<ProductEmployee> orderList = ProductEmployeeRepo.getAll();
            for (ProductEmployee order : orderList) {

                String eName= EmployeeRepo.getName(order.getEmployeeId());
                String pName = ProductRepo.getName(order.getProductId());

                ProductEmployeeTm tm = new ProductEmployeeTm(
                        order.getEmployeeId(),
                        eName,
                        order.getProductId(),
                        pName,
                        order.getAssignmentType()

                );

                obList.add(tm);
            }

            tblTask.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colEmployeeID.setCellValueFactory(new PropertyValueFactory<>("eId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("eName"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("pId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("pName"));
        colAssignmentType.setCellValueFactory(new PropertyValueFactory<>("assignmentType"));

    }

    private void getEmployeeName(){
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> nameList = EmployeeRepo.getName();
            for (String name: nameList) {
                obList.add(name);
            }
            cmbEmployeeName.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getProductName() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> descriptionList = ProductRepo.getName();
            for (String description: descriptionList) {
                obList.add(description);
            }
            cmbProductName.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        ProductEmployeeTm productEmployeeTm = tblTask.getSelectionModel().getSelectedItem();
        try {
            boolean isDeleted = ProductEmployeeRepo.detele(productEmployeeTm.getEId(), productEmployeeTm.getPId(),productEmployeeTm.getAssignmentType());
            new Alert(Alert.AlertType.CONFIRMATION,"Supplier Order Deleted.").show();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnIngredientOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String employeeId = txtEmployeeId.getText();
        String productId = txtProductId.getText();
        String assignmentType = txtAssignmentType.getText();


        ProductEmployee productEmployee = new ProductEmployee(employeeId,productId,assignmentType );

        try {
            boolean isSaved = ProductEmployeeRepo.save(productEmployee);
            if (isSaved ) {
                new Alert(Alert.AlertType.CONFIRMATION,"supplier order saved").show();
                loadAllProductEmployee();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void cmbEmployeeNameOnAction(ActionEvent event) {
        String nameValue = cmbEmployeeName.getValue();
        try {
            Employee employee = EmployeeRepo.searchByName(nameValue);
            if (employee != null) {
                txtEmployeeId.setText(employee.getEmployeeId());

            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbProductNameOnAction(ActionEvent event) {
        String nameValue = cmbProductName.getValue();
        try {
            Product product = ProductRepo.searchByName(nameValue);
            if (product != null) {
                txtProductId.setText(product.getId());

            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml"));
        this.rootNode.getChildren().removeAll();
        this.rootNode.getChildren().setAll(rootNode);
    }

}
