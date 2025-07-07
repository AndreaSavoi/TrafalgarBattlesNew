package graphiccontrollerscli;

import applicationcontrollers.ApplicationControllerDeleteTourn;
import applicationcontrollers.ApplicationControllerModifyTourn;
import applicationcontrollers.ApplicationControllerTournInfo;
import bean.BeanCurrTourn;
import bean.BeanTournCreation;
import singleton.SessionManager;
import users.User;
import util.TournValidation;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TournPageOrgCLI {
    private final BufferedReader reader;
    private final int tournamentSno;

    public TournPageOrgCLI(BufferedReader reader, int tournamentSno) {
        this.reader = reader;
        this.tournamentSno = tournamentSno;
    }

    public void manageTournament() throws IOException, SQLException {
        BeanCurrTourn beanCurrTourn = BeanCurrTourn.getInstance();
        beanCurrTourn.setSno(tournamentSno);

        try {
            new ApplicationControllerTournInfo(beanCurrTourn);
        } catch (Exception e) {
            System.out.println("Error while loading tournament info: " + e.getMessage());
            return;
        }

        System.out.println("\n--- Tournament Edit: " + beanCurrTourn.gettName() + " ---");
        System.out.println("Name: " + beanCurrTourn.gettName());
        System.out.println("Date: " + beanCurrTourn.getDates());
        System.out.println("participants: " + beanCurrTourn.getnSubscribed() + "/" + beanCurrTourn.getnparticipants());

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Modify Tournament");
            System.out.println("2. Delete Tournament");
            System.out.println("0. Go back to main menu");
            System.out.print("Choose an option: ");
            String choice = reader.readLine();

            switch (choice) {
                case "1":
                    modifyTournament(beanCurrTourn);
                    try {
                        new ApplicationControllerTournInfo(beanCurrTourn);
                    } catch (Exception e) {
                        System.out.println("Error while reloading informations: " + e.getMessage());
                    }
                    System.out.println("Changes saved.");
                    System.out.println("Name: " + beanCurrTourn.gettName());
                    System.out.println("Date: " + beanCurrTourn.getDates());
                    System.out.println("participants: " + beanCurrTourn.getnSubscribed() + "/" + beanCurrTourn.getnparticipants());
                    break;
                case "2":
                    deleteTournament(beanCurrTourn.gettName());
                    return;
                case "0":
                    return;
                default:
                    System.out.println("Not a valid option.");
            }
        }
    }

    private void modifyTournament(BeanCurrTourn currentTournBean) throws IOException {
        System.out.println("\n--- Modify Tournament ---");
        System.out.println("Keep it blank if you don't want to change it.");

        System.out.print("New tournament date (YYYY-MM-DD, actual: " + currentTournBean.getDates() + "): ");
        String newDateText = reader.readLine();
        LocalDate newDate = null;
        if (!newDateText.isBlank()) {
            try {
                newDate = LocalDate.parse(newDateText);
            } catch (DateTimeParseException e) {
                System.out.println("Date format not valid. Date won't be modified.");
            }
        } else {
            newDate = LocalDate.parse(currentTournBean.getDates());
        }


        System.out.print("New Max participants number (actual: " + currentTournBean.getnparticipants() + "): ");
        String newMaxText = reader.readLine();
        int newMaxPlayers = 0;
        String validationError = null;

        if (newMaxText.isBlank()) {
            newMaxPlayers = Integer.parseInt(currentTournBean.getnparticipants());
        } else {
            try {
                newMaxPlayers = Integer.parseInt(newMaxText);
                if (newMaxPlayers <= 0) {
                    validationError = "The Maximum number must be greater than 0.";
                } else if (newMaxPlayers < Integer.parseInt(currentTournBean.getnSubscribed())) {
                    validationError = "Maximum number must be equal or greater than the current subscribed players. (" + currentTournBean.getnSubscribed() + ").";
                }
            } catch (NumberFormatException e) {
                validationError = "Maximum number must be a number.";
            }
        }

        if (validationError != null) {
            System.out.println(validationError);
            return;
        }

        BeanTournCreation bean = getBeanTournCreation(currentTournBean, newDate, newMaxPlayers);

        try {
            new ApplicationControllerModifyTourn(bean);
            System.out.println("Tournament successfully modified.");
        } catch (Exception e) {
            System.out.println("Error while updating the tournament: " + e.getMessage());
        }
    }

    private static BeanTournCreation getBeanTournCreation(BeanCurrTourn currentTournBean, LocalDate newDate, int newMaxPlayers) {
        LocalDate finalDate = (newDate != null) ? newDate : LocalDate.parse(currentTournBean.getDates());
        int finalMaxPlayers = (newMaxPlayers != 0) ? newMaxPlayers : Integer.parseInt(currentTournBean.getnparticipants());

        User currentUser = SessionManager.getCurrentUser();
        String organizer = currentUser.getUsername();
        return new BeanTournCreation(currentTournBean.gettName(), finalMaxPlayers, finalDate, organizer);
    }

    private void deleteTournament(String tournamentName) throws IOException, SQLException {
        System.out.print("Are you sure you want to delete the tournament '" + tournamentName + "'? (y/n): ");
        String confirmation = reader.readLine();
        if (confirmation.equalsIgnoreCase("s")) {
            try {
                ApplicationControllerDeleteTourn controller = new ApplicationControllerDeleteTourn();
                controller.deleteTournament(tournamentName);
                System.out.println("Tournament successfully deleted.");
            } catch (Exception e) {
                System.out.println("Error while deleting the tournament: " + e.getMessage());
            }
        } else {
            System.out.println("Tournament was not deleted.");
        }
    }
}