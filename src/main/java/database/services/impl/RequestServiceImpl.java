package database.services.impl;

import database.DataAccessObject;
import database.entities.RequestsEntity;
import database.services.RequestService;
import restapi.pojo.RequestFilterPojo;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Максим Зеленский
 * @since 06.03.2020
 */
@ApplicationScoped
public class RequestServiceImpl implements RequestService {
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
        final String REQUEST_XML_FIELD = "requestXml";
        Integer pageNumber = filter.getPage_number();
        Integer pageSize = filter.getPage_size();

        List<String> keyWords = getKeywords(filter);
        if (keyWords.isEmpty()) {
            if (pageNumber == null || pageSize == null) {
                return dao.findAll();
            } else {
                return dao.findAllLimited(pageNumber, pageSize);
            }
        } else {
            if (pageNumber == null || pageSize == null) {
                return dao.findByFieldWithKeyWords(keyWords, REQUEST_XML_FIELD);
            } else {
                return dao.findByFieldWithKeyWordsLimited(keyWords, REQUEST_XML_FIELD, pageNumber, pageSize);
            }
        }
    }

    private List<String> getKeywords(RequestFilterPojo filter) {
        final String FAMILY_NAME_OPEN_TAG = "<smev:FamilyName>";
        final String FIRST_NAME_OPEN_TAG = "<smev:FirstName>";
        final String PATRONYMIC_OPEN_TAG = "<smev:Patronymic>";
        final String FAMILY_NAME_CLOSE_TAG = "</smev:FamilyName>";
        final String FIRST_NAME_CLOSE_TAG = "</smev:FirstName>";
        final String PATRONYMIC_CLOSE_TAG = "</smev:Patronymic>";

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
        return keyWords;
    }

    @Override
    public Integer getRowsCountByFilter(RequestFilterPojo filter) {
        List<RequestsEntity> data = getDataByFilter(filter);
        return data.size();
    }

    @Override
    public Long getRowsCount() {
        return dao.getRowsCount();
    }

    @Override
    public List<RequestsEntity> getAll(int pageNumber, int pageSize) {
        return dao.findAllLimited(pageNumber, pageSize);
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
