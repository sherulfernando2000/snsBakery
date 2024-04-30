package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.model.User;
import lk.ijse.repository.UserRepo;

import java.io.IOException;
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

    @FXML
    void linkSignInOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("LogIn form");
    }

}
