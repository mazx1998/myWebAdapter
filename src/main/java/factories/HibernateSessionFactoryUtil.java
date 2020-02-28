package factories;

import dao.DataAccessObject;
import entities.ResponsesEntityImpl;
import entities.UsersEntityImpl;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("main/java/resources/hibernate.cfg.xml");

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {

        //Users
        DataAccessObject<UsersEntityImpl> usersEntityDataAccessObject
                = new DataAccessObject<UsersEntityImpl>(UsersEntityImpl.class);

        UsersEntityImpl newUser = new UsersEntityImpl("NewUser1", "NewPassword");
        usersEntityDataAccessObject.create(newUser);
        //System.out.println(newUser);


        //Responses
        DataAccessObject<ResponsesEntityImpl> respDAO
                = new DataAccessObject<ResponsesEntityImpl>(ResponsesEntityImpl.class);

        /*ResponsesEntityImpl responsesEntity = respDAO.findById(3);
        System.out.println(responsesEntity);*//*

        //System.out.println(userDaoImpl.findByName("Maks"));

        *//*ResponsesEntityImpl responses = getSession().get(RequestsEntityImpl.class, 1).getResponsesById();
        System.out.println(responses.getSnils());*/
    }

}
