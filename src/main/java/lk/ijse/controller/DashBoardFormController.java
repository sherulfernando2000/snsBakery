package lk.ijse.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import  javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lk.ijse.db.DbConnection;
import lk.ijse.model.Tm.DailyRevenueTm;
import lk.ijse.model.Tm.MostSellItemTm;
import lk.ijse.repository.*;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.scene.chart.PieChart;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

public class DashBoardFormController {
    public AnchorPane mainNode;
    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnEmployee;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblUserName;


    @FXML
    private Text txtDailyRevenue;

    @FXML
    private Text txtMonthlyRevenue;

    @FXML
    private Text txtNoOfCustomers;

    @FXML
    private Text txtNoOfEmployee;

    @FXML
    private Text txtNoOfOrders;

    @FXML
    private Text txtNoOfProducts;

    @FXML
    private AreaChart<?, ?> barChart;

    @FXML
    private AreaChart<?, ?> barChart1;

    @FXML
    private PieChart pieChart;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField txtSearchByDate;

    @FXML
    private TextField txtSearchByProduct;

    @FXML
    private Text txtDailyRevenueSearch;

    @FXML
    private Text txtProductSoldSearch;

    private AutoCompletionBinding<String> autoCompletionBinding;
    private List<String> possibleSuggestion;

    private Set< String> possibleSugg;




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

    public void initialize() throws SQLException {
        int noOfCustomer = CustomerRepo.getAll().size();
        txtNoOfCustomers.setText(String.valueOf(noOfCustomer));
        int noOfOrders = OrderRepo.getOderCount();
        txtNoOfOrders.setText(String.valueOf(noOfOrders));
        int noOfProduct = ProductRepo.getAll().size();
        txtNoOfProducts.setText(String.valueOf(noOfProduct));
        int noOfEmployee = EmployeeRepo.getAll().size();
        txtNoOfEmployee.setText(String.valueOf(noOfEmployee));
        double dailyRevenue = DailyReportRepo.getDailyRevenue();
        txtDailyRevenue.setText(String.valueOf(dailyRevenue));
        double monthlyRevenue = weeklyReportRepo.getMonthlyRevenue();
        txtMonthlyRevenue.setText(String.valueOf(monthlyRevenue));

        setDatetime();
        //lineChart();
        lineChart1();
        pieChartConnect();
        setTxtSearchByProduct();


    }

    public void setTxtSearchByProduct(){
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> descriptionList = ProductRepo.getDescription();
            for (String description: descriptionList) {
                obList.add(description);
            }

            TextFields.bindAutoCompletion(txtSearchByProduct,obList);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        /*try {
            possibleSuggestion = ProductRepo.getDescription();

            possibleSugg = new HashSet<>(Arrays.asList(possibleSuggestion));
            TextFields.bindAutoCompletion(txtSearchByProduct,possibleSugg);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }*/



    }


    private void setDatetime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd       HH:mm:ss");
                    String formattedDateTime = now.format(formatter);
                    lblDate.setText(formattedDateTime);
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.mainNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Bakery Management System");
        /*Image icon = new Image(this.getClass().getResourceAsStream("/icon/sns-removebg-preview.png"));
        stage.getIcons().add(icon);*/

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

    public void txtSet(){

    }


  /*  public void lineChart(){
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Bakery");

    PreparedStatement stm = null;
        try {
        stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT date, SUM(totalAmount) AS totalAmountSum\n" +
                "FROM payment\n" +
                "WHERE date >= CURDATE() - INTERVAL 6 DAY\n" +
                "GROUP BY date\n" +
                "ORDER BY date ASC;");
    } catch (SQLException e) {
        e.printStackTrace();
    }

    ResultSet rst = null;
        try {
        rst = stm.executeQuery();
    } catch (SQLException e) {
        e.printStackTrace();
    }

        while (true) {
        try {
            if (!rst.next()) break;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String date = null;
        try {
            date = rst.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int count = 0;
        try {
            count = rst.getInt(2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        series1.getData().add(new XYChart.Data<>(date, count));
    }
        barChart.getData().addAll(series1);
    }*/

    public void lineChart1(){
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Bakery");
        List<DailyRevenueTm> dailyRevenueTmList = DashboardRepo.getDateCount();

        for (DailyRevenueTm dailyRevenue: dailyRevenueTmList) {
            series1.getData().add(new XYChart.Data<>(dailyRevenue.getDate(),dailyRevenue.getCount()));
        }
        barChart.getData().addAll(series1);
        }





    public void setUsername(String username) {

        lblUserName.setText(username);
    }

    public void pieChartConnect() throws SQLException {
        //ObservableList<MostSellItemTm> obList = FXCollections.observableArrayList();

        List<MostSellItemTm> itemList = DashboardRepo.getMostSellItem();

        for (MostSellItemTm sellItem : itemList) {

            ObservableList<PieChart.Data> pieChartData =
                    FXCollections.observableArrayList(
                            new PieChart.Data(sellItem.getName(), sellItem.getQty())
                    );

            pieChart.getData().addAll(pieChartData);
        }
    }

    @FXML
    void txtSearchByProductOnAction(ActionEvent event) {
        String desc = txtSearchByProduct.getText();

        if (!desc.isEmpty()) {
            try {
                int qty = DashboardRepo.getProductSold(desc);
                txtProductSoldSearch.setText(String.valueOf(qty));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void txtSearchBydateOnAction(ActionEvent event) {
        if (!txtSearchByDate.getText().isEmpty()) {
            try {
                double dailyRevenue = DashboardRepo.getDailyRevenue(txtSearchByDate.getText());
                txtDailyRevenueSearch.setText(String.valueOf(dailyRevenue));
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"Input a date").show();
        }
    }

    @FXML
    void getDate(ActionEvent event) {
        LocalDate myDate = datePicker.getValue();
        String myFormattedDate = myDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        txtSearchByDate.setText(myFormattedDate);
        txtSearchByDate.requestFocus();
    }


    /*public void lineChart1(){
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Bakery");

        PreparedStatement stm = null;
        try {
            stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT\n" +
                    "    date,\n" +
                    "    COUNT(orderId) AS orderCount\n" +
                    "FROM\n" +
                    "    orders\n" +
                    "WHERE\n" +
                    "    date >= CURDATE() - INTERVAL 6 DAY  -- Select data for the last 7 days\n" +
                    "GROUP BY\n" +
                    "    date\n" +
                    "ORDER BY\n" +
                    "    date ASC;\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rst = null;
        try {
            rst = stm.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                if (!rst.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String date = null;
            try {
                date = rst.getString(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            int count = 0;
            try {
                count = rst.getInt(2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            series1.getData().add(new XYChart.Data<>(date, count));
        }
        barChart1.getData().addAll(series1);
    }*/


}
