package com.example.trafalgarbattlesnew.graphiccontrollers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseEvent;
import singleton.SessionManager;
import users.User;

import java.sql.SQLException;

import static util.HoverEffectUtil.applyHoverEffect;

public abstract class AbstractTournController implements Initializable {

    protected Label logReg;

    protected void initializeCommonTourn(Labeled... nodes) {

        applyHoverEffect(nodes);

        User currentUser = SessionManager.getCurrentUser();
        if (currentUser != null && currentUser.getUsername() != null) {
            logReg.setText(currentUser.getUsername());
            logReg.setOnMouseClicked(event -> {
                try {
                    goHome(event);
                } catch (SQLException _) {
                    throw new RuntimeException();
                }
            });
        }
    }

    protected abstract void goHome(MouseEvent event) throws SQLException;
}
