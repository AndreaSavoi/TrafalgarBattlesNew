package singleton;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBconn {

    static Connection conn = null;

    private DBconn() {}

    public static Connection getDBConnection() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream= new FileInputStream("application.properties");
        try (inputStream) {
            properties.load(inputStream);
            if (conn == null) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost/trafalgarbattles", "root", (String) properties.get("password"));
            }
        } catch (Exception _) {
            throw new IOException("Something went wrong");
        }

        return conn;
    }

    public static void closeConnection() throws SQLException {
        if(conn != null) {
            try{
                conn.close();
                conn = null;
            } catch (SQLException _) {
                throw new SQLException("Something went wrong");
            }
        }
    }
}

