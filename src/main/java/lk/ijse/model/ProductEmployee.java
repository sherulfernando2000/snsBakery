package lk.ijse.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductEmployee {
   private String employeeId ;
   private String productId ;
   private String assignmentType;
}
