package IConnectionpac;

import java.sql.Connection;

public interface IConnection {
    Connection getConnection() throws Exception;
}
