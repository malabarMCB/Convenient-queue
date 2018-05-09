package convenientQueue.sqlDataAccess;

import convenientQueue.sqlDataAccess.functionalInterface.*;

import java.sql.*;

public class SqlQueryExecutor {
    public static ResultSet executePreparedStatement(String connectionString, String procedure,PreparedStatementFunctionalInterface configureCallback) {
        ResultSet result = null;

        Connection connection = getConnection(connectionString);
        if(connection == null)
            return result;

        try {
            PreparedStatement prepearedStatement = connection.prepareStatement(String.format("{call %s}",procedure));
            configureCallback.run(prepearedStatement);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static Connection getConnection(String connectionString) {
        Connection connection = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}
