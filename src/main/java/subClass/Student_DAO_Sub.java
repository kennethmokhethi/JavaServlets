package subClass;

import dao.Student_DAO;
import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Student_DAO_Sub extends Student_DAO {

    public Student_DAO_Sub(Connection getConnection) throws Exception {
        super(getConnection);
    }

    @Override
    public void close() throws Exception {
        conn.createStatement().execute("DROP ALL OBJECTS");
        conn.close();
    }

    @Override
    public void updateStudent(Student student, String m_code_1_init, String m_code_2_init) throws Exception {
        String update_str = "UPDATE Student SET S_Password=?, S_Name=?, S_Surname=?,Degree=? WHERE S_No=?";
        PreparedStatement ps1 = conn.prepareStatement(update_str);
        ps1.setString(1, student.getSt_password());
        ps1.setString(2, student.getS_Name());
        ps1.setString(3, student.getS_Surname());
        ps1.setString(4, student.getS_Degree());
        ps1.setInt(5, student.getS_No());

        ps1.executeUpdate();

        Statement stmt = conn.createStatement();
        stmt.execute("COMMIT");

    }

    @Override
    public void deleteStudent(int student_no, String module_code_1, String module_code_2) throws Exception {
        String delete_student_rec = "DELETE FROM Student WHERE S_No=?";

        PreparedStatement ps = conn.prepareStatement(delete_student_rec);
        ps.setInt(1, student_no);

        ps.executeUpdate();
        Statement stmt = conn.createStatement();
        stmt.execute("COMMIT");
    }

    public Connection getConnection() {
        return conn;
    }


}
