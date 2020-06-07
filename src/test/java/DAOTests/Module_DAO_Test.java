package DAOTests;

import IConnectionpac.IConnection;
import connectionH2.IConnectionImplH2;
import model.Student;
import org.junit.*;
import subClass.Module_DAO_Sub;
import model.Module;

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

public class Module_DAO_Test {

    private static ArrayList<Module> module_list = new ArrayList<>();
    private static Student student;
    private static Module_DAO_Sub h2conn;
    private static IConnection connection_class;

    @Before
    public void setUp() throws Exception {
        connection_class = new IConnectionImplH2();
        h2conn = new Module_DAO_Sub(connection_class.getConnection());
        initialize();
        createTables();
    }

    private static void initialize() {
        student = new Student("", "Sophie", "Mavuso", "Bcom Accounting");
        module_list.add(new Module("Calculus", "Cal1a", 6, 15, 77));
        module_list.add(new Module("Applied Mathematics", "Appm1a", 6, 15, 65));
        student.addModule(module_list.get(0));
        student.addModule(module_list.get(1));
    }

    private void createTables() throws Exception {
        for (File file : getFiles()) {
            executeSQL(getFileData(file.getPath()));
        }
    }

    private List<File> getFiles() {
        URL url = Student_DAO_Test.class.getClassLoader().getResource("sql");
        File directory = new File(url.getFile());
        List<File> fileInDirectory = Arrays.asList(directory.listFiles());
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
    public void create_module_table() throws Exception {


        PreparedStatement ps = h2conn.getConnection().prepareStatement("SHOW COLUMNS FROM MODULE;");
        ResultSet rs = ps.executeQuery();
        List<String> column_names = new ArrayList<>();

        while (rs.next()) {
            column_names.add(rs.getString("Field"));
        }

        Assert.assertEquals("M_NAME", column_names.get(0));
        Assert.assertEquals("M_CODE", column_names.get(1));
        Assert.assertEquals("M_DURATION", column_names.get(2));
        Assert.assertEquals("M_CREDITS", column_names.get(3));

        PreparedStatement disable_foreign_key_check = h2conn.getConnection().prepareStatement("DROP ALL OBJECTS;");
        disable_foreign_key_check.execute();

    }

    @Test
    public void add_module() throws Exception {


        h2conn.add_module(student, 0);
        h2conn.add_module(student, 1);

        PreparedStatement ps = h2conn.getConnection().prepareStatement("SELECT * FROM MODULE;");
        ResultSet rs = ps.executeQuery();
        List<Module> modules_from_db2 = new ArrayList<>();

        while (rs.next()) {
            modules_from_db2.add(new Module(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), 0));
        }
        Assert.assertEquals(2, modules_from_db2.size());
        Assert.assertEquals(student.getStudent_modules().get(0).getM_Code(), modules_from_db2.get(0).getM_Code());
        Assert.assertEquals(student.getStudent_modules().get(0).getM_Name(), modules_from_db2.get(0).getM_Name());
        Assert.assertEquals(student.getStudent_modules().get(0).getM_Credits(), modules_from_db2.get(0).getM_Credits());
        Assert.assertEquals(student.getStudent_modules().get(0).getM_Duration(), modules_from_db2.get(0).getM_Duration());

        Assert.assertEquals(student.getStudent_modules().get(1).getM_Code(), modules_from_db2.get(1).getM_Code());
        Assert.assertEquals(student.getStudent_modules().get(1).getM_Name(), modules_from_db2.get(1).getM_Name());
        Assert.assertEquals(student.getStudent_modules().get(1).getM_Credits(), modules_from_db2.get(1).getM_Credits());
        Assert.assertEquals(student.getStudent_modules().get(1).getM_Duration(), modules_from_db2.get(1).getM_Duration());

        PreparedStatement disable_foreign_key_check = h2conn.getConnection().prepareStatement("DROP ALL OBJECTS;");
        disable_foreign_key_check.execute();

    }

    @Test
    public void verify_update() throws Exception {


        h2conn.add_module(student, 0);
        h2conn.add_module(student, 1);

        Student newStudent = new Student(" ", "Test", "Johns", "BeD");
        newStudent.addModule(new Module("Teaching studies", "TST2A", 6, 15, 54));
        newStudent.addModule(new Module("English2A", "Eng2A", 6, 15, 84));
        h2conn.update_module(newStudent, student.getStudent_modules().get(0).getM_Code(), student.getStudent_modules().get(1).getM_Code());

        //Extracting the updated data on the module table
        PreparedStatement ps = h2conn.getConnection().prepareStatement("SELECT * FROM MODULE WHERE M_Code IN(?,?);");
        ps.setString(1, newStudent.getStudent_modules().get(0).getM_Code());
        ps.setString(2, newStudent.getStudent_modules().get(1).getM_Code());
        ResultSet rs = ps.executeQuery();

        //Storing the results in an arraylist
        List<Module> modules_from_db = new ArrayList<>();

        while (rs.next()) {
            modules_from_db.add(new Module(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), 0));
        }
        //Comparing data from database with the newStudent created
        Assert.assertEquals(2, modules_from_db.size());
        Assert.assertEquals(newStudent.getStudent_modules().get(0).getM_Code(), modules_from_db.get(1).getM_Code());
        Assert.assertEquals(newStudent.getStudent_modules().get(0).getM_Name(), modules_from_db.get(1).getM_Name());
        Assert.assertEquals(newStudent.getStudent_modules().get(0).getM_Credits(), modules_from_db.get(1).getM_Credits());
        Assert.assertEquals(newStudent.getStudent_modules().get(0).getM_Duration(), modules_from_db.get(1).getM_Duration());

        Assert.assertEquals(newStudent.getStudent_modules().get(1).getM_Code(), modules_from_db.get(0).getM_Code());
        Assert.assertEquals(newStudent.getStudent_modules().get(1).getM_Name(), modules_from_db.get(0).getM_Name());
        Assert.assertEquals(newStudent.getStudent_modules().get(1).getM_Credits(), modules_from_db.get(0).getM_Credits());
        Assert.assertEquals(newStudent.getStudent_modules().get(1).getM_Duration(), modules_from_db.get(0).getM_Duration());

        PreparedStatement dropping_objects = h2conn.getConnection().prepareStatement("DROP ALL OBJECTS;");
        dropping_objects.execute();

    }


    @Test
    public void delete_module() throws Exception {

        h2conn.add_module(student, 0);
        h2conn.add_module(student, 1);

        PreparedStatement ps = h2conn.getConnection().prepareStatement("SELECT * FROM MODULE;");
        ResultSet rs = ps.executeQuery();
        List<Module> modules_from_db2 = new ArrayList<>();

        //Before deletion
        while (rs.next()) {
            modules_from_db2.add(new Module(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), 0));
        }


        h2conn.delete_module(student.getStudent_modules().get(0).getM_Code(), student.getStudent_modules().get(1).getM_Code());

        Assert.assertEquals(2, modules_from_db2.size());

        //after deletion
        PreparedStatement ps2 = h2conn.getConnection().prepareStatement("SELECT * FROM MODULE;");
        ResultSet rs2 = ps2.executeQuery();
        List<Module> modules_from_db3 = new ArrayList<>();

        while (rs2.next()) {
            modules_from_db3.add(new Module(rs2.getString(1), rs2.getString(2), rs2.getInt(3), rs2.getInt(4), 0));
        }

        Assert.assertEquals(0, modules_from_db3.size());

        PreparedStatement dropping_objects = h2conn.getConnection().prepareStatement("DROP ALL OBJECTS;");
        dropping_objects.execute();

    }


    @After
    public void close_connection() throws Exception {
        h2conn.close();
    }

}





