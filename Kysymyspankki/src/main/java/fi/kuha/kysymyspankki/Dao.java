package fi.kuha.kysymyspankki;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T extends DaoObject, K> {
    T findOne(K key) throws SQLException;
    List<T> findAll() throws SQLException;
    void saveOrUpdate(T object) throws SQLException;
    void delete(K key) throws SQLException;
}
