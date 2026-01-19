package com.wejrup.handballprojekt.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * *************** Match ***************
 *
 * Domæneklasse som repræsenterer en håndboldkamp.
 *
 * Klassen indeholder information om hjemme- og
 * udehold, målscore samt alle events der opstår
 * under kampen.
 *
 * Klassen bruges som det centrale objekt under
 * kamp-afvikling i systemet.
 *
 */
public class Match {

    // ID på kampen
    private final int matchID;

    // Hjemmeholdets team-objekt
    private final Team homeTeam;

    // Udeholdets team-objekt
    private final Team awayTeam;

    // Målscore for hjemme- og udehold
    private int homeTeamGoals, awayTeamGoals;

    // Liste med alle events registreret under kampen
    private final ObservableList<Event> events = FXCollections.observableArrayList();

    // Opretter et nyt Match-objekt med startscore 0-0
    public Match(int matchID, Team homeTeam, Team awayTeam){
        this.matchID = matchID;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;

        homeTeamGoals = 0;
        awayTeamGoals = 0;
    }

    /** Intern hjælpe-metode til oprettelse af events
       Eventen indsættes forrest så nyeste events vises øverst */
    private void addEvent(Event.EventType type,
                          int timestampSeconds,
                          int teamId,
                          Event.TeamSide side,
                          String currentScore) {

        events.add(0, new Event(matchID, type, timestampSeconds, teamId, side, currentScore));
    }

    // Registrerer et mål til hjemmeholdet og opdaterer score
    public void addOneHomeGoal(int timestampSeconds){
        homeTeamGoals++;

        events.add(0, new Event(matchID, Event.EventType.Goal, timestampSeconds,
                homeTeam.getTeamID(), Event.TeamSide.Home, getScoreline()));
    }

    // Registrerer et mål til udeholdet og opdaterer score
    public void addOneAwayGoal(int timestampSeconds){
        awayTeamGoals++;

        events.add(0, new Event(matchID, Event.EventType.Goal, timestampSeconds,
                awayTeam.getTeamID(), Event.TeamSide.Away, getScoreline()));
    }

    /** Fortryder et mål for hjemmeholdet hvis muligt
        Der sikres at score ikke kan blive negativ */
    public void removeOneHomeGoal(int timestampSeconds){

        if (homeTeamGoals > 0){
            homeTeamGoals--;

            events.add(0, new Event(matchID, Event.EventType.GoalUndo, timestampSeconds,
                    homeTeam.getTeamID(), Event.TeamSide.Home, getScoreline()));
        }
    }

    /** Fortryder et mål for udeholdet hvis muligt
        Der sikres at score ikke kan blive negativ */
    public void removeOneAwayGoal(int timestampSeconds){

        if (awayTeamGoals > 0){
            awayTeamGoals--;

            events.add(0, new Event(matchID, Event.EventType.GoalUndo, timestampSeconds,
                    awayTeam.getTeamID(), Event.TeamSide.Away, getScoreline()));
        }
    }

    // Registrerer en udvisning for hjemmeholdet
    public void addSuspensionHome(int timestampSeconds) {
        addEvent(Event.EventType.Suspension, timestampSeconds,
                homeTeam.getTeamID(), Event.TeamSide.Home, " ");
    }

    // Registrerer en udvisning for udeholdet
    public void addSuspensionAway(int timestampSeconds) {
        addEvent(Event.EventType.Suspension, timestampSeconds,
                awayTeam.getTeamID(), Event.TeamSide.Away, " ");
    }

    // Fortryder seneste udvisning for hjemmeholdet
    public void undoSuspensionHome(int timestampSeconds) {
        addEvent(Event.EventType.SuspensionUndo, timestampSeconds,
                homeTeam.getTeamID(), Event.TeamSide.Home, " ");
    }

    // Fortryder seneste udvisning for udeholdet
    public void undoSuspensionAway(int timestampSeconds) {
        addEvent(Event.EventType.SuspensionUndo, timestampSeconds,
                awayTeam.getTeamID(), Event.TeamSide.Away, " ");
    }

    public ObservableList<Event> getEvents() {
        return events;
    }

    public void setHomeTeamGoals(int goals){
        homeTeamGoals = goals;
    }

    public void setAwayTeamGoals(int goals){
        awayTeamGoals = goals;
    }

    public String getScoreline(){
        return homeTeamGoals + " - " + awayTeamGoals;
    }

    public int getMatchID(){
        return matchID;
    }

    public String getHomeTeamName(){
        return homeTeam.getTeamName();
    }

    public String getAwayTeamName(){
        return awayTeam.getTeamName();
    }

    public int getHomeTeamGoals(){
        return homeTeamGoals;
    }

    public int getAwayTeamGoals(){
        return awayTeamGoals;
    }

    @Override
    public String toString(){
        return getHomeTeamName() + " " + homeTeamGoals + " - " + awayTeamGoals + " " + getAwayTeamName();
    }

}
