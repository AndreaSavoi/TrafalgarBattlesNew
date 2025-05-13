package dao;

import exception.AlreadySubscribedException;
import exception.MaxParticipantsReachedException;
import exception.UserNotSubscribedException;
import queries.Queries;
import singleton.DBconn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TournInfoDAOImpl implements TournInfoDAO {
    private final Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    private void connVerify() throws IOException {
        if(conn == null) {
            DBconn.getDBConnection();
        }
    }

    public TournInfoDAOImpl() throws IOException {
        conn = DBconn.getDBConnection();
    }

    @Override
    public void getAllInfo(List<String> tName, List<String> nPartecipants, List<String> nSubscribed, List<String> dates, List<String> sno, String mode, String username) throws SQLException, IOException {
        connVerify();

        String sql = getString(mode, username);

        stmt = conn.prepareStatement(sql);

        if (mode.equals("sub") || mode.equals("org")) {
            stmt.setString(1, username); // imposto l'username solo in sub e org
        }

        rs = stmt.executeQuery();
        while(rs.next()){
            tName.add(rs.getString("tname"));
            nPartecipants.add(rs.getString("npartecipants"));
            nSubscribed.add(rs.getString("nsubscribed"));
            dates.add(rs.getString("dates"));
            sno.add(rs.getString("sno"));
        }
    }

    private static String getString(String mode, String username) {
        String sql;
        switch (mode) {
            case "sub" -> {
                if (username == null) {
                    throw new IllegalArgumentException("Username cannot be null when mode is 'sub'");
                }
                sql = Queries.getQuerySubTournaments();
            }
            case "all" -> sql = Queries.getQueryAllTournaments();
            case "org" -> sql = Queries.getQueryOrgTournaments();
            default -> throw new IllegalArgumentException("Invalid mode: " + mode);
        }
        return sql;
    }

    public void getSpecific(List<String> curr, int sno) throws SQLException, IOException {
        connVerify();

        stmt = conn.prepareStatement(Queries.getQueryCurrTourn());
        stmt.setInt(1, sno);
        rs = stmt.executeQuery();
        while(rs.next()) {
            curr.add(rs.getString("tname"));
            curr.add(rs.getString("npartecipants"));
            curr.add(rs.getString("nsubscribed"));
            curr.add(rs.getString("dates"));
        }
    }

    public void addSub(String username, String tName) throws SQLException, IOException, AlreadySubscribedException, MaxParticipantsReachedException {
        connVerify();

        stmt = conn.prepareStatement(Queries.getQueryAddSub());
        stmt.setString(1, username);
        stmt.setString(2, tName);
        stmt.executeUpdate();
    }

    public void removeSub(String username, String tName) throws SQLException, IOException, UserNotSubscribedException {
        connVerify();

        stmt = conn.prepareStatement(Queries.getQueryCheckSub());
        stmt.setString(1, username);
        stmt.setString(2, tName);
        rs = stmt.executeQuery();

        if(!rs.next()) {throw new UserNotSubscribedException("You're not subscribed to this tournament.");}

        stmt = conn.prepareStatement(Queries.getQueryDelSub());
        stmt.setString(1, username);
        stmt.setString(2, tName);
        stmt.executeUpdate();
    }
}
