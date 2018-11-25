package studentsystem;

import entity.Group;
import exceptions.PersistException;
import java.util.List;
import service.hsql.HSqlDaoFactory;
import service.hsql.HSqlGroupDao;

public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws PersistException {
        // TODO code application logic here
        HSqlDaoFactory factory = new HSqlDaoFactory();
        HSqlGroupDao grDao = new HSqlGroupDao(factory.getConnection());
        
        Group create = grDao.create();
        
        System.out.println(create);
        
        create.setNameFaculty("lol");
        grDao.update(create);
        
        List<Group> all = grDao.getAll();
        for (Group all1 : all) {
            System.out.println(all1);
        }
        long id = 1;
        Group byPK = grDao.getByPK(id);
        System.out.println(byPK);
        
        byPK.setNameFaculty("lol");
        
        grDao.update(byPK);
        byPK = grDao.getByPK(id);
        System.out.println(byPK);
        
        grDao.delete(byPK);
        
        all = grDao.getAll();
        for (Group all1 : all) {
            System.out.println(all1);
        }
    }
    
}
