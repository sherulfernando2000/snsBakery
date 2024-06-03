package lk.ijse.repository;

import javafx.scene.chart.XYChart;
import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;
import lk.ijse.model.Product;
import lk.ijse.model.Tm.DailyRevenueTm;
import lk.ijse.model.Tm.MostSellItemTm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardRepo {
    public static List<MostSellItemTm> getMostSellItem() throws SQLException {
        String sql = "SELECT p.pName AS MostSoldProductName, SUM(opd.qty) AS TotalQuantitySold\n" +
                "FROM orderProductDetail opd\n" +
                "JOIN product p ON opd.productId = p.productId\n" +
                "GROUP BY opd.productId, p.pName\n" +
                "ORDER BY SUM(opd.qty) DESC\n" +
                "LIMIT 6;\n" +
                "\n";;
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<MostSellItemTm> itemList = new ArrayList<>();

        while (resultSet.next()) {
            String name= resultSet.getString(1);
            int qty = Integer.parseInt(resultSet.getString(2));


            MostSellItemTm mostSellItemTm = new MostSellItemTm(name, qty);
            itemList.add(mostSellItemTm);
        }
        return itemList;

    }


    public static double getDailyRevenue(String text) throws SQLException {
        String sql = "SELECT date, SUM(totalAmount) AS DailyRevenue\n" +
                "FROM payment\n" +
                "WHERE date = ?\n" +
                "GROUP BY date;\n";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1,text);

        ResultSet resultSet = pstm.executeQuery();

        double dailyRevenue=0;
        while (resultSet.next()) {
            dailyRevenue = Double.parseDouble(resultSet.getString(2));

        }
        return dailyRevenue;
    }


    public static int getProductSold(String desc) throws SQLException {
        String sql = "SELECT p.pName AS ProductName, SUM(opd.qty) AS QuantitySold\n" +
                "FROM orderProductDetail opd\n" +
                "JOIN product p ON opd.productId = p.productId\n" +
                "WHERE p.pName = ? \n" +
                "GROUP BY p.pName;\n";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1,desc);

        ResultSet resultSet = pstm.executeQuery();

        int qty =0;
        while (resultSet.next()) {
            qty = Integer.parseInt(resultSet.getString(2));

        }
        return qty;
    }

    public static List<DailyRevenueTm> getDateCount() {
        List<DailyRevenueTm> dailyRevenueTmList = new ArrayList<>();
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
           DailyRevenueTm dailyRevenueTm = new DailyRevenueTm(date,count);
            dailyRevenueTmList.add(dailyRevenueTm);
        }
        return dailyRevenueTmList;
    }
}


