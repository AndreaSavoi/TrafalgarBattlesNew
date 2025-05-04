package com.example.trafalgarbattlesnew.graphiccontrollers;

import com.jfoenix.controls.JFXButton;
import singleton.SessionManager;
import users.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static util.HoverEffectUtil.applyHoverEffect;
import static util.PrivacyUtil.obfuscateEmail;

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
    @FXML
    protected Label logReg;
    @FXML
    protected Label email;
    @FXML
    protected JFXButton home;
    @FXML
    protected JFXButton subs;



    VisualizeScene visualizer = VisualizeScene.getVisualizer(null);

    @FXML
    public void goLog(MouseEvent event) { visualizer.sceneVisualizer("LogRegForm.fxml", event); }

    @FXML
    public void goHome(MouseEvent event) { visualizer.sceneVisualizer("MainView.fxml", event); }

    @FXML
    public void showsubs(MouseEvent event) { visualizer.sceneVisualizer("Subs.fxml", event);}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyHoverEffect(home, subs, logReg);
        User currentUser = SessionManager.getCurrentUser();

        if(currentUser!= null && currentUser.getUsername() != null) {
            usr.setText(currentUser.getUsername());
            logReg.setText(currentUser.getUsername());
            email.setText(obfuscateEmail(currentUser.getEmail()));
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
