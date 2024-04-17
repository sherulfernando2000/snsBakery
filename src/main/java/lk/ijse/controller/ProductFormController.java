package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableView;
import lk.ijse.model.Product;

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
    private TableView<?> tblProduct;

    @FXML
    private TextField txtProductIName;

    @FXML
    private TextField txtProductIPrice;

    @FXML
    private TextField txtProductQty;

    public void initialize(){
        getProductCategory();
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

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtProductId.getText();
        String name = txtProductIName.getText();
        String category = String.valueOf(cmbProductCategory.getValue());
        int qty = Integer.parseInt(txtProductQty.getText());
        int price = Integer.parseInt(txtProductIPrice.getText());

        Product product = new Product(id,name,category,qty,price);


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchNameOnAction(ActionEvent event) {

    }



    @FXML
    void btnsearchIdOnAction(ActionEvent event) {

    }


}
