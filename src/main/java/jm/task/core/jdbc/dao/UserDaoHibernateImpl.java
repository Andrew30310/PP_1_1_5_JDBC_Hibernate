package jm.task.core.jdbc.dao;

import com.mysql.cj.Query;
import jm.task.core.jdbc.model.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jpa.HibernateQuery;

import java.sql.SQLException;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
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
        try (Session session = sessionFactory.getCurrentSession()) {
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
        Transaction tx = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            User newUser = new User(name, lastName, age);
            tx = session.beginTransaction();
            session.save(newUser);
            tx.commit();
            System.out.printf("User с именем - %s добавлен в базу данных\n", name);
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            tx = session.beginTransaction();
            User removedUser = session.get(User.class, id);
            session.delete(removedUser);
            tx.commit();
            System.out.printf("User с id - %s удален из базы данных\n", id);
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<User> getAllUsers() {
        List userList = null;
        Transaction tx = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            tx = session.beginTransaction();
            userList = session.createQuery("from User")
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            tx = session.beginTransaction();
            session.createQuery("delete User")
                    .executeUpdate();
            tx.commit();
            System.out.println("Таблица очищена");
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }
}
