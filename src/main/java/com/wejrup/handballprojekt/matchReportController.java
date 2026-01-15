package com.wejrup.handballprojekt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class matchReportController {

    @FXML private ListView<MatchReportLine> matchReportListView;
    @FXML private Button backButton;

    private final ObservableList<Match> matches = FXCollections.observableArrayList();

    public void initialize() {
        matches.addAll(Database.selectAllMatches());

        // Monospace font => kolonner står lige
        matchReportListView.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14px;");

        refreshMatches();
    }

    /** Ingen sortering – bare list dem som de kommer fra DB */
    public void refreshMatches() {
        matchReportListView.getItems().clear();

        for (Match m : matches) {
            matchReportListView.getItems().add(new MatchReportLine(m));
        }
    }

    // Note MouseEvent fremfor ActionEvent
    @FXML
    private void matchListClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {

            MatchReportLine selected = matchReportListView
                    .getSelectionModel()
                    .getSelectedItem();

            if (selected == null) return;

            Match match = selected.getMatch();
            System.out.println("Dobbeltklik på matchId: " + match.getMatchID());

            // Load næste scene + send match til controller
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectedMatchReport.fxml"));
                Parent root = loader.load();

                SelectedMatchReportController controller = loader.getController();
                controller.setMatch(match); // her “sendes” objektet videre

                Stage stage = (Stage) matchReportListView.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void backButtonAction() {
        sceneChange("Menu.fxml");
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
