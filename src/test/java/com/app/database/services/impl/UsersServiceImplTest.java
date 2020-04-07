package com.app.database.services.impl;

import com.app.database.entities.UsersEntity;
import com.app.exceptions.DataBaseException;
import com.app.restapi.authorization.Roles;
import com.app.restapi.pojo.RequestResponsePojo;
import com.app.restapi.utils.XMLParserUtil;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;

public class UsersServiceImplTest {
    private final UsersServiceImpl usersServiceTest = new UsersServiceImpl();
    private final UsersEntity testUsersEntity
            = new UsersEntity("testLogin","testHash", Roles.USER);

    @Test
    public void findByLogin() {
        //Add record that will find
        usersServiceTest.create(testUsersEntity);
        // Search
        UsersEntity testUsersEntity2 = null;
        try {
            testUsersEntity2 = usersServiceTest.findByLogin(testUsersEntity.getLogin());
        } catch (DataBaseException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(testUsersEntity, testUsersEntity2);

        if (testUsersEntity2 != null) {
            usersServiceTest.delete(testUsersEntity);
        }
    }

    @Test
    public void sandbox() {
        RequestResponsePojo p = new RequestResponsePojo();
        p.setFirst_name("Максим");
        try {
            System.out.println(XMLParserUtil.getRequestXML(p));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //XMLParserUtil.getRequestXML(p);
    }
}
