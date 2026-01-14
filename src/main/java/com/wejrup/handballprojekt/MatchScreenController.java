package com.wejrup.handballprojekt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class  MatchScreenController {

    private Match match;

    @FXML private Label timeLabel;
    @FXML private MatchTimer matchTimer;
    @FXML private Button startTimerButton;
    @FXML private Button pauseTimerButton;
    @FXML private Label homeTeamGoals;
    @FXML private Label awayTeamGoals;
    @FXML private TextArea eventBox;
    @FXML private Label homeTeamName;
    @FXML private Label awayTeamName;
    @FXML private Button saveandexit;


    //Deafult fxml metode som bliver kaldt af fxml loader
    public void initialize(){
        System.out.println("[MatchScreen] initialize()");
        setTimeButtonsOpacity(1,0.4);
        matchTimer = new MatchTimer(1);
        matchTimer.setOnTick(() -> {
            setTimelabelText(formatTime(matchTimer.getCurrentSeconds()));
        });
    }

    private void refresh() {
        if (match == null) return;

        homeTeamName.setText(match.getHomeTeamName());
        awayTeamName.setText(match.getAwayTeamName());
    }

    private String formatTime(int totalSeconds){
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @FXML
    private void startTimer(){
        setTimeButtonsOpacity(0.4,1);
        matchTimer.startTimer();
    }

    @FXML
    private void pauseTimer(){
        setTimeButtonsOpacity(1, 0.4);
        matchTimer.pauseTimer();
    }

    @FXML
    private void reset(){
        matchTimer.resetTimer();
        setTimelabelText("00:00");
    }

    //Bruges flere gange i løbet af koden og derfor metoden (Mindsker redundans)
    private void setTimelabelText(String string){
        timeLabel.setText(string);
    }

    //Metode til at sætte opacity
    private void setTimeButtonsOpacity(double startButton, double pauseButton){
        startTimerButton.setOpacity(startButton);
        pauseTimerButton.setOpacity(pauseButton);
    }

    @FXML
    private void addHomeGoal(){
        match.addOneHomeGoal(matchTimer.getCurrentSeconds());
        updateScoreLabel();
        renderEventBox();
    }

    @FXML
    private void addAwayGoal(){
        match.addOneAwayGoal(matchTimer.getCurrentSeconds());
        updateScoreLabel();
        renderEventBox();
    }

    @FXML
    private void removeHomeGoal(){
        match.removeOneHomeGoal(matchTimer.getCurrentSeconds());
        updateScoreLabel();
        renderEventBox();
    }

    @FXML
    private void removeAwayGoal(){
        match.removeOneAwayGoal(matchTimer.getCurrentSeconds());
        updateScoreLabel();
        renderEventBox();
    }

    @FXML
    private void addSuspensionHome(){
        match.addSuspensionHome(matchTimer.getCurrentSeconds());
        renderEventBox();
    }

    @FXML
    private void addSuspensionAway(){
        match.addSuspensionAway(matchTimer.getCurrentSeconds());
        renderEventBox();
    }

    @FXML
    private void undoSuspensionHome(){
        match.undoSuspensionHome(matchTimer.getCurrentSeconds());
        renderEventBox();
    }

    @FXML
    private void undoSuspensionAway(){
        match.undoSuspensionAway(matchTimer.getCurrentSeconds());
        renderEventBox();
    }


    private void updateScoreLabel(){
        homeTeamGoals.setText(String.format("%02d", match.getHomeTeamGoals()));
        awayTeamGoals.setText(String.format("%02d", match.getAwayTeamGoals()));

    }

    public void setMatch(Match match){
        this.match = match;
        refresh();
        renderEventBox();
    }

    private void renderEventBox() {
        if (match == null) return;

        StringBuilder sb = new StringBuilder();

        for (Event e : match.getEvents()) {
            sb.append(e.formatTime())
                    .append(" - ")
                    .append(e.getTeamSide())
                    .append(" ")
                    .append(e.getType())
                    .append("   ")
                    .append(e.getCurrentScore())
                    .append("\n");
        }

        eventBox.setText(sb.toString());
    }

    @FXML
    public void saveAndExit(ActionEvent event){
        Database.updateMatchGoals(match.getMatchID(),match.getHomeTeamGoals(),match.getAwayTeamGoals());
        for (Event e : match.getEvents()) {
            Database.addEvent(e);
            matchTimer.resetTimer();
            sceneChange("Menu.fxml");
        }
    }
    private void sceneChange(String sceneFile) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneFile));
            Parent root = loader.load();

            Stage stage = (Stage) saveandexit.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}