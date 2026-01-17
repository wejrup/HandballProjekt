package com.wejrup.handballprojekt.controller;

import com.wejrup.handballprojekt.data.Database;
import com.wejrup.handballprojekt.domain.Event;
import com.wejrup.handballprojekt.domain.Match;
import com.wejrup.handballprojekt.util.MatchTimer;
import com.wejrup.handballprojekt.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

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
        System.out.println(match.getEvents());
        for (Event e : match.getEvents()) {
            System.out.println(e);
            Database.addEvent(e);
        }
        Database.updateMatchGoals(match.getMatchID(),match.getHomeTeamGoals(),match.getAwayTeamGoals());
        matchTimer.resetTimer();
        SceneManager.switchScene(event, "scenes/Menu.fxml");
    }

}