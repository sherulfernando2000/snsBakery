package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.Util.Regex;
import lk.ijse.model.Supplier;
import lk.ijse.model.Tm.SupplierTm;
import lk.ijse.repository.CustomerRepo;
import lk.ijse.repository.SupplierRepo;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colPhoneNo;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colSupplierName;



    @FXML
    private TableView<SupplierTm> tblSupplier;

    public void initialize(){
        setCellValueFactory();
        loadAllSuppliers();
    }

    private void loadAllSuppliers() {
        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();
        try {
            List<Supplier> supplierList = SupplierRepo.getAll();
            for (Supplier supplier : supplierList) {
                SupplierTm tm = new SupplierTm(
                        supplier.getId(),
                        supplier.getName(),
                        supplier.getAddress(),
                        supplier.getTel()
                );

                obList.add(tm);
            }

            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("tel"));

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtSupplierId.getText();

        try {
            boolean isDeleted = SupplierRepo.delete(id);
            new Alert(Alert.AlertType.CONFIRMATION,"Supplier Deleted.").show();
            clearFields();
            loadAllSuppliers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtSupplierId.getText();
        String name = txtSupplierName.getText();
        String tel = txtSupplierTel.getText();
        String address = txtSupplierAddress.getText();

        Supplier supplier = new Supplier(id,name,tel,address);

        switch (isValied()) {
            case 0:
                try {
                    boolean isSaved = SupplierRepo.save(supplier);
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "supplier saved").show();
                        clearFields();
                        loadAllSuppliers();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                ;
                break;

            case 1:
                new Alert(Alert.AlertType.ERROR, "Invalid supplier Id format should be in S001 type").show();
                break;
            case 2:
                new Alert(Alert.AlertType.ERROR, "Invalid supplier name format").show();
                break;
            case 3:
                new Alert(Alert.AlertType.ERROR, "Invalid phone no format").show();
                break;
            case 4:
                new Alert(Alert.AlertType.ERROR, "Invalid address format").show();
                break;

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
        String id = txtSupplierId.getText();
        String name = txtSupplierName.getText();
        String tel = txtSupplierTel.getText();
        String address = txtSupplierAddress.getText();

        Supplier supplier = new Supplier(id,name,tel,address);

        try {
            boolean isUpdated = SupplierRepo.update(supplier);
            if (isUpdated ) {
                new Alert(Alert.AlertType.CONFIRMATION,"supplier updated").show();
                clearFields();
                loadAllSuppliers();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtSupplierIdOnAction(ActionEvent event) {
        String id = txtSupplierId.getText();

        try {
            Supplier supplier = SupplierRepo.searchId(id);
            if (supplier != null) {
                txtSupplierId.setText(supplier.getId());
                txtSupplierName.setText(supplier.getName());
                txtSupplierTel.setText(supplier.getTel());
                txtSupplierAddress.setText(supplier.getAddress());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "supplier not found!").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnIngredientOnAction(ActionEvent actionEvent) throws IOException {
            AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/View/ingredient_form.fxml"));
            this.rootNode.getChildren().removeAll();
            this.rootNode.getChildren().setAll(rootNode);
    }

    @FXML
    void btnSupplierOrderOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/View/supplierOrder_form.fxml"));
        this.rootNode.getChildren().removeAll();
        this.rootNode.getChildren().setAll(rootNode);
    }

    @FXML
    void txtSupplierAddressOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Util.TextField.ADDRESS,txtSupplierAddress);
    }

    @FXML
    void txtSupplierIDOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Util.TextField.SID,txtSupplierId);
    }

    @FXML
    void txtSupplierPhoneNoOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Util.TextField.PHONENO,txtSupplierTel);
    }

    @FXML
    void txtSuuplierNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Util.TextField.NAME,txtSupplierName);
    }

    public int isValied(){
        if (!Regex.setTextColor(lk.ijse.Util.TextField.SID,txtSupplierId)) return 1;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.NAME,txtSupplierName)) return 2;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.PHONENO,txtSupplierTel)) return 3;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.ADDRESS,txtSupplierAddress)) return 4;
        return 0;
    }

}
