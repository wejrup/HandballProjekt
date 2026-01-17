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

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


public class SelectedMatchReportController {
    @FXML private Button backButton;
    @FXML private Label homeTeamName;
    @FXML private Label awayTeamName;
    @FXML private Label scoreLine;
    @FXML private ListView matchReportListView;

    private final ObservableList<Event> events = FXCollections.observableArrayList();

    public void setMatch(Match match){
        homeTeamName.setText(match.getHomeTeamName());
        awayTeamName.setText(match.getAwayTeamName());
        scoreLine.setText(match.getScoreline());

        events.setAll(Database.selectEventsByMatchId(match.getMatchID()));
        refreshMatches();
    }

    public void initialize() {
        matchReportListView.getItems().clear();
        matchReportListView.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14px;");

        refreshMatches();
    }

    public void refreshMatches() {
        matchReportListView.getItems().clear();

        for (Event e : events) {
            //noinspection unchecked
            matchReportListView.getItems().add(new SelectedMatchReportLine(e));
        }
    }

    @FXML
    private void backAction(ActionEvent event){
        SceneManager.switchScene(event, "scenes/MatchReport.fxml");
    }

}
