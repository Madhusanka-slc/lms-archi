package lk.ijse.dep9.service.util;

import java.sql.SQLException;

@FunctionalInterface
public interface ExecutionContextOld {

    void consume() throws SQLException;


}
