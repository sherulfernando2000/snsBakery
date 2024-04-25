package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment {
    String paymentId;
    String paymentMethod ;
    String date ;
    double discountAmount;
    double totalAmount;
    String orderId;

    String discountType;
    double discountPrecentage;

}
