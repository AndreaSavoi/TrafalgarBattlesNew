package com.example.trafalgarbattlesnew.graphiccontrollers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class VisualizeScene {
    private static VisualizeScene visualizer = null;

    private Stage stage;

    private VisualizeScene() {}

    public static VisualizeScene getVisualizer(Stage newS) {
        if(visualizer==null) {
            visualizer = new VisualizeScene();
            visualizer.stage = newS;
        }
        return visualizer;
    }

    public void mainVisualizer(String sName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(sName));
        Scene scene = new Scene(loader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void sceneVisualizer(String sName, MouseEvent event) {
        try{
            Parent loader = FXMLLoader.load(Objects.requireNonNull(VisualizeScene.class.getResource(sName)));
            Scene scene = new Scene(loader);
            if(event != null) {
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            }
            stage.setScene(scene);
            stage.show();
        } catch (Exception _) {
            System.exit(-1);
        }
    }
}
