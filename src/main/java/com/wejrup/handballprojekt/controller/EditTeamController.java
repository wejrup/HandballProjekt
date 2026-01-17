package com.wejrup.handballprojekt.controller;

import com.wejrup.handballprojekt.controller.data.Database;
import com.wejrup.handballprojekt.domain.Team;
import com.wejrup.handballprojekt.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.ArrayList;
import java.util.Optional;

public class EditTeamController {
    @FXML private ChoiceBox<Team> selectTeamChoiceBox;
    @FXML private Button saveAndExitButton;
    @FXML private TextField teamName;
    @FXML private TextField teamPoints;

    private Team chosenTeam;

    @FXML
    private void initialize(){
        ArrayList<Team> teams = Database.selectAllTeams();
        selectTeamChoiceBox.getItems().addAll(teams);
        selectTeamChoiceBox.setOnAction(this::choiceBoxTeamSelected);
    }

    @FXML
    private void cancelAction(ActionEvent event){
        SceneManager.switchScene(event, "scenes/Standings.fxml");
    }
    @FXML
    private void saveAndExitAction(ActionEvent event){
        //Get name from textfield
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

    @FXML
    private void deleteAction(ActionEvent event){
        if (chosenTeam == null){
            return;
        }
        if (confirmDelete(chosenTeam.getTeamName())){
            Database.deleteTeam(chosenTeam.getTeamID());
            SceneManager.switchScene(event,"scenes/Standings.fxml");
        }
    }

    private void choiceBoxTeamSelected(ActionEvent event){
        chosenTeam = selectTeamChoiceBox.getValue();
        refreshTextField(chosenTeam);
    }

    private void refreshTextField(Team team){
        teamName.setText(team.getTeamName());
        teamPoints.setText(String.valueOf(team.getPoints()));
    }

    public static boolean confirmDelete(String teamName) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bekræft sletning");
        alert.setHeaderText("Vil du slette hold: " + teamName + "?");
        alert.setContentText("Denne handling kan ikke fortrydes.");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }



}
