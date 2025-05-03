package com.example.trafalgarbattlesnew.graphiccontrollers;

import users.User;
import applicationcontrollers.ApplicationControllerTournaments;
import singleton.SessionManager;
import bean.BeanCurrTourn;
import bean.BeanTournList;
import com.jfoenix.controls.JFXButton;
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

public class MainGraphicController implements Initializable {
    @FXML
    protected Label logReg;
    private final VisualizeScene visualizer = VisualizeScene.getVisualizer(null);
    @FXML
    protected VBox tournaments;
    @FXML
    protected JFXButton subs;
    @FXML
    protected JFXButton profile;
    @FXML
    public void show(MouseEvent event) { visualizer.sceneVisualizer("LogRegForm.fxml", event); }

    @FXML
    public void showprofile(MouseEvent event) { visualizer.sceneVisualizer("Profile.fxml", event);}

    @FXML
    public void showsubs(MouseEvent event) { visualizer.sceneVisualizer("Subs.fxml", event);}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User currentUser = SessionManager.getCurrentUser();

        if(currentUser != null && currentUser.getUsername() != null) {
            logReg.setText(currentUser.getUsername());
            profile.setDisable(false);
            subs.setDisable(false);
        } else {
            profile.setDisable(true);
            subs.setDisable(true);
        }
        try {
            BeanTournList tL = new BeanTournList();
            new ApplicationControllerTournaments(tL, "all", null);
            displayTournaments(tL, visualizer, tournaments);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    static void displayTournaments(BeanTournList tL, VisualizeScene visualizer, VBox tournaments) {
        int count = tL.sno.size();

        for (int i = 0; i < count; i++) {
            Pane pane = new Pane();
            pane.setPrefSize(1083, 150);
            Label label = new Label();
            String paneId = "Info" + tL.getSNO(i);
            pane.setId(paneId);
            pane.setOnMouseClicked(event -> {
                int infoN = Integer.parseInt(((Pane) event.getSource()).getId().replace("Info", ""));
                BeanCurrTourn tournament = BeanCurrTourn.getInstance();
                tournament.setSno(infoN);
                visualizer.sceneVisualizer("TournamentInfo.fxml", event);
            });
            HBox hbox = new HBox();
            hbox.setPrefHeight(125);
            hbox.setPrefWidth(1083);
            hbox.setSpacing(50);
            String tabs = "\t\t\t\t";
            label.setText(tL.getName(i) + tabs + tL.getNS(i) + "/" + tL.getNP(i) + tabs + tL.getDate(i) + tabs);
            label.setFont(new Font("Century Gothic", 20));
            label.setStyle("-fx-font-weight: bold italic");
            label.setLayoutY(hbox.getHeight() / 2);
            hbox.getChildren().addAll(label);
            BorderStroke bS = new BorderStroke(
                    Color.BLACK,
                    BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY,
                    BorderWidths.DEFAULT
            );
            Border border = new Border(bS);
            pane.setBorder(border);
            pane.getChildren().addAll(hbox);
            hbox.setAlignment(Pos.CENTER);
            hbox.setLayoutY((pane.getPrefHeight() - hbox.getPrefHeight()) / 2);
            hbox.setLayoutX(10 + (pane.getPrefWidth() - hbox.getPrefWidth()) / 2);
            tournaments.getChildren().add(pane);
        }
    }
}