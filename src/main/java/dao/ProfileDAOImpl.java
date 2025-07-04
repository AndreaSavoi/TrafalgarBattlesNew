package dao;

import queries.Queries;
import singleton.DBconn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ProfileDAOImpl implements ProfileDAO{
    private final Connection conn;

    public ProfileDAOImpl() throws IOException {
        conn = DBconn.getDBConnection();
    }

    private void connVerify() throws IOException {
        if (conn == null) {
            DBconn.getDBConnection();
        }
    }

    @Override
    public void updateProfileInfo(String birth, String game, String sex, String fullname, String username) throws SQLException, IOException {
        connVerify();

        try (PreparedStatement stmt = conn.prepareStatement(Queries.getAddProfileInfo())) {
            if (birth != null && !birth.isBlank()) {
                stmt.setString(1, birth);
            } else {
                stmt.setNull(1, java.sql.Types.DATE);
            }
            stmt.setString(2, game);
            stmt.setString(3, sex);
            stmt.setString(4, fullname);
            stmt.setString(5, username);

            stmt.executeUpdate();
        }
    }

    @Override
    public Map<String, String> getProfileInfo(String username) throws SQLException, IOException {
        connVerify();
        Map<String, String> profileData = new HashMap<>();

        try (PreparedStatement stmt = conn.prepareStatement(Queries.getQueryProfileInfo())) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    profileData.put("birth", rs.getString("birth"));
                    profileData.put("game", rs.getString("game"));
                    profileData.put("sex", rs.getString("sex"));
                    profileData.put("fullname", rs.getString("fullname"));
                }
            }
        }

        return profileData;
    }
}
