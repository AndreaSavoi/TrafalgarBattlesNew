package com.example.trafalgarbattlesnew.graphiccontrollers;

import com.jfoenix.controls.JFXButton;
import users.User;
import applicationcontrollers.ApplicationControllerTournaments;
import singleton.SessionManager;
import bean.BeanTournList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static util.HoverEffectUtil.applyHoverEffect;

public class SubsController implements Initializable {
    @FXML
    protected Label logReg;
    @FXML
    protected Label noTournaments;
    @FXML
    protected JFXButton home;
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
    public void gohome(MouseEvent event) {visualizer.sceneVisualizer("MainView.fxml", event);}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyHoverEffect(profile, home, logReg);

        User currentUser = SessionManager.getCurrentUser();

        if(currentUser != null && currentUser.getUsername() != null) {
            logReg.setText(currentUser.getUsername());

            try {
                BeanTournList tL = new BeanTournList();
                ApplicationControllerTournaments controller = new ApplicationControllerTournaments(tL, "sub", currentUser.getUsername());
                if(controller.hasTournaments()) {
                    MainGraphicController.displayTournaments(tL, visualizer, tournaments);
                } else {
                       noTournaments.setVisible(true);
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}