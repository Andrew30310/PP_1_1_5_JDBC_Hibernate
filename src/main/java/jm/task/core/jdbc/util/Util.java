package jm.task.core.jdbc.util;

import javax.xml.stream.FactoryConfigurationError;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String username = "root";
    private static final String password = "root";
    private static final String connectionUrl = "jdbc:mysql://localhost:3306/mysql";
    private static final String driver = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection connection;
        Class.forName(driver);
        return connection = DriverManager.getConnection(connectionUrl, username, password);

    }
}
