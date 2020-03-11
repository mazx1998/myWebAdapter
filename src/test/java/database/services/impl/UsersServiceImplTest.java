package database.services.impl;

import database.entities.UsersEntity;
import exceptions.MoreThanOneLoginException;
import org.junit.Assert;
import org.junit.Test;

public class UsersServiceImplTest {
    private final UsersServiceImpl usersServiceTest = new UsersServiceImpl();
    private final UsersEntity testUsersEntity
            = new UsersEntity("testLogin","testHash");

    @Test
    public void findByLogin() {
        //Add record that will find
        usersServiceTest.create(testUsersEntity);
        // Search
        UsersEntity testUsersEntity2 = null;
        try {
            testUsersEntity2 = usersServiceTest.findByLogin(testUsersEntity.getLogin());
        } catch (MoreThanOneLoginException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(testUsersEntity, testUsersEntity2);

        if (testUsersEntity2 != null) {
            usersServiceTest.delete(testUsersEntity);
        }
    }
}