package com.example.trafalgarbattlesnew.graphiccontrollers;

import applicationcontrollers.ApplicationControllerCreateTourn;
import applicationcontrollers.ApplicationControllerDeleteTourn;
import applicationcontrollers.ApplicationControllerModifyTourn;
import applicationcontrollers.ApplicationControllerTournInfo;
import bean.BeanCurrTourn;
import bean.BeanTournCreation;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import singleton.SessionManager;
import users.User;
import util.TournValidation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static util.FadeTransitionUtil.getFadeTransition;

public class TournPageOrgController extends AbstractTournController implements Initializable{

    @FXML protected Label tName;
    @FXML protected JFXButton create;
    @FXML protected TextField curr;
    @FXML protected Label save;
    @FXML protected Label result;
    @FXML protected ImageView check;
    @FXML protected Pane delCheck;

    @FXML
    public void goHome(MouseEvent event) { visualizer.sceneVisualizer("MainViewOrganizer.fxml", event); }

    @FXML
    public void goLog(MouseEvent event){  visualizer.sceneVisualizer("LogRegForm.fxml", event);   }

    @FXML
    public void edit(MouseEvent ignoredEvent) {  date.setDisable(false); max.setDisable(false); save.setDisable(false); }

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

    @FXML
    public void saveMod(MouseEvent event) {

        BeanTournCreation bean = TournValidation.validateAndBuildBean(tName.getText(), max, date, result, curr.getText());
        if (bean == null) return;

        try {
            new ApplicationControllerModifyTourn(bean);
        } catch (Exception e) {
            result.setText("Error while updating the tournament.");
            return;
        }

        result.setText("");

        tName.setDisable(true);
        max.setDisable(true);
        date.setDisable(true);
        save.setDisable(true);

        check.setOpacity(0.0);
        check.setVisible(true);

        FadeTransition fadeIn = getFadeTransition(check);

        fadeIn.play();


    }

    @FXML
    public void deleteTourn(MouseEvent event) {
        delCheck.setDisable(false);
        delCheck.setVisible(true);
        FadeTransition fadein = new FadeTransition(Duration.millis(200),delCheck);
        fadein.setFromValue(0.0);
        fadein.setToValue(1.0);
        fadein.play();

    }

    @FXML
    public void onYesClicked(MouseEvent event) {
        result.setText("Tournament successfully canceled.");
        try {
            ApplicationControllerDeleteTourn controller = new ApplicationControllerDeleteTourn();
            controller.deleteTournament(tName.getText());
            result.setText("Tournament deleted successfully.");

            tName.setDisable(true);
            max.setDisable(true);
            date.setDisable(true);
            save.setDisable(true);

        } catch (Exception e) {

            result.setText("Error while deleting the tournament.");
            return;
        }
        closePane();
    }

    @FXML
    public void onNoClicked(MouseEvent event) {
        result.setText("Tournament not canceled.");
        closePane();
    }

    private void closePane() {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), delCheck);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> delCheck.setVisible(false));
        fadeOut.play();

        delCheck.setDisable(true);
    }

}
