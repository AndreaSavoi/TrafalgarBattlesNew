package dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import singleton.DBconn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProfileDAOImplIntegrationTest {

    private ProfileDAOImpl profileDAO;
    private Connection conn;
    private static final String TEST_USERNAME = "profile_test_user";

    @BeforeEach
    void setUp() throws SQLException, IOException {
        conn = DBconn.getDBConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM trafalgarbattles.login WHERE username = '" + TEST_USERNAME + "';");
            stmt.executeUpdate("INSERT INTO trafalgarbattles.login (username, password, email, type) VALUES ('" + TEST_USERNAME + "', 'pass', 'test@example.com', 'Player');");
        }
        profileDAO = new ProfileDAOImpl();
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM trafalgarbattles.login WHERE username = '" + TEST_USERNAME + "';");
        }
        DBconn.closeConnection();
    }

    @Test
    void testGetProfileInfoSuccessfully() throws SQLException, IOException {
        Map<String, String> profileInfo = profileDAO.getProfileInfo(TEST_USERNAME);
        assertNotNull(profileInfo);
        assertEquals(4, profileInfo.size());
        assertTrue(profileInfo.containsKey("birth"));
        assertTrue(profileInfo.containsKey("game"));
        assertTrue(profileInfo.containsKey("sex"));
        assertTrue(profileInfo.containsKey("fullname"));
    }

    @Test
    void testUpdateProfileInfoSuccessfully() throws SQLException, IOException {
        String newBirth = "1995-12-25";
        String newGame = "Chess";
        String newSex = "Male";
        String newFullname = "Test User Fullname";

        assertDoesNotThrow(() -> profileDAO.updateProfileInfo(newBirth, newGame, newSex, newFullname, TEST_USERNAME));

        Map<String, String> updatedProfile = profileDAO.getProfileInfo(TEST_USERNAME);
        assertEquals(newBirth, updatedProfile.get("birth"));
        assertEquals(newGame, updatedProfile.get("game"));
        assertEquals(newSex, updatedProfile.get("sex"));
        assertEquals(newFullname, updatedProfile.get("fullname"));
    }

    @Test
    void testUpdateProfileInfoWithBlankValuesSetsNullForDateAndEmptyForOthers() throws SQLException, IOException {

        profileDAO.updateProfileInfo("2000-01-01", "Poker", "Female", "Original Name", TEST_USERNAME);

        String blankBirth = "";
        String newGame = "Go";
        String blankSex = "";
        String newFullname = "New Name";

        assertDoesNotThrow(() -> profileDAO.updateProfileInfo(blankBirth, newGame, blankSex, newFullname, TEST_USERNAME));

        Map<String, String> updatedProfile = profileDAO.getProfileInfo(TEST_USERNAME);
        assertNull(updatedProfile.get("birth"));

        assertEquals(newGame, updatedProfile.get("game"));
        assertEquals("", updatedProfile.get("sex"));
        assertEquals(newFullname, updatedProfile.get("fullname"));
    }
}
