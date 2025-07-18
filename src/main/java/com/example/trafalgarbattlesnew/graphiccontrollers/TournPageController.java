package com.example.trafalgarbattlesnew.graphiccontrollers;

import com.jfoenix.controls.JFXButton;
import exception.AlreadySubscribedException;
import exception.MaxParticipantsReachedException;
import exception.UserNotSubscribedException;
import users.User;
import applicationcontrollers.ApplicationControllerTournInfo;
import singleton.SessionManager;
import bean.BeanCurrTourn;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static util.HoverEffectUtil.applyHoverEffect;

public class TournPageController implements Initializable {
    @FXML
    protected Label logReg;
    @FXML
    protected Label tName;
    @FXML
    protected Label nSub;
    @FXML
    protected Label date;
    @FXML
    protected Label nPart;
    @FXML
    protected JFXButton home;
    @FXML
    protected JFXButton profile;
    @FXML
    protected JFXButton subs;
    @FXML
    protected Button register;
    @FXML
    protected Label regRes;
    @FXML
    protected Button unregister;
    private final VisualizeScene visualizer = VisualizeScene.getVisualizer(null);
    public void goLog(MouseEvent event){
        visualizer.sceneVisualizer("LogRegForm.fxml", event);
    }

    public void goHome(MouseEvent event) throws SQLException {
        visualizer.sceneVisualizer("MainView.fxml", event);
    }

    @FXML
    public void showprofile(MouseEvent event) { visualizer.sceneVisualizer("Profile.fxml", event);}

    @FXML
    public void showsubs(MouseEvent event) { visualizer.sceneVisualizer("Subs.fxml", event);}

    public void sub(MouseEvent ignoredEvent) {
        try {
            User currentUser = SessionManager.getCurrentUser();
            if (currentUser!= null && currentUser.getUsername() != null) {
                BeanCurrTourn bCT = BeanCurrTourn.getInstance();
                new ApplicationControllerTournInfo(currentUser.getUsername(), bCT.gettName(), "sub");
            }
            regRes.setText("Registered successfully!");
            regRes.setTextFill(Color.GREEN);
        } catch (AlreadySubscribedException | MaxParticipantsReachedException e) {
            regRes.setText(e.getMessage());
            regRes.setTextFill(Color.RED);
        } catch (Exception _) {
            regRes.setText("Something went wrong.");
            regRes.setTextFill(Color.RED);
        }
    }

    public void unSub(MouseEvent ignoredEvent) {
        try {
            User currentUser = SessionManager.getCurrentUser();
            if (currentUser.getUsername() != null) {
                BeanCurrTourn bCT = BeanCurrTourn.getInstance();
                new ApplicationControllerTournInfo(currentUser.getUsername(), bCT.gettName(), "unsub");
            }
            regRes.setText("Unregistered successfully!");
            regRes.setTextFill(Color.GREEN);
        } catch (UserNotSubscribedException e) {
            regRes.setText(e.getMessage());
            regRes.setTextFill(Color.RED);
        } catch (Exception _) {
            regRes.setText("Something went wrong.");
            regRes.setTextFill(Color.RED);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyHoverEffect(subs, profile, logReg, home);

        User currentUser = SessionManager.getCurrentUser();
        if(currentUser!= null && currentUser.getUsername() != null) {
            logReg.setText(currentUser.getUsername());
        logReg.setOnMouseClicked(event -> {
            try {
                goHome(event);
            } catch (SQLException _) {
                throw new RuntimeException();
            }
        });
    } else {
        register.setDisable(true);
        unregister.setDisable(true);
        profile.setDisable(true);
        subs.setDisable(true);
    }
    BeanCurrTourn bCT = BeanCurrTourn.getInstance();
    try {
        new ApplicationControllerTournInfo(bCT);
    } catch (SQLException | IOException _) {
        throw new IllegalArgumentException("Something went wrong");
    }
    tName.setText(bCT.gettName());
    date.setText(bCT.getDates());
    nSub.setText(bCT.getnSubscribed());
    nPart.setText(bCT.getnparticipants());
    }
}