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
        InputStream IS= new FileInputStream("application.properties");
        try (IS) {
            properties.load(IS);
            if (conn == null) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost/trafalgarbattles", "root", (String) properties.get("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static void closeConnection() throws SQLException {
        if(conn != null) {
            try{
                conn.close();
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

