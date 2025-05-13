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

public class MainOrganizerController extends AbstractMainController implements Initializable {
    @FXML protected JFXButton create;

    @FXML
    public void show(MouseEvent event) { visualizer.sceneVisualizer("LogRegForm.fxml", event); }

    @FXML
    public void showcreate(MouseEvent event) { visualizer.sceneVisualizer("CreateTournament.fxml", event);}

    @Override
    protected JFXButton[] getHoverButtons() {
        return new JFXButton[]{create};
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCommon();
    }
}