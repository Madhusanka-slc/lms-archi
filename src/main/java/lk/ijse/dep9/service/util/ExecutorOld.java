package lk.ijse.dep9.service.util;

import java.sql.SQLException;

public class ExecutorOld {

    public static void execute(ExecutionContextOld e) {
        try {
            e.consume();
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }

    }
}
