package com.wejrup.handballprojekt.controller;

import com.wejrup.handballprojekt.data.Database;
import com.wejrup.handballprojekt.domain.Event;
import com.wejrup.handballprojekt.domain.Match;
import com.wejrup.handballprojekt.domain.SelectedMatchReportLine;
import com.wejrup.handballprojekt.util.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * *************** SelectedMatchReportController ***************
 *
 * Controller class som er ansvarlig for user
 * interactions på skærmen der viser detaljeret
 * kamp-rapport for én specifik kamp.
 *
 * Klassen gør det muligt at se holdnavne, slutresultat
 * samt en liste over alle events knyttet til kampen.
 *
 */
public class SelectedMatchReportController {
    // Label der viser hjemmeholdets navn
    @FXML private Label homeTeamName;

    // Label der viser udeholdets navn
    @FXML private Label awayTeamName;

    // Label der viser kampens slutresultat
    @FXML private Label scoreLine;

    // ListView der viser kampens events
    @FXML private ListView matchReportListView;

    // Liste der indeholder alle events for den valgte kamp
    private final ObservableList<Event> events = FXCollections.observableArrayList();

    /**
     * Modtager match-objektet fra forrige scene og
     * opdaterer UI med tilhørende data.
     *
     * Metoden henter events fra databasen baseret
     * på matchId og opdaterer event-listen.
     *
     * @param match den valgte match som skal vises
     */
    public void setMatch(Match match){
        homeTeamName.setText(match.getHomeTeamName());
        awayTeamName.setText(match.getAwayTeamName());
        scoreLine.setText(match.getScoreline());

        events.setAll(Database.selectEventsByMatchId(match.getMatchID()));
        refreshMatches();
    }

    /**
     * Initialisering efter FXML er indlæst.
     *
     * Sætter styling på ListView og sikrer at listen
     * starter i en tom tilstand.
     */
    public void initialize() {
        matchReportListView.getItems().clear();
        matchReportListView.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14px;");

        refreshMatches();
    }

    /**
     * Opdaterer ListView med events fra event-listen.
     *
     * Hver Event konverteres til en SelectedMatchReportLine,
     * som håndterer visningsformatet.
     */
    public void refreshMatches() {
        matchReportListView.getItems().clear();

        for (Event e : events) {
            matchReportListView.getItems().add(new SelectedMatchReportLine(e));
        }
    }

    /**
     * Navigerer tilbage til match-rapport oversigten.
     */
    @FXML
    private void backAction(ActionEvent event){
        SceneManager.switchScene(event, "scenes/MatchReport.fxml");
    }

}
