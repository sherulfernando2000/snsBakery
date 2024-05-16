package lk.ijse.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.Customer;
import lk.ijse.model.EmailUtil;
import lk.ijse.repository.CustomerRepo;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class OffersFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private JFXTextArea txtArea;

    @FXML
    private TextField txtSubject;


    @FXML
    void btnSendOnAction(ActionEvent event) {
        try {
            emailSent();
        } catch (MessagingException e) {
            throw new RuntimeException(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void emailSent() throws MessagingException, SQLException {

        List<Customer> customerList = CustomerRepo.getAll();

        for (int i = 0; i < 3/*customerList.size()*/; i++) {
            String subject = txtSubject.getText();
            String recipientEmail = customerList.get(i).getAddress();// Replace with recipient emailString subject = "Welcome to S & S Bakery!";
            String body = txtArea.getText();
            EmailUtil.sendEmail(recipientEmail, subject, body);

            // Show confirmation message to the user

        }
        new Alert(Alert.AlertType.CONFIRMATION, "Emails are sent.").show();
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/View/customer_from.fxml"));
        this.rootNode.getChildren().removeAll();
        this.rootNode.getChildren().setAll(rootNode);
    }


}
