package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.Product;
import lk.ijse.model.Tm.ProductTm;
import lk.ijse.repository.ProductRepo;

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
        int price = Integer.parseInt(txtProductIPrice.getText());

        Product product = new Product(id,name,category,qty,price);

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
        int price = Integer.parseInt(txtProductIPrice.getText());

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
}
