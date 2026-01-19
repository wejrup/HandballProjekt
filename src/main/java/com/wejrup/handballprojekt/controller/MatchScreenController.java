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

/**
 * *************** MatchScreenController ***************
 *
 * Controller som håndterer kamp-skærmen under en igangværende kamp.
 *
 * Klassen er ansvarlig for at:
 * - vise og opdatere tid, holdnavne og score
 * - registrere hændelser (mål, udvisninger osv.)
 * - rendere hændelseslog i UI
 * - gemme kampdata (events + mål) i databasen ved afslutning
 */

public class  MatchScreenController {

    // Match-objektet som skærmen arbejder ud fra
    private Match match;

    // Label der viser kampens tid (mm:ss)
    @FXML private Label timeLabel;

    // Timer der styrer kampens tid (sekunder)
    @FXML private MatchTimer matchTimer;

    // Knap til at starte timeren
    @FXML private Button startTimerButton;

    // Knap til at pause timeren
    @FXML private Button pauseTimerButton;

    // Label der viser hjemmeholdets mål
    @FXML private Label homeTeamGoals;

    // Label der viser udeholdets mål
    @FXML private Label awayTeamGoals;

    // TextArea der viser hændelser (events) under kampen
    @FXML private TextArea eventBox;

    // Label der viser hjemmeholdets navn
    @FXML private Label homeTeamName;

    // Label der viser udeholdets navn
    @FXML private Label awayTeamName;


    /**
     * Deafult fxml metode som bliver kaldt af fxml loader efter alt fxml er loaded
     *
     * Opsætter standard UI-tilstand for timer-knapper og
     * initialiserer MatchTimer med en onTick-callback, der
     * opdaterer tids-label.
     */
    public void initialize(){
        System.out.println("[MatchScreen] initialize()");

        // Standard UI-state hvor start er aktiv og pause er inaktiv/greyed Out
        setTimeButtonsOpacity(1,0.4);

        matchTimer = new MatchTimer(1);

        //Opdatere timelabel hver gang timeren ticker (1 gang i sekundet)
        matchTimer.setOnTick(() -> {
            setTimelabelText(formatTime(matchTimer.getCurrentSeconds()));
        });
    }

    /**
     * Opdaterer UI med data fra match-objektet.
     * Metoden kaldes når match bliver sat via setMatch(...).
     */
    private void refresh() {
        if (match == null) return;

        //Sætter navne øverste i UI´en til deres respektive hold navne
        homeTeamName.setText(match.getHomeTeamName());
        awayTeamName.setText(match.getAwayTeamName());
    }

    /**
     * Konvetere totalSekunder til en formateret form "mm:ss"
     *
     * @param totalSeconds
     * @return String formateret "mm:ss"
     */
    private String formatTime(int totalSeconds){
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Starter kamp-timeren og opdaterer UI-state for knapper.
     */
    @FXML
    private void startTimer(){
        setTimeButtonsOpacity(0.4,1);
        matchTimer.startTimer();
    }

    /**
     * Pauser kamp-timeren og opdaterer UI-state for knapper.
     */
    @FXML
    private void pauseTimer(){
        setTimeButtonsOpacity(1, 0.4);
        matchTimer.pauseTimer();
    }

    /**
     * Sætter TimeLabel Teksten
     */
    private void setTimelabelText(String string){
        timeLabel.setText(string);
    }

    /**
     * Sætter opacity på start/pause knapper for at vise hvilken handling der er aktiv
     */
    private void setTimeButtonsOpacity(double startButton, double pauseButton){
        startTimerButton.setOpacity(startButton);
        pauseTimerButton.setOpacity(pauseButton);
    }

    /**
     * Registrerer et mål til hjemmeholdet, opdaterer score og rendrer events.
     */
    @FXML
    private void addHomeGoal(){
        match.addOneHomeGoal(matchTimer.getCurrentSeconds());
        updateScoreLabel();
        renderEventBox();
    }

    /**
     * Registrerer et mål til udeholdet, opdaterer score og rendrer events.
     */
    @FXML
    private void addAwayGoal(){
        match.addOneAwayGoal(matchTimer.getCurrentSeconds());
        updateScoreLabel();
        renderEventBox();
    }

    /**
     * Fjerner et mål fra hjemmeholdet, opdaterer score og rendrer events.
     */
    @FXML
    private void removeHomeGoal(){
        match.removeOneHomeGoal(matchTimer.getCurrentSeconds());
        updateScoreLabel();
        renderEventBox();
    }

    /**
     * Fjerner et mål fra udeholdet, opdaterer score og rendrer events.
     */
    @FXML
    private void removeAwayGoal(){
        match.removeOneAwayGoal(matchTimer.getCurrentSeconds());
        updateScoreLabel();
        renderEventBox();
    }

    /**
     * Tilføjer en udvisning til hjemmeholdet og rendrer events.
     */
    @FXML
    private void addSuspensionHome(){
        match.addSuspensionHome(matchTimer.getCurrentSeconds());
        renderEventBox();
    }

    /**
     * Tilføjer en udvisning til udeholdet og rendrer events.
     */
    @FXML
    private void addSuspensionAway(){
        match.addSuspensionAway(matchTimer.getCurrentSeconds());
        renderEventBox();
    }

    /**
     * Fortryder sidste udvisning for hjemmeholdet og rendrer events.
     */
    @FXML
    private void undoSuspensionHome(){
        match.undoSuspensionHome(matchTimer.getCurrentSeconds());
        renderEventBox();
    }

    /**
     * Fortryder sidste udvisning for udeholdet og rendrer events.
     */
    @FXML
    private void undoSuspensionAway(){
        match.undoSuspensionAway(matchTimer.getCurrentSeconds());
        renderEventBox();
    }

    /**
     * Opdaterer score-labels baseret på match-objektets aktuelle mål.
     */
    private void updateScoreLabel(){
        homeTeamGoals.setText(String.format("%02d", match.getHomeTeamGoals()));
        awayTeamGoals.setText(String.format("%02d", match.getAwayTeamGoals()));

    }

    /**
     * Sætter match-objektet som skærmen skal arbejde med.
     * Metoden kaldes typisk fra den tidligere scene, når match sendes videre.
     */
    public void setMatch(Match match){
        this.match = match;
        refresh();
        renderEventBox();
    }

    /**
     * Rendrer match-hændelser til eventBox.
     *
     * Event-listen formatteres til en linje pr. event for at give
     * et simpelt log-overblik til brugeren.
     */
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

    /**
     * Gemmer kampens events og mål i databasen og returnerer til menuen.
     */
    @FXML
    public void saveAndExit(ActionEvent event){

        // Gemmer alle events i databasen
        for (Event e : match.getEvents()) {
            System.out.println(e);
            Database.addEvent(e);
        }

        //Gemmer slutresultat (mål) for kampen i match databasen
        Database.updateMatchGoals(match.getMatchID(),match.getHomeTeamGoals(),match.getAwayTeamGoals());

        matchTimer.resetTimer();
        SceneManager.switchScene(event, "scenes/Menu.fxml");
    }
}