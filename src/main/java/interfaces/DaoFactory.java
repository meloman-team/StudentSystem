package interfaces;

import exceptions.PersistException;
import java.sql.Connection;

public interface DaoFactory {
    Connection getConnection() throws PersistException;
    DaoEntity getDaoEntity(Connection connection, Class dtoClass) throws PersistException;
}
