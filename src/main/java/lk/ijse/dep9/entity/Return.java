package lk.ijse.dep9.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Return implements SuperEntity {
  Date date;
  ReturnPK returnPK;

    public Return(Date date, int issueId,String isbn) {
        this.date = date;
        this.returnPK = new ReturnPK(issueId,isbn);
    }
}
