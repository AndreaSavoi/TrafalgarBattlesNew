package graphiccontrollerscli;

import applicationcontrollers.ApplicationControllerTournInfo;
import bean.BeanCurrTourn;
import exception.AlreadySubscribedException;
import exception.MaxParticipantsReachedException;
import exception.UserNotSubscribedException;
import singleton.SessionManager;
import users.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

public class TournPagePlayerCLI {
    private final BufferedReader reader;
    private final int tournamentSno;

    public TournPagePlayerCLI(BufferedReader reader, int tournamentSno) {
        this.reader = reader;
        this.tournamentSno = tournamentSno;
    }

    public void showTournamentInfo() throws IOException, SQLException {
        BeanCurrTourn beanCurrTourn = BeanCurrTourn.getInstance();
        beanCurrTourn.setSno(tournamentSno);

        try {
            new ApplicationControllerTournInfo(beanCurrTourn);
        } catch (Exception e) {
            System.out.println("Error while loading the tournament info: " + e.getMessage());
            return;
        }

        System.out.println("\n--- Tournament Details: " + beanCurrTourn.gettName() + " ---");
        System.out.println("Name: " + beanCurrTourn.gettName());
        System.out.println("Date: " + beanCurrTourn.getDates());
        System.out.println("Partecipants: " + beanCurrTourn.getnSubscribed() + "/" + beanCurrTourn.getnPartecipants());

        User currentUser = SessionManager.getCurrentUser();
        if (currentUser != null) {
            while (true) {
                System.out.println("\nOptions:");
                System.out.println("1. Subscribe");
                System.out.println("2. Unsubscribe");
                System.out.println("0. Go back to the tournament page");
                System.out.print("Choose an option: ");
                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        subscribeToTournament(currentUser.getUsername(), beanCurrTourn.gettName());
                        try {
                            new ApplicationControllerTournInfo(beanCurrTourn);
                            System.out.println("Partecipants updated: " + beanCurrTourn.getnSubscribed() + "/" + beanCurrTourn.getnPartecipants());
                        } catch (Exception e) {
                            System.out.println("Error while reloading informations: " + e.getMessage());
                        }
                        break;
                    case "2":
                        unsubscribeFromTournament(currentUser.getUsername(), beanCurrTourn.gettName());
                        try {
                            new ApplicationControllerTournInfo(beanCurrTourn);
                            System.out.println("Partecipants updated: " + beanCurrTourn.getnSubscribed() + "/" + beanCurrTourn.getnPartecipants());
                        } catch (Exception e) {
                            System.out.println("Error while reloading informations: " + e.getMessage());
                        }
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Not a valid option!.");
                }
            }
        } else {
            System.out.println("Please login to subscribe or unsubscribe to the tournament.");
            System.out.println("Press enter to continue...");
            String ignoredInput = reader.readLine();
        }
    }

    private void subscribeToTournament(String username, String tName) throws SQLException, IOException {
        try {
            new ApplicationControllerTournInfo(username, tName, "sub");
            System.out.println("Successfully subscribed the tournament.!");
        } catch (AlreadySubscribedException | MaxParticipantsReachedException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error while subscribing to the tournament.");
        }
    }

    private void unsubscribeFromTournament(String username, String tName) throws SQLException, IOException {
        try {
            new ApplicationControllerTournInfo(username, tName, "unsub");
            System.out.println("Successfully unsubscribed the tournament.!");
        } catch (UserNotSubscribedException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error while unsubscribing to the tournament.");
        }
    }
}
