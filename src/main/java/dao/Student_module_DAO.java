package dao;

import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Student_module_DAO {
    protected Connection conn;

    public Student_module_DAO(Connection getConnection) throws Exception {
        conn = getConnection;
    }

    public void add_Student_module(Student obj_Student, int index) throws Exception {
        Statement stmt = conn.createStatement();
        PreparedStatement Student_Module_ps = conn.prepareStatement("INSERT INTO Student_module(S_No,M_Code,Marks) VALUES((SELECT MAX(S_No) FROM Student),?,?)");
        Student_Module_ps.setString(1, obj_Student.getStudent_modules().get(index).getM_Code());
        Student_Module_ps.setInt(2, obj_Student.getStudent_modules().get(index).getM_Marks());
        Student_Module_ps.executeUpdate();
        stmt.execute("COMMIT");
    }

    public void update_student_module(Student student, String module_code_1, String module_code_2) throws Exception {
        String update_str = "UPDATE Student_module SET M_Code=?, Marks=? WHERE M_Code=? AND S_No=?";
        PreparedStatement ps1 = getPreparedStatement_update(student, module_code_1, update_str, 0);
        PreparedStatement ps2 = getPreparedStatement_update(student, module_code_2, update_str, 1);
        disable_constraint();
        ps1.executeUpdate();
        ps2.executeUpdate();
        enable_constraint();
        Statement stmt = conn.createStatement();
        stmt.execute("COMMIT");
    }

    private PreparedStatement getPreparedStatement_update(Student student, String module_code_1, String update_str, int index) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(update_str);
        ps.setString(1, student.getStudent_modules().get(index).getM_Code());
        ps.setInt(2, student.getStudent_modules().get(index).getM_Marks());
        ps.setString(3, module_code_1);
        ps.setInt(4, student.getS_No());
        return ps;
    }


    private void disable_constraint() throws SQLException {
        Statement disable_constraints = conn.createStatement();
        disable_constraints.executeUpdate("AlTER TABLE Student_module MODIFY CONSTRAINT FR_STUDENT_MODULE_S DISABLE");
        disable_constraints.execute("COMMIT");
        disable_constraints.executeUpdate("AlTER TABLE Student_module MODIFY CONSTRAINT FR_STUDENT_MODULE_M DISABLE");
        disable_constraints.execute("COMMIT");
    }

    private void enable_constraint() throws SQLException {
        Statement enables_constraints = conn.createStatement();
        enables_constraints.executeUpdate("AlTER TABLE Student_module MODIFY CONSTRAINT FR_STUDENT_MODULE_S ENABLE");
        enables_constraints.execute("COMMIT");
        enables_constraints.executeUpdate("AlTER TABLE Student_module MODIFY CONSTRAINT FR_STUDENT_MODULE_M ENABLE");
        enables_constraints.execute("COMMIT");
    }


    public void deleteRecord(int student_no, String module_code_1, String module_code_2) throws Exception {
        disable_constraint();
        getPreparedStatement_delete(student_no, module_code_1, module_code_2).executeUpdate();
        Statement stmt = conn.createStatement();
        stmt.execute("COMMIT");
        enable_constraint();
    }

    private PreparedStatement getPreparedStatement_delete(int student_no, String module_code_1, String module_code_2) throws SQLException {
        String delete_rec = "DELETE FROM Student_module WHERE S_No=? AND M_Code IN(?,?)";
        PreparedStatement ps = conn.prepareStatement(delete_rec);
        ps.setInt(1, student_no);
        ps.setString(2, module_code_1);
        ps.setString(3, module_code_2);
        return ps;
    }

    public void close() throws Exception {
        conn.close();
    }
}
