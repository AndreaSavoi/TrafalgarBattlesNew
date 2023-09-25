package dao;

import applicationcontrollers.CurrentUser;
import queries.Queries;
import singleton.DBconn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogRegDAOImpl implements LogRegDAO {

    private final Connection conn;

    private PreparedStatement stmt;

    private void connVerify() throws IOException {
        if(conn == null) {
            DBconn.getDBConnection();
        }
    }

    public LogRegDAOImpl() throws SQLException, IOException {
        conn = DBconn.getDBConnection();
    }

    @Override
    public boolean getLogInfo(String username, String password) throws SQLException, IOException {
        connVerify();

        stmt = conn.prepareStatement(Queries.getQueryLogin());
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            CurrentUser.setUsername(username);
            return true;
        }
        return false;
    }

    @Override
    public boolean register(String email, String username, String password) throws SQLException, IOException {
        connVerify();

        try {
            stmt = conn.prepareStatement(Queries.getRegisterUser());
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}
