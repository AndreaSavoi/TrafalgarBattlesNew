package com.example.trafalgarbattlesnew.graphiccontrollers;

import applicationcontrollers.ApplicationControllerCreateTourn;
import bean.BeanTournCreation;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
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
import static util.HoverEffectUtil.applyHoverEffect;

public class CreateTournamentController implements Initializable{
    @FXML  protected Label logReg;
    @FXML  protected TextField tournamentName;
    @FXML  protected JFXButton home;
    @FXML  protected JFXButton profile;
    @FXML  protected TextField max;
    @FXML  protected DatePicker date;
    @FXML  protected ImageView saveok;
    @FXML  protected Label result;

    private final VisualizeScene visualizer = VisualizeScene.getVisualizer(null);

    @FXML
    public void goHome(MouseEvent event) { visualizer.sceneVisualizer("MainViewOrganizer.fxml", event); }

    @FXML
    public void showprofile(MouseEvent event) { visualizer.sceneVisualizer("Profile.fxml", event); }

    @FXML
    public void goLog(MouseEvent event){ visualizer.sceneVisualizer("LogRegForm.fxml", event); }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyHoverEffect(home, profile, logReg);

        User currentUser = SessionManager.getCurrentUser();
        if (currentUser != null && currentUser.getUsername() != null) {
            logReg.setText(currentUser.getUsername());
            logReg.setOnMouseClicked(this::goHome);
        }
    }


    @FXML
    public void createTournament(MouseEvent event) {
        String name = tournamentName.getText();
        String maxText = max.getText();
        LocalDate tournDate = this.date.getValue();

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
        } catch (NumberFormatException e) {
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
