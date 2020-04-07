package com.app.database.services.impl;

import com.app.database.DataAccessObject;
import com.app.database.entities.UsersEntity;
import com.app.database.services.UsersService;
import com.app.exceptions.DataBaseException;
import com.app.exceptions.NotFoundException;
import com.app.exceptions.TwinLoginException;

import java.util.List;

/**
 * @author Максим Зеленский
 * @since 06.03.2020
 */
public class UsersServiceImpl implements UsersService {
    private final DataAccessObject<UsersEntity> dao = new DataAccessObject<>(UsersEntity.class);

    @Override
    public UsersEntity findByLogin(String login) throws DataBaseException {
        List<UsersEntity> foundUsers = dao.findByField("login", login);
        if (foundUsers.size() > 1) {
            throw new TwinLoginException("Logins is unique field. It cant have more than one equal values");
        }
        if (foundUsers.size() == 0) {
            throw new NotFoundException("Such user is not exists in data base");
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
    public long getRowsCount() {
        return dao.getRowsCount();
    }

    @Override
    public List<UsersEntity> getAll(int pageNumber, int pageSize) {
        return dao.findAllLimited(pageNumber, pageSize);
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
