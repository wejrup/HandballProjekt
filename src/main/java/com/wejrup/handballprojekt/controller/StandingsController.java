package com.wejrup.handballprojekt.controller;

import com.wejrup.handballprojekt.data.Database;
import com.wejrup.handballprojekt.domain.LeaugeStandLine;
import com.wejrup.handballprojekt.domain.Team;
import com.wejrup.handballprojekt.util.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StandingsController {

    @FXML private Button backButton;

    @FXML private ListView<LeaugeStandLine> standingListView;

    private final ObservableList<Team> teams = FXCollections.observableArrayList();

    public void initialize() {
        teams.addAll(Database.selectAllTeams());

        // Monospace font => kolonner står helt lige
        standingListView.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14px;");

        refreshStandings();
    }

    /** Kald denne når points ændrer sig, for at opdatere listen i den nye rækkefølge */
    public void refreshStandings() {
        // Sortér efter points (højeste først). Ved lige points: alfabetisk
        List<Team> sorted = teams.stream()
                .sorted(Comparator.comparingInt(Team::getPoints).reversed()
                        .thenComparing(Team::getTeamName))
                .toList();

        // Byg linjer med position 1..N
        List<LeaugeStandLine> lines = new ArrayList<>();
        for (int i = 0; i < sorted.size(); i++) {
            lines.add(new LeaugeStandLine(i + 1, sorted.get(i)));
        }

        // Opdatér ListView
        standingListView.getItems().setAll(lines);
    }

    @FXML
    private void backButtonAction(ActionEvent event){
        SceneManager.switchScene(event,"scenes/Menu.fxml");
    }

    @FXML
    private void createTeamAction(ActionEvent event){
        SceneManager.switchScene(event, "scenes/CreateTeam.fxml");
    }

    @FXML
    private void editTeamAction(ActionEvent event){
        SceneManager.switchScene(event, "scenes/EditTeam.fxml");
    }


}

