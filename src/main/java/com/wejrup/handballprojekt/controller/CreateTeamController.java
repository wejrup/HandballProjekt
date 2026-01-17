package com.wejrup.handballprojekt.controller;

import com.wejrup.handballprojekt.data.Database;
import com.wejrup.handballprojekt.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CreateTeamController {

    @FXML private Button saveAndExit;
    @FXML private TextField teamName;

    @FXML
    public void cancelAction(ActionEvent event){
        SceneManager.switchScene(event, "scenes/Standings.fxml");
    }

    @FXML
    public void saveAndExitAction(ActionEvent event){
        String name = teamName.getText();

        //Tjek at navn ikke er tomt
        if (name == null || name.isBlank()) {
            System.out.println("Navn mangler");
            return;
        }

        Database.createTeam(name);
        SceneManager.switchScene(event, "scenes/Standings.fxml");
    }


}
