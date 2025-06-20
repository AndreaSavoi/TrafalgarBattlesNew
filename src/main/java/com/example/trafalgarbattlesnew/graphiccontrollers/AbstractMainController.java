package com.example.trafalgarbattlesnew.graphiccontrollers;

import applicationcontrollers.ApplicationControllerTournaments;
import bean.BeanTournList;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import singleton.SessionManager;
import users.Organizer;
import users.User;

import java.io.IOException;
import java.sql.SQLException;

import static util.HoverEffectUtil.applyHoverEffect;

public abstract class AbstractMainController {

    @FXML protected Label logReg;
    @FXML protected Label noTournaments;
    @FXML protected VBox tournaments;

    protected final VisualizeScene visualizer = VisualizeScene.getVisualizer(null);


    protected abstract JFXButton[] getHoverButtons();

    protected void initializeCommon() {
        applyHoverEffect(logReg);
        applyHoverEffect(getHoverButtons());

        User currentUser = SessionManager.getCurrentUser();
        boolean loggedIn = currentUser != null && currentUser.getUsername() != null;

        if (loggedIn) {
            logReg.setText(currentUser.getUsername());
            for (JFXButton b : getHoverButtons()) b.setDisable(false);
        } else {
            for (JFXButton b : getHoverButtons()) b.setDisable(true);
        }

        String mode;
        String username = null;

        if (currentUser instanceof Organizer) {
            mode = "org";
            username = currentUser.getUsername();
        } else {
            mode = "all";
        }

        try {
            BeanTournList tL = new BeanTournList();
            ApplicationControllerTournaments controller = new ApplicationControllerTournaments(tL, mode, username);
            if (controller.hasTournaments()) {
                MainGraphicController.displayTournaments(tL, visualizer, tournaments,
                        mode.equals("org") ? "organizer" : "player");
            } else {
                noTournaments.setVisible(true);
            }
        } catch (SQLException | IOException _) {
            throw new IllegalArgumentException("Error while loading tournaments");
        }
    }
}
