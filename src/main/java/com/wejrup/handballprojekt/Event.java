package com.wejrup.handballprojekt;

public class Event {

    //An enum is a special "class" that represents a group of constants (unchangeable variables, like final variables).
    public enum EventType {
        Goal,
        Suspension,
        GoalUndo,
        SuspensionUndo
    }

    public enum TeamSide {
        Home,
        Away
    }

    private final int matchID;
    private final EventType type;
    private final int eventTimestampSeconds;
    private final int teamID;
    private final TeamSide teamSide;
    private final String currentScore;

    public Event(int matchID, EventType type, int eventTimestampSeconds, int teamID, TeamSide teamSide, String currentScore){
        this.matchID = matchID;
        this.type = type;
        this.eventTimestampSeconds = eventTimestampSeconds;
        this.teamID = teamID;
        this.teamSide = teamSide;
        this.currentScore = currentScore;
    }

    public int getEventTimestampSeconds(){
        return eventTimestampSeconds;
    }

    public EventType getType(){
        return type;
    }

    public int getTeamID(){
        return teamID;
    }

    public TeamSide getTeamSide(){
        return teamSide;
    }

    public String formatTime(){
        int minutes = getEventTimestampSeconds() / 60;
        int seconds = getEventTimestampSeconds() % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public String getCurrentScore(){
        return currentScore;
    }

    public String toString() {
        return "Event type: " + getType() + " TimeStamp: " + getEventTimestampSeconds() + " Side: " + getTeamSide() + " Current Score: " + getCurrentScore();
    }
}
