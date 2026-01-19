package com.wejrup.handballprojekt.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.wejrup.handballprojekt.util.SceneManager;

/**
 * *************** MenuController ***************
 *
 * Controller som håndterer brugerinteraktion i hovedmenuen.
 *
 * Klassen giver brugeren mulighed for at navigere til:
 * - oprettelse/start af kamp
 * - standings-visning
 * - match-rapport oversigt
 */

public class MenuController {


    /**
     * Navigere til StartMatch View
     */
    @FXML
    private void startMatchAction(ActionEvent event){
        SceneManager.switchScene(event, "scenes/StartMatch.fxml");
    }

    /**
     * Navigere til Standings View
     */
    @FXML
    private void standingsAction(ActionEvent event){
        SceneManager.switchScene(event, "scenes/Standings.fxml");
    }

    /**
     * Navigere til Matchreport View
     */
    @FXML
    private void matchReportAction(ActionEvent event){
        SceneManager.switchScene(event, "scenes/MatchReport.fxml");
    }

}
