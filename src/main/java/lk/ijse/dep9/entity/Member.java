package lk.ijse.dep9.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member implements SuperEntity {
    String id;
    String name;
    String address;
    String contact;
}
