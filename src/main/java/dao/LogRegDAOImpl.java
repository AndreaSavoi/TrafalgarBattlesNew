package dao;

import Users.Organizer;
import Users.Player;
import singleton.SessionManager;
import Users.User;
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
    public User getLogInfo(String username, String password, String type) throws SQLException, IOException {
        connVerify();

        stmt = conn.prepareStatement(Queries.getQueryLogin());
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setString(3, type);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            User user;
            if(type.equals("Player")){
                user = new Player(username);
            } else if (type.equals("Organizer")){
                user = new Organizer(username);
            } else {
                throw new IllegalArgumentException("Invalid type");
            }

            SessionManager.setCurrentUser(user);

            return user;
        }

        throw new IllegalArgumentException("Something went wrong");
    }

    @Override
    public boolean register(String email, String username, String password, String type) throws SQLException, IOException {
        connVerify();

        try {
            stmt = conn.prepareStatement(Queries.getRegisterUser());
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setString(4, type);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}
