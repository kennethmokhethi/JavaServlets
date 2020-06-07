package subClass;

import dao.Module_DAO;
import dao.Student_DAO;
import dao.Student_module_DAO;

import java.sql.Connection;


public class Student_module_DAO_Sub extends Student_module_DAO {

    private Student_DAO student_dao;
    private Module_DAO module_dao;

    public Student_module_DAO_Sub(Connection getConnection) throws Exception {
        super(getConnection);
        student_dao = new Student_DAO(getConnection);
        module_dao = new Module_DAO(getConnection);
    }

    @Override
    public void close() throws Exception {
        conn.createStatement().execute("DROP ALL OBJECTS");
        conn.close();
    }

    public Connection getConnection() {
        return conn;
    }

}
