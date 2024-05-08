package lk.ijse.model.Tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DailyReportTm {
    private Date day;

    private String desc;

    private int sold;

    private double total;
}
