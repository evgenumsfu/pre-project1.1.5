package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory;

    private final String SQL_CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS usersnew (id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            " name VARCHAR(255), lastName VARCHAR(255), age TINYINT)");
    private final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS usersnew";
    private Transaction transaction = null;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getConnectedHibernate();
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(SQL_CREATE_TABLE).executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");
        } catch (HibernateException e) {
            System.out.println("Ошибка " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(SQL_DROP_TABLE).executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");
        } catch (HibernateException e) {
            System.out.println("Ошибка " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Ошибка " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
            System.out.println("User с id = " + id + " удален");
        } catch (HibernateException e) {
            System.out.println("При удалении User с id = " + id + " произошла ошибка: " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("FROM User", User.class).list();
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Ошибка " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
            System.out.println("Таблица очищена");
        } catch (HibernateException e) {
            System.out.println("Ошибка " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}