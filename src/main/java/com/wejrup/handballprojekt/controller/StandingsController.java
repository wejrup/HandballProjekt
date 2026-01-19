package com.wejrup.handballprojekt.controller;

import com.wejrup.handballprojekt.data.Database;
import com.wejrup.handballprojekt.domain.LeaugeStandLine;
import com.wejrup.handballprojekt.domain.Team;
import com.wejrup.handballprojekt.util.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * *************** StandingsController ***************
 *
 * Controller class som er ansvarlig for user
 * interactions på standings-skærmen.
 *
 * Klassen viser en oversigt over alle hold sorteret
 * efter point og giver mulighed for at oprette og
 * redigere hold.
 *
 */
public class StandingsController {
    // ListView der viser standings-linjer (placering, hold og point)
    @FXML private ListView<LeaugeStandLine> standingListView;

    // Intern liste der skal indeholde alle hold hentet fra databasen
    private final ObservableList<Team> teams = FXCollections.observableArrayList();

    /**
     * Initialisering efter FXML er indlæst.
     *
     * Henter alle hold fra databasen, anvender styling
     * på ListView og opdaterer standings-visningen.
     */
    public void initialize() {
        teams.addAll(Database.selectAllTeams());

        standingListView.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14px;");

        refreshStandings();
    }

    /**
     * Opdaterer standings-listen baseret på de aktuelle point.
     *
     * Holdene sorteres først efter point (højeste først) og
     * derefter alfabetisk ved lige point. Herefter genereres
     * standings-linjer med korrekt placering.
     */
    public void refreshStandings() {

        List<Team> sorted = teams.stream()

                //Sortering af holdende
                .sorted(Comparator.comparingInt(Team::getPoints).reversed()
                        .thenComparing(Team::getTeamName))
                .toList();

        // Byg linjer med position 1..N
        List<LeaugeStandLine> lines = new ArrayList<>();
        for (int i = 0; i < sorted.size(); i++) {
            lines.add(new LeaugeStandLine(i + 1, sorted.get(i)));
        }

        // Opdater ListView med de nye standings-linjer
        standingListView.getItems().setAll(lines);
    }

    /**
     * Navigere tilbage til hovedmenuen
     */
    @FXML
    private void backButtonAction(ActionEvent event){
        SceneManager.switchScene(event,"scenes/Menu.fxml");
    }

    /**
     * Navigerer til skærmen hvor et nyt hold kan oprettes.
     */
    @FXML
    private void createTeamAction(ActionEvent event){
        SceneManager.switchScene(event, "scenes/CreateTeam.fxml");
    }

    /**
     * Navigere til skærmen hvor et hold kan redigeres
     */
    @FXML
    private void editTeamAction(ActionEvent event){
        SceneManager.switchScene(event, "scenes/EditTeam.fxml");
    }

}

