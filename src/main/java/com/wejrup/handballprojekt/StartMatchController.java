package com.wejrup.handballprojekt;

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

public class StartMatchController {
    @FXML private Button nextButton;
    @FXML private Label errorLabel;
    @FXML private ChoiceBox<Team> homeTeamChoiceBox;
    @FXML private ChoiceBox<Team> awayTeamChoiceBox;

    private final Team[] teams = {
            new Team(1,"hold1",0),
            new Team(2,"hold2",0),
            new Team(3,"hold3",0)

    };

    public void initialize(){
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

        Match match = new Match(1, home, away);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("matchScreen.fxml"));
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




}
