package dao;

import entities.MainEntity;
import factories.HibernateSessionFactoryUtil;
import factories.JdbcStatementFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataAccessObject<T extends MainEntity> {

    private final Class<T> type;

    public DataAccessObject(Class<T> type) {
        this.type = type;
    }

    public Class<T> getMyType() {
        return this.type;
    }

    public T findByField(String field, String fieldValue) {
        /*Session session = HibernateSessionFactoryUtil.getSession();
        Criteria criteria = session.createCriteria(UsersEntity.class);
        UsersEntity foundUser = (UsersEntity)criteria.add(Restrictions.eq("login", name))
                .uniqueResult();*/
        ResultSet resultSet;
        int rowId = 0;
        try {
            resultSet = JdbcStatementFactory.getStatement()
                    .executeQuery("SELECT * from users where  "+  field + " = '" + fieldValue + "';");

            while (resultSet.next()) {
                rowId = Integer.parseInt(resultSet.getString("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return HibernateSessionFactoryUtil.getSession().get(type, rowId);
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
