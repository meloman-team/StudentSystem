/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.hsql;

import exceptions.PersistException;
import interfaces.DaoEntity;
import interfaces.DaoFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ilya-pc
 */
public class HSqlDaoFactory implements DaoFactory {

    String driver = "org.hsqldb.jdbcDriver";
    String path = "StudentSystemDB\\";
    String connectionString = "jdbc:hsqldb:file:" + path;
    String login = "StudentSystemDB";
    String password = "123";

    public HSqlDaoFactory() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HSqlDaoFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Connection getConnection() throws PersistException {
        Connection connection = null;
        try {
        connection = DriverManager.getConnection(connectionString, login, password);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return  connection;
    }
    
    @Override
    public DaoEntity getDaoEntity (Connection connection, Class dtoClass) throws PersistException{
        DaoEntity daoEntity = null;
        switch(dtoClass.getName()){
            case "entity.Group":
                daoEntity = new HSqlGroupDao(connection);
                break;
            case "entity.Student":
                daoEntity = new HSqlStudentDao(connection);
                break;
        }
        if (daoEntity == null) {
            throw new PersistException("Dao object for " + dtoClass + " not found.");
        }
        return daoEntity;
    }

}
