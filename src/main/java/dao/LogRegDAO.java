package dao;

import java.io.IOException;
import java.sql.SQLException;

public interface LogRegDAO {
    public boolean getLogInfo(String username, String password) throws SQLException, IOException;

    public boolean register(String email, String username, String password) throws SQLException, IOException;
}
