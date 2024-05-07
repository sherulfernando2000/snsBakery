package lk.ijse.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import lk.ijse.Util.Regex;
import lk.ijse.db.DbConnection;
import lk.ijse.model.*;
import lk.ijse.model.Tm.CartTm;
import lk.ijse.repository.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.layout.AnchorPane;


import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.mail.MessagingException;


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
    public Label lblGrossTotal;

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

    private static ObservableList<CartTm> obList = FXCollections.observableArrayList();

    public static int grossTotal=0;
    public double totalSaving = 0;

    double netAmount;

    double balance;

    public static int totalQty=0;

    public static  Order order;

    public  static List<OrderProductDetail> odList;




    public void initialize(){

    }

    void initializePayment() {
        getNextPaymentId();
        lblGrossAmount.setText(String.valueOf(grossTotal));
        setTime();
        lblItem.setText(String.valueOf(totalQty));
        getDiscountType();
        getCashOrCard();

    }

    private void getCashOrCard() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.add("Cash");
        obList.add("Card");
        cbxCardCash.setItems(obList);
    }

    private void getDiscountType() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.add("seasonal Offer");
        obList.add("loyal customer");
        obList.add("Other");
        cbxDisType.setItems(obList);
    }

    private void getTotalItemQty() {
        if (totalQty != 0) {
            totalQty = 0;
            for (CartTm cartItem : obList) {
                totalQty += cartItem.getQty();
        }
        }else{
            for (CartTm cartItem : obList) {
                totalQty += cartItem.getQty();
            }
        }


    }

    private void setTime() {
        LocalTime now1 = LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute());
        lblTime.setText(String.valueOf(now1));
    }

    private void getNextPaymentId() {
        try {
            String currentPaymentId = PaymentRepo.getCurrentPaymentId();
            String nextpaymentId = generateNextpaymentId(currentPaymentId);
            lblPaymentId.setText(nextpaymentId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextpaymentId(String currentPaymentId) {
        if(currentPaymentId != null) {
            String[] split = currentPaymentId.split("P-");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "P-" + ++idNum;
        }
        return "P-1";
    }

    public void initializeOrder() {
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
        if (isValied()) {
            String code = lblCode.getText();
            String description = cmbDescription.getValue();
            double unitPrice = Double.parseDouble(lblUnitPrice.getText());
            int qty = Integer.parseInt(txtQty.getText());
            double total = qty * unitPrice;

            CartTm tm = new CartTm(code, description, qty, unitPrice, total);
            obList.add(tm);
            tblOrder.setItems(obList);
            calculateGrossTotal();
            getTotalItemQty();
            txtQty.setText("");
        }else{
            new Alert(Alert.AlertType.ERROR,"Invalid inputs to fields").show();

        }




    }

    private void calculateGrossTotal() {
        if (grossTotal != 0) {
            grossTotal = 0;
            for (int i = 0; i < tblOrder.getItems().size(); i++) {
                grossTotal += (double) colTotal.getCellData(i);
            }
        }else {
            for (int i = 0; i < tblOrder.getItems().size(); i++) {
                grossTotal += (double) colTotal.getCellData(i);
            }
        }


        lblGrossTotal.setText(String.valueOf(grossTotal));
    }

    @FXML
    void btnCancelOrderOnAction(ActionEvent event) {
        obList.clear();
        tblOrder.refresh();
    }

    @FXML
    void btnConfirmOrderOnAction(ActionEvent event) throws IOException {

        makeOrderTransactionObjects();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/payment_form.fxml"));
        AnchorPane rootNode = loader.load();

        // Set the controller for the loaded FXML file
        OrderFormController controller = loader.getController();
        controller.initializePayment(); // Call your custom initialization method

        this.rootNode.getChildren().removeAll();
        this.rootNode.getChildren().setAll(rootNode);

        System.out.println(order);
        System.out.println(odList);



    }

    private void makeOrderTransactionObjects() {
        String orderId = lblOrderId.getText();
        String status = lblOrderTime.getText();
        Date date = Date.valueOf(lblOrderDate.getText());
        double grossAmount = Double.parseDouble(lblGrossTotal.getText());
        String customerId = lblCustomerId.getText();

        order = new Order(orderId,status,date,grossAmount,customerId);

        odList = new ArrayList<>();

        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            CartTm tm = obList.get(i);

            OrderProductDetail od = new OrderProductDetail(
                    orderId,
                    tm.getCode(),
                    tm.getUnitPrice(),
                    tm.getQty(),
                    tm.getTotal()
            );

            odList.add(od);
        }


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

    @FXML
    void txtPhoneNoOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Util.TextField.PHONENO,txtCustomerTel);
    }

    @FXML
    void txtQtyOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Util.TextField.QTY,txtQty);
    }

    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.Util.TextField.PHONENO,txtCustomerTel)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.QTY,txtQty)) return false;

        return true;
    }

    //payment controller

    @FXML
    private JFXComboBox<String> cbxCardCash;

    @FXML
    private JFXComboBox<String> cbxDisType;

    @FXML
    private TableColumn<?, ?> colPCode;

    @FXML
    private TableColumn<?, ?> colPDescription;

    @FXML
    private TableColumn<?, ?> colPQty;

    @FXML
    private TableColumn<?, ?> colPTotal;

    @FXML
    private TableColumn<?, ?> colPUnitPrice;

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

    @FXML
    private AnchorPane rootNodeP;



    /*public void initialize(){


    }*/

    private void calculateTotalSaving(){
        totalSaving = Double.parseDouble(lblGrossAmount.getText()) * (Double.parseDouble(txtDiscountPrec.getText())/100);
        lblTotalSaving.setText(String.valueOf(totalSaving));
    }

    private void calculateNetAmount(){
        netAmount = Double.parseDouble(lblGrossAmount.getText()) - totalSaving;
        lblNetAmount.setText(String.valueOf(netAmount));
    }

    @FXML
    void txtDiscountPrecOnAction(ActionEvent event) {
        if (isValiedPayment1()) {
            calculateTotalSaving();
            calculateNetAmount();
        }else{
            new Alert(Alert.AlertType.ERROR,"Invalid input").show();
        }


    }

    @FXML
    void txtAmountOnAction(ActionEvent event) {
        if (isValiedPayment2()) {
            calculateBalance();
        }else{
            new Alert(Alert.AlertType.ERROR,"Invalid input").show();

        }

    }

    private void calculateBalance() {
        balance = Double.parseDouble(txtAmount.getText()) - netAmount ;
        lblBalance.setText(String.valueOf(balance));
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws IOException {
        if (isValiedPayment()) {
            String paymentId = lblPaymentId.getText();
            String paymentMethod = cbxCardCash.getValue();
            LocalDate now = LocalDate.now();
            String date = String.valueOf(now);
            double discountAmount = Double.parseDouble(lblTotalSaving.getText());
            double totalAmount = Double.parseDouble(lblNetAmount.getText());
            String orderId = order.getOrderId();
            String discountType = cbxDisType.getValue();
            double discountPrecentage = Double.parseDouble(txtDiscountPrec.getText());

            Payment payment = new Payment(paymentId, paymentMethod, date, discountAmount, totalAmount, orderId,discountType, discountPrecentage);

            PlaceOrder po = new PlaceOrder(order,odList,payment);

            try {
                boolean isPlaced =  PlaceOrderRepo.placeOrder(po);
                if(isPlaced) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
                    //new code set
                /*String recipientEmail = "sherul.dhanushka@gmail.com"; // Replace with recipient email
                String subject = "Order Confirmation";
                String body = "Your order has been successfully placed. Thank you!";
                EmailUtil.sendEmail(recipientEmail, subject, body);*/

                    // Show confirmation message to the user
                    //new Alert(Alert.AlertType.CONFIRMATION, "Order Placed! Email sent.").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();
                }

            } catch (SQLException e) {
                // Print the exception message
                System.out.println("Exception occurred: " + e.getMessage());
                throw new RuntimeException(e.getMessage());
        /*} catch (MessagingException e) {
            throw new RuntimeException(e);*/
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"Invalid input to fields").show();

        }



       /* PlaceOrder po = new PlaceOrder(order, odList);
        try {
            boolean isPlaced = PlaceOrderRepo.placeOrder(po);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }*/
    }


    public void btnPrintBillOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/reports/bakery.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("orderId",order.getOrderId());
        data.put("cashOrCard", txtAmount.getText());
        data.put("balance", String.valueOf(balance));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

    }

    @FXML
    void btnNextOrderOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/order_form.fxml"));
        AnchorPane rootNode = loader.load();

        obList.clear();

        // Set the controller for the loaded FXML file
        OrderFormController controller = loader.getController();
        controller.initializeOrder(); // Call your custom initialization method

        this.rootNodeP.getChildren().removeAll();
        this.rootNodeP.getChildren().setAll(rootNode);

        //obList.removeAll();
    }

    @FXML
    void txtDiscountOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Util.TextField.PRICE,txtDiscountPrec);
    }

    @FXML
    void txtSAmountOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Util.TextField.PRICE,txtAmount);
    }

    public boolean isValiedPayment(){
        if (!Regex.setTextColor(lk.ijse.Util.TextField.PRICE,txtDiscountPrec)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.PRICE,txtAmount)) return false;

        return true;
    }

    public boolean isValiedPayment1(){
        if (!Regex.setTextColor(lk.ijse.Util.TextField.PRICE,txtDiscountPrec)) return false;
        return true;
    }

    public boolean isValiedPayment2(){
        if (!Regex.setTextColor(lk.ijse.Util.TextField.PRICE,txtAmount)) return false;
        return true;
    }


}
