package database.services.impl;

import database.entities.RequestsEntity;
import database.factories.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;

public class RequestServiceImplTest {
    private final RequestServiceImpl requestServiceTest = new RequestServiceImpl();

    @Test
    public void create() {
        RequestsEntity testRequestEntity
                = new RequestsEntity("testName",
                "testLastName",
                "testPatronymic",
                "G",
                new Date(2000, 1, 1),
                new Date(2000, 1, 1));
        requestServiceTest.create(testRequestEntity);

        boolean actual = false;

        if (HibernateSessionFactoryUtil
                .getSession()
                .get(RequestsEntity.class, testRequestEntity.getId()) != null) {
            actual = true;

            // Remove created
            Session session = HibernateSessionFactoryUtil.getSession();
            Transaction transaction = session.beginTransaction();
            session.delete(testRequestEntity);
            transaction.commit();
        }

        Assert.assertTrue(actual);
    }

    @Test
    public void getById() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}