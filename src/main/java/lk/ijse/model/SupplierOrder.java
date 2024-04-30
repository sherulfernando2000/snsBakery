package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierOrder {
   private String ingredientId;
   private String supplierId;

   private Date date;
   private int qty;
   private double price;
   private double total;
}
