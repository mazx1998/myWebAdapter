package com.app.database.services.impl;

import com.app.database.DataAccessObject;
import com.app.database.entities.RequestsEntity;
import com.app.database.services.RequestService;
import com.app.restapi.pojo.RequestFilterPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Максим Зеленский
 * @since 06.03.2020
 */
public class RequestServiceImpl implements RequestService {
    private final String FAMILY_NAME_OPEN_TAG = "<smev:FamilyName>";
    private final String FIRST_NAME_OPEN_TAG = "<smev:FirstName>";
    private final String PATRONYMIC_OPEN_TAG = "<smev:Patronymic>";
    private final String FAMILY_NAME_CLOSE_TAG = "</smev:FamilyName>";
    private final String FIRST_NAME_CLOSE_TAG = "</smev:FirstName>";
    private final String PATRONYMIC_CLOSE_TAG = "</smev:Patronymic>";
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
        Integer pageNumber;
        Integer pageSize;
        if (filter.getPage_number() == null) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        } else {
            pageNumber = filter.getPage_number();
        }
        if (filter.getPage_size() == null) {
            pageSize = DEFAULT_PAGE_SIZE;
        } else {
            pageSize = filter.getPage_size();
            if (pageSize > DEFAULT_MAX_PAGE_SIZE) {
                pageSize = DEFAULT_MAX_PAGE_SIZE;
            }
        }

        if (filter.getFamily_name() == null &&
                filter.getFirst_name() == null && filter.getPatronymic() == null) {
            return dao.findAll(pageNumber, pageSize);
        }

        List<String> keyWords = new ArrayList<>();
        if (filter.getFamily_name() != null) {
            keyWords.add(FAMILY_NAME_OPEN_TAG + filter.getFamily_name() + FAMILY_NAME_CLOSE_TAG);
        }
        if (filter.getFirst_name() != null) {
            keyWords.add(FIRST_NAME_OPEN_TAG + filter.getFirst_name() + FIRST_NAME_CLOSE_TAG);
        }
        if (filter.getPatronymic() != null) {
            keyWords.add(PATRONYMIC_OPEN_TAG + filter.getPatronymic() + PATRONYMIC_CLOSE_TAG);
        }
        return dao.findInFieldWIthLike(keyWords, "requestXml", pageNumber, pageSize);
    }

    @Override
    public Integer getRowsCountByFilter(RequestFilterPojo filter) {
        List<RequestsEntity> result = getDataByFilter(filter);
        return result != null ? result.size() : null;
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
