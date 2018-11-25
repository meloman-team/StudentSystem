package interfaces;

import entity.Student;
import java.sql.SQLException;
import java.util.List;

public interface StudentDao {
    public Student create();
    public Student read(long key);
    public void update(Student student);
    public void delete(Student student);
    public List<Student> getAll() throws SQLException;
}
