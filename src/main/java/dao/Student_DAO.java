package dao;

import model.Module;
import model.Student;

import java.sql.*;
import java.util.ArrayList;

public class Student_DAO {
    protected Connection conn;
    private Student student;
    private Student objStudent;
    private int index = 0;
    private int index2 = 0;
    private Student_module_DAO student_module_dao;
    private Module_DAO module_dao;


    public Student_DAO(Connection getConnection) throws Exception {
        conn = getConnection;
        student_module_dao = new Student_module_DAO(getConnection);
        module_dao = new Module_DAO(getConnection);
    }

    public void add_Student(Student student) throws Exception {
        Statement stmt = conn.createStatement();
        PreparedStatement Student_ps = conn.prepareStatement("INSERT INTO Student(S_No,S_Password,S_Name,S_Surname,Degree) VALUES(s_no_seq.NEXTVAL,?,?,?,?)");
        Student_ps.setString(1, student.getSt_password());
        Student_ps.setString(2, student.getS_Name());
        Student_ps.setString(3, student.getS_Surname());
        Student_ps.setString(4, student.getS_Degree());
        Student_ps.executeUpdate();
        stmt.execute("COMMIT");
    }

    public ArrayList<Student> getAllStudents() throws Exception {
        ArrayList<Student> studentlist = new ArrayList<>();
        ArrayList<Integer> students_nums = new ArrayList<>();
        Statement retrive_data_from_db = conn.createStatement();
        String select_statement = "SELECT Student.S_No,Student.S_Password,Student.S_Name,Student.S_Surname,Student.Degree,Module.M_Name,Module.M_Code,Module.M_Duration,Module.M_Credits,Student_module.Marks FROM Student,Module,Student_module WHERE Student.S_No=Student_module.S_No AND Student_module.M_Code=Module.M_Code ORDER BY Student.S_No";
        ResultSet output_from_db = retrive_data_from_db.executeQuery(select_statement);
        initialise_student_list(studentlist, students_nums, output_from_db);
        return studentlist;
    }

    private void initialise_student_list(ArrayList<Student> studentlist, ArrayList<Integer> students_nums, ResultSet output_from_db) throws SQLException {
        while (output_from_db.next()) {
            int S_No = output_from_db.getInt(1);
            String Password = output_from_db.getString(2);
            String S_Name = output_from_db.getString(3);
            String S_Surname = output_from_db.getString(4);
            String S_Degree = output_from_db.getString(5);
            String M_Name = output_from_db.getString(6);
            String M_Code = output_from_db.getString(7);
            int M_Duration = output_from_db.getInt(8);
            int M_Credits = output_from_db.getInt(9);
            int M_Marks = output_from_db.getInt(10);
            init_student_list(S_No, Password, S_Name, S_Surname, S_Degree, M_Name, M_Code, M_Duration, M_Credits, M_Marks, studentlist, students_nums);
        }
    }

    private void init_student_list(int S_No, String password, String S_Name, String S_Surname, String Degree, String M_Name
            , String M_Code, int M_Duration, int M_Credits, int Marks, ArrayList<Student> studentlist, ArrayList<Integer> students_nums) {

        Module module = new Module(M_Name, M_Code, M_Duration, M_Credits, Marks);
        students_nums.add(S_No);
        Student newStudent = new Student(password, S_Name, S_Surname, Degree);
        newStudent.setS_No(S_No);
        if ((same_Pk(newStudent, students_nums)) && (index == 0)) {
            student = newStudent;
            ++index;
        } else {

            index = 0;
        }
        student.addModule(module);
        if ((students_nums.size() > 1) && (students_nums.size() % 2 == 0)) {
            studentlist.add(student);
        }
    }


    private boolean same_Pk(Student student_target, ArrayList<Integer> students_nums) {
        for (int i = 0; i < students_nums.size(); i++) {
            if (student_target.getS_No() == students_nums.get(i)) {
                return true;
            }
        }
        return false;
    }


    public void close() throws Exception {
        conn.close();
    }


    public Student getStudent(int student_num) throws Exception {
        Statement retrieve_data_from_db = conn.createStatement();
        ResultSet output_from_db = retrieve_data_from_db.executeQuery("SELECT Student.S_No,Student.S_Password,Student.S_Name,Student.S_Surname,Student.Degree,Module.M_Name,Module.M_Code,Module.M_Duration,Module.M_Credits,Student_module.Marks FROM Student,Module,Student_module WHERE((Student.S_No=" +
                student_num + ") AND (Student.S_No=Student_module.S_No AND Student_module.M_Code=Module.M_Code)) ORDER BY Student.S_No");
        getStudent_util(student_num, output_from_db);
        return objStudent;
    }

    private void getStudent_util(int student_num, ResultSet output_from_db) throws SQLException {
        while (output_from_db.next()) {
            String password = output_from_db.getString(2);
            String name = output_from_db.getString(3);
            String lastname = output_from_db.getString(4);
            String degree = output_from_db.getString(5);
            String m_name = output_from_db.getString(6);
            String m_code = output_from_db.getString(7);
            int duration = output_from_db.getInt(8);
            int credits = output_from_db.getInt(9);
            int marks = output_from_db.getInt(10);

            if (index2 == 0) {
                ++index2;
                Student newStundent = new Student(password, name, lastname, degree);
                newStundent.setS_No(student_num);
                objStudent = newStundent;
            } else {
                --index2;
            }
            objStudent.addModule(new Module(m_name, m_code, duration, credits, marks));
        }
    }

    public Student getStudent2(int studentNum, String password) throws Exception {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Student WHERE S_No=? AND S_Password=?");
        ps.setInt(1, studentNum);
        ps.setString(2, password);
        ResultSet output_from_db = ps.executeQuery();
        Student student = null;
        while (output_from_db.next()) {
            student = new Student(output_from_db.getString(2), output_from_db.getString(3), output_from_db.getString(4), output_from_db.getString(5));
            student.setS_No(output_from_db.getInt(1));
        }
        return student;

    }

    public void updateStudent(Student student, String m_code_1_init, String m_code_2_init) throws Exception {
        student_module_dao.update_student_module(student, m_code_1_init, m_code_2_init);
        module_dao.update_module(student, m_code_1_init, m_code_2_init);
        updateStudent_util(student);
        Statement stmt = conn.createStatement();
        stmt.execute("COMMIT");
    }

    private void updateStudent_util(Student student) throws SQLException {
        PreparedStatement ps1 = conn.prepareStatement("UPDATE Student SET S_No=?,S_Name=?, S_Surname=?,Degree=? WHERE S_No=?");
        ps1.setInt(1, student.getS_No());
        ps1.setString(2, student.getS_Name());
        ps1.setString(3, student.getS_Surname());
        ps1.setString(4, student.getS_Degree());
        ps1.setInt(5, student.getS_No());
        ps1.executeUpdate();
    }

    public void deleteStudent(int student_no, String module_code_1, String module_code_2) throws Exception {
        student_module_dao.deleteRecord(student_no, module_code_1, module_code_2);
        module_dao.delete_module(module_code_1, module_code_2);
        String delete_student_rec = "DELETE FROM Student WHERE S_No=?";
        PreparedStatement ps = conn.prepareStatement(delete_student_rec);
        ps.setInt(1, student_no);
        ps.executeUpdate();
        Statement stmt = conn.createStatement();
        stmt.execute("COMMIT");

    }


}
