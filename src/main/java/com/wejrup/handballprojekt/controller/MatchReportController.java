package com.wejrup.handballprojekt.controller;

import com.wejrup.handballprojekt.data.Database;
import com.wejrup.handballprojekt.domain.Match;
import com.wejrup.handballprojekt.domain.MatchReportLine;
import com.wejrup.handballprojekt.util.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * *************** MatchReportController ***************
 *
 * Controller class som er ansvarlig for visning og
 * interaktion med kamp report oversigten
 *
 * Klassen henter alle kampe fra databasen, viser dem
 * i en ListView og giver mulighed for at åbne en
 * detaljeret rapport ved dobbeltklik.
 *
 */

public class MatchReportController {

    //ListView der viser match report lines
    @FXML private ListView<MatchReportLine> matchReportListView;

    //Liste som skal indeholde alle kampe fra databasen
    private final ObservableList<Match> matches = FXCollections.observableArrayList();

    /**
     * Initialisering efter FXML er indlæst.
     *
     * Henter alle kampe fra databasen, anvender visuel styling
     * på listen og opdaterer visningen.
     */
    public void initialize() {

        //Tilføjer alle kampe fra databasen til listen matches
        matches.addAll(Database.selectAllMatches());

        // Styler teksten til viwet
        matchReportListView.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14px;");

        refreshMatches();
    }

    /**
     * Opdaterer ListView med de aktuelle kamp-data.
     *
     * Hver Match konverteres til en MatchReportLine,
     * som er ansvarlig for præsentationsformatet.
     */
    public void refreshMatches() {
        matchReportListView.getItems().clear();

        for (Match m : matches) {
            matchReportListView.getItems().add(new MatchReportLine(m));
        }
    }

    /**
     * Håndterer museklik på match-listen.
     *
     * Ved dobbeltklik åbnes en detaljeret kamp-rapport,
     * og det valgte Match-objekt sendes videre til
     * den næste controller.
     *
     * NOTE: MouseEvent fremfor ActionEvent
     */
    @FXML
    private void matchListClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {

            MatchReportLine selected = matchReportListView.getSelectionModel().getSelectedItem();

            if (selected == null) return;

            Match match = selected.getMatch();
            System.out.println("Dobbeltklik på matchId: " + match.getMatchID());

            /** Load næste scene + send match til controller */
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wejrup/handballprojekt/scenes/SelectedMatchReport.fxml"));
                Parent root = loader.load();

                SelectedMatchReportController controller = loader.getController();
                controller.setMatch(match);

                Stage stage = (Stage) matchReportListView.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Action til at navigere tilbage til menuen
     */
    @FXML
    private void backButtonAction(ActionEvent event) {
        SceneManager.switchScene(event, "scenes/Menu.fxml");
    }

}
