package database.services.impl;

import database.entities.UsersEntity;
import exceptions.DataBaseException;
import restapi.authorization.Roles;
import org.junit.Assert;
import org.junit.Test;

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

        //XMLParserUtil.getRequestXML(p);
    }
}
