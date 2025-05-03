package dao;

import users.User;

import java.io.IOException;
import java.sql.SQLException;

public interface LogRegDAO {
    public User getLogInfo(String username, String password, String type) throws SQLException, IOException;

    public boolean register(String email, String username, String password, String type) throws SQLException, IOException;
}
