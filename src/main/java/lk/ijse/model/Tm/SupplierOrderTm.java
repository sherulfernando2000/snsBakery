package lk.ijse.model.Tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierOrderTm  {
    private String sId;
    private String sName;

    private String iId;
    private String iName;
    private Date date;

    private int qty;
    private double price;
    private double total;

}
