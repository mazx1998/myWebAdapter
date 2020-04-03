package database.services.impl;

import database.DataAccessObject;
import database.entities.RequestsEntity;
import database.services.RequestService;
import restapi.pojo.RequestFilterPojo;

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
    private final int DEFAULT_PAGE_NUMBER = 1;
    private final int DEFAULT_PAGE_SIZE = 10;
    private final int DEFAULT_MAX_PAGE_SIZE = 20;
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
    public List<RequestsEntity> getDataByFilter(RequestFilterPojo filter) {
        return extractDataUsingFilter(filter).data;
    }

    @Override
    public Integer getRowsCountByFilter(RequestFilterPojo filter) {
        return extractDataUsingFilter(filter).dataCount;
    }

    private DataGotWithFilter extractDataUsingFilter(RequestFilterPojo filter) {
        DataGotWithFilter dataGotWithFilter = new DataGotWithFilter();
        String firstName = filter.getFirst_name();
        String lastName = filter.getLast_name();
        String patronymic = filter.getPatronymic();
        Integer pageNumber = filter.getPage_number();
        Integer pageSize = filter.getPage_size();

        if (pageNumber == null) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if (pageSize == null) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (pageSize > 20) {
            pageSize = DEFAULT_MAX_PAGE_SIZE;
        }

        List<String> fields = new ArrayList<>();
        List<String> values = new ArrayList<>();

        // if all of names is null
        if (firstName == null && lastName == null && patronymic == null) {
            dataGotWithFilter.data = dao.findAll(pageNumber, pageSize);
            if (dataGotWithFilter.data != null) {
                dataGotWithFilter.dataCount = dataGotWithFilter.data.size();
            }
            else {
                dataGotWithFilter.dataCount = null;
            }
            return dataGotWithFilter;
        }

        // If just one of names is not null
        if (firstName != null && lastName == null && patronymic == null) {
            dataGotWithFilter.data = dao.findByFieldLimited(FIRST_NAME_FIELD, firstName, pageNumber, pageSize);
            if (dataGotWithFilter.data != null) {
                dataGotWithFilter.dataCount = dataGotWithFilter.data.size();
            }
            else {
                dataGotWithFilter.dataCount = null;
            }
            return dataGotWithFilter;
        }
        if (firstName == null && lastName != null && patronymic == null) {
            dataGotWithFilter.data = dao.findByFieldLimited(LAST_NAME_FIELD, lastName, pageNumber, pageSize);
            if (dataGotWithFilter.data != null) {
                dataGotWithFilter.dataCount = dataGotWithFilter.data.size();
            }
            else {
                dataGotWithFilter.dataCount = null;
            }
            return dataGotWithFilter;
        }
        if (firstName == null && lastName == null && patronymic != null) {
            dataGotWithFilter.data = dao.findByFieldLimited(PATRONYMIC_FIELD, patronymic, pageNumber, pageSize);
            if (dataGotWithFilter.data != null) {
                dataGotWithFilter.dataCount = dataGotWithFilter.data.size();
            }
            else {
                dataGotWithFilter.dataCount = null;
            }
            return dataGotWithFilter;
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

        dataGotWithFilter.data = dao.findByFields(fields, values, pageNumber, pageSize);
        if (dataGotWithFilter.data != null) {
            dataGotWithFilter.dataCount = dataGotWithFilter.data.size();
        }
        else {
            dataGotWithFilter.dataCount = null;
        }
        return dataGotWithFilter;
    }

    private class DataGotWithFilter {
        private List<RequestsEntity> data;
        private Integer dataCount;
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
