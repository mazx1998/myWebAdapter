package com.app.database;

import com.app.database.entities.MainEntity;
import com.app.database.utils.HibernateSessionUtil;
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
        return HibernateSessionUtil
                .getSession()
                .createQuery("select e from " + type.getSimpleName() + " e " +
                        "WHERE e." + field + " = '" + fieldValue + "'", type)
                .getResultList();
    }

    public List<T> findByFieldLimited(String field, String fieldValue, int pageNumber, int pageSize) {
        try {
            return HibernateSessionUtil
                    .getSession()
                    .createQuery("select e from " + type.getSimpleName() + " e " +
                            "WHERE e." + field + " = '" + fieldValue + "'", type)
                    .setFirstResult((pageNumber - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        }  catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<T> findByFieldWithKeyWordsLimited(List<String> keyWords, String field, int pageNumber, int pageSize) {
        StringBuilder query = getQueryLike(keyWords, field);

        try {
            return HibernateSessionUtil
                    .getSession()
                    .createQuery(query.toString(), type)
                    .setFirstResult((pageNumber - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<T> findByFieldWithKeyWords(List<String> keyWords, String field) {
        StringBuilder query = getQueryLike(keyWords, field);

        try {
            return HibernateSessionUtil
                    .getSession()
                    .createQuery(query.toString(), type)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
        try {
            return HibernateSessionUtil
                    .getSession()
                    .createQuery("from " + type.getSimpleName(), type)
                    .setFirstResult((pageNumber - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<T> findAll() {
        try {
            return HibernateSessionUtil
                    .getSession()
                    .createQuery("from " + type.getSimpleName(), type)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public T findById(int id) {
        return HibernateSessionUtil.getSession().get(type, id);
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

    public long getRowsCount() {
        return  (Long)
                HibernateSessionUtil
                .getSession()
                .createQuery("select count(*) from " + type.getSimpleName())
                .iterate()
                .next();
    }
}
