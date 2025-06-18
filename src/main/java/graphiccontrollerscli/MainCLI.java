package graphiccontrollerscli;

import singleton.SessionManager;
import users.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class MainCLI {

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        MainCLI mainCLI = new MainCLI();
        mainCLI.start();
        // try
    }

    public void start() {
        System.out.println("Welcome in Trafalgar Battles!");
        while (true) {
            displayMainMenu();
            try {
                String choice = READER.readLine();
                if (handleMainMenuChoice(choice)) {
                    System.out.println("Goodbye!");
                    break;
                }
            } catch (IOException e) {
                System.err.println("I/O Error: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("Not valid input, please insert a number.");
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private void displayMainMenu() {
        User currentUser = SessionManager.getCurrentUser();
        System.out.println("\n--- Main Menu ---");
        if (currentUser == null) {
            System.out.println("1. Login");
            System.out.println("2. Register");
        } else {
            System.out.println("Welcome, " + currentUser.getUsername() + "!");
            if (currentUser instanceof users.Organizer) {
                System.out.println("1. Manage Tournaments (Organizer)");
                System.out.println("2. Create Tournament");
                System.out.println("3. Logout");
            } else { // Player
                System.out.println("1. Show Tournaments");
                System.out.println("2. Subscriptions");
                System.out.println("3. My Profile");
                System.out.println("4. Logout");
            }
        }
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
    }

    private boolean handleMainMenuChoice(String choice) throws IOException, SQLException {
        User currentUser = SessionManager.getCurrentUser();

        if (currentUser == null) {
            switch (choice) {
                case "1":
                    new LogRegCLI(READER).showLoginForm();
                    break;
                case "2":
                    new LogRegCLI(READER).showRegistrationForm();
                    break;
                case "0":
                    return true;
                default:
                    System.out.println("Not a valid option!.");
            }
        } else {
            if (currentUser instanceof users.Organizer) {
                switch (choice) {
                    case "1":
                        new MainOrganizerCLI(READER).displayOrganizerTournaments();
                        break;
                    case "2":
                        new CreateTournamentCLI(READER).createTournament();
                        break;
                    case "3":
                        SessionManager.clear();
                        System.out.println("Logout successful!.");
                        break;
                    case "0":
                        return true;
                    default:
                        System.out.println("Not a valid option!.");
                }
            } else {
                switch (choice) {
                    case "1":
                        new MainPlayerCLI(READER).displayAvailableTournaments();
                        break;
                    case "2":
                        new SubsCLI(READER).displaySubscribedTournaments();
                        break;
                    case "3":
                        new ProfileCLI(READER).showProfile();
                        break;
                    case "4":
                        SessionManager.clear();
                        System.out.println("Logout successful!.");
                        break;
                    case "0":
                        return true;
                    default:
                        System.out.println("Not a valid option!.");
                }
            }
        }
        return false;
    }
}