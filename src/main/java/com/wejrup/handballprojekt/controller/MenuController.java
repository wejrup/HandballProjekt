package com.wejrup.handballprojekt.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.wejrup.handballprojekt.util.SceneManager;

public class MenuController {
    @FXML private Button startMatchButton;
    @FXML private Button standingsButton;
    @FXML private Button matchReportButton;

    @FXML
    private void startMatchAction(ActionEvent event){
        SceneManager.switchScene(event, "scenes/StartMatch.fxml");
    }

    @FXML
    private void standingsAction(ActionEvent event){
        SceneManager.switchScene(event, "scenes/Standings.fxml");
    }

    @FXML
    private void matchReportAction(ActionEvent event){
        SceneManager.switchScene(event, "scenes/MatchReport.fxml");
    }

}
