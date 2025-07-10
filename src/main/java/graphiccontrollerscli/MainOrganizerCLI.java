package graphiccontrollerscli;

import applicationcontrollers.ApplicationControllerTournaments;
import bean.BeanTournList;
import singleton.SessionManager;
import users.Organizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

public class MainOrganizerCLI {
    private final BufferedReader reader;

    public MainOrganizerCLI(BufferedReader reader) {
        this.reader = reader;
    }

    public void displayOrganizerTournaments() throws IOException, SQLException {
        Organizer currentUser = (Organizer) SessionManager.getCurrentUser();
        if (currentUser == null) {
            System.out.println("You must be logged in to visualize your tournaments.");
            return;
        }

        System.out.println("\n--- Your Tournaments (Organizer) ---");
        BeanTournList tL = new BeanTournList();
        ApplicationControllerTournaments controller;
        try {
            controller = new ApplicationControllerTournaments(tL, "org", currentUser.getUsername());
        } catch (Exception e) {
            System.out.println("Error while loading the tournaments: " + e.getMessage());
            return;
        }

        if (controller.hasTournaments()) {
            for (int i = 0; i < tL.sno.size(); i++) {
                System.out.printf("%d. %s - participants: %s/%s - Date: %s (SNO: %s)%n",
                        (i + 1), tL.getName(i), tL.getNS(i), tL.getNP(i), tL.getDate(i), tL.getSNO(i));
            }
            System.out.print("Insert the number of the tournament to edit (0 to go back): ");
            String choice = reader.readLine();
            try {
                int selectedIndex = Integer.parseInt(choice);
                if (selectedIndex > 0 && selectedIndex <= tL.sno.size()) {
                    int selectedSno = Integer.parseInt(tL.getSNO(selectedIndex - 1));
                    new TournPageOrgCLI(reader, selectedSno).manageTournament();
                } else if (selectedIndex == 0) {
                    // Go back
                } else {
                    System.out.println("Not a valid option.");
                }
            } catch (NumberFormatException _) {
                System.out.println("Not valid input. Please insert a number.");
            }
        } else {
            System.out.println("No tournaments found.");
        }
    }
}