package interfaces;

import exceptions.PersistException;
import java.io.Serializable;
import java.util.List;

public interface DaoEntity<T extends Identified<PK>, PK extends Serializable> {

    /** Создает новую запись и соответствующий ей объект
     * @return 
     * @throws PersistException */
    public T create() throws PersistException;

    /** Создает новую запись, соответствующую объекту object
     * @param object
     * @return 
     * @throws PersistException */
    public T persist(T object)  throws PersistException;

    /** Возвращает объект соответствующий записи с первичным ключом key или null
     * @param key
     * @return 
     * @throws PersistException */
    public T getByPK(PK key) throws PersistException;

    /** Сохраняет состояние объекта group в базе данных
     * @param object
     * @throws PersistException */
    public void update(T object) throws PersistException;

    /** Удаляет запись об объекте из базы данных
     * @param object
     * @throws PersistException */
    public void delete(T object) throws PersistException;

    /** Возвращает список объектов соответствующих всем записям в базе данных
     * @return 
     * @throws PersistException */
    public List<T> getAll() throws PersistException;
}
