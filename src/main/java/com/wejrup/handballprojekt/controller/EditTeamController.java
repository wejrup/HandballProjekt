package com.wejrup.handballprojekt.controller;

import com.wejrup.handballprojekt.data.Database;
import com.wejrup.handballprojekt.domain.Team;
import com.wejrup.handballprojekt.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.ArrayList;
import java.util.Optional;

/**
 * *************** EditTeamController ***************
 *
 * Controller class som er ansvarlig for user
 * interactions når man redigere et Hold
 *
 *  Klassen gør det muligt at vælge et hold, opdatere
 *  navnet og point samt slettet et hold fra databasen
 *
 */

public class EditTeamController {

    //Choicebox der bruges til at vælge det eksiterende hold
    @FXML private ChoiceBox<Team> selectTeamChoiceBox;

    // TextField der indeholder holdets navn og gør det muligt at redigere det
    @FXML private TextField teamName;

    //TextField der indeholder holdets navn og gør det muligt at redigere dem
    @FXML private TextField teamPoints;

    // Variabel til det valgte
    private Team chosenTeam;

    /**
     * Initializer contrlleren efter alt fxml er loaded
     *
     * Henter alle hold fra databasen og sætter dem i choiceBoxen
     * Sætter også en listener som registrer når brugeren vælger et hold
     */
    @FXML
    private void initialize(){
        ArrayList<Team> teams = Database.selectAllTeams();
        selectTeamChoiceBox.getItems().addAll(teams);
        selectTeamChoiceBox.setOnAction(this::choiceBoxTeamSelected);
    }

    /** Anullerer redigering og retunere til Standings view */
    @FXML
    private void cancelAction(ActionEvent event){
        SceneManager.switchScene(event, "scenes/Standings.fxml");
    }

    /**
     * Gemmer ændringer på det valgte hold og retuner til Standings view
     *
     * Brugerens input valideres før Databasen odpateres
     */
    @FXML
    private void saveAndExitAction(ActionEvent event){

        String name = teamName.getText();
        int value;

        try {
            value = Integer.parseInt(teamPoints.getText());
            // string er en int
        } catch (NumberFormatException e) {
            // string er IKKE en int
            System.out.println("String er ikke en int");
            return;
        }

        if (name != null && !name.isBlank()){
            Database.updateTeam(chosenTeam.getTeamID(), name, value);
            SceneManager.switchScene(event,"scenes/Standings.fxml");
        }
        }


    /**
     * SLet det valgte hold med alertbox til bekræftelse
     * Det valideres at der er valgt et hold inden sletning
     */
    @FXML
    private void deleteAction(ActionEvent event){
        if (chosenTeam == null){
            return;
        }

        //Alertbox med bekræftigelse sendes og hold slettes hvis ok vælges
        if (confirmDelete(chosenTeam.getTeamName())){
            Database.deleteTeam(chosenTeam.getTeamID());
            SceneManager.switchScene(event,"scenes/Standings.fxml");
        }
    }

    /**
     * Håndtere valg af hold i ChoiceBox
     */
    private void choiceBoxTeamSelected(ActionEvent event){
        chosenTeam = selectTeamChoiceBox.getValue();
        refreshTextField(chosenTeam);
    }

    /**
     * Opdater inputfelter med data fra det valgte hold
     */
    private void refreshTextField(Team team){

        //Sætter teamName TextField til hold navnet
        teamName.setText(team.getTeamName());

        //Sætter teamPoints til holdets nuværende point
        teamPoints.setText(String.valueOf(team.getPoints()));
    }

    /**
     *
     * AlertBox til bekræftelse af sletning
     *
     * @param teamName - Navnet på holdet der ønskes slettet
     * @return true hvis brugeren vælger ok ellers false
     */
    public static boolean confirmDelete(String teamName) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bekræft sletning");
        alert.setHeaderText("Vil du slette hold: " + teamName + "?");
        alert.setContentText("Denne handling kan ikke fortrydes.");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
