package com.app.database.services;

import com.app.database.entities.UsersEntity;
import com.app.exceptions.DataBaseException;

/**
 * @author Максим Зеленский
 * @since 06.03.2020
 */
public interface UsersService extends EntityService<UsersEntity> {
    UsersEntity findByLogin(String login) throws DataBaseException;
}
