package graphiccontrollerscli;

import applicationcontrollers.ApplicationControllerTournaments;
import bean.BeanTournList;
import singleton.SessionManager;
import users.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

public class MainPlayerCLI {
    private final BufferedReader reader;

    public MainPlayerCLI(BufferedReader reader) {
        this.reader = reader;
    }

    public void displayAvailableTournaments() throws IOException, SQLException {
        String mode = "all";

        System.out.println("\n--- Available Tournaments ---");
        BeanTournList tL = new BeanTournList();
        ApplicationControllerTournaments controller;
        try {
            controller = new ApplicationControllerTournaments(tL, mode, null);
        } catch (Exception e) {
            System.out.println("Error while loading tournaments: " + e.getMessage());
            return;
        }

        if (controller.hasTournaments()) {
            TournSelectionCLI(tL, reader);
        } else {
            System.out.println("No available Tournaments.");
        }
    }

    public static void TournSelectionCLI(BeanTournList tL, BufferedReader reader) throws IOException, SQLException {
        for (int i = 0; i < tL.sno.size(); i++) {
            System.out.printf("%d. %s - participants: %s/%s - Date: %s (SNO: %s)\n",
                    (i + 1), tL.getName(i), tL.getNS(i), tL.getNP(i), tL.getDate(i), tL.getSNO(i));
        }
        System.out.print("Insert Tournament number for more info (0 to go back): ");
        String choice = reader.readLine();
        try {
            int selectedIndex = Integer.parseInt(choice);
            if (selectedIndex > 0 && selectedIndex <= tL.sno.size()) {
                int selectedSno = Integer.parseInt(tL.getSNO(selectedIndex - 1));
                new TournPagePlayerCLI(reader, selectedSno).showTournamentInfo();
            } else if (selectedIndex == 0) {
                // Go back
            } else {
                System.out.println("Not a valid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Not a valid option. Please insert a valid number.");
        }
    }
}
