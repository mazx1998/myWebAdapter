package database.services.impl;

import database.entities.BirthPlacesEntity;
import database.entities.PassportsEntity;
import database.entities.RequestsEntity;
import database.services.RequestService;
import database.utils.HibernateSessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class RequestServiceImplTest {
    private final RequestService requestServiceTest = new RequestServiceImpl();

    private final BirthPlacesEntity testBirthPlaceEntity = new BirthPlacesEntity(
            "ОСОБОЕ",
            "ГОРОД",
            "РАЙОН",
            "РЕГИОН",
            "СТРАНА"
    );
    private final PassportsEntity testPassportEntity = new PassportsEntity(
            "11111",
            "121313",
            new Date(1012),
            "ISSUER"
    );
    private final RequestsEntity testRequestEntity
                = new RequestsEntity("testName",
                "testLastName",
                "testPatronymic",
                "Мужской",
                new Date(2000, 1, 1),
                new Timestamp(11111),
                new Timestamp(11111),
            null,
            testBirthPlaceEntity,
            testPassportEntity);

    @Test
    public void create() {
        boolean actual = false;
        // Start 'create' method
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
            transaction.commit();
        }

        Assert.assertTrue(actual);
    }

    @Test
    public void getById() {
        boolean actual = false;
        //Add record that will find
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
                testRequestEntity.getFirstName(),
                testRequestEntity.getLastName(),
                testRequestEntity.getPatronymic(),
                testRequestEntity.getGender(),
                testRequestEntity.getBirthDate(),
                testRequestEntity.getReqDate(),
                testRequestEntity.getRespDate(),
                testRequestEntity.getSnils(),
                testRequestEntity.getBirthplacesByBirthPlaceId(),
                testRequestEntity.getPassportsByPassportId()
        );
        requestServiceTest.create(testRequestEntity1);
        // Change firstName in object
        testRequestEntity1.setFirstName("AnotherFirstName");
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
        boolean actual = testRequestEntity1.getFirstName().equals(testRequestEntity2.getFirstName());

        // Remove created
        session = HibernateSessionUtil.getSession();
        transaction = session.beginTransaction();
        session.delete(testRequestEntity1);
        transaction.commit();
        session.close();

        Assert.assertTrue(actual);
    }

    @Test
    public void delete() {
        // Create record
        requestServiceTest.create(testRequestEntity);
        // Delete it
        requestServiceTest.delete(testRequestEntity);
        // Check if deleted
        RequestsEntity testRequestEntity1
                = requestServiceTest.getById(testRequestEntity.getId());
        boolean actual = testRequestEntity1 == null;

        Assert.assertTrue(actual);
    }

    private String a;
    private Integer b;
    @Test
    public void sandbox() {

        System.out.println(b);
    }
}