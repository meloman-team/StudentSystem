package service.hsql;

import exceptions.PersistException;
import interfaces.DaoEntity;
import interfaces.Identified;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * @param <T>
 * @param <PK>
 * @author Ilya-pc
 */
public abstract class AbstractJDBCDao<T extends Identified<PK>, PK extends Long>
        implements DaoEntity<T, PK> {

    private final Connection connection;

    public AbstractJDBCDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Возвращает sql запрос для получения всех записей.
     * SELECT * FROM [Table]
     *
     * @return sql запрос для получения всех записей
     */
    protected abstract String getSelectQuery();

    /**
     * Возвращает sql запрос для вставки новой записи в базу данных.
     * INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);
     *
     * @return sql запрос для вставки новой записи в базу данных
     */
    protected abstract String getCreateQuery();

    /**
     * Возвращает sql запрос для обновления записи.
     * UPDATE [Table] SET [column = ?, column = ?, ...] WHERE id = ?;
     *
     * @return sql запрос для обновления записи
     */
    protected abstract String getUpdateQuery();

    /**
     * * Возвращает sql запрос для удаления записи из базы данных.
     * DELETE FROM [Table] WHERE id= ?;
     *
     * @return sql запрос для удаления записи из базы данных
     */
    protected abstract String getDeleteQuery();

    /**
     * Разбирает ResultSet и возвращает список объектов соответствующих содержимому ResultSet.
     *
     * @param rs
     * @return возвращает список объектов соответствующих содержимому ResultSet
     * @throws PersistException
     */
    protected abstract List<T> parseResultSet(ResultSet rs) throws PersistException;

    /**
     * Устанавливает аргументы insert запроса в соответствии со значением полей объекта object.
     *
     * @param statement
     * @param object
     * @throws PersistException
     */
    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws PersistException;

    /**
     * Устанавливает аргументы update запроса в соответствии со значением полей объекта object.
     *
     * @param statement
     * @param object
     * @throws PersistException
     */
    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws PersistException;

    /**
     * Устанавливает аргументы delete запроса в соответствии со значением полей объекта object.
     *
     * @param statement
     * @param object
     * @throws PersistException
     */
    protected abstract void prepareStatementForDelete(PreparedStatement statement, T object) throws PersistException;

    @Override
    public abstract T create() throws PersistException;

    @Override
    public T getByPK(PK key) throws PersistException {
        List<T> list;
        String sql = getSelectQuery();
        sql += " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, (Long) key);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (list.size() > 1) {
            throw new PersistException("Received more than one record.");
        }
        return list.iterator().next();
    }

    @Override
    public List<T> getAll() throws PersistException {
        List<T> list;
        String sql = getSelectQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }

    @Override
    public void update(T object) throws PersistException {
        String sql = getUpdateQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            prepareStatementForUpdate(statement, object); // заполнение аргументов запроса оставим на совесть потомков 
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On update modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void delete(T object) throws PersistException {
        String sql = getDeleteQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForDelete(statement, object); // заполнение аргументов запроса оставим на совесть потомков 
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On delete modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    /**
     * @param object
     * @return
     * @throws PersistException
     */
    @Override
    public T persist(T object) throws PersistException {
        T persistInstance;
        // Добавляем запись 
        String sql = getCreateQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForInsert(statement, object);
            int count = statement.executeUpdate();
//            if (count != 1) {
//                throw new PersistException("On persist modify more then 1 record: " + count);
//            }
        } catch (Exception e) {
            throw new PersistException(e);
        }

        // Получаем только что вставленную запись 
        sql = getSelectQuery() + " WHERE id = IDENTITY();";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            List<T> list = parseResultSet(rs);
            if ((list == null) || (list.size() != 1)) {
                throw new PersistException("Exception on findByPK new persist data.");
            }
            persistInstance = list.iterator().next();
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return persistInstance;
    }

    public void CHECKPOINT() throws PersistException {
        String sql = "CHECKPOINT";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}
