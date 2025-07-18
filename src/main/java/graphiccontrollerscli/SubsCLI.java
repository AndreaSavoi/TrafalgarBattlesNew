package graphiccontrollerscli;

import applicationcontrollers.ApplicationControllerTournaments;
import bean.BeanTournList;
import singleton.SessionManager;
import users.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

public class SubsCLI {
    private final BufferedReader reader;

    public SubsCLI(BufferedReader reader) {
        this.reader = reader;
    }

    public void displaySubscribedTournaments() throws IOException, SQLException {
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser == null) {
            System.out.println("Must be logged in to visualize your subscriptions.");
            return;
        }

        System.out.println("\n--- Your subscriptions ---");
        BeanTournList tL = new BeanTournList();
        ApplicationControllerTournaments controller;
        try {
            controller = new ApplicationControllerTournaments(tL, "sub", currentUser.getUsername());
        } catch (Exception e) {
            System.out.println("Error while loading subscriptions: " + e.getMessage());
            return;
        }

        if (controller.hasTournaments()) {
            MainPlayerCLI.tournSelectionCLI(tL, reader);
        } else {
            System.out.println("No available subscriptions.");
            System.out.println("Press enter to go back to main menu.");
            String _ = reader.readLine();
        }
    }
}