package py.una.bd;

import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.ResultSet;
import java.sql.SQLException;

public class Bd {

    private static final String url = "jdbc:postgresql://localhost:5433/TP-Sockets";
    private static final String user = "postgres";
    private static final String password = "1234";
 
    /**
     * @return objeto Connection 
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    

}
