package com.example.trafalgarbattlesnew.graphiccontrollers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import singleton.SessionManager;
import users.User;

import java.sql.SQLException;

import static util.HoverEffectUtil.applyHoverEffect;

public abstract class AbstractTournController implements Initializable {

    @FXML  protected Label logReg;
    @FXML  protected JFXButton home;
    @FXML  protected TextField max;
    @FXML  protected DatePicker date;

    protected final VisualizeScene visualizer = VisualizeScene.getVisualizer(null);

    protected abstract JFXButton[] getHoverButtons();

    protected void initializeCommonTourn() {

        applyHoverEffect(logReg, home);
        applyHoverEffect(getHoverButtons());

        User currentUser = SessionManager.getCurrentUser();
        if (currentUser != null && currentUser.getUsername() != null) {
            logReg.setText(currentUser.getUsername());
            logReg.setOnMouseClicked(this::goHome);
        }    }

    protected abstract void goHome(MouseEvent event);
}
