package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PaymentFormController {

    @FXML
    private JFXComboBox<?> cbxCardCash;

    @FXML
    private JFXComboBox<?> cbxDisType;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblBalance;

    @FXML
    private Label lblGrossAmount;

    @FXML
    private Label lblItem;

    @FXML
    private Label lblNetAmount;

    @FXML
    private Label lblPaymentId;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblTotalSaving;

    @FXML
    private TableView<?> tblOrderSummary;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtDiscountPrec;

    public int grossTotal;

    public void initialize(){

        lblGrossAmount.setText(String.valueOf(grossTotal));
    }
    @FXML
    void btnNextOrderOnAction(ActionEvent event) {

    }

}
