package database;

import database.entities.MainEntity;
import database.utils.HibernateSessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Table;
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

    public List<T> findAll() {
        return HibernateSessionUtil
                .getSession()
                .createQuery("select e from " + type.getSimpleName() + " e", type)
                .getResultList();
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
}
