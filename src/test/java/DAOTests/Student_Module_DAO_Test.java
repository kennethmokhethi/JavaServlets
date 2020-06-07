package DAOTests;

import IConnectionpac.IConnection;
import connectionH2.IConnectionImplH2;
import model.Student;
import model.Module;
import model.Student_module;
import org.junit.*;
import subClass.Module_DAO_Sub;
import subClass.Student_module_DAO_Sub;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Student_Module_DAO_Test {
    private static Student_module_DAO_Sub h2conn;
    private static Module_DAO_Sub h2connModule;
    private static Student student;
    private static IConnection connection_class;

    @Before
    public void setUp() throws Exception {
        connection_class = new IConnectionImplH2();
        h2conn = new Student_module_DAO_Sub(connection_class.getConnection());
        h2connModule = new Module_DAO_Sub(connection_class.getConnection());
        initialize();
        createTables();
    }

    private static void initialize() {
        student = new Student("sdw3fd", "John", "Simelane", "Graphic desing");
        student.addModule(new Module("Drawing32a2", "Draw32", 6, 20, 67));
        student.addModule(new Module("Paint2a", "Pt2a", 6, 20, 58));
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
    public void create_Student_module_table() throws Exception {

        PreparedStatement ps = h2conn.getConnection().prepareStatement("SHOW COLUMNS FROM STUDENT_MODULE;");
        ResultSet rs = ps.executeQuery();
        List<String> column_names = new ArrayList<>();

        while (rs.next()) {
            column_names.add(rs.getString("Field"));
        }

        Assert.assertEquals("M_CODE", column_names.get(0));
        Assert.assertEquals("S_NO", column_names.get(1));
        Assert.assertEquals("MARKS", column_names.get(2));

    }


    @After
    public void close_connection() throws Exception {
        h2conn.close();

    }

}
