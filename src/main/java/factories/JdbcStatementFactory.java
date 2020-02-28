package factories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcStatementFactory {
    private static Statement statement;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/adapterDataBase",
                    "postgres",
                    "postgres");

            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Statement getStatement() {
        return statement;
    }
}
