package studentsystem;

import entity.Group;
import exceptions.PersistException;
import interfaces.DaoFactory;
import interfaces.GroupDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.hsql.HSqlDaoFactory;
import service.hsql.HSqlGroupDao;

public class StudentSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // TODO code application logic here
        
        System.out.println(Group.class.getName());
        
        
        Class.forName("org.hsqldb.jdbcDriver");

        String path = "StudentSystemDB\\";
        String connectionString = "jdbc:hsqldb:file:" + path;
        String login = "StudentSystemDB";
        String password = "123";

        Connection connection = DriverManager.getConnection(connectionString, login, password);


        DaoFactory daoFactory = new HSqlDaoFactory();
        List<Group> list;
        try (Connection con = daoFactory.getConnection()) {
//            GroupDao dao = daoFactory.getGroupDao(con);
//            list = dao.getAll();
//            Group g = list.get(0);
//            System.out.println("num " + g.getNumber());
//            System.out.println("NameFaculty " + g.getNameFaculty());

        } catch (SQLException ex) {
            Logger.getLogger(StudentSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PersistException ex) {
            Logger.getLogger(StudentSystem.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void createTable(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE testTable (id IDENTITY , value VARCHAR(255))";
            statement.executeUpdate(sql);
        } catch (SQLException e) {

        }
    }

    public static void fillTable(Connection connection) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String sql = "INSERT INTO testTable (value) VALUES('Вася')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO testTable (value) VALUES('Петя')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO testTable (value) VALUES('Саша')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO testTable (value) VALUES('Катя')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO testTable (value) VALUES('Света')";
            statement.executeUpdate(sql);

        } catch (SQLException e) {

        }
    }

    public static void printTable(Connection connection) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String sql = "SELECT id, value FROM testTable";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " "
                        + resultSet.getString(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void printsTable(Connection connection) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String sql = "SELECT * FROM groups";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " "
                        + resultSet.getString(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
