package com.example.trafalgarbattlesnew.graphiccontrollers;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static util.HoverEffectUtil.applyHoverEffect;

public class MainGraphicController extends AbstractMainController implements Initializable {

    private final VisualizeScene visualizer = VisualizeScene.getVisualizer(null);

    @FXML protected JFXButton subs;

    @FXML
    public void show(MouseEvent event) { visualizer.sceneVisualizer("LogRegForm.fxml", event); }

    @FXML
    public void showprofile(MouseEvent event) { visualizer.sceneVisualizer("Profile.fxml", event);}

    @FXML
    public void showsubs(MouseEvent event) { visualizer.sceneVisualizer("Subs.fxml", event);}

    @Override
    protected JFXButton[] getHoverButtons() {
        return new JFXButton[]{profile, subs};
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCommon();
    }

    static void displayTournaments(BeanTournList tL, VisualizeScene visualizer, VBox tournaments, String type) {
        int count = tL.sno.size();

        for (int i = 0; i < count; i++) {
            Pane pane = new Pane();
            pane.setPrefSize(1083, 150);
            String paneId = "Info" + tL.getSNO(i);
            pane.setId(paneId);
            pane.setOnMouseClicked(event -> {
                int infoN = Integer.parseInt(((Pane) event.getSource()).getId().replace("Info", ""));
                BeanCurrTourn tournament = BeanCurrTourn.getInstance();
                tournament.setSno(infoN);
                if(type.equals("player")) {
                    visualizer.sceneVisualizer("TournamentInfo.fxml", event);
                } else if(type.equals("organizer")) {
                    visualizer.sceneVisualizer("TournamentInfoOrg.fxml", event);
                }
            });
            GridPane grid = new GridPane();
            grid.setPrefSize(1083, 125);
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(0);
            grid.setVgap(0);
            grid.setPadding(new Insets(0));

            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPrefWidth(361);
            col1.setHalignment(HPos.CENTER);

            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPrefWidth(361);
            col2.setHalignment(HPos.CENTER);

            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPrefWidth(361);
            col3.setHalignment(HPos.CENTER);

            grid.getColumnConstraints().addAll(col1, col2, col3);

            Label nameLabel = new Label(tL.getName(i));
            Label participantsLabel = new Label(tL.getNS(i) + "/" + tL.getNP(i));
            Label dateLabel = new Label(tL.getDate(i));

            for (Label label : List.of(nameLabel, participantsLabel, dateLabel)) {
                label.setFont(new Font("Century Gothic", 20));
                label.setStyle("-fx-font-weight: bold;");
            }

            grid.add(nameLabel, 0, 0);
            grid.add(participantsLabel, 1, 0);
            grid.add(dateLabel, 2, 0);

            pane.setBorder(new Border(new BorderStroke(
                    Color.BLACK,
                    BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY,
                    BorderWidths.DEFAULT
            )));

            grid.setLayoutY((pane.getPrefHeight() - grid.getPrefHeight()) / 2);
            pane.getChildren().add(grid);

            tournaments.getChildren().add(pane);

        }
    }
}