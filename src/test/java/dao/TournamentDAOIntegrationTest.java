package dao;

import bean.BeanTournCreation;
import exception.AlreadySubscribedException;
import exception.MaxParticipantsReachedException;
import exception.UserNotSubscribedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import singleton.DBconn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TournamentDAOIntegrationTest {

    private TournInfoDAOImpl tournInfoDAO;
    private TournOrgDAOImpl tournOrgDAO;
    private Connection conn;
    private static final String TEST_ORGANIZER = "org_test";
    private static final String TEST_PLAYER = "player_test";

    @BeforeEach
    void setUp() throws SQLException, IOException {
        conn = DBconn.getDBConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM trafalgarbattles.subscription WHERE username = '" + TEST_PLAYER + "';");
            stmt.executeUpdate("DELETE FROM trafalgarbattles.tournaments WHERE organizer = '" + TEST_ORGANIZER + "';");
            stmt.executeUpdate("DELETE FROM trafalgarbattles.login WHERE username = '" + TEST_ORGANIZER + "' OR username = '" + TEST_PLAYER + "';");

            stmt.executeUpdate("INSERT INTO trafalgarbattles.login (username, password, email, type) VALUES ('" + TEST_ORGANIZER + "', 'pass', 'org@example.com', 'Organizer');");
            stmt.executeUpdate("INSERT INTO trafalgarbattles.login (username, password, email, type) VALUES ('" + TEST_PLAYER + "', 'pass', 'player@example.com', 'Player');");
        }
        tournInfoDAO = new TournInfoDAOImpl();
        tournOrgDAO = new TournOrgDAOImpl();
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM trafalgarbattles.subscription WHERE username = '" + TEST_PLAYER + "';");
            stmt.executeUpdate("DELETE FROM trafalgarbattles.tournaments WHERE organizer = '" + TEST_ORGANIZER + "';");
            stmt.executeUpdate("DELETE FROM trafalgarbattles.login WHERE username = '" + TEST_ORGANIZER + "' OR username = '" + TEST_PLAYER + "';");
        }
        DBconn.closeConnection();
    }

    @Test
    void testAddTournamentSuccessfully() throws SQLException, IOException {
        BeanTournCreation bean = new BeanTournCreation("NewTournamentDB", 10, LocalDate.now().plusDays(7), TEST_ORGANIZER);
        assertDoesNotThrow(() -> tournOrgDAO.addTourn(bean));

        List<String> tNames = new ArrayList<>();
        List<String> nParticipants = new ArrayList<>();
        List<String> nSubscribed = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        List<String> snos = new ArrayList<>();
        tournInfoDAO.getAllInfo(tNames, nParticipants, nSubscribed, dates, snos, "org", TEST_ORGANIZER);

        assertTrue(tNames.contains("NewTournamentDB"));
        assertEquals("10", nParticipants.get(tNames.indexOf("NewTournamentDB")));
    }

    @Test
    void testModifyTournamentSuccessfully() throws SQLException, IOException {
        BeanTournCreation originalBean = new BeanTournCreation("ModifyTournamentDB", 10, LocalDate.now().plusDays(10), TEST_ORGANIZER);
        tournOrgDAO.addTourn(originalBean);

        LocalDate newDate = LocalDate.now().plusMonths(1);
        BeanTournCreation modifiedBean = new BeanTournCreation("ModifyTournamentDB", 15, newDate, TEST_ORGANIZER);
        assertDoesNotThrow(() -> tournOrgDAO.modifyTourn(modifiedBean));

        List<String> curr = new ArrayList<>();
        List<String> tNames = new ArrayList<>();
        List<String> nParticipants = new ArrayList<>();
        List<String> nSubscribed = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        List<String> snos = new ArrayList<>();
        tournInfoDAO.getAllInfo(tNames, nParticipants, nSubscribed, dates, snos, "org", TEST_ORGANIZER);
        int sno = Integer.parseInt(snos.get(tNames.indexOf("ModifyTournamentDB")));

        tournInfoDAO.getSpecific(curr, sno);
        assertEquals(newDate.toString(), curr.get(3));
        assertEquals("15", curr.get(1));
    }

    @Test
    void testDeleteTournamentSuccessfully() throws SQLException, IOException {
        BeanTournCreation bean = new BeanTournCreation("DeleteTournamentDB", 5, LocalDate.now().plusDays(2), TEST_ORGANIZER);
        tournOrgDAO.addTourn(bean);

        assertDoesNotThrow(() -> tournOrgDAO.deleteTourn("DeleteTournamentDB"));

        List<String> tNames = new ArrayList<>();
        List<String> nParticipants = new ArrayList<>();
        List<String> nSubscribed = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        List<String> snos = new ArrayList<>();
        tournInfoDAO.getAllInfo(tNames, nParticipants, nSubscribed, dates, snos, "org", TEST_ORGANIZER);
        assertFalse(tNames.contains("DeleteTournamentDB"));
    }

    @Test
    void testAddSubSuccessfully() throws SQLException, IOException {
        BeanTournCreation bean = new BeanTournCreation("SubscribeTournamentDB", 2, LocalDate.now().plusDays(5), TEST_ORGANIZER);
        tournOrgDAO.addTourn(bean);

        assertDoesNotThrow(() -> tournInfoDAO.addSub(TEST_PLAYER, "SubscribeTournamentDB"));

        List<String> curr = new ArrayList<>();
        List<String> tNames = new ArrayList<>();
        List<String> nParticipants = new ArrayList<>();
        List<String> nSubscribed = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        List<String> snos = new ArrayList<>();
        tournInfoDAO.getAllInfo(tNames, nParticipants, nSubscribed, dates, snos, "all", null);
        int sno = Integer.parseInt(snos.get(tNames.indexOf("SubscribeTournamentDB")));

        tournInfoDAO.getSpecific(curr, sno);
        assertEquals("1", curr.get(2));
    }

    @Test
    void testAddSubAlreadySubscribedThrowsException() throws SQLException, IOException, AlreadySubscribedException, MaxParticipantsReachedException {
        BeanTournCreation bean = new BeanTournCreation("SubAlreadyExistsDB", 2, LocalDate.now().plusDays(5), TEST_ORGANIZER);
        tournOrgDAO.addTourn(bean);

        tournInfoDAO.addSub(TEST_PLAYER, "SubAlreadyExistsDB");

        AlreadySubscribedException thrown = assertThrows(AlreadySubscribedException.class, () -> {
            tournInfoDAO.addSub(TEST_PLAYER, "SubAlreadyExistsDB");
        });
        assertEquals("You're already subscribed to this tournament.", thrown.getMessage());
    }

    @Test
    void testAddSubMaxParticipantsReachedThrowsException() throws SQLException, IOException, AlreadySubscribedException, MaxParticipantsReachedException {
        BeanTournCreation bean = new BeanTournCreation("MaxParticipantsDB", 1, LocalDate.now().plusDays(5), TEST_ORGANIZER);
        tournOrgDAO.addTourn(bean);

        tournInfoDAO.addSub(TEST_PLAYER, "MaxParticipantsDB");

        MaxParticipantsReachedException thrown = assertThrows(MaxParticipantsReachedException.class, () -> {
            tournInfoDAO.addSub("another_player", "MaxParticipantsDB");
        });
        assertEquals("Max participants reached for this tournament.", thrown.getMessage());
    }


    @Test
    void testRemoveSubSuccessfully() throws SQLException, IOException, AlreadySubscribedException, MaxParticipantsReachedException {
        BeanTournCreation bean = new BeanTournCreation("UnsubscribeTournamentDB", 2, LocalDate.now().plusDays(5), TEST_ORGANIZER);
        tournOrgDAO.addTourn(bean);
        tournInfoDAO.addSub(TEST_PLAYER, "UnsubscribeTournamentDB");

        assertDoesNotThrow(() -> tournInfoDAO.removeSub(TEST_PLAYER, "UnsubscribeTournamentDB"));

        List<String> curr = new ArrayList<>();
        List<String> tNames = new ArrayList<>();
        List<String> nParticipants = new ArrayList<>();
        List<String> nSubscribed = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        List<String> snos = new ArrayList<>();
        tournInfoDAO.getAllInfo(tNames, nParticipants, nSubscribed, dates, snos, "all", null);
        int sno = Integer.parseInt(snos.get(tNames.indexOf("UnsubscribeTournamentDB")));

        tournInfoDAO.getSpecific(curr, sno);
        assertEquals("0", curr.get(2));
    }

    @Test
    void testRemoveSubUserNotSubscribedThrowsException() {
        UserNotSubscribedException thrown = assertThrows(UserNotSubscribedException.class, () -> {
            tournInfoDAO.removeSub(TEST_PLAYER, "NonExistentSubscription");
        });
        assertEquals("You're not subscribed to this tournament.", thrown.getMessage());
    }
}
