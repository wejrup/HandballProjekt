package com.wejrup.handballprojekt;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateTeamController {

    @FXML private Button saveAndExit;
    @FXML private TextField teamName;

    @FXML
    public void cancelAction(){
        sceneChange("Standings.fxml");
    }

    @FXML
    public void saveAndExitAction(){
        String name = teamName.getText();

        if (name == null || name.isBlank()) {
            System.out.println("Navn mangler");
            return;
        }

        Database.createTeam(name);
        sceneChange("Standings.fxml");
    }

    private void sceneChange(String sceneFile) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneFile));
            Parent root = loader.load();

            Stage stage = (Stage) saveAndExit.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
