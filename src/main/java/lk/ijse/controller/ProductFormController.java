package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Util.Regex;
import lk.ijse.model.Product;
import lk.ijse.model.Tm.ProductTm;
import lk.ijse.repository.ProductRepo;
import lk.ijse.repository.SupplierRepo;
import lk.ijse.repository.WasteRepo;

import java.sql.SQLException;
import java.util.List;

public class ProductFormController {

    @FXML
    private JFXComboBox<String> cmbProductCategory;

    @FXML
    private TableColumn<?, ?> colProductCategory;

    @FXML
    private TableColumn<?, ?> colProductId;

    @FXML
    private TableColumn<?, ?> colProductName;

    @FXML
    private TableColumn<?, ?> colProductPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtProductId;


    @FXML
    private TableView<ProductTm> tblProduct;

    @FXML
    private TextField txtProductIName;

    @FXML
    private TextField txtProductIPrice;

    @FXML
    private TextField txtProductQty;

    public void initialize(){
        getProductCategory();
        setCellValueFactory();
        loadAllProducts();
    }

    private void setCellValueFactory() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colProductCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));


    }

    private void loadAllProducts() {
        ObservableList<ProductTm> obList = FXCollections.observableArrayList();

        try {
            List<Product> productList = ProductRepo.getAll();
            for (Product product : productList) {
                ProductTm tm = new ProductTm(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getCategory(),
                        product.getQty()

                );

                obList.add(tm);
            }

            tblProduct.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void getProductCategory() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.add("Buns");
        obList.add("Cake");
        obList.add("Pastry");
        obList.add("Others");
        cmbProductCategory.setItems(obList);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtProductId.getText();

        try {
            boolean isDeleted = ProductRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION,"product deleted successfully.").show();
                clearFields();
                loadAllProducts();

            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtProductId.getText();
        String name = txtProductIName.getText();
        String category = String.valueOf(cmbProductCategory.getValue());
        int qty = Integer.parseInt(txtProductQty.getText());
        double price = Double.parseDouble(txtProductIPrice.getText());

        Product product = new Product(id,name,category,qty,price);

        switch (isValied()) {
            case 0:
                try {
                    boolean isSaved = ProductRepo.save(product);
                    if (isSaved ) {
                        new Alert(Alert.AlertType.CONFIRMATION,"product saved successfully.").show();
                        clearFields();
                        loadAllProducts();

                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                ;
                break;

            case 1:
                new Alert(Alert.AlertType.ERROR, "Invalid Product Id format should be in P001 type").show();
                break;
            case 2:
                new Alert(Alert.AlertType.ERROR, "Invalid Product name format").show();
                break;
            case 3:
                new Alert(Alert.AlertType.ERROR, "Invalid price format").show();
                break;
            case 4:
                new Alert(Alert.AlertType.ERROR, "Invalid qty format").show();
                break;

        }


    }

    private void clearFields() {
        txtProductId.setText("");
        txtProductIName.setText("");
        txtProductIPrice.setText("");
        txtProductQty.setText("");
        cmbProductCategory.getSelectionModel().clearSelection();


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtProductId.getText();
        String name = txtProductIName.getText();
        String category = String.valueOf(cmbProductCategory.getValue());
        int qty = Integer.parseInt(txtProductQty.getText());
        double price = Double.parseDouble(txtProductIPrice.getText());

        Product product = new Product(id,name,category,qty,price);

        try {
            boolean isUpdated = ProductRepo.update(product);
            if (isUpdated ) {
                new Alert(Alert.AlertType.CONFIRMATION,"product updated successfully.").show();
                clearFields();
                loadAllProducts();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void txtSearchNameOnAction(ActionEvent actionEvent) {

    }

    public void txtsearchIdOnAction(ActionEvent actionEvent) {
        String id = txtProductId.getText();

        Product product = null;
        try {
            product = ProductRepo.searchById(id);
            if (product != null) {
                txtProductId.setText(product.getId());
                txtProductIName.setText(product.getName());
                cmbProductCategory.setValue(product.getCategory());
                txtProductQty.setText(String.valueOf(product.getQty()));
                txtProductIPrice.setText(String.valueOf(product.getPrice()));
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnWasteOnAction(ActionEvent event) throws SQLException {
            List<Product> product = ProductRepo.getAll();

        try {
            boolean isSave = WasteRepo.save(product);
            if(isSave) {
                new Alert(Alert.AlertType.CONFIRMATION, "Waste save!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Waste saved Unsuccessfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

           // ProductRepo.updateQty;

    }
    @FXML
    void txtPriceOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Util.TextField.PRICE,txtProductIPrice);
    }

    @FXML
    void txtProductIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Util.TextField.PID,txtProductId);
    }

    @FXML
    void txtPrroductNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Util.TextField.NAME,txtProductIName);
    }

    @FXML
    void txtQtyOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Util.TextField.QTY,txtProductQty);
    }

    public int isValied(){
        if (!Regex.setTextColor(lk.ijse.Util.TextField.PID,txtProductId)) return 1;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.NAME,txtProductIName)) return 2;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.PRICE,txtProductIPrice)) return 3;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.QTY,txtProductQty)) return 4;
        return 0;
    }




}
