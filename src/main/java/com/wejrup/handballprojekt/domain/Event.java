package com.wejrup.handballprojekt.domain;

/**
 * *************** Event ***************
 *
 * Domæneklasse som repræsenterer en hændelse
 * under en kamp.
 *
 * Klassen bruges til at gemme information om
 * mål, udvisninger og fortrydelses-handlinger
 * samt tidspunkt og stilling på hændelsen.
 *
 */
public class Event {

    /**
     * Enum der repræsenterer de forskellige
     * typer af events der kan forekomme i en kamp.
     */
    public enum EventType {
        Goal,
        Suspension,
        GoalUndo,
        SuspensionUndo
    }

    /**
     * Enum der angiver hvilket hold eventen
     * tilhører (hjemme eller ude).
     */
    public enum TeamSide {
        Home,
        Away
    }

    // ID på kampen som eventen tilhører
    private final int matchID;

    // Typen af event (mål, udvisning osv.)
    private final EventType type;

    // Tidspunkt for eventen i sekunder siden kampstart
    private final int eventTimestampSeconds;

    // ID på holdet som eventen tilhører
    private final int teamID;

    // Angiver om eventen tilhører hjemme- eller udehold
    private final TeamSide teamSide;

    // Kampens score på tidspunktet hvor eventen blev registreret
    private final String currentScore;

    /**
     * Opretter et nyt Event-objekt.
     *
     * @param matchID id på kampen
     * @param type typen af event
     * @param eventTimestampSeconds tidspunkt i sekunder siden kampstart
     * @param teamID id på holdet
     * @param teamSide hjemme- eller udehold
     * @param currentScore stillingen på tidspunktet for eventen
     */
    public Event(int matchID, EventType type, int eventTimestampSeconds, int teamID, TeamSide teamSide, String currentScore){
        this.matchID = matchID;
        this.type = type;
        this.eventTimestampSeconds = eventTimestampSeconds;
        this.teamID = teamID;
        this.teamSide = teamSide;
        this.currentScore = currentScore;
    }

    /**
     * Returnerer tidspunktet for eventen i sekunder.
     *
     * @return antal sekunder siden kampstart
     */
    public int getEventTimestampSeconds(){
        return eventTimestampSeconds;
    }

    /**
     * Returnerer typen af event.
     *
     * @return eventtype
     */
    public EventType getType(){
        return type;
    }

    /**
     * Returnerer ID på holdet som eventen tilhører.
     *
     * @return teamID
     */
    public int getTeamID(){
        return teamID;
    }

    /**
     * Returnerer hvilken side eventen tilhører.
     *
     * @return hjemme eller ude
     */
    public TeamSide getTeamSide(){
        return teamSide;
    }

    /**
     * Returnerer kampens score på tidspunktet
     * hvor eventen blev oprettet.
     *
     * @return scorelinje som String
     */
    public String getCurrentScore(){
        return currentScore;
    }

    /**
     * Returnerer ID på kampen som eventen tilhører.
     *
     * @return matchID
     */
    public int getMatchID(){
        return matchID;
    }

    /**
     * Formatterer eventens tidspunkt til mm:ss format.
     *
     * @return formateret tid som mm:ss
     */
    public String formatTime(){
        int minutes = getEventTimestampSeconds() / 60;
        int seconds = getEventTimestampSeconds() % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Returnerer en tekst-repræsentation af eventen.
     *
     * Bruges primært til debugging og log-output.
     *
     * @return event som læsbar tekst
     */
    @Override
    public String toString() {
        return "Event type: " + getType() +
                " TimeStamp: " + getEventTimestampSeconds() +
                " Side: " + getTeamSide() +
                " Current Score: " + getCurrentScore();
    }
}
