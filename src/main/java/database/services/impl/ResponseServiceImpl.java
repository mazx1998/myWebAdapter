package database.services.impl;

import database.DataAccessObject;
import database.entities.ResponsesEntity;
import database.services.ResponseService;

import java.util.List;

/**
 * @author Максим Зеленский
 * @since 06.03.2020
 */
public class ResponseServiceImpl implements ResponseService {
    private final DataAccessObject<ResponsesEntity> dao = new DataAccessObject<>(ResponsesEntity.class);

    @Override
    public void create(ResponsesEntity obj) {
        dao.create(obj);
    }

    @Override
    public ResponsesEntity getById(int id) {
        return dao.findById(id);
    }

    @Override
    public List<ResponsesEntity> getAll() {
        return dao.findAll();
    }

    @Override
    public void update(ResponsesEntity obj) {
        dao.update(obj);
    }

    @Override
    public void delete(ResponsesEntity obj) {
        dao.delete(obj);
    }
}
