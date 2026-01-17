package com.wejrup.handballprojekt.controller;

import com.wejrup.handballprojekt.data.Database;
import com.wejrup.handballprojekt.domain.Match;
import com.wejrup.handballprojekt.domain.Team;
import com.wejrup.handballprojekt.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("CallToPrintStackTrace")
public class StartMatchController {
    @FXML private Button nextButton;
    @FXML private Label errorLabel;
    @FXML private ChoiceBox<Team> homeTeamChoiceBox;
    @FXML private ChoiceBox<Team> awayTeamChoiceBox;

    public void initialize(){
        ArrayList<Team> teams = Database.selectAllTeams();
        homeTeamChoiceBox.getItems().addAll(teams);
        awayTeamChoiceBox.getItems().addAll(teams);
    }

    @FXML
    private void next(ActionEvent event) {
        Team home = homeTeamChoiceBox.getValue();
        Team away = awayTeamChoiceBox.getValue();

        if (home == null || away == null) {
            System.out.println("Vælg begge hold først.");
            errorLabel.setText("Du mangler at vælge en eller flere hold.");
            return;
        }
        else if (home == away){
            System.out.println("2 ens hold er valgt.");
            errorLabel.setText("Du skal vælge 2 forskellige hold");
            return;
        }
        int matchID = Database.createMatch(home.getTeamID(), away.getTeamID());
        Match match = new Match(matchID, home, away);
        System.out.println("MathID: " + matchID);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wejrup/handballprojekt/scenes/MatchScreen.fxml"));
            Parent root = loader.load();

            MatchScreenController controller = loader.getController();
            controller.setMatch(match);

            Stage stage = (Stage) nextButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void backButton(ActionEvent event){
        SceneManager.switchScene(event, "scenes/Menu.fxml");
    }

}

