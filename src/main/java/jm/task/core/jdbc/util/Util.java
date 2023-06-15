package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/mysql";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static SessionFactory SESSION_FACTORY;


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
    public static SessionFactory getSessionFactory() {

            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, DRIVER);
                settings.put(Environment.URL, CONNECTION_URL);
                settings.put(Environment.USER, USERNAME);
                settings.put(Environment.PASS, PASSWORD);

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                SESSION_FACTORY = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                System.out.println("Проблема создания Session Factory");
                e.printStackTrace();
            }
        return SESSION_FACTORY;
    }
}
