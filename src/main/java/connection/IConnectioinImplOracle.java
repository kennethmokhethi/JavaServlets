package connection;

import IConnectionpac.IConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class IConnectioinImplOracle implements IConnection {

    public Connection getConnection() throws Exception {
        final String URL = "URL_OF_DATABASE_TO_CONNECT_TO";
        final String USER = "USER";
        final String PASSWORD = "PASSWORD_OF_DATABASE";
        final String dbDrive = "oracle.jdbc.driver.OracleDriver";
        Class.forName(dbDrive);
        return DriverManager.getConnection(URL, USER, PASSWORD);

    }

}
