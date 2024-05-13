package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;
import lk.ijse.model.Tm.DailyReportTm;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DailyReportRepo {

    public static List<DailyReportTm> getAll() throws SQLException {
        String sql = "SELECT\n" +
                "    o.date AS Date,\n" +
                "    p.pName AS ProductName,\n" +
                "    SUM(opd.qty) AS TotalProductsSold,\n" +
                "    SUM(opd.total) AS TotalIncome\n" +
                "FROM\n" +
                "    orders o\n" +
                "JOIN\n" +
                "    payment pm ON o.orderId = pm.orderId\n" +
                "JOIN\n" +
                "    orderproductdetail opd ON o.orderId = opd.orderId\n" +
                "JOIN\n" +
                "    product p ON opd.productId = p.productId\n" +
                "GROUP BY\n" +
                "    o.date,\n" +
                "    p.pName\n" +
                "ORDER BY\n" +
                "    o.date;\n";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<DailyReportTm> dailyRepoList = new ArrayList<>();

        while (resultSet.next()){
            Date day = Date.valueOf(resultSet.getString(1));
            String desc = resultSet.getString(2);
            int sold = Integer.parseInt(resultSet.getString(3));
            double total = Double.parseDouble(resultSet.getString(4));

            DailyReportTm dailyReportTm = new DailyReportTm(day,desc,sold, total);
            dailyRepoList.add(dailyReportTm);
        }
        return dailyRepoList;
    }

    public static double getDailyRevenue() throws SQLException {
        String sql = "SELECT\n" +
                "    DATE_FORMAT(date, '%Y-%m-%d') AS PaymentDate,\n" +
                "    SUM(totalAmount) AS DailyRevenue\n" +
                "FROM\n" +
                "    payment\n" +
                "WHERE\n" +
                "    date = CURDATE()\n" +
                "GROUP BY\n" +
                "    date;\n";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();


        double total =0;
        if (resultSet.next()){

            total = Double.parseDouble(resultSet.getString(2));

        }
        return total;
    }
}
