package com.example.trafalgarbattlesnew.graphiccontrollers;

import applicationcontrollers.ApplicationControllerProfile;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
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

import static util.FadeTransitionUtil.getFadeTransition;
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
    @FXML
    protected JFXButton save;
    @FXML
    protected ImageView saveok;

    User user = SessionManager.getCurrentUser();

    VisualizeScene visualizer = VisualizeScene.getVisualizer(null);

    @FXML
    public void goLog(MouseEvent event) { visualizer.sceneVisualizer("LogRegForm.fxml", event); }

    @FXML
    public void goHome(MouseEvent event) { visualizer.sceneVisualizer(user.getDashboardFXML(), event); }

    @FXML
    public void showsubs(MouseEvent event) { visualizer.sceneVisualizer("Subs.fxml", event);}
    @FXML
    private void enableBirthEdit(MouseEvent ignoredEvent) {    birth.setDisable(false);    }

    @FXML
    private void enableGameEdit(MouseEvent ignoredEvent) { game.setDisable(false); }

    @FXML
    private void enableSexEdit(MouseEvent ignoredEvent) {  sex.setDisable(false);  }

    @FXML
    private void enableNameEdit(MouseEvent ignoredEvent) { fullname.setDisable(false); }

    @FXML
    public void saveProfileFields(MouseEvent ignoredEvent) {

        if (user == null || user.getUsername() == null) return;

        try {
            ApplicationControllerProfile appController = new ApplicationControllerProfile();
            // Ottieni i dati attuali dal DB
            Map<String, String> current = appController.getUserProfile(user.getUsername());

            // Scegli se aggiornare solo i campi compilati
            String b = !birth.getText().isBlank() ? birth.getText() : current.get("birth");
            String g = !game.getText().isBlank() ? game.getText() : current.get("game");
            String s = !sex.getText().isBlank() ? sex.getText() : current.get("sex");
            String f = !fullname.getText().isBlank() ? fullname.getText() : current.get("fullname");

            // Esegui l'update completo (ma con valori misti tra nuovi e già esistenti)
            appController.updateUserProfile(b, g, s, f, user.getUsername());

            // Disabilita solo i campi che sono stati compilati
            if (!birth.getText().isBlank()) birth.setDisable(true);
            if (!game.getText().isBlank()) game.setDisable(true);
            if (!sex.getText().isBlank()) sex.setDisable(true);
            if (!fullname.getText().isBlank()) fullname.setDisable(true);

            // Fade-in iniziale (0.3 secondi)
            saveok.setOpacity(0.0);
            saveok.setVisible(true);

            FadeTransition fadeIn = getFadeTransition(saveok);

            fadeIn.play();
        } catch (SQLException | IOException _) {
            throw new IllegalArgumentException("Something went wrong");
        }
    }

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
        setOrEnable(fullname, data.get("fullname"));
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
