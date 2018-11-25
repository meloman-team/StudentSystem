/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.hsql;

import entity.Student;
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
public class HSqlStudentDao extends AbstractJDBCDao<Student, Long> {
    
    private class PersistStudent extends Student {
        public void setId(long id) {
            super.setId(id);
        }
    }

    public HSqlStudentDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getSelectQuery() {
        return "SELECT * FROM student";
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO student (id, name, middle_name, second_name, date_birth, id_group) \n" +
                "VALUES (DEFAULT, ?, ?, ?, ?, ?);";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE student SET name = ?, middle_name =?, second_name=?, date_birth=?, id_group=? WHERE id= ?;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM student WHERE id= ?;";
    }
    
    @Override
    public Student create() throws PersistException {//попробовать сделать как с get ID
        return persist(new Student());
    }

    @Override
    protected List<Student> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Student> result = new LinkedList<Student>();
        try {
            while (rs.next()) {
                PersistStudent group = new PersistStudent();
                group.setId(rs.getLong("id"));
                group.setName(rs.getString("name"));
                group.setMiddleName(rs.getString("middle_name"));
                group.setSecondName(rs.getString("second_name"));
                group.setDateBirth(rs.getDate("date_birth"));//возможно косяк
                group.setIdGroup(rs.getLong("id_group"));
                result.add(group);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Student object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getMiddleName());
            statement.setString(3, object.getSecondName());
            statement.setDate(4, object.getDateBirth());//возможно косяк
            statement.setLong(5, object.getIdGroup());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Student object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getMiddleName());
            statement.setString(3, object.getSecondName());
            statement.setDate(4, object.getDateBirth());
            statement.setLong(5, object.getIdGroup());
            statement.setLong(6, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Student object) throws PersistException {
        try {
            statement.setLong(1, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
    
}
