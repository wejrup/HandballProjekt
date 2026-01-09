package com.wejrup.handballprojekt;

public class Match {
    private final String homeTeamName;
    private final String awayTeamName;
    private final int homeTeamID;
    private final int awayTeamID;
    private int homeTeamGoals, awayTeamGoals;

    public Match(int homeTeamID,int awayTeamID){
        this.homeTeamID = homeTeamID;
        this.awayTeamID = awayTeamID;
        homeTeamName = getTeamNamesFromId(homeTeamID);
        awayTeamName = getTeamNamesFromId(awayTeamID);
        homeTeamGoals = 0;
        awayTeamGoals = 0;
    }

    public void addOneHomeGoal(){
        homeTeamGoals++;
        //ToDo Events.java
    }
    public void addOneAwayGoal(){
        awayTeamGoals++;
        //ToDo Events.java
    }
    public void removeOneHomeGoal(){
       if (homeTeamGoals > 0) {
           homeTeamGoals--;
       }
        //ToDo Events.java
    }
    public void removeOneAwayGoal(){
        if (awayTeamGoals > 0) {
            awayTeamGoals--;
        }
        //ToDo Events.java
    }

    public int getHomeTeamGoals(){
        return homeTeamGoals;
    }

    public int getAwayTeamGoals(){
        return awayTeamGoals;
    }

    //Hent teamNavne
    public String getTeamNamesFromId(int ID){
        return "ToDo";
    }

    public String toString() {
        return "Home Team ID: " + homeTeamID + " Home name: " + homeTeamName   + " Goals: " + homeTeamGoals  +  "\n Away team ID: " + awayTeamID + " Away name: " + awayTeamName + " Goals: " + awayTeamGoals;
    }
}




