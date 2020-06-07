package connectionH2;

import IConnectionpac.IConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class IConnectionImplH2 implements IConnection {

    public Connection getConnection() throws Exception {
        final String H2_URL = "jdbc:h2:mem:";
        final String H2_dbDrive = "org.h2.Driver";

        Class.forName(H2_dbDrive);
        Connection conn = DriverManager.getConnection(H2_URL, "", "");
        return conn;
    }
}
