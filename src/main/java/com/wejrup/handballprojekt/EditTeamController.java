package com.wejrup.handballprojekt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
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
    private void cancelAction(){
        sceneChange("Standings.fxml");
    }
    @FXML
    private void saveAndExitAction(){
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
            Database.updateTeam(chosenTeam.teamID, name, value);
            sceneChange("Standings.fxml");
        }
        }

    @FXML
    private void deleteAction(){
        if (chosenTeam == null){
            return;
        }
        if (confirmDelete(chosenTeam.teamName)){
            Database.deleteTeam(chosenTeam.teamID);
            sceneChange("Standings.fxml");
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

    private void sceneChange(String sceneFile) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneFile));
            Parent root = loader.load();

            Stage stage = (Stage) saveAndExitButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
