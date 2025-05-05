package applicationcontrollers;

import dao.ProfileDAO;
import dao.ProfileDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class ApplicationControllerProfile {
    private final ProfileDAO dao;

    public ApplicationControllerProfile() throws IOException {
        this.dao = new ProfileDAOImpl();
    }

    public Map<String, String> getUserProfile(String username) throws SQLException, IOException {
        return dao.getProfileInfo(username);
    }

    public void updateUserProfile(String birth, String game, String sex, String fullname, String username) throws SQLException, IOException {
        dao.updateProfileInfo(birth, game, sex, fullname, username);
    }
}
