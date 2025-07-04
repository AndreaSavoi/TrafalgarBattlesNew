package dao;

import bean.BeanTournCreation;
import queries.Queries;
import singleton.DBconn;

import java.io.IOException;
import java.sql.*;

public class TournOrgDAOImpl implements TournOrgDAO {

    private final Connection conn;

    private void connVerify() throws IOException {
        if(conn == null) {
            DBconn.getDBConnection();
        }
    }

    public TournOrgDAOImpl() throws IOException {
        conn = DBconn.getDBConnection();
    }

    @Override
    public void addTourn(BeanTournCreation bean) throws IOException, SQLException {
        connVerify();

        try (PreparedStatement stmt = conn.prepareStatement(Queries.getAddTournament())) {
            stmt.setString(1, bean.getName());
            stmt.setDate(2, Date.valueOf(bean.getDate()));
            stmt.setInt(3, bean.getMaxPlayers());
            stmt.setInt(4, 0);
            stmt.setString(5, bean.getOrganizer());
            stmt.executeUpdate();
        }
    }

    @Override
    public void modifyTourn(BeanTournCreation bean) throws IOException, SQLException {

        connVerify();

        try (PreparedStatement stmt = conn.prepareStatement(Queries.getModTournament())) {
            stmt.setDate(1, Date.valueOf(bean.getDate()));
            stmt.setInt(2, bean.getMaxPlayers());
            stmt.setString(3, bean.getName());
            stmt.executeUpdate();
        }

    }

    @Override
    public void deleteTourn(String name) throws IOException, SQLException {
        connVerify();

        try (PreparedStatement stmt = conn.prepareStatement(Queries.getDelTournament())) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        }

    }

}
