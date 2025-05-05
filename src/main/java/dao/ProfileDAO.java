package dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public interface ProfileDAO {
    Map<String, String> getProfileInfo(String username) throws SQLException, IOException;

    void updateProfileInfo(String username, String birth, String sex, String fullname, String game) throws SQLException, IOException;

}
