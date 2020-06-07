package subClass;

import dao.Module_DAO;

import java.sql.Connection;

public class Module_DAO_Sub extends Module_DAO {

    public Module_DAO_Sub(Connection getConnection) throws Exception {
        super(getConnection);

    }

    @Override
    public void close() throws Exception {
        conn.createStatement().execute("DROP ALL OBJECTS");
        conn.close();
    }

    public Connection getConnection() {
        return conn;
    }
}
