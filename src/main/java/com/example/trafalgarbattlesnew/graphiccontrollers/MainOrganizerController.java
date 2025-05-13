package com.example.trafalgarbattlesnew.graphiccontrollers;

import applicationcontrollers.ApplicationControllerTournaments;
import bean.BeanTournList;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import singleton.SessionManager;
import users.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static util.HoverEffectUtil.applyHoverEffect;

public class MainOrganizerController implements Initializable {
    @FXML
    protected Label logReg;
    @FXML
    protected Label noTournaments;
    @FXML
    protected JFXButton create;
    @FXML
    protected JFXButton profile;
    private final VisualizeScene visualizer = VisualizeScene.getVisualizer(null);
    @FXML
    protected VBox tournaments;
    @FXML
    public void show(MouseEvent event) { visualizer.sceneVisualizer("LogRegForm.fxml", event); }

    @FXML
    public void showprofile(MouseEvent event) { visualizer.sceneVisualizer("Profile.fxml", event);}

    @FXML
    public void showcreate(MouseEvent event) { visualizer.sceneVisualizer("CreateTournament.fxml", event);}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyHoverEffect(profile, create, logReg);

        User currentUser = SessionManager.getCurrentUser();

        if(currentUser != null && currentUser.getUsername() != null) {
            logReg.setText(currentUser.getUsername());

            try {
                BeanTournList tL = new BeanTournList();
                ApplicationControllerTournaments controller = new ApplicationControllerTournaments(tL, "org", currentUser.getUsername());
                if(controller.hasTournaments()) {
                    MainGraphicController.displayTournaments(tL, visualizer, tournaments, "organizer");
                } else {
                       noTournaments.setVisible(true);
                }
            } catch (SQLException | IOException _) {
                throw new IllegalArgumentException("Something went wrong");
            }
        }
    }
}