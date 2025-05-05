package com.example.trafalgarbattlesnew.graphiccontrollers;

import applicationcontrollers.ApplicationControllerProfile;
import com.jfoenix.controls.JFXButton;
import singleton.SessionManager;
import users.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
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
    protected TextField fullname;
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
            setStaticData(currentUser);
            try{
                loadProfileFields(currentUser.getUsername());
            } catch (SQLException | IOException _) {
                throw new IllegalArgumentException("Something went wrong");
            }
        } else {
            usr.setText("Not currently logged in.");
            usr.setWrapText(true);
            sex.setDisable(true);
            birth.setDisable(true);
            fullname.setDisable(true);
            game.setDisable(true);
        }
    }

    private void setStaticData(User user) {
        usr.setText(user.getUsername());
        logReg.setText(user.getUsername());
        email.setText(obfuscateEmail(user.getEmail()));
    }

    private void loadProfileFields(String username) throws SQLException, IOException {
        ApplicationControllerProfile appController = new ApplicationControllerProfile();
        Map<String, String> data = appController.getUserProfile(username);

        setOrEnable(birth, data.get("birth"));
        setOrEnable(sex, data.get("sex"));
        setOrEnable(fullname, data.get("manage"));
        setOrEnable(game, data.get("game"));
    }

    private void setOrEnable(TextField field, String value) {
        if (value != null && !value.isBlank()) {
            field.setText(value);
            field.setDisable(true);
        } else {
            field.setText("");
            field.setDisable(false);
        }
    }
}
