package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.User;
import lk.ijse.repository.UserRepo;

import java.sql.SQLException;


public class SignUpFormController {
    @FXML
    private Button btnSignup;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtPhoneNo;

    @FXML
    private TextField txtRole;

    @FXML
    private TextField txtUserName;




    public void signupbtnOnAction(ActionEvent actionEvent) {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        String role = txtRole.getText();
        String phoneNo = txtPhoneNo.getText();

        User user = new User(userName, password, phoneNo, role);
        try {
            boolean isSaved = UserRepo.saveUser(user);
            if (isSaved ) {
                new Alert(Alert.AlertType.CONFIRMATION,"user saved.").show();
                clearFields();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }


    }

    private void clearFields() {
        txtUserName.setText("");
        txtPassword.setText("");
        txtPhoneNo.setText("");
        txtRole.setText("");

    }
}
