package util;

import bean.BeanTournCreation;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import singleton.SessionManager;
import users.User;

import java.time.LocalDate;

public class TournValidation {

    private TournValidation() {
        throw new IllegalStateException("Utility class");
    }

    public static BeanTournCreation validateAndBuildBean(String name, TextField maxField, DatePicker datePicker, Label resultLabel, String currentPlayersTextIfAny) {
        String maxText = maxField.getText();
        LocalDate tournDate = datePicker.getValue();

        if (name == null || name.isBlank() || maxText == null || maxText.isBlank() || tournDate == null) {
            resultLabel.setText("Please fill all the fields.");
            return null;
        }

        int maxPlayers;
        try {
            maxPlayers = Integer.parseInt(maxText);
            if (maxPlayers <= 0) {
                resultLabel.setText("Maximum number must be greater than 0.");
                return null;
            }

            if (currentPlayersTextIfAny != null) {
                int currentPlayers = Integer.parseInt(currentPlayersTextIfAny);
                if (maxPlayers < currentPlayers) {
                    resultLabel.setText("Maximum number must be greater than or equal to " + currentPlayersTextIfAny + ".");
                    return null;
                }
            }
        } catch (NumberFormatException _) {
            resultLabel.setText("Max number must be a number.");
            return null;
        }

        User currentUser = SessionManager.getCurrentUser();
        return new BeanTournCreation(name, maxPlayers, tournDate, currentUser.getUsername());
    }
}