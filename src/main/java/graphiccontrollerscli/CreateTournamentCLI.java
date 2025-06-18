package graphiccontrollerscli;

import applicationcontrollers.ApplicationControllerCreateTourn;
import bean.BeanTournCreation;
import singleton.SessionManager;
import users.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CreateTournamentCLI {
    private final BufferedReader reader;

    public CreateTournamentCLI(BufferedReader reader) {
        this.reader = reader;
    }

    public void createTournament() throws IOException, SQLException {
        User currentUser = SessionManager.getCurrentUser();
        if (!(currentUser instanceof users.Organizer)) {
            System.out.println("You must be a logged organizer to create a tournament.");
            return;
        }

        System.out.println("\n--- Creating a tournament ---");
        System.out.print("Tournament Name: ");
        String name = reader.readLine();
        System.out.print("Maximum Participants: ");
        String maxText = reader.readLine();
        System.out.print("Tournament Date (YYYY-MM-DD): ");
        String dateText = reader.readLine();

        LocalDate tournDate;
        try {
            tournDate = LocalDate.parse(dateText);
        } catch (DateTimeParseException e) {
            System.out.println("Not valid date format. Please use YYYY-MM-DD.");
            return;
        }

        String validationError = null;
        int maxPlayers = 0;

        if (name == null || name.isBlank() || maxText == null || maxText.isBlank() || tournDate == null) {
            validationError = "Please, fill every field.";
        } else {
            try {
                maxPlayers = Integer.parseInt(maxText);
                if (maxPlayers <= 0) {
                    validationError = "Maximum number of players must be greater than 0.";
                }
            } catch (NumberFormatException _) {
                validationError = "Maximum number must be a number.";
            }
        }

        if (validationError != null) {
            System.out.println(validationError);
            return;
        }
        BeanTournCreation bean = new BeanTournCreation(name, maxPlayers, tournDate, currentUser.getUsername());

        try {
            new ApplicationControllerCreateTourn(bean);
            System.out.println("Tournament created.!");
        } catch (Exception e) {
            System.out.println("Error during tournament creation: " + e.getMessage());
        }
    }
}