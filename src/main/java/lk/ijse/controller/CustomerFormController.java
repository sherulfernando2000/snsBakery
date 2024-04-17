package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.Customer;
import lk.ijse.model.Tm.CustomerTm;
import lk.ijse.repository.CustomerRepo;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import java.sql.SQLException;
import java.util.List;


public class CustomerFormController {
    @FXML
    private JFXButton btnClear;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtCustomerAddress;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtCustomerTel;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colPhoneNo;


    @FXML
    private TableView<CustomerTm> tblCustomer;

    public void initialize(){
        setCellValueFactory();
        loadAllCustomers();
    }


    private void setCellValueFactory() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void loadAllCustomers() {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
        try {
            List<Customer> customerList = CustomerRepo.getAll();
            for (Customer customer : customerList) {
                CustomerTm tm = new CustomerTm(
                        customer.getId(),
                        customer.getName(),
                        customer.getTel(),
                        customer.getAddress()
                );

                obList.add(tm);
            }

            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
            String id = txtCustomerId.getText();

        try {
            boolean isDeleted = CustomerRepo.delete(id);
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Deleted.").show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtCustomerId.getText();
        String name = txtCustomerName.getText();
        String tel = txtCustomerTel.getText();
        String address = txtCustomerAddress.getText();

        Customer customer = new Customer(id,name,tel,address);

        try {
            boolean isSaved = CustomerRepo.save(customer);
            if (isSaved ) {
                new Alert(Alert.AlertType.CONFIRMATION,"customer saved").show();
                clearFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void clearFields() {
        txtCustomerId.setText("");
        txtCustomerName.setText("");
        txtCustomerTel.setText("");
        txtCustomerAddress.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtCustomerId.getText();
        String name = txtCustomerName.getText();
        String tel = txtCustomerTel.getText();
        String address = txtCustomerAddress.getText();

        Customer customer = new Customer(id,name,tel,address);

        try {
            boolean isUpdated = CustomerRepo.update(customer);
            if (isUpdated ) {
                new Alert(Alert.AlertType.CONFIRMATION,"customer updated").show();
                clearFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void txtCustomerIdOnAction(ActionEvent actionEvent) {
        String id = txtCustomerId.getText();

        try {
            Customer customer = CustomerRepo.searchId(id);
            if (customer != null) {
                txtCustomerId.setText(customer.getId());
                txtCustomerName.setText(customer.getName());
                txtCustomerAddress.setText(customer.getAddress());
                txtCustomerTel.setText(customer.getTel());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void txtCustomerTelOnAction(ActionEvent actionEvent) {
        String tel = txtCustomerTel.getText();

        try {
            Customer customer = CustomerRepo.searchTel(tel);
            if (customer != null) {
                txtCustomerId.setText(customer.getId());
                txtCustomerName.setText(customer.getName());
                txtCustomerAddress.setText(customer.getAddress());
                txtCustomerTel.setText(customer.getTel());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

