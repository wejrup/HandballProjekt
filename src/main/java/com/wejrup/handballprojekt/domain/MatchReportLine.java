package com.wejrup.handballprojekt.domain;

/**
 * *************** MatchReportLine ***************
 *
 * Klasse som repræsenterer én visningslinje
 * i match-rapport oversigten.
 *
 * Klassen bruges til at formatere kampdata
 * (holdnavne og mål) til et tekstlayout der
 * vises direkte i ListView.
 *
 */
public class MatchReportLine {

    // Match-objektet som linjen repræsenterer
    private final Match match;

    // Opretter en ny match-rapport linje
    public MatchReportLine(Match match) {
        this.match = match;
    }

    public Match getMatch(){
        return match;
    }

    /** Returnerer formatteret tekst til visning i ListView
    Layoutet er opsat så hjemme- og udehold vises på hver sin linje */
    @Override
    public String toString() {
        return String.format(
                "%-30s %02d\n%-30s %02d",
                match.getHomeTeamName(),
                match.getHomeTeamGoals(),
                match.getAwayTeamName(),
                match.getAwayTeamGoals()
        );
    }

}
