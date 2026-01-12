package com.wejrup.handballprojekt;

public class Team {
    int teamID;
    String teamName;
    int points;

    public Team(int teamID, String teamName, int points){
        this.teamID = teamID;
        this.teamName = teamName;
        this.points = points;
    }

    public int getTeamID(){
        return teamID;
    }
    public String getTeamName(){
        return teamName;
    }
    public int getPoints(){
        return points;
    }
    public String toString(){
        return teamName;
    }
}
