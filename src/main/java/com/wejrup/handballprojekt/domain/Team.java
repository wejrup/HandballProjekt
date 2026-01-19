package com.wejrup.handballprojekt.domain;

/**
 * *************** Team ***************
 *
 * Domæneklasse som repræsenterer et håndboldhold.
 *
 * Klassen indeholder grundlæggende information
 * om holdets identitet og aktuelle point.
 *
 */
public class Team {

    // Unikt ID for holdet i databasen
    private final int teamID;

    // Navnet på holdet
    private final String teamName;

    // Holdets aktuelle point
    private int points;

    // Opretter et nyt Team-objekt
    public Team(int teamID, String teamName, int points){
        this.teamID = teamID;
        this.teamName = teamName;
        this.points = points;
    }

    public void setPoints(int points){
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

    // Returnerer holdets navn ved visning i UI-komponenter (fx ChoiceBox)
    @Override
    public String toString(){
        return teamName;
    }

}
