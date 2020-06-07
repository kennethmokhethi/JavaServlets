package DAOTests;

import IConnectionpac.IConnection;
import connectionH2.IConnectionImplH2;
import model.Module;
import model.Student;
import org.junit.*;
import subClass.Student_DAO_Sub;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Student_DAO_Test {

    private static Student student;
    private static Student_DAO_Sub h2conn;
    private static IConnection connection_class;

    @Before
    public void setUp() throws Exception {
        connection_class = new IConnectionImplH2();
        h2conn = new Student_DAO_Sub(connection_class.getConnection());
        initialize();
        createTables();
    }

    private static void initialize() throws Exception {
        student = new Student("dfs123", "Valentino", "Mokgale", "Mathematical Science");
        student.addModule(new Module("Calculus", "Cal1a", 6, 15, 77));
        student.addModule(new Module("Applied Mathematics", "Appm1a", 6, 15, 65));
    }

    private void createTables() throws Exception {
        for (File file : getFiles()) {
            executeSQL(getFileData(file.getPath()));
        }
    }

    private List<File> getFiles() {
        URL url = Student_DAO_Test.class.getClassLoader().getResource("sql");
        System.out.println("Testing " + url);
        File directory = new File(url.getFile());
        System.out.println("Testing2 " + directory);
        List<File> fileInDirectory = Arrays.asList(directory.listFiles());
        System.out.println("Testing3 " + fileInDirectory.get(0));
        Collections.sort(fileInDirectory, new SortByDQLFileIndex());

        return fileInDirectory;
    }

    private String getFileData(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
    }

    private void executeSQL(String sql_statement) throws SQLException {
        Statement stm = h2conn.getConnection().createStatement();
        stm.executeUpdate(sql_statement);
    }

    @Test
    public void create_student_table() throws Exception {

        PreparedStatement ps = h2conn.getConnection().prepareStatement("SHOW COLUMNS FROM STUDENT");
        ResultSet rs = ps.executeQuery();
        List<String> column_names = new ArrayList<>();

        while (rs.next()) {
            column_names.add(rs.getString("Field"));
        }
        Assert.assertEquals("S_NO", column_names.get(0));
        Assert.assertEquals("S_PASSWORD", column_names.get(1));
        Assert.assertEquals("S_NAME", column_names.get(2));
        Assert.assertEquals("S_SURNAME", column_names.get(3));
        Assert.assertEquals("DEGREE", column_names.get(4));

    }

    @Test
    public void add_student() throws Exception {
        h2conn.add_Student(student);

        PreparedStatement ps = h2conn.getConnection().prepareStatement("SELECT * FROM STUDENT;");
        ResultSet rs = ps.executeQuery();
        List<Student> students_from_db = new ArrayList<>();
        while (rs.next()) {
            Student newStudent = new Student(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            newStudent.setS_No(rs.getInt(1));
            students_from_db.add(newStudent);
        }

        Assert.assertEquals(1, students_from_db.size());
        Assert.assertEquals(student.getSt_password(), students_from_db.get(0).getSt_password());
        Assert.assertEquals(student.getS_Name(), students_from_db.get(0).getS_Name());
        Assert.assertEquals(student.getS_Surname(), students_from_db.get(0).getS_Surname());
        Assert.assertEquals(student.getS_Degree(), students_from_db.get(0).getS_Degree());

        PreparedStatement disable_foreign_key_check = h2conn.getConnection().prepareStatement("DROP ALL OBJECTS;");
        disable_foreign_key_check.execute();
    }

    @Test
    public void update_student() throws Exception {

        h2conn.add_Student(student);
        Student newStudent = new Student("dfs123", "New Student", "Jordan", "Writing");
        newStudent.setS_No(202000000);

        h2conn.updateStudent(newStudent, "", "");

        PreparedStatement ps = h2conn.getConnection().prepareStatement("SELECT * FROM STUDENT WHERE S_No=?;");
        ps.setInt(1, 202000000);
        ResultSet rs = ps.executeQuery();
        List<Student> students_from_db = new ArrayList<>();
        while (rs.next()) {
            Student newStudent2 = new Student(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            newStudent2.setS_No(rs.getInt(1));
            students_from_db.add(newStudent2);
        }
        Assert.assertEquals(1, students_from_db.size());
        Assert.assertEquals(newStudent.getSt_password(), students_from_db.get(0).getSt_password());
        Assert.assertEquals(newStudent.getS_Name(), students_from_db.get(0).getS_Name());
        Assert.assertEquals(newStudent.getS_Surname(), students_from_db.get(0).getS_Surname());
        Assert.assertEquals(newStudent.getS_Degree(), students_from_db.get(0).getS_Degree());
        PreparedStatement disable_foreign_key_check = h2conn.getConnection().prepareStatement("DROP ALL OBJECTS;");
        disable_foreign_key_check.execute();

    }

    @Test
    public void delete_student() throws Exception {
        h2conn.add_Student(student);
        //Before deletion
        PreparedStatement ps = h2conn.getConnection().prepareStatement("SELECT * FROM STUDENT;");
        ResultSet rs = ps.executeQuery();
        List<Student> students_from_db = new ArrayList<>();
        while (rs.next()) {
            Student newStudent = new Student(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            newStudent.setS_No(rs.getInt(1));
            students_from_db.add(newStudent);
        }
        Assert.assertEquals(1, students_from_db.size());

        h2conn.deleteStudent(student.getS_No(), "", "");
        //After deletion

        PreparedStatement ps2 = h2conn.getConnection().prepareStatement("SELECT * FROM STUDENT;");
        ResultSet rs2 = ps2.executeQuery();
        List<Student> students_from_db2 = new ArrayList<>();
        while (rs.next()) {
            Student newStudent = new Student(rs2.getString(2), rs2.getString(3), rs2.getString(4), rs2.getString(5));
            newStudent.setS_No(rs2.getInt(1));
            students_from_db2.add(newStudent);
        }
        Assert.assertEquals(0, students_from_db2.size());

        //dropping all object on db
        PreparedStatement disable_foreign_key_check = h2conn.getConnection().prepareStatement("DROP ALL OBJECTS;");
        disable_foreign_key_check.execute();
    }


    @After
    public void close_connection() throws Exception {
        h2conn.close();
    }


}

class SortByDQLFileIndex implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        return getFileIndex((File) o1) > getFileIndex((File) o2) ? 1 : -1;
    }

    private int getFileIndex(File file) {
        return Integer.parseInt(file.getName().split("\\_")[0]);
    }
}
