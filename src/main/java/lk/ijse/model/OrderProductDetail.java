package lk.ijse.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class OrderProductDetail {
    String orderId;
    String productId;

    double unitPrice;
     int qty;

     double total;


}
