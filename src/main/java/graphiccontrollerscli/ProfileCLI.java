package graphiccontrollerscli;

import applicationcontrollers.ApplicationControllerProfile;
import singleton.SessionManager;
import users.User;
import util.PrivacyUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class ProfileCLI {
    private final BufferedReader reader;

    public ProfileCLI(BufferedReader reader) {
        this.reader = reader;
    }

    public void showProfile() throws IOException, SQLException {
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser == null) {
            System.out.println("You must be logged in to do this.");
            return;
        }

        System.out.println("\n--- My profile ---");
        System.out.println("Username: " + currentUser.getUsername());
        System.out.println("Email: " + PrivacyUtil.obfuscateEmail(currentUser.getEmail()));

        ApplicationControllerProfile appController = new ApplicationControllerProfile();
        Map<String, String> userData;
        try {
            userData = appController.getUserProfile(currentUser.getUsername());
        } catch (Exception e) {
            System.out.println("Error while loading profile datas: " + e.getMessage());
            return;
        }

        String birth = userData.getOrDefault("birth", "Not specified");
        String game = userData.getOrDefault("game", "Not specified");
        String sex = userData.getOrDefault("sex", "Not specified");
        String fullname = userData.getOrDefault("fullname", "Not specified");

        System.out.println("Birth Date: " + birth);
        System.out.println("Favourite Game: " + game);
        System.out.println("Sex: " + sex);
        System.out.println("Full Name: " + fullname);

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Modify Profile");
            System.out.println("0. Go back to main menu");
            System.out.print("Choose an option: ");
            String choice = reader.readLine();

            switch (choice) {
                case "1":
                    editProfile(appController, currentUser.getUsername(), userData);
                    try {
                        userData = appController.getUserProfile(currentUser.getUsername());
                    } catch (Exception e) {
                        System.out.println("Error while loading profile datas: " + e.getMessage());
                    }
                    System.out.println("\n--- Updatated Profile ---");
                    System.out.println("Birth Date: " + userData.getOrDefault("birth", "Not specified"));
                    System.out.println("Favourite Game: " + userData.getOrDefault("game", "Not specified"));
                    System.out.println("Sex: " + userData.getOrDefault("sex", "Not specified"));
                    System.out.println("Full name: " + userData.getOrDefault("fullname", "Not specified"));
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Not a valid option!.");
            }
        }
    }

    private void editProfile(ApplicationControllerProfile appController, String username, Map<String, String> currentData) throws IOException, SQLException {
        System.out.println("\n--- Modify Profile Fields ---");
        System.out.println("Keep it blank if you don't want to change it.");

        System.out.print("New Birth Date (actual: " + currentData.getOrDefault("birth", "") + "): ");
        String newBirth = reader.readLine();
        if (newBirth.isBlank()) newBirth = currentData.get("birth");

        System.out.print("New Favourite Game (actual: " + currentData.getOrDefault("game", "") + "): ");
        String newGame = reader.readLine();
        if (newGame.isBlank()) newGame = currentData.get("game");

        System.out.print("New Sex (actual: " + currentData.getOrDefault("sex", "") + "): ");
        String newSex = reader.readLine();
        if (newSex.isBlank()) newSex = currentData.get("sex");

        System.out.print("New Full name (actual: " + currentData.getOrDefault("fullname", "") + "): ");
        String newFullname = reader.readLine();
        if (newFullname.isBlank()) newFullname = currentData.get("fullname");

        try {
            appController.updateUserProfile(newBirth, newGame, newSex, newFullname, username);
            System.out.println("Profile updated!");
        } catch (Exception e) {
            System.out.println("Error during profile update: " + e.getMessage());
        }
    }
}
