package com.wejrup.handballprojekt;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class StartMatchController {
    private static final Match match = new Match(1,2);
    @FXML private Button nextButton;

    @FXML
    private void next() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("matchScreen.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) nextButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Match getMatch(){
        return match;
    }



}
