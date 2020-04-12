package database.services;

import database.entities.UsersEntity;
import exceptions.DataBaseException;

/**
 * @author Максим Зеленский
 * @since 06.03.2020
 */
public interface UsersService extends EntityService<UsersEntity> {
    UsersEntity findByLogin(String login) throws DataBaseException;
}
