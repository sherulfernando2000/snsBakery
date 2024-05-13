package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Tm.DailyReportTm;
import lk.ijse.model.Tm.WeeklyReportTm;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class weeklyReportRepo {

    public static List<WeeklyReportTm> getAll() throws SQLException {
        String sql = "SELECT\n" +
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
                "    WeekStartDate;\n";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<WeeklyReportTm> weeklyRepoList = new ArrayList<>();

        while (resultSet.next()){
            Date weekStart = Date.valueOf(resultSet.getString(1));
            Date weekEnd = Date.valueOf(resultSet.getString(2));
            int orders = Integer.parseInt(resultSet.getString(3));
            double total = Double.parseDouble(resultSet.getString(4));

            WeeklyReportTm weeklyReportTm = new WeeklyReportTm(weekStart,weekEnd,orders, total);
            weeklyRepoList.add(weeklyReportTm);
        }
        return weeklyRepoList;
    }

    public static double getMonthlyRevenue() throws SQLException {
        String sql = "SELECT\n" +
                "    DATE_FORMAT(date, '%Y-%m') AS PaymentMonth,\n" +
                "    SUM(totalAmount) AS MonthlyRevenue\n" +
                "FROM\n" +
                "    payment\n" +
                "WHERE\n" +
                "    YEAR(date) = YEAR(CURDATE()) AND MONTH(date) = MONTH(CURDATE())\n" +
                "GROUP BY\n" +
                "    DATE_FORMAT(date, '%Y-%m')\n" +
                "ORDER BY\n" +
                "    PaymentMonth;\n";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();


        double total =0;
        if (resultSet.next()){

            total = Double.parseDouble(resultSet.getString(2));

        }
        return total;
    }
}
