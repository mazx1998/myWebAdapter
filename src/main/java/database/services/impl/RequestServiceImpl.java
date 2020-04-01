package database.services.impl;

import database.DataAccessObject;
import database.entities.RequestsEntity;
import database.services.RequestService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Максим Зеленский
 * @since 06.03.2020
 */
public class RequestServiceImpl implements RequestService {
    private final String FIRST_NAME_FIELD = "firstName";
    private final String LAST_NAME_FIELD = "lastName";
    private final String PATRONYMIC_FIELD = "patronymic";
    private final DataAccessObject<RequestsEntity> dao = new DataAccessObject<>(RequestsEntity.class);

    @Override
    public void create(RequestsEntity obj) {
        dao.create(obj);
    }

    @Override
    public RequestsEntity getById(int id) {
        return dao.findById(id);
    }

    @Override
    public List<RequestsEntity> getByFullName(String firstName, String lastName, String patronymic) {
        List<String> fields = new ArrayList<>();
        List<String> values = new ArrayList<>();

        // if all of names is null
        if (firstName == null && lastName == null && patronymic == null) {
            return null;
        }

        // If just one of names is not null
        if (firstName != null && lastName == null && patronymic == null) {
            return dao.findByField(FIRST_NAME_FIELD, firstName);
        }
        if (firstName == null && lastName != null && patronymic == null) {
            return dao.findByField(LAST_NAME_FIELD, lastName);
        }
        if (firstName == null && lastName == null && patronymic != null) {
            return dao.findByField(PATRONYMIC_FIELD, patronymic);
        }

        // if minimum two of names is not null
        if (firstName != null) {
            fields.add(FIRST_NAME_FIELD);
            values.add(firstName);
        }
        if (lastName != null) {
            fields.add(LAST_NAME_FIELD);
            values.add(lastName);
        }
        if (patronymic != null) {
            fields.add(PATRONYMIC_FIELD);
            values.add(patronymic);
        }
        return dao.findByFields(fields, values);
    }

    @Override
    public long getRowsCount() {
        return dao.getRowsCount();
    }

    @Override
    public List<RequestsEntity> getAll(int pageNumber, int pageSize) {
        return dao.findAll(pageNumber, pageSize);
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
