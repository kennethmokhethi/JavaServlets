package dao;

import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Module_DAO {

    protected Connection conn;

    public Module_DAO(Connection getConnection) throws Exception {
        conn = getConnection;
    }

    public void add_module(Student obj_student, int index) throws Exception {
        PreparedStatement Module_ps = conn.prepareStatement("INSERT INTO Module(M_Name,M_Code,M_Duration,M_Credits) VALUES(?,?,?,?)");
        Module_ps.setString(1, obj_student.getStudent_modules().get(index).getM_Name());
        Module_ps.setString(2, obj_student.getStudent_modules().get(index).getM_Code());
        Module_ps.setInt(3, obj_student.getStudent_modules().get(index).getM_Duration());
        Module_ps.setInt(4, obj_student.getStudent_modules().get(index).getM_Credits());
        Module_ps.executeUpdate();
        Statement stmt = conn.createStatement();
        stmt.execute("COMMIT");
    }

    public void update_module(Student student, String module_code_1, String module_code_2) throws Exception {
        update_module_util(student, module_code_1, 0);
        update_module_util(student, module_code_2, 1);
        Statement stmt = conn.createStatement();
        stmt.execute("COMMIT");
    }

    private void update_module_util(Student student, String module_code, int index) throws SQLException {
        PreparedStatement ps1 = conn.prepareStatement("UPDATE Module SET M_Name=?, M_Code=?, M_Duration=?,M_Credits=? WHERE M_Code=?");
        ps1.setString(1, student.getStudent_modules().get(index).getM_Name());
        ps1.setString(2, student.getStudent_modules().get(index).getM_Code());
        ps1.setInt(3, student.getStudent_modules().get(index).getM_Duration());
        ps1.setInt(4, student.getStudent_modules().get(index).getM_Credits());
        ps1.setString(5, module_code);
        ps1.executeUpdate();

    }

    public void delete_module(String module_code_1, String module_code_2) throws Exception {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM Module WHERE M_Code IN(?,?)");
        ps.setString(1, module_code_1);
        ps.setString(2, module_code_2);
        ps.executeUpdate();
        Statement stmt = conn.createStatement();
        stmt.execute("COMMIT");
    }

    public void close() throws Exception {
        conn.close();
    }

}


