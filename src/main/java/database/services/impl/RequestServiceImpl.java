package database.services.impl;

import database.dao.RequestDataAccess;
import database.entities.RequestsEntity;
import database.entities.UsersEntity;
import database.services.RequestService;
import exceptions.DataBaseException;
import restapi.pojo.RequestFilterPojo;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * @author Максим Зеленский
 */
@ApplicationScoped
public class RequestServiceImpl implements RequestService {
    private final RequestDataAccess dao = new RequestDataAccess(RequestsEntity.class);

    @Override
    public void create(RequestsEntity obj) {
        dao.create(obj);
    }

    @Override
    public RequestsEntity getById(int id) {
        return dao.findById(id);
    }

    @Override
    public List<RequestsEntity> getDataByFilter(RequestFilterPojo filter) {
        Integer offset = filter.getOffset();
        Integer limit = filter.getLimit();
        String author = filter.getAuthor();

        if (author == null) {
            if (offset == null || limit == null) {
                return dao.findAll("requestDate", true);
            } else {
                return dao.findAll(offset, limit, "requestDate", true);
            }
        } else {
            UsersServiceImpl usersService = new UsersServiceImpl();
            UsersEntity user = null;
            try {
                user = usersService.findByLogin(author);
            } catch (DataBaseException e) {
                e.printStackTrace();
            }
            if (offset == null || limit == null) {
                return user != null ?
                        dao.getByAuthorName(user.getLogin(), "requestDate", true) : null;
            } else {
                return user != null ?
                        dao.getByAuthorName(offset, limit, user.getLogin(),
                                "requestDate", true): null;
            }
        }
    }

    @Override
    public Integer getRowsCountByFilter(RequestFilterPojo filter) {
        List<RequestsEntity> data = getDataByFilter(filter);
        return data != null ? data.size() : 0;
    }

    @Override
    public void update(RequestsEntity obj) {
        dao.update(obj);
    }

    @Override
    public void delete(RequestsEntity obj) {
        dao.delete(obj);
    }
}
