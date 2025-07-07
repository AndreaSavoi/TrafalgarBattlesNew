package com.example.trafalgarbattlesnew.graphiccontrollers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Font.loadFont(getClass().getResourceAsStream("/fonts/Century Gothic.ttf"), 21);
        Font.loadFont(getClass().getResourceAsStream("/fonts/GOTHICB.ttf"), 21);
        VisualizeScene visualizeScene = VisualizeScene.getVisualizer(stage);
        visualizeScene.mainVisualizer("MainView.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}