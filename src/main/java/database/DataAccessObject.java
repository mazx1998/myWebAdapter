package database;

import database.entities.MainEntity;
import database.utils.HibernateSessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DataAccessObject<T extends MainEntity> {

    private final Class<T> type;

    public DataAccessObject(Class<T> type) {
        this.type = type;
    }

    public Class<T> getMyType() {
        return this.type;
    }

    public List<T> findByField(String field, String fieldValue) {
        List<T> result = null;
        try (Session session = HibernateSessionUtil.getSession()) {
            result = session
                    .createQuery("select e from " + type.getSimpleName() + " e " +
                            "WHERE e." + field + " = '" + fieldValue + "'", type)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<T> findByFieldLimited(String field, String fieldValue, int pageNumber, int pageSize) {
        List<T> result = null;
        try (Session session = HibernateSessionUtil.getSession()) {
            result = session
                    .createQuery("select e from " + type.getSimpleName() + " e " +
                    "WHERE e." + field + " = '" + fieldValue + "'", type)
                    .setFirstResult((pageNumber - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<T> findByFieldWithKeyWordsLimited(List<String> keyWords, String field, int pageNumber, int pageSize) {
        StringBuilder query = getQueryLike(keyWords, field);
        List<T> result = null;
        try (Session session = HibernateSessionUtil.getSession()) {
            result = session
                    .createQuery(query.toString(), type)
                    .setFirstResult((pageNumber - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<T> findByFieldWithKeyWords(List<String> keyWords, String field) {
        StringBuilder query = getQueryLike(keyWords, field);
        List<T> result = null;
        try (Session session = HibernateSessionUtil.getSession()) {
            result = session
                    .createQuery(query.toString(), type)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private StringBuilder getQueryLike(List<String> keyWords, String field) {
        StringBuilder query = new StringBuilder("select e from ");
        query.append(type.getSimpleName());
        query.append(" e WHERE e.");
        query.append(field).append(" LIKE '");
        keyWords.forEach(tag -> query.append("%").append(tag));
        query.append("%'");
        return query;
    }

    public List<T> findAllLimited(int pageNumber, int pageSize) {
        List<T> result = null;
        try (Session session = HibernateSessionUtil.getSession()) {
            result = session
                    .createQuery("from " + type.getSimpleName(), type)
                    .setFirstResult((pageNumber - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public T findById(int id) {
        T result = null;
        try (Session session = HibernateSessionUtil.getSession()) {
            result = session.get(type, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void create(T obj) {
        Session session = HibernateSessionUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.save(obj);
        transaction.commit();
        session.close();
    }

    public void update(T obj) {
        Session session = HibernateSessionUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.update(obj);
        transaction.commit();
        session.close();
    }

    public void delete(T obj) {
        Session session = HibernateSessionUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.delete(obj);
        transaction.commit();
        session.close();
    }

    public Long getRowsCount() {
        Long result = null;
        try (Session session = HibernateSessionUtil.getSession()) {
            result = (Long)session
                    .createQuery("select count(*) from " + type.getSimpleName())
                    .iterate()
                    .next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
