package database.dao;

import database.utils.HibernateSessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author Максим Зеленский
 */
public class MainDataAccess<T> {

    protected final Logger LOGGER = Logger.getLogger(MainDataAccess.class.getName());

    private final Class<T> type;

    protected MainDataAccess(Class<T> type) {
        this.type = type;
    }

    public T findById(int id) {
        T result = null;
        try (Session session = HibernateSessionUtil.getSession()) {
            result = session.get(type, id);
            LOGGER.info("IN findById: " + type.getSimpleName() + " with id " + id + " was found");
        } catch (Exception e) {
            LOGGER.severe("IN findById: exception " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public List<T> findByField(String field, Object value) {
        List<T> result = null;
        try (Session session = HibernateSessionUtil.getSession()) {
            result = session
                    .createQuery("select e from " + type.getSimpleName() + " e " +
                            "WHERE e." + field + " = '" + value + "'", type)
                    .getResultList();
            LOGGER.info("IN findByField: " + type.getSimpleName() + " where " + field + " = " + value + " was found");
        } catch (Exception e) {
            LOGGER.severe("IN findByField: exception " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public List<T> findByField(String field, Object value, String orderField, boolean desc) {
        List<T> result = null;
        try (Session session = HibernateSessionUtil.getSession()) {
            result = session
                    .createQuery("select e from " + type.getSimpleName() + " e " +
                            "WHERE e." + field + " = '" + value + "'"
                             + " order by e." + orderField + " " + (desc ? "desc" : "asc"),
                            type)
                    .getResultList();
            LOGGER.info("IN findByField: " + type.getSimpleName() + " where " + field + " = " + value + " was found");
        } catch (Exception e) {
            LOGGER.severe("IN findByField: exception " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public List<T> findByField(int offset, int limit, String field, Object value) {
        List<T> result = null;
        try (Session session = HibernateSessionUtil.getSession()) {
            result = session
                    .createQuery(
                            "select e from " + type.getSimpleName() + " e " +
                            "WHERE e." + field + " = '" + value + "'", type)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
            LOGGER.info("IN findByField: " + type.getSimpleName() + " where " + field + " = " + value + " was found");
        } catch (Exception e) {
            LOGGER.severe("IN findByField: exception " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public List<T> findByField(int offset, int limit, String field, Object value, String orderField, boolean desc) {
        List<T> result = null;
        try (Session session = HibernateSessionUtil.getSession()) {
            result = session
                    .createQuery(
                            "select e from " + type.getSimpleName() + " e " +
                            "WHERE e." + field + " = '" + value + "' "
                            + " order by e." + orderField + " " + (desc ? "desc" : "asc"),
                            type)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
            LOGGER.info("IN findByField: " + type.getSimpleName() + " where " + field + " = " + value + " was found");
        } catch (Exception e) {
            LOGGER.severe("IN findByField: exception " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public List<T> findAll() {
        List<T> result = null;
        try (Session session = HibernateSessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            result = session
                    .createQuery("from " + type.getSimpleName(), type)
                    .getResultList();
            transaction.commit();
            LOGGER.info("IN findAll: " + type.getSimpleName() + " was found");
        } catch (Exception e) {
            LOGGER.severe("IN findAll: exception " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public List<T> findAll(String orderField, boolean desc) {
        List<T> result = null;
        try (Session session = HibernateSessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            result = session
                    .createQuery(
                            "from " + type.getSimpleName() + " order by "
                            + orderField + " " + (desc ? "desc" : "asc"),
                            type)
                    .getResultList();
            transaction.commit();
            LOGGER.info("IN findAll: " + type.getSimpleName() + " was found");
        } catch (Exception e) {
            LOGGER.severe("IN findAll: exception " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public List<T> findAll(int offset, int limit) {
        List<T> result = null;
        try (Session session = HibernateSessionUtil.getSession()) {
            result = session
                    .createQuery("from " + type.getSimpleName(), type)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
            LOGGER.info("IN findAll: " + type.getSimpleName() + " was found");
        } catch (Exception e) {
            LOGGER.severe("IN findAll: exception " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public List<T> findAll(int offset, int limit, String orderField, boolean desc) {
        List<T> result = null;
        try (Session session = HibernateSessionUtil.getSession()) {
            result = session
                    .createQuery(
                            "from " + type.getSimpleName() + " order by "
                                    + orderField + " " + (desc ? "desc" : "asc"),
                            type)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
            LOGGER.info("IN findAll: " + type.getSimpleName() + " was found");
        } catch (Exception e) {
            LOGGER.severe("IN findAll: exception " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public void create(T obj) {
        try (Session session = HibernateSessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(obj);
            transaction.commit();
            LOGGER.info("IN create: " + type.getSimpleName() + " was created");
        } catch (Exception e) {
            LOGGER.severe("IN create: exception " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update(T obj) {
        try (Session session = HibernateSessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(obj);
            transaction.commit();
            LOGGER.info("IN update: " + type.getSimpleName() + " was updated");
        } catch (Exception e) {
            LOGGER.severe("IN update: exception " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(T obj) {
        try (Session session = HibernateSessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(obj);
            transaction.commit();
            LOGGER.info("IN delete: " + type.getSimpleName() + " was deleted" );
        } catch (Exception e) {
            LOGGER.severe("IN delete: exception " + e.getMessage());
            e.printStackTrace();
        }
    }
}
