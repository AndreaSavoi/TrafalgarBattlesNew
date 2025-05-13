package com.example.trafalgarbattlesnew.graphiccontrollers;

import applicationcontrollers.ApplicationControllerCreateTourn;
import bean.BeanTournCreation;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import singleton.SessionManager;
import users.User;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static util.FadeTransitionUtil.getFadeTransition;

public class CreateTournamentController extends AbstractTournController implements Initializable{

    @FXML  protected TextField tournamentName;
    @FXML  protected ImageView saveok;
    @FXML  protected Label result;

    @FXML
    @Override
    public void goHome(MouseEvent event) { visualizer.sceneVisualizer("MainViewOrganizer.fxml", event); }

    @FXML
    public void goLog(MouseEvent event){ visualizer.sceneVisualizer("LogRegForm.fxml", event); }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCommonTourn();
    }

    @Override
    protected JFXButton[] getHoverButtons() {
        return new JFXButton[]{};
    }


    @FXML
    public void createTournament(MouseEvent ignoredEvent) {
        String name = tournamentName.getText();
        String maxText = max.getText();
        LocalDate tournDate = date.getValue();

        if (name == null || name.isBlank() || maxText == null || maxText.isBlank() || tournDate == null) {
            result.setText("Please fill all the fields.");
            return;
        }

        int maxPlayers;
        try {
            maxPlayers = Integer.parseInt(maxText);
            if (maxPlayers <= 0) {
                result.setText("Maximum number must be greater than 0.");
                return;
            }
        } catch (NumberFormatException _) {
            result.setText("Max number must be a number.");
            return;
        }

        User currentUser = SessionManager.getCurrentUser();
        String organizer = currentUser.getUsername();

        BeanTournCreation bean = new BeanTournCreation(name, maxPlayers, tournDate, organizer);
        try {
            new ApplicationControllerCreateTourn(bean);
        } catch (Exception _) {
            result.setText("Error while creating the tournament.");
            return;
        }

        result.setText("");

        tournamentName.setDisable(true);
        max.setDisable(true);
        date.setDisable(true);

        saveok.setOpacity(0.0);
        saveok.setVisible(true);

        FadeTransition fadeIn = getFadeTransition(saveok);

        fadeIn.play();
    }
}
