package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.Ingredient;
import lk.ijse.model.Tm.IngredientTm;
import lk.ijse.repository.IngredientRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class IngredientFormController {

    @FXML
    private JFXButton btnIngredient;

    @FXML
    private JFXButton btnSupplier;

    @FXML
    private JFXButton btnSupplierOrder;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<IngredientTm> tblIngredient;

    @FXML
    private TextField txtIngredientCategory;

    @FXML
    private TextField txtIngredientId;

    @FXML
    private TextField txtIngredientName;


    public void initialize(){
        setCellValueFactory();
        loadAllIngredients();
    }

    private void loadAllIngredients() {
        ObservableList<IngredientTm> obList = FXCollections.observableArrayList();

        try {
            List<Ingredient> ingredientList = IngredientRepo.getAll();
            for (Ingredient ingredient : ingredientList) {
                IngredientTm tm = new IngredientTm(
                        ingredient.getId(),
                        ingredient.getName(),
                        ingredient.getCategory()

                );

                obList.add(tm);
            }

            tblIngredient.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtIngredientId.setText("");
        txtIngredientName.setText("");
        txtIngredientCategory.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtIngredientId.getText();

        try {
            boolean isDeleted = IngredientRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Ingredient deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnIngredientOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtIngredientId.getText();
        String name = txtIngredientName.getText();
        String category = txtIngredientCategory.getText();


        Ingredient ingredient = new Ingredient(id, name, category);


        try {
            boolean isSaved = IngredientRepo.save(ingredient);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Ingredient saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/View/Supplier_Form.fxml"));
        this.rootNode.getChildren().removeAll();
        this.rootNode.getChildren().setAll(rootNode);
    }

    @FXML
    void btnSupplierOrderOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/View/supplierOrder_form.fxml"));
        this.rootNode.getChildren().removeAll();
        this.rootNode.getChildren().setAll(rootNode);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtIngredientId.getText();
        String name = txtIngredientName.getText();
        String category = txtIngredientCategory.getText();

        Ingredient ingredient = new Ingredient(id, name, category);

        try {
            boolean isUpdated = IngredientRepo.update(ingredient);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Ingredient updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtIdSearchOnAction(ActionEvent event) throws SQLException {
        String id = txtIngredientId.getText();

        Ingredient ingredient = IngredientRepo.searchById(id);
        if (ingredient != null) {
            txtIngredientId.setText(ingredient.getId());
            txtIngredientName.setText(ingredient.getName());
            txtIngredientCategory.setText(ingredient.getCategory());

        } else {
            new Alert(Alert.AlertType.INFORMATION, "ingredient not found!").show();
        }
    }

}
