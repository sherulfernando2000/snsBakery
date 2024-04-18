package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class IngredientFormController {

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
    private AnchorPane rootNode;


    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/Supplier_Form.fxml"));
        this.rootNode.getChildren().removeAll();
        this.rootNode.getChildren().setAll(rootNode);

    }

}
