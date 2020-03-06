package database.services;

import java.util.List;

/**
 * @author Максим Зеленский
 * @since 06.03.2020
 */
public interface EntityService<T> {
    void create(T obj);
    T getById(int id);
    List<T> getAll();
    void update(T obj);
    void delete(T obj);
}
