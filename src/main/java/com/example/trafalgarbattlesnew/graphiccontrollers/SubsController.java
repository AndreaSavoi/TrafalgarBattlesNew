package com.example.trafalgarbattlesnew.graphiccontrollers;

import applicationcontrollers.ApplicationControllerTournaments;
import applicationcontrollers.CurrentUser;
import bean.BeanCurrTourn;
import bean.BeanTournList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SubsController implements Initializable {
    @FXML
    protected Label logReg;
    @FXML
    protected Label teams;
    @FXML
    protected Label profile;
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
        if(CurrentUser.getUser() != null) {
            logReg.setText(CurrentUser.getUser());
        }
        try {
            BeanTournList tL = new BeanTournList();
            new ApplicationControllerTournaments(tL, "sub", CurrentUser.getUser());
            MainGraphicController.displayTournaments(tL, visualizer, tournaments);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}