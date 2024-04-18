package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.model.Customer;
import lk.ijse.model.Product;
import lk.ijse.model.Tm.CartTm;
import lk.ijse.repository.CustomerRepo;
import lk.ijse.repository.OrderRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javafx.scene.layout.AnchorPane;
import lk.ijse.repository.ProductRepo;


public class OrderFormController {

    @FXML
    private ComboBox<String> cmbDescription;

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
    private Label lblCode;

    @FXML
    private Label lblCustomerId;
    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblCustomerTel;

    @FXML
    private Label lblGrossTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblOrderTime;

    @FXML
    private TextField lblQty;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TableView<CartTm> tblOrder;

    @FXML
    private TextField txtCustomerTel;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtQty;

    private ObservableList<CartTm> obList = FXCollections.observableArrayList();

    public static int grossTotal = 0;




    public void initialize(){
        setDateTime();
        getCurrentOrderId();
        getProductDescription();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

    }

    private void getProductDescription() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> descriptionList = ProductRepo.getDescription();
            for (String description: descriptionList) {
                obList.add(description);
            }
            cmbDescription.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void getCurrentOrderId() {
        try {
            String currentId = OrderRepo.getCurrentId();
            String nextOrderId = generateNextOrderId(currentId);
            lblOrderId.setText(nextOrderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private String generateNextOrderId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("O-");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "O-" + ++idNum;
        }
        return "O-1";
    }

    private void setDateTime() {
        LocalDate now = LocalDate.now();
        lblOrderDate.setText(String.valueOf(now));
        LocalTime now1 = LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute());
        lblOrderTime.setText(String.valueOf(now1));

    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String code = lblCode.getText();
        String description = cmbDescription.getValue();
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double total = qty * unitPrice;

        CartTm tm = new CartTm(code, description, qty, unitPrice, total);
        obList.add(tm);

        tblOrder.setItems(obList);
        calculateGrossTotal();
        txtQty.setText("");



    }

    private void calculateGrossTotal() {
        //int grossTotal = 0;
        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            grossTotal += (double) colTotal.getCellData(i);
        }
        lblGrossTotal.setText(String.valueOf(grossTotal));
    }

    @FXML
    void btnCancelOrderOnAction(ActionEvent event) {
        obList.removeAll();
        tblOrder.refresh();
    }

    @FXML
    void btnConfirmOrderOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/payment_form.fxml"));
        this.rootNode.getChildren().removeAll();
        this.rootNode.getChildren().setAll(rootNode);


    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/customer_from.fxml"));
        this.rootNode.getChildren().removeAll();
        this.rootNode.getChildren().setAll(rootNode);
    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {
        int selectedIndex = tblOrder.getSelectionModel().getSelectedIndex();
        obList.remove(selectedIndex);

        tblOrder.refresh();
        calculateGrossTotal();
    }

    @FXML
    void cmbProductOnAction(ActionEvent event) {
        String description = cmbDescription.getValue();
        try {
            Product product = ProductRepo.searchByDescription(description);
            if (product != null) {
                lblCode.setText(product.getId());
                lblUnitPrice.setText(String.valueOf(product.getPrice()));
                lblQtyOnHand.setText(String.valueOf(product.getQty()));
            }

            txtQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void txtTelOnAction(ActionEvent event) {
        String tel = txtCustomerTel.getText();
        try {
            Customer customer = CustomerRepo.searchTel(tel);
            if (customer != null) {
                lblCustomerId.setText(customer.getId());
                lblCustomerName.setText(customer.getName());
            }else{
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
