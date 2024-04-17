package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Customer {
   private String id;
   private String name;
   private String tel;
   private String address;

   public Customer(String id, String name, String tel, String address) {
      this.id = id;
      this.name = name;
      this.tel = tel;
      this.address = address;
   }
}
