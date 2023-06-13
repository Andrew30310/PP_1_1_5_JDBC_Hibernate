package jm.task.core.jdbc.util;

import javax.xml.stream.FactoryConfigurationError;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/mysql";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() {
        Connection connection;
        try {
            Class.forName(DRIVER);
            return connection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Ошибка подключения");
            throw new RuntimeException(e);
        }
    }
}
