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

/**
 * *************** StartMatchController ***************
 *
 * Controller class som er ansvarlig for user
 * interactions ved opstart af en ny kamp.
 *
 * Klassen gør det muligt at vælge hjemme- og udehold,
 * validere valget og oprette en ny kamp i databasen
 * før brugeren sendes videre til kamp-skærmen.
 *
 */
public class StartMatchController {

    // Knap til at gå videre til næste skærm
    @FXML private Button nextButton;

    // Label til visning af fejlbeskeder ved ugyldigt input
    @FXML private Label errorLabel;

    // ChoiceBox til valg af hjemmehold
    @FXML private ChoiceBox<Team> homeTeamChoiceBox;

    // ChoiceBox til valg af udehold
    @FXML private ChoiceBox<Team> awayTeamChoiceBox;

    /**
     * Initialisering efter FXML er indlæst.
     *
     * Henter alle hold fra databasen og indsætter dem
     * i begge ChoiceBoxes.
     */
    public void initialize(){
        ArrayList<Team> teams = Database.selectAllTeams();
        homeTeamChoiceBox.getItems().addAll(teams);
        awayTeamChoiceBox.getItems().addAll(teams);
    }

    /**
     * Opretter en ny kamp baseret på brugerens valg
     * og navigerer videre til kamp-skærmen.
     *
     * Brugerinput valideres før kamp oprettes.
     *
     */
    @FXML
    private void next(ActionEvent event) {
        Team home = homeTeamChoiceBox.getValue();
        Team away = awayTeamChoiceBox.getValue();

        //Validere at der er valgt et hold i begge
        if (home == null || away == null) {
            System.out.println("Vælg begge hold først.");
            errorLabel.setText("Du mangler at vælge en eller flere hold.");
            return;
        }

        // Validere at det ikke er de samme hold som er valgt
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

    /**
     * Navigere tilbage til hovedmenuen
     */
    @FXML
    private void backButton(ActionEvent event){
        SceneManager.switchScene(event, "scenes/Menu.fxml");
    }

}

