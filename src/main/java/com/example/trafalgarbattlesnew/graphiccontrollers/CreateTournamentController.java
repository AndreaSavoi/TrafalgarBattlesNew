package com.example.trafalgarbattlesnew.graphiccontrollers;

import applicationcontrollers.ApplicationControllerCreateTourn;
import applicationcontrollers.ApplicationControllerModifyTourn;
import bean.BeanTournCreation;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import util.TournValidation;

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
        BeanTournCreation bean = TournValidation.validateAndBuildBean(tournamentName.getText(), max, date, result, null);
        if (bean == null) return;

        try {
            new ApplicationControllerCreateTourn(bean);
        } catch (Exception e) {
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
