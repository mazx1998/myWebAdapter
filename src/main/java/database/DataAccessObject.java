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

    public List<T> findByFields(List<String> fields, List<String> fieldValues, int pageNumber, int pageSize) {
        StringBuilder query = new StringBuilder("select e from ");
        query.append(type.getSimpleName());
        query.append(" e WHERE ");
        for (int i = 0; i < fields.size(); i++) {
            query.append("e.").append(fields.get(i))
                 .append(" = '").append(fieldValues.get(i));

            if (i < fields.size() - 1) {
                query.append("' AND ");
            } else {
                query.append("'");
            }
        }

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

    public List<T> findAll(int pageNumber, int pageSize) {
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
