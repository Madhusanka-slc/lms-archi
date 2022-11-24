package lk.ijse.dep9.entity;


import com.sun.source.doctree.SerialDataTree;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueItemPK implements Serializable {
    private int issueId;
    private String isbn;
}
