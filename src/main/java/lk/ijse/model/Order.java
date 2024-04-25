package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Order {
   private String orderId;

   private String status;

   private Date date;
   private double totalAmount;

   private String customerId;
}
