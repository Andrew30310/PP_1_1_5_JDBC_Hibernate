package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Иван", "Иванов", (byte) 54);
        service.saveUser("Петр", "Петров", (byte) 30);
        service.saveUser("Сергей", "Сергеев", (byte) 15);
        service.saveUser("John", "Doe", (byte) 25);
        System.out.println(service.getAllUsers());
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
