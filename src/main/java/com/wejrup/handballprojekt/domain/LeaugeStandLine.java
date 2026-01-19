package com.wejrup.handballprojekt.domain;

/**
 * *************** LeaugeStandLine ***************
 *
 * Klasse som repræsenterer én linje i standings-
 * oversigten.
 *
 * Klassen bruges til at kombinere placering og
 * hold-data i ét objekt, så det kan vises korrekt
 * formateret i ListView.
 *
 */
public class LeaugeStandLine {

    // Holdets placering i tabellen
    private final int position;

    // Team-objektet som linjen repræsenterer
    private final Team team;

    /**
     * Opretter en ny standings-linje.
     *
     * @param position holdets placering i tabellen
     * @param team team-objektet der skal vises
     */
    public LeaugeStandLine(int position, Team team) {
        this.position = position;
        this.team = team;
    }

    /**
     * Returnerer en formatteret tekst-repræsentation
     * af standings-linjen.
     *
     * Formatet bruges direkte i ListView og er
     * opbygget så kolonner står pænt justeret.
     *
     * @return standings-linje som formateret String
     */
    @Override
    public String toString() {

        return String.format("%2d  %-30s  %4d",
                position,
                team.getTeamName(),
                team.getPoints()
        );
    }

}
