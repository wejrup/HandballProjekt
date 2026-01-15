package com.wejrup.handballprojekt;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML private Button startMatchButton;
    @FXML private Button standingsButton;
    @FXML private Button matchReportButton;

    @FXML
    private void startMatchAction(){
        sceneChange("StartMatch.fxml");
    }

    @FXML
    private void standingsAction(){
        sceneChange("Standings.fxml");
    }

    @FXML
    private void matchReportAction(){
        sceneChange("MatchReport.fxml");
    }


    private void sceneChange(String sceneFile) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneFile));
            Parent root = loader.load();

            Stage stage = (Stage) startMatchButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
