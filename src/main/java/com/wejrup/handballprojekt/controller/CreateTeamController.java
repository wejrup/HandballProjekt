package com.wejrup.handballprojekt.controller;

import com.wejrup.handballprojekt.data.Database;
import com.wejrup.handballprojekt.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * *************** CreateTeamController ***************
 *
 * Controller class som er ansvarlig for user
 * interactions når man opretter et nyt hold
 */

public class CreateTeamController {

    // Text field hvor teamName bliver indtastet
    @FXML private TextField teamName;

    /** Annullere create team og går tilbage til standings view */
    @FXML
    public void cancelAction(ActionEvent event){
        SceneManager.switchScene(event, "scenes/Standings.fxml");
    }

    /**
     * Gemmer det indtastet team navn i databasen og går tilbage til standings view
     *
     * Inputtet bliver valideret for at tjekke det ikke er Null eller blankt
     * */
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
