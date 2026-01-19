package com.wejrup.handballprojekt.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


/**
 * *************** SceneManager ***************
 *
 * Utility-klasse som håndterer skift mellem
 * JavaFX scener i applikationen.
 *
 * Klassen bruges til at centralisere scene-skift
 * logik så den ikke gentages i hver controller.
 *
 */
public class SceneManager {

    /** Skifter aktiv scene baseret på et ActionEvent
     Metoden finder automatisk aktivt Stage-vindue
     og loader det ønskede FXML-view */
    public static void switchScene(ActionEvent event, String path) {

        try {

            // Sammensætter fuld sti til FXML-filen
            String fxmlPath = "/com/wejrup/handballprojekt/" + path;

            // Finder nuværende Stage ud fra event-kilden
            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            // Loader FXML-filen
            Parent root = FXMLLoader.load(
                    SceneManager.class.getResource(fxmlPath)
            );

            // Skifter scene og viser vinduet
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
