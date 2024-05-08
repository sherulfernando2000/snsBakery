package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.db.DbConnection;
import lk.ijse.model.Tm.WeeklyReportTm;
import lk.ijse.repository.weeklyReportRepo;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class WeeklyReportFormController {

    @FXML
    private AreaChart<?, ?> barChart1;

    @FXML
    private JFXButton btnDailyIncome;

    @FXML
    private TableColumn<?, ?> colOrders;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colWeekEnd;

    @FXML
    private TableColumn<?, ?> colWeekStart;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<WeeklyReportTm> tableWeeklyReport;

    public void initialize(){
        setCellValueFactory();
        loadAllWeeklyReport();
        lineChart1();
    }

    private void setCellValueFactory() {
        colWeekStart.setCellValueFactory(new PropertyValueFactory<>("weekStart"));
        colWeekEnd.setCellValueFactory(new PropertyValueFactory<>("weekEnd"));
        colOrders.setCellValueFactory(new PropertyValueFactory<>("orders"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    private void loadAllWeeklyReport(){
        ObservableList<WeeklyReportTm> obList = FXCollections.observableArrayList();
        try {
            List<WeeklyReportTm> repoList = weeklyReportRepo.getAll();
            for (WeeklyReportTm weeklyReportTm: repoList) {
                WeeklyReportTm tm = new WeeklyReportTm(
                        weeklyReportTm.getWeekStart(),
                        weeklyReportTm.getWeekEnd(),
                        weeklyReportTm.getOrders(),
                        weeklyReportTm.getTotal()
                );

                obList.add(tm);
            }

            tableWeeklyReport.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void lineChart1(){
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Bakery");

        PreparedStatement stm = null;
        try {
            stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT\n" +
                    "    DATE_FORMAT(MIN(o.date), '%Y-%m-%d') AS WeekStartDate,\n" +
                    "    DATE_FORMAT(MAX(o.date), '%Y-%m-%d') AS WeekEndDate,\n" +
                    "    COUNT(*) AS WeeklyOrders,\n" +
                    "    SUM(o.totalAmount) AS TotalAmount\n" +
                    "FROM\n" +
                    "    orders o\n" +
                    "WHERE\n" +
                    "    o.date BETWEEN (SELECT MIN(date) FROM orders) AND (SELECT MAX(date) FROM orders)\n" +
                    "GROUP BY\n" +
                    "    YEARWEEK(o.date, 1)\n" +
                    "ORDER BY\n" +
                    "    WeekStartDate;\n");
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
                date = rst.getString(2);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            int count = 0;
            try {
                count = rst.getInt(4);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            series1.getData().add(new XYChart.Data<>(date, count));
        }
        barChart1.getData().addAll(series1);
    }


    @FXML
    void btnDailyIncomeOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/View/report.fxml"));
        this.rootNode.getChildren().removeAll();
        this.rootNode.getChildren().setAll(rootNode);
    }






}
