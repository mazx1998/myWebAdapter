package database.dao;

import database.entities.UsersEntity;

import java.util.List;

/**
 * @author Максим Зеленский
 */
public class UserDataAccess extends MainDataAccess<UsersEntity> {

    public UserDataAccess(Class<UsersEntity> type) {
        super(type);
    }
}
