package com.wejrup.handballprojekt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class SelectedMatchReportController {
    @FXML private Button backButton;
    @FXML private Label homeTeamName;
    @FXML private Label awayTeamName;
    @FXML private Label scoreLine;
    @FXML private ListView matchReportListView;

    private Match match;

    private final ObservableList<Event> events = FXCollections.observableArrayList();


    public void setMatch(Match match){
        this.match = match;
        homeTeamName.setText(match.getHomeTeamName());
        awayTeamName.setText(match.getAwayTeamName());
        scoreLine.setText(match.getScoreline());

        events.setAll(Database.selectEventsByMatchId(match.getMatchID()));
        refreshMatches();
    }



    public void initialize() {
        matchReportListView.getItems().clear();
        // Monospace font => kolonner står lige
        matchReportListView.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14px;");

        refreshMatches();
    }

    /** Ingen sortering – bare list dem som de kommer fra DB */
    public void refreshMatches() {
        matchReportListView.getItems().clear();

        for (Event e : events) {
            matchReportListView.getItems().add(new SelectedMatchReportLine(e));
        }
    }


    @FXML
    private void backAction(){
        sceneChange("MatchReport.fxml");
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
