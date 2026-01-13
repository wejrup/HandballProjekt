package com.wejrup.handballprojekt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Match {
    private final int matchID;
    private final Team homeTeam;
    private final Team awayTeam;
    private int homeTeamGoals, awayTeamGoals;

    private final ObservableList<Event> events = FXCollections.observableArrayList();

    public Match(int matchID, Team homeTeam, Team awayTeam){
        this.matchID = matchID;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        homeTeamGoals = 0;
        awayTeamGoals = 0;
    }

    public ObservableList<Event> getEvents() {
        return events;
    }

    private void addEvent(Event.EventType type, int timestampSeconds, int teamId, Event.TeamSide side, String currentScore) {
        events.add(0, new Event(matchID, type, timestampSeconds, teamId, side, currentScore));
    }

    public void addOneHomeGoal(int timestampSeconds){
        homeTeamGoals++;
        events.add(0, new Event(matchID, Event.EventType.Goal, timestampSeconds,
                homeTeam.getTeamID(), Event.TeamSide.Home, getScoreline()));
    }

    public void addOneAwayGoal(int timestampSeconds){
        awayTeamGoals++;
        events.add(0, new Event(matchID, Event.EventType.Goal, timestampSeconds,
                awayTeam.getTeamID(), Event.TeamSide.Away, getScoreline()));
    }

    public void removeOneHomeGoal(int timestampSeconds){
        if (homeTeamGoals > 0){
            homeTeamGoals--;
            events.add(0, new Event(matchID, Event.EventType.GoalUndo, timestampSeconds,
                    homeTeam.getTeamID(), Event.TeamSide.Home, getScoreline()));
        }
    }

    public void removeOneAwayGoal(int timestampSeconds){
        if (awayTeamGoals > 0){
            awayTeamGoals--;
            events.add(0, new Event(matchID, Event.EventType.GoalUndo, timestampSeconds,
                    awayTeam.getTeamID(), Event.TeamSide.Away, getScoreline()));
        }
    }

    public void addSuspensionHome(int timestampSeconds) {
        addEvent(Event.EventType.Suspension, timestampSeconds, homeTeam.getTeamID(), Event.TeamSide.Home," ");
    }

    public void addSuspensionAway(int timestampSeconds) {
        addEvent(Event.EventType.Suspension, timestampSeconds, awayTeam.getTeamID(), Event.TeamSide.Away," ");
    }

    public void undoSuspensionHome(int timestampSeconds) {
        addEvent(Event.EventType.SuspensionUndo, timestampSeconds, homeTeam.getTeamID(), Event.TeamSide.Home," ");
    }

    public void undoSuspensionAway(int timestampSeconds) {
        addEvent(Event.EventType.SuspensionUndo, timestampSeconds, awayTeam.getTeamID(), Event.TeamSide.Away," ");
    }

    //Hent teamNavne
    public String getTeamNamesFromId(int ID){
        return "ToDo";
    }

    public String toString() {
        return "Home Team ID: " + homeTeam.getTeamID() + " Home name: " + homeTeam.getTeamName()   + " Goals: " + homeTeamGoals  +  "\n Away team ID: " + awayTeam.getTeamID() + " Away name: " + awayTeam.getTeamName() + " Goals: " + awayTeamGoals;
    }

    public String getScoreline(){
        return homeTeamGoals + " - " + awayTeamGoals;
    }

    public int getMatchID(){
        return matchID;
    }

    public int getHomeTeamID(){
        return homeTeam.getTeamID();
    }

    public int getAwayTeamID(){
        return awayTeam.getTeamID();
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

}




