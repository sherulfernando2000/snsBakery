package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.Ingredient;
import lk.ijse.model.Supplier;
import lk.ijse.model.SupplierOrder;
import lk.ijse.model.Tm.SupplierOrderTm;
import lk.ijse.repository.CustomerRepo;
import lk.ijse.repository.IngredientRepo;
import lk.ijse.repository.SupplierOrderRepo;
import lk.ijse.repository.SupplierRepo;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class supplierOrderFormController {

    @FXML
    private TextField txtIngredientName;

    @FXML
    private TextField txtProductIName;

    @FXML
    private TextField txtProductIPrice;

    @FXML
    private TextField txtProductId;

    @FXML
    private TextField txtProductQty;

    @FXML
    private TextField txtProductQty1;

    @FXML
    private TextField txtIngredientId;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private ComboBox<String> cmbIngredientName;

    @FXML
    private ComboBox<String> cmbSupplierName;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField txtDate;

    @FXML
    private TableView<SupplierOrderTm> tblSupplierOrder;

    @FXML
    private TableColumn<?, ?> colIngredientName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupplierDate;

    @FXML
    private TableColumn<?, ?> colSupplierName;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colSupplierID;

    @FXML
    private TableColumn<?, ?> colIngredientId;






    public void initialize(){
        setCellValueFactory();
        loadAllSupplierOrder();
        getSupplierName();
        getIngredientName();
    }

    private void setCellValueFactory() {
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("sId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("sName"));
        colIngredientId.setCellValueFactory(new PropertyValueFactory<>("iId"));
        colIngredientName.setCellValueFactory(new PropertyValueFactory<>("iName"));
        colSupplierDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    private void loadAllSupplierOrder() {
        ObservableList<SupplierOrderTm> obList = FXCollections.observableArrayList();

        try {
            List<SupplierOrder> orderList = SupplierOrderRepo.getAll();
            for (SupplierOrder order : orderList) {

                String iName= IngredientRepo.getName(order.getIngredientId());
                String sName = SupplierRepo.getName(order.getSupplierId());

                SupplierOrderTm tm = new SupplierOrderTm(
                        order.getSupplierId(),
                        sName,
                        order.getIngredientId(),
                        iName,
                        order.getDate(),
                        order.getQty(),
                        order.getPrice(),
                        order.getTotal()

                );

                obList.add(tm);
            }

            tblSupplierOrder.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getIngredientName() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> descriptionList = IngredientRepo.getName();
            for (String description: descriptionList) {
                obList.add(description);
            }
            cmbIngredientName.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getSupplierName() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> descriptionList = SupplierRepo.getName();
            for (String description: descriptionList) {
                obList.add(description);
            }
            cmbSupplierName.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        SupplierOrderTm supplierOrderTm = tblSupplierOrder.getSelectionModel().getSelectedItem();
        try {
            boolean isDeleted = SupplierOrderRepo.detele(supplierOrderTm.getSId(), supplierOrderTm.getIId(),supplierOrderTm.getDate());
            new Alert(Alert.AlertType.CONFIRMATION,"Supplier Order Deleted.").show();
            clearFields();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String supplierId = txtSupplierId.getText();
        String ingredientId = txtIngredientId.getText();
        Date date = Date.valueOf((txtDate.getText()));
        int qty = Integer.parseInt(txtQty.getText());
        double price = Double.parseDouble(txtPrice.getText());
        double total = qty * price;

        SupplierOrder supplierOrder = new SupplierOrder( ingredientId,supplierId, date, qty, price, total);

        try {
            boolean isSaved = SupplierOrderRepo.save(supplierOrder);
            if (isSaved ) {
                new Alert(Alert.AlertType.CONFIRMATION,"supplier order saved").show();
                clearFields();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void btnIngredientOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/ingredient_form.fxml"));
        this.rootNode.getChildren().removeAll();
        this.rootNode.getChildren().setAll(rootNode);
    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/Supplier_Form.fxml"));
        this.rootNode.getChildren().removeAll();
        this.rootNode.getChildren().setAll(rootNode);

    }

    @FXML
    void cmbSupplierNameOnAction(ActionEvent event) {
        String nameValue = cmbSupplierName.getValue();
        try {
            Supplier supplier = SupplierRepo.searchByName(nameValue);
            if (supplier != null) {
                txtSupplierId.setText(supplier.getId());

            }

            txtQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbIngredientNameOnAction(ActionEvent event) {
        String nameValue = cmbIngredientName.getValue();
        try {
            Ingredient ingredient = IngredientRepo.searchByName(nameValue);
            if (ingredient != null) {
                txtIngredientId.setText(ingredient.getId());

            }

            txtPrice.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void getDate(ActionEvent event) {
        LocalDate myDate = datePicker.getValue();
        String myFormattedDate = myDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        txtDate.setText(myFormattedDate);
    }

}
