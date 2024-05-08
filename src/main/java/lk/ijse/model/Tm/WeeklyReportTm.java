package lk.ijse.model.Tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WeeklyReportTm {

    private Date weekStart;

    private Date weekEnd;

    private int orders;

    private double total;
}
