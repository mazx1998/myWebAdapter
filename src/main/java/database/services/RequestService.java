package database.services;

import database.entities.RequestsEntity;

import java.util.List;

/**
 * @author Максим Зеленский
 * @since 06.03.2020
 */
public interface RequestService extends EntityService<RequestsEntity> {
    List<RequestsEntity> getByFullName(String firstName, String lastName, String patronymic);
}
