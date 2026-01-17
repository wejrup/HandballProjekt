package com.wejrup.handballprojekt.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

@SuppressWarnings("CallToPrintStackTrace")
public class SceneManager {

    public static void switchScene(ActionEvent event, String path) {

        try {
            String fxmlPath = "/com/wejrup/handballprojekt/" + path;
            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            Parent root = FXMLLoader.load(
                    SceneManager.class.getResource(fxmlPath)
            );

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
