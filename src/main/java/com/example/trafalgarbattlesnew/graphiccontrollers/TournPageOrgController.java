package com.example.trafalgarbattlesnew.graphiccontrollers;

import applicationcontrollers.ApplicationControllerTournInfo;
import bean.BeanCurrTourn;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.w3c.dom.Text;
import singleton.SessionManager;
import users.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static util.HoverEffectUtil.applyHoverEffect;

public class TournPageOrgController implements Initializable{

    @FXML protected Label logReg;
    @FXML protected Label tName;
    @FXML protected JFXButton home;
    @FXML protected JFXButton profile;
    @FXML protected JFXButton create;
    @FXML protected TextField max;
    @FXML protected TextField curr;
    @FXML protected DatePicker date;

    private final VisualizeScene visualizer = VisualizeScene.getVisualizer(null);

    @FXML
    public void goHome(MouseEvent event) { visualizer.sceneVisualizer("MainViewOrganizer.fxml", event); }

    @FXML
    public void showprofile(MouseEvent event) { visualizer.sceneVisualizer("Profile.fxml", event);}

    @FXML
    public void goLog(MouseEvent event){  visualizer.sceneVisualizer("LogRegForm.fxml", event);   }

    @FXML
    public void edit(MouseEvent event) {  date.setDisable(false); max.setDisable(false);  }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyHoverEffect(create, home, profile, logReg);

        User currentUser = SessionManager.getCurrentUser();
        if (currentUser != null && currentUser.getUsername() != null) {
            logReg.setText(currentUser.getUsername());
            logReg.setOnMouseClicked(this::goHome);
        }

        BeanCurrTourn bCT = BeanCurrTourn.getInstance();
        try {
            new ApplicationControllerTournInfo(bCT);
        } catch (SQLException | IOException _) {
            throw new IllegalArgumentException("Something went wrong");
        }
        tName.setText(bCT.gettName());
        date.setValue(LocalDate.parse(bCT.getDates()));
        curr.setText(bCT.getnSubscribed());
        max.setText(bCT.getnPartecipants());
        date.setDisable(true);
        curr.setDisable(true);
        max.setDisable(true);
    }
}
