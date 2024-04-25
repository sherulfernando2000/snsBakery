package lk.ijse.controller;

import  javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardFormController {
    public AnchorPane mainNode;
    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnEmployee;

    @FXML
    private AnchorPane rootNode;

    public void navigateTo(String url) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource(url));
        this.rootNode.getChildren().removeAll();
        this.rootNode.getChildren().setAll(rootNode);
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        navigateTo("/View/customer_from.fxml");



    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        navigateTo("/View/employee_form.fxml");

        /*AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml"));
        this.rootNode.getChildren().removeAll();
        this.rootNode.getChildren().setAll(rootNode);*/
    }


    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.mainNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Bakery Management System");



    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.mainNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Bakery Management System");
    }

    @FXML
    void btnOrderOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/order_form.fxml"));
        AnchorPane rootNode = loader.load();

        // Set the controller for the loaded FXML file
        OrderFormController controller = loader.getController();
        controller.initializeOrder(); // Call your custom initialization method

        this.rootNode.getChildren().removeAll();
        this.rootNode.getChildren().setAll(rootNode);

    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) throws IOException {
        navigateTo("/View/payment_form.fxml");

    }

    @FXML
    void btnProductOnAction(ActionEvent event) throws IOException {
        navigateTo("/View/product_from.fxml");

    }

    @FXML
    void btnReportOnAction(ActionEvent event) throws IOException {
        navigateTo("/View/report.fxml");

    }

    @FXML
    void btnSuppliesOnAction(ActionEvent event) throws IOException {
        navigateTo("/View/Supplier_Form.fxml");

    }


}
