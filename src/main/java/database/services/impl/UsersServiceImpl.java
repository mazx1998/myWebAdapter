package database.services.impl;

import database.dao.UserDataAccess;
import database.entities.UsersEntity;
import database.services.UsersService;
import exceptions.DataBaseException;
import exceptions.TwinLoginException;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * @author Максим Зеленский
 * @since 06.03.2020
 */
@ApplicationScoped
public class UsersServiceImpl implements UsersService {
    private final UserDataAccess dao = new UserDataAccess(UsersEntity.class);

    @Override
    public UsersEntity findByLogin(String login) throws DataBaseException {
        List<UsersEntity> foundUsers = dao.findByField("login", login);
        if (foundUsers.size() > 1) {
            throw new TwinLoginException("Logins is unique field. It cant have more than one equal values");
        }
        if (foundUsers.size() == 0) {
            return null;
        }
        return foundUsers.get(0);
    }

    @Override
    public void create(UsersEntity obj) {
        dao.create(obj);
    }

    @Override
    public UsersEntity getById(int id) {
        return dao.findById(id);
    }

    @Override
    public void update(UsersEntity obj) {
        dao.update(obj);
    }

    @Override
    public void delete(UsersEntity obj) {
        dao.delete(obj);
    }
}
