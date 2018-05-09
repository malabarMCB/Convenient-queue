package convenientQueue.sqlDataAccess.functionalInterface;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementFunctionalInterface {
    void run(PreparedStatement preparedStatement) throws SQLException;
}
