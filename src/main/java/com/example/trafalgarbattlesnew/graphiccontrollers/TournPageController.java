package com.example.trafalgarbattlesnew.graphiccontrollers;

import Users.User;
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
    protected Label homeB;
    @FXML
    protected Button register;
    private final VisualizeScene visualizer = VisualizeScene.getVisualizer(null);
    public void goLog(MouseEvent event){
        visualizer.sceneVisualizer("LogRegForm.fxml", event);
    }

    public void goHome(MouseEvent event) throws SQLException {
        visualizer.sceneVisualizer("MainView.fxml", event);
    }

    public void sub(MouseEvent event) throws SQLException, IOException {
        User currentUser = SessionManager.getCurrentUser();
        if(currentUser.getUsername() != null) {
            BeanCurrTourn bCT = BeanCurrTourn.getInstance();
            new ApplicationControllerTournInfo(currentUser.getUsername(), bCT.gettName());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User currentUser = SessionManager.getCurrentUser();
        if(currentUser.getUsername() != null) {
            logReg.setText(currentUser.getUsername());
            logReg.setOnMouseClicked(event -> {
                try {
                    goHome(event);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            register.setTextFill(Color.GREEN);
        } else {
            register.setTextFill(Color.RED);
        }
        BeanCurrTourn bCT = BeanCurrTourn.getInstance();
        try {
            new ApplicationControllerTournInfo(bCT);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        tName.setText(bCT.gettName());
        date.setText(bCT.getDates());
        nSub.setText(bCT.getnSubscribed());
        nPart.setText(bCT.getnPartecipants());
    }
}
