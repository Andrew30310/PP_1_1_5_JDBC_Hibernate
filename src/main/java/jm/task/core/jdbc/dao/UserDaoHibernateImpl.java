package jm.task.core.jdbc.dao;

import com.mysql.cj.Query;
import jm.task.core.jdbc.model.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jpa.HibernateQuery;

import java.sql.SQLException;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS `users`.`users_table` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT, \n " +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastName` VARCHAR(45) NOT NULL,\n" +
                    "  `age` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id`));")
                    .addEntity(User.class)
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица создана");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS `users`.`users_table`;")
                    .addEntity(User.class)
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица удалена");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = getSessionFactory().getCurrentSession()) {
            User newUser = new User(name, lastName, age);
            session.beginTransaction();
            session.save(newUser);
            session.getTransaction().commit();
            System.out.printf("User с именем - %s добавлен в базу данных\n", name);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            User removedUser = session.get(User.class, id);
            session.delete(removedUser);
            session.getTransaction().commit();
            System.out.printf("User с id - %s удален из базы данных\n", id);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            List userList = session.createQuery("from User")
                            .getResultList();
            session.getTransaction().commit();
            return userList;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("delete User")
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица очищена");
        }
    }
}
