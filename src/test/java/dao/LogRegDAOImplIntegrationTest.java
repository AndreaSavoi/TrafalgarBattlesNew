package dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import singleton.DBconn;
import singleton.SessionManager;
import users.Player;
import users.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class LogRegDAOImplIntegrationTest {

    private LogRegDAOImpl logRegDAO;
    private Connection conn;

    @BeforeEach
    void setUp() throws SQLException, IOException {
        conn = DBconn.getDBConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM trafalgarbattles.login WHERE username = 'testuser_db' OR username = 'newuser_db';");
        }
        logRegDAO = new LogRegDAOImpl();
        SessionManager.clear();
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM trafalgarbattles.login WHERE username = 'testuser_db' OR username = 'newuser_db';");
        }
        DBconn.closeConnection();
    }

    @Test
    void testRegisterUserSuccessfully() {
        assertDoesNotThrow(() -> logRegDAO.register("newuser_db@example.com", "newuser_db", "password123", "Player"));
        assertTrue(true, "User should be registered successfully.");
    }

    @Test
    void testRegisterDuplicateUsernameThrowsException() throws SQLException, IOException {
        logRegDAO.register("user_exists@example.com", "testuser_db", "pass", "Player");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            logRegDAO.register("another_email@example.com", "testuser_db", "anotherpass", "Player");
        });
        assertEquals("Something went wrong", thrown.getMessage());
    }

    @Test
    void testGetLogInfoSuccessfully() throws SQLException, IOException {
        logRegDAO.register("login_test@example.com", "testuser_db", "testpass_db", "Player");

        User user = logRegDAO.getLogInfo("testuser_db", "testpass_db", "Player");
        assertNotNull(user);
        assertInstanceOf(Player.class, user);
        assertEquals("testuser_db", user.getUsername());
        assertEquals("login_test@example.com", user.getEmail());
        assertEquals(user, SessionManager.getCurrentUser());
    }

    @Test
    void testGetLogInfoInvalidCredentialsThrowsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            logRegDAO.getLogInfo("nonexistent", "wrongpass", "Player");
        });
        assertEquals("Something went wrong", thrown.getMessage());
        assertNull(SessionManager.getCurrentUser());
    }
}
