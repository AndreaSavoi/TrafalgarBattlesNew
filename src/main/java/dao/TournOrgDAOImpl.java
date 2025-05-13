package dao;

import queries.Queries;
import singleton.DBconn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TournOrgDAOImpl {

    private final Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    private void connVerify() throws IOException {
        if(conn == null) {
            DBconn.getDBConnection();
        }
    }

    public TournOrgDAOImpl() throws IOException {
        conn = DBconn.getDBConnection();
    }

    public void addTourn(String tName, String date, String nPartecipants, String organizer) throws IOException, SQLException {
        connVerify();

        stmt = conn.prepareStatement(Queries.getAddTournament());
        stmt.setString(1, tName);
        stmt.setString(2, date);
        stmt.setString(3, nPartecipants);
        stmt.setString(4, organizer);
        stmt.executeUpdate();
    }

}
