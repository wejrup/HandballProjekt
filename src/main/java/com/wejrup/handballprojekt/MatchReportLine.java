package com.wejrup.handballprojekt;

public class MatchReportLine {
    private final Match match;

    public MatchReportLine(Match match) {
        this.match = match;
    }

    public Match getMatch(){
        return match;
    }

    @Override
    public String toString() {
        return String.format("%-30s %02d\n%-30s %02d", match.getHomeTeamName(), match.getHomeTeamGoals(), match.getAwayTeamName(), match.getAwayTeamGoals());
    }
}

