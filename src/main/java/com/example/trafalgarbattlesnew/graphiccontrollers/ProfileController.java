package com.example.trafalgarbattlesnew.graphiccontrollers;

import applicationcontrollers.CurrentUser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @FXML
    protected Label usr;
    @FXML
    protected TextField birth;
    @FXML
    protected TextField game;
    @FXML
    protected TextField manga;
    @FXML
    protected TextField sex;

    VisualizeScene visualizer = VisualizeScene.getVisualizer(null);

    @FXML
    public void goLog(MouseEvent event) { visualizer.sceneVisualizer("LogRegForm.fxml", event); }

    public void goHome(MouseEvent event) { visualizer.sceneVisualizer("MainView.fxml", event); }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(CurrentUser.getUser() != null) {
            usr.setText(CurrentUser.getUser());
        } else {
            usr.setText("Not currently logged in.");
            usr.setWrapText(true);
            sex.setDisable(true);
            birth.setDisable(true);
            manga.setDisable(true);
            game.setDisable(true);
        }
    }
}
