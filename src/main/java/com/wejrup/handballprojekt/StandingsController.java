package com.wejrup.handballprojekt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StandingsController {

    @FXML private Button backButton;

    @FXML
    private ListView<LeaugeStandLine> standingListView;

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

    // --- Eksempel: ændr points og opdatér ---
    public void addPoints(String teamName, int pointsToAdd) {
        for (Team t : teams) {
            if (t.getTeamName().equalsIgnoreCase(teamName)) {
                t.setPoints(t.getPoints() + pointsToAdd);
                break;
            }
        }
        refreshStandings();
    }

    @FXML
    private void backButtonAction(){
        sceneChange("Menu.fxml");
    }

    @FXML
    private void createTeamAction(){
        sceneChange("CreateTeam.fxml");
    }

    @FXML
    private void editTeamAction(){
        sceneChange("EditTeam.fxml");
    }

    private void sceneChange(String sceneFile) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneFile));
            Parent root = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

