package convenientQueue.sqlDataAccess.repository;

import convenientQueue.logic.model.LoginRequest;
import convenientQueue.logic.repository.IUserRepository;
import convenientQueue.sqlDataAccess.SqlQueryExecutor;

import java.sql.ResultSet;
import java.util.concurrent.atomic.AtomicInteger;

public class SqlUserRepository implements IUserRepository {
    private final String connectionString;

    public SqlUserRepository(String connectionString) {
        this.connectionString = connectionString;
    }

    @Override
    public int GetUserId(LoginRequest loginRequest) {
        AtomicInteger result = new AtomicInteger();
        SqlQueryExecutor.executePreparedStatement(connectionString, "GetUserId (?, ?)",
        (preparedStatement)-> {
            preparedStatement.setString(1, loginRequest.getLogin());
            preparedStatement.setString(2, loginRequest.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                result.set(resultSet.getInt(1));
        });

        return result.get();
    }
}
