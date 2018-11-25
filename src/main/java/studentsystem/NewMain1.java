package studentsystem;

import entity.Student;
import exceptions.PersistException;
import service.hsql.HSqlDaoFactory;
import service.hsql.HSqlStudentDao;

import java.util.Date;
import java.util.List;

public class NewMain1 {

    /**
     * @param args the command line arguments
     * @throws PersistException
     */
    public static void main(String[] args) throws PersistException {
        HSqlDaoFactory factory = new HSqlDaoFactory();
        HSqlStudentDao grDao = new HSqlStudentDao(factory.getConnection());

        Student create = grDao.create();

        System.out.println(create);

        create.setName("lol");
        create.setMiddleName("lolo");
        create.setSecondName("ololo");
        create.setDateBirth(new java.sql.Date(new Date().getTime()));
        create.setIdGroup(0);
        grDao.update(create);

        List<Student> all = grDao.getAll();
        for (Student all1 : all) {
            System.out.println(all1);
        }
        Long id = 1L;
        Student byPK = grDao.getByPK(id);
        System.out.println(byPK);

        grDao.delete(byPK);

        all = grDao.getAll();
        for (Student all1 : all) {
            System.out.println(all1);
        }
    }
}
