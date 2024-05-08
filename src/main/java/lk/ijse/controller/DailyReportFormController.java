package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.XYChart;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lk.ijse.db.DbConnection;
import lk.ijse.model.Tm.DailyReportTm;
import lk.ijse.repository.DailyReportRepo;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.chart.AreaChart;

public class DailyReportFormController {

    @FXML
    private JFXButton btnSupplier;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableColumn<?, ?> colDay;

    @FXML
    private TableColumn<?, ?> colDesc;

    @FXML
    private TableColumn<?, ?> colSold;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableView<DailyReportTm> tableDailyReport;

    @FXML
    private AreaChart<?, ?> barChart;


    public void initialize(){
        setCellValueFactory();
        loadAllDailyReport();
        lineChart();
    }

    private void setCellValueFactory() {
        colDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colSold.setCellValueFactory(new PropertyValueFactory<>("sold"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    private void loadAllDailyReport(){
        ObservableList<DailyReportTm> obList = FXCollections.observableArrayList();
        try {
            List<DailyReportTm> repoList = DailyReportRepo.getAll();
            for (DailyReportTm dailyReportTm : repoList) {
                DailyReportTm tm = new DailyReportTm(
                        dailyReportTm.getDay(),
                        dailyReportTm.getDesc(),
                        dailyReportTm.getSold(),
                        dailyReportTm.getTotal()
                );

                obList.add(tm);
            }

            tableDailyReport.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnMonthlyIncomeOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/View/weekly_report_form.fxml"));
        this.rootNode.getChildren().removeAll();
        this.rootNode.getChildren().setAll(rootNode);
    }

    public void lineChart(){
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
    }

}
