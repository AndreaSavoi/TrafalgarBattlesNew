package com.example.trafalgarbattlesnew.graphiccontrollers;

import applicationcontrollers.ApplicationControllerLogReg;
import bean.BeanLog;
import bean.BeanReg;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import singleton.SessionManager;
import users.User;

import java.sql.SQLException;

import static util.HoverEffectUtil.applyHoverEffect;

public class LogRegFormController implements Initializable {
    @FXML
    protected TextField usernameR;
    @FXML
    protected TextField email;
    @FXML
    protected PasswordField passwordR;
    @FXML
    protected TextField usernameL;
    @FXML
    protected TextField passwordL;
    @FXML
    protected Label formRes;
    @FXML
    protected Label logout;
    @FXML
    protected CheckBox checkRoleL;
    @FXML
    protected CheckBox checkRoleR;
    @FXML
    protected Label home;
    private String type;

    private final VisualizeScene visualizer = VisualizeScene.getVisualizer(null);

    @FXML
    public void loginVer() {
        if(usernameL.getText().isEmpty() || passwordL.getText().isEmpty()){
            formRes.setText("Invalid password/username");
        } else {
            if(checkRoleL.isSelected()){type = "Organizer";} else {type = "Player";}
            BeanLog bL = new BeanLog(usernameL.getText(), passwordL.getText(), type);
            try{
                new ApplicationControllerLogReg(bL);
                passwordL.setDisable(true);
                usernameL.setDisable(true);
                email.setDisable(true);
                passwordR.setDisable(true);
                usernameR.setDisable(true);
                formRes.setText("Successfully logged in");
                formRes.setTextFill(javafx.scene.paint.Color.GREEN);
            } catch (Exception _) {
                formRes.setText("Something went wrong, please try again");
                formRes.setTextFill(javafx.scene.paint.Color.RED);
            }
        }
    }

    @FXML
    public void signupVer(){
        if(usernameR.getText().isEmpty() || passwordR.getText().isEmpty() || email.getText().isEmpty() || !email.getText().contains("@")){
            formRes.setText("Invalid credentials, please try again");
        } else {
            if(checkRoleR.isSelected()){type = "Organizer";} else {type = "Player";}
            BeanReg bR = new BeanReg(email.getText(), usernameR.getText(), passwordR.getText(), type);
            try{
                new ApplicationControllerLogReg(bR);
                email.setDisable(true);
                passwordR.setDisable(true);
                usernameR.setDisable(true);
                formRes.setText("Successfully registered, please login");
                formRes.setTextFill(javafx.scene.paint.Color.GREEN);
            } catch (Exception _) {
                formRes.setText("Something went wrong, please try again");
                formRes.setTextFill(javafx.scene.paint.Color.RED);
            }
        }
    }

    @FXML
    public void returnHome(MouseEvent event){
        User currentUser = SessionManager.getCurrentUser();
        if(currentUser == null){
            visualizer.sceneVisualizer("MainView.fxml", event);
        } else {
            visualizer.sceneVisualizer(currentUser.getDashboardFXML(), event);
        }
    }

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {
        applyHoverEffect(home);
        User currentUser = SessionManager.getCurrentUser();
        if(currentUser == null){
            logout.setDisable(true);
        }
    }

    @FXML
    public void logout(MouseEvent mouseEvent) {
        SessionManager.clear();
        formRes.setText("Successfully logged out");
        logout.setDisable(true);
    }
}
