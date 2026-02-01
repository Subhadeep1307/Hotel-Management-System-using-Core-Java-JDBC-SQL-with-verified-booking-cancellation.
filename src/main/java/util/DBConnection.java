package util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jjdbc:postgresql://localhost:5432/Hotel_DB_P?user=postgres&password=1234";
//    private static final String USERNAME = "postgres";
//    private static final String PASSWORD = "1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}

