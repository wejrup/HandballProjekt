package com.wejrup.handballprojekt;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class  MatchScreenController {
    public Match match;
    @FXML private Label timeLabel;
    @FXML private MatchTimer matchTimer;
    @FXML private Button startTimerButton;
    @FXML private Button pauseTimerButton;
    @FXML private Label homeTeamGoals;
    @FXML private Label awayTeamGoals;

    //Deafult fxml metode som bliver kaldt af fxml loader
    public void initialize(){
        match = StartMatchController.getMatch();
        setTimeButtonsOpacity(1,0.4);
        matchTimer = new MatchTimer(1);

        matchTimer.setOnTick(() -> {
            setTimelabelText(formatTime(matchTimer.getCurrentSeconds()));
        });

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
        match.addOneHomeGoal();
        updateScoreLabel();
    }

    @FXML
    private void addAwayGoal(){
        match.addOneAwayGoal();
        updateScoreLabel();
    }

    @FXML
    private void removeHomeGoal(){
        match.removeOneHomeGoal();
        updateScoreLabel();
    }

    @FXML
    private void removeAwayGoal(){
        match.removeOneAwayGoal();
        updateScoreLabel();
    }


    private void updateScoreLabel(){
        homeTeamGoals.setText(String.format("%02d", match.getHomeTeamGoals()));
        awayTeamGoals.setText(String.format("%02d", match.getAwayTeamGoals()));

    }


}