package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.model.Customer;
import lk.ijse.model.Supplier;
import lk.ijse.repository.CustomerRepo;
import lk.ijse.repository.SupplierRepo;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.sql.SQLException;

public class SupplierFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private JFXButton btnClear;

    @FXML
    private TextField txtSupplierAddress;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private TextField txtSupplierName;

    @FXML
    private TextField txtSupplierTel;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtSupplierId.getText();
        String name = txtSupplierName.getText();
        String tel = txtSupplierTel.getText();
        String address = txtSupplierAddress.getText();

        Supplier supplier = new Supplier(id,name,tel,address);

        try {
            boolean isSaved = SupplierRepo.save(supplier);
            if (isSaved ) {
                new Alert(Alert.AlertType.CONFIRMATION,"supplier saved").show();
                clearFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void clearFields() {
        txtSupplierName.setText("");
        txtSupplierId.setText("");
        txtSupplierAddress.setText("");
        txtSupplierTel.setText("");

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void txtSupplierIdOnAction(ActionEvent event) {

    }

    public void btnIngredientOnAction(ActionEvent actionEvent) throws IOException {
            AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/ingredient_form.fxml"));
            this.rootNode.getChildren().removeAll();
            this.rootNode.getChildren().setAll(rootNode);
    }
}
