package database.services.impl;

import database.entities.RequestsEntity;
import database.entities.UsersEntity;
import database.services.RequestService;
import database.services.UsersService;
import database.utils.HibernateSessionUtil;
import restapi.authorization.Roles;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;

public class RequestServiceImplTest {
    private final RequestService requestServiceTest = new RequestServiceImpl();
    private final UsersService usersServiceTest = new UsersServiceImpl();

    private final UsersEntity usersEntityTest
            = new UsersEntity("1","2", Roles.USER);

    private final RequestsEntity testRequestEntity
                = new RequestsEntity("testName",
                "testLastName",
                new Timestamp(11111),
                new Timestamp(11111),
                usersEntityTest
         );

    @Test
    public void create() {
        boolean actual = false;
        // Start 'create' method
        usersServiceTest.create(usersEntityTest);
        requestServiceTest.create(testRequestEntity);

        // If record was created in data base
        if (HibernateSessionUtil
                .getSession()
                .get(RequestsEntity.class, testRequestEntity.getId()) != null) {
            actual = true;

            // Remove created
            Session session = HibernateSessionUtil.getSession();
            Transaction transaction = session.beginTransaction();
            session.delete(testRequestEntity);
            session.delete(usersEntityTest);
            transaction.commit();
        }

        Assert.assertTrue(actual);
    }

    @Test
    public void getById() {
        boolean actual = false;
        //Add record that will find
        usersServiceTest.create(usersEntityTest);
        requestServiceTest.create(testRequestEntity);
        // Start 'getById' method
        RequestsEntity testRequestEntity1 =  requestServiceTest.getById(testRequestEntity.getId());
        // If found object and object from data base are equal
        if (testRequestEntity1.equals(testRequestEntity)) {
            actual = true;
        }
        // Remove created
        Session session = HibernateSessionUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(testRequestEntity);
        session.delete(usersEntityTest);
        transaction.commit();

        Assert.assertTrue(actual);
    }

    @Test
    public void getAll() {
        int pageNumber = 1;
        int pageSize = 20;
        // Get all requests entities using Hibernate session factory
        List<RequestsEntity> testList =
                HibernateSessionUtil.getSession()
                .createQuery("from RequestsEntity", RequestsEntity.class)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
        // Get all requests entities using method 'getAll'
        List<RequestsEntity> testList2 = requestServiceTest.getAll(pageNumber, pageSize);
        // Compare lists
        boolean actual = testList.equals(testList2);

        Assert.assertTrue(actual);
    }

    @Test
    public void update() {
        //Add record that we will change
        RequestsEntity testRequestEntity1 = new RequestsEntity(
                testRequestEntity.getRequestXml(),
                testRequestEntity.getResponseXml(),
                testRequestEntity.getRequestDate(),
                testRequestEntity.getResponseDate(),
                testRequestEntity.getUserId()
        );
        usersServiceTest.create(usersEntityTest);
        requestServiceTest.create(testRequestEntity1);
        // Change RequestXml in object
        testRequestEntity1.setRequestXml("AnotherRequestXml");
        // Try update it in data base
        Session session = HibernateSessionUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(testRequestEntity1);
        transaction.commit();
        session.close();
        // Get from data base
        RequestsEntity testRequestEntity2
                = requestServiceTest.getById(testRequestEntity1.getId());
        // Compare firstNames
        boolean actual = testRequestEntity1.getRequestXml().equals(testRequestEntity2.getRequestXml());

        // Remove created
        session = HibernateSessionUtil.getSession();
        transaction = session.beginTransaction();
        session.delete(testRequestEntity1);
        session.delete(usersEntityTest);
        transaction.commit();
        session.close();

        Assert.assertTrue(actual);
    }

    @Test
    public void delete() {
        // Create record
        usersServiceTest.create(usersEntityTest);
        requestServiceTest.create(testRequestEntity);
        // Delete it
        requestServiceTest.delete(testRequestEntity);
        usersServiceTest.delete(usersEntityTest);

        // Check if deleted
        RequestsEntity testRequestEntity1
                = requestServiceTest.getById(testRequestEntity.getId());
        boolean actual = testRequestEntity1 == null;

        Assert.assertTrue(actual);
    }

    @Test
    public void sandbox() {
        RequestsEntity requestsEntity = requestServiceTest.getById(15);
        System.out.println(requestsEntity.getRequestXml());
    }
}
