package database;

import database.entities.MainEntity;
import database.factories.HibernateSessionFactoryUtil;
import database.factories.JdbcStatementFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Table;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DataAccessObject<T extends MainEntity> {

    private final Class<T> type;
    private final String tableName;

    public DataAccessObject(Class<T> type) {
        this.type = type;
        tableName = type.getAnnotation(Table.class).name();
    }

    public Class<T> getMyType() {
        return this.type;
    }

    public T findByField(String field, String fieldValue) {

        ResultSet resultSet;
        int rowId = 0;
        try {
            resultSet = JdbcStatementFactory.getStatement()
                    .executeQuery("SELECT * FROM "
                            + tableName
                            + " WHERE  "
                            +  field
                            + " = '" + fieldValue + "';");

            while (resultSet.next()) {
                rowId = Integer.parseInt(resultSet.getString("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return HibernateSessionFactoryUtil.getSession().get(type, rowId);
    }

    public List<T> findAll() {
        return HibernateSessionFactoryUtil
                .getSession()
                .createQuery("select a from " + type.getSimpleName() + " a", type)
                .getResultList();
    }

    public T findById(int id) {
        return HibernateSessionFactoryUtil.getSession().get(type, id);
    }



    public void create(T obj) {
        Session session = HibernateSessionFactoryUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.save(obj);
        transaction.commit();
        session.close();
    }

    public void update(T obj) {
        Session session = HibernateSessionFactoryUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.update(obj);
        transaction.commit();
        session.close();
    }

    public void delete(T obj) {
        Session session = HibernateSessionFactoryUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.delete(obj);
        transaction.commit();
        session.close();
    }
}
