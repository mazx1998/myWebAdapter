package database.dao;

import database.entities.RequestsEntity;
import database.utils.HibernateSessionUtil;
import org.hibernate.Session;

import java.util.List;

/**
 * @author Максим Зеленский
 */
public class RequestDataAccess extends MainDataAccess<RequestsEntity> {

    public RequestDataAccess(Class<RequestsEntity> type) {
        super(type);
    }

    public List<RequestsEntity> getByAuthorName(String value, String orderField, boolean desc) {
        List<RequestsEntity> result = null;
        try (Session session = HibernateSessionUtil.getSession()) {
            result = session
                    .createQuery(
                            "from " + RequestsEntity.class.getSimpleName() +
                                    " as e WHERE e.userId.login = '" + value
                                    + "' order by e." + orderField + " " + (desc ? "desc" : "asc"),
                            RequestsEntity.class)
                    .getResultList();
            LOGGER.info("IN getByAuthorName: " + RequestsEntity.class.getSimpleName() + " where  author  = " + value + " was found");
        } catch (Exception e) {
            LOGGER.severe("IN getByAuthorName: exception " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public List<RequestsEntity> getByAuthorName(int offset, int limit, String value, String orderField, boolean desc) {
        List<RequestsEntity> result = null;
        try (Session session = HibernateSessionUtil.getSession()) {
            result = session
                    .createQuery(
                            "from " + RequestsEntity.class.getSimpleName() +
                                    " as e WHERE e.userId.login = '" + value
                                    + "' order by e." + orderField + " " + (desc ? "desc" : "asc"),
                            RequestsEntity.class)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
            LOGGER.info("IN getByAuthorName: " + RequestsEntity.class.getSimpleName() + " where  author  = " + value + " was found");
        } catch (Exception e) {
            LOGGER.severe("IN getByAuthorName: exception " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
