package util;

// Create a new abstract class or an interface for common methods if many controllers share them.
// For simplicity, I'm showing a static utility method here.

import bean.BeanTournCreation;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import singleton.SessionManager;
import users.User;

import java.time.LocalDate;

public class TournValidation {

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
        } catch (NumberFormatException e) {
            resultLabel.setText("Max number must be a number.");
            return null;
        }

        User currentUser = SessionManager.getCurrentUser();
        return new BeanTournCreation(name, maxPlayers, tournDate, currentUser.getUsername());
    }
}