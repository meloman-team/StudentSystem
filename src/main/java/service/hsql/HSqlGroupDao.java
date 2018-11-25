/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.hsql;

import entity.Group;
import exceptions.PersistException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Ilya-pc
 */
public class HSqlGroupDao extends AbstractJDBCDao<Group, Long> {
    
    private class PersistGroup extends Group {
        public void setId(long id) {
            super.setNumber(id);
        }
    }
    
    public HSqlGroupDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getSelectQuery() {
        return "SELECT * FROM \"group\"";
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO \"group\" (id, name_faculty) \n" +
                "VALUES (DEFAULT, ?);";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE \"group\" SET NAME_FACULTY = ? WHERE id= ?;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM \"group\" WHERE id= ?;";
    }
    
    @Override
    public Group create() throws PersistException {
        return persist(new Group());
    }
    
    @Override
    protected List<Group> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Group> result = new LinkedList<Group>();
        try {
            while (rs.next()) {
                PersistGroup group = new PersistGroup();
                group.setNumber(rs.getLong("id"));
                group.setNameFaculty(rs.getString("name_faculty"));
                result.add(group);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }
    
    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Group object) throws PersistException {
        try {
            statement.setString(1, object.getNameFaculty());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Group object) throws PersistException {
        try {
            statement.setString(1, object.getNameFaculty());
            statement.setLong(2, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Group object) throws PersistException {
        try {
            statement.setLong(1, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    
}
