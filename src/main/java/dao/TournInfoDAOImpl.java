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

        try( PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (mode.equals("sub") || mode.equals("org")) {
                stmt.setString(1, username);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tName.add(rs.getString("tname"));
                    nPartecipants.add(rs.getString("npartecipants"));
                    nSubscribed.add(rs.getString("nsubscribed"));
                    dates.add(rs.getString("dates"));
                    sno.add(rs.getString("sno"));
                }
            }
        }
    }

    @Override
    public void getSpecific(List<String> curr, int sno) throws SQLException, IOException {
        connVerify();

        try (PreparedStatement stmt = conn.prepareStatement(Queries.getQueryCurrTourn())) {
            stmt.setInt(1, sno);
            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    curr.add(rs.getString("tname"));
                    curr.add(rs.getString("npartecipants"));
                    curr.add(rs.getString("nsubscribed"));
                    curr.add(rs.getString("dates"));
                }
            }
        }
    }

    @Override
    public void addSub(String username, String tName) throws SQLException, IOException, AlreadySubscribedException, MaxParticipantsReachedException {
        connVerify();

        try (PreparedStatement stmt = conn.prepareStatement(Queries.getQueryAddSub())) {
            stmt.setString(1, username);
            stmt.setString(2, tName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = e.getMessage().toLowerCase();
            if (msg.contains("already registered")) {
                throw new AlreadySubscribedException("You're already subscribed to this tournament.");
            } else if (msg.contains("maximum participants")) {
                throw new MaxParticipantsReachedException("Max partecipants reached for this tournament.");
            } else {
                throw e;
            }
        }
    }

    public void removeSub(String username, String tName) throws SQLException, IOException, UserNotSubscribedException {
        connVerify();

        try (PreparedStatement stmtCheck = conn.prepareStatement(Queries.getQueryCheckSub())) {
            stmtCheck.setString(1, username);
            stmtCheck.setString(2, tName);
            try (ResultSet rs = stmtCheck.executeQuery()) {
                if(!rs.next()) {throw new UserNotSubscribedException("You're not subscribed to this tournament.");}
            }
        }

        try (PreparedStatement stmtDelete = conn.prepareStatement(Queries.getQueryDelSub())) {
            stmtDelete.setString(1, username);
            stmtDelete.setString(2, tName);
            stmtDelete.executeUpdate();
        } catch (SQLException _) {
            throw new IllegalArgumentException("Something went wrong while removing subscribed tournament.");
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
}
