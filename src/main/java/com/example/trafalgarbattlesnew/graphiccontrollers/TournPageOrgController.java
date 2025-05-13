package com.example.trafalgarbattlesnew.graphiccontrollers;

import applicationcontrollers.ApplicationControllerTournInfo;
import bean.BeanCurrTourn;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TournPageOrgController extends AbstractTournController implements Initializable{

    @FXML protected Label tName;
    @FXML protected JFXButton create;
    @FXML protected TextField curr;

    @FXML
    public void goHome(MouseEvent event) { visualizer.sceneVisualizer("MainViewOrganizer.fxml", event); }

    @FXML
    public void goLog(MouseEvent event){  visualizer.sceneVisualizer("LogRegForm.fxml", event);   }

    @FXML
    public void edit(MouseEvent ignoredEvent) {  date.setDisable(false); max.setDisable(false);  }

    @Override
    protected JFXButton[] getHoverButtons() {
        return new JFXButton[]{create};
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCommonTourn();

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
