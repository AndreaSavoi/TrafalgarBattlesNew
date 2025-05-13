package dao;

import bean.BeanTournCreation;
import queries.Queries;
import singleton.DBconn;

import java.io.IOException;
import java.sql.*;

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

    public void addTourn(BeanTournCreation bean) throws IOException, SQLException {
        connVerify();

        stmt = conn.prepareStatement(Queries.getAddTournament());
        stmt.setString(1, bean.getName());
        stmt.setDate(2, Date.valueOf(bean.getDate()));
        stmt.setInt(3, bean.getMaxPlayers());
        stmt.setString(4, bean.getOrganizer());
        stmt.executeUpdate();
    }

}
