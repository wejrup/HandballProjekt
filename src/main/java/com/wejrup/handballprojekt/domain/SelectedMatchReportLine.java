package com.wejrup.handballprojekt.domain;

/**
 * *************** SelectedMatchReportLine ***************
 *
 * Klasse som repræsenterer én visningslinje
 * i den detaljerede match-rapport.
 *
 * Klassen bruges til at formatere et Event-objekt
 * til en læsbar tekstlinje som vises i ListView.
 *
 */
public class SelectedMatchReportLine {

    // Event-objektet som linjen repræsenterer
    private final Event event;

    // Opretter en ny SelectedMatchReportLine
    public SelectedMatchReportLine(Event event){
        this.event = event;
    }

    /** Formatterer antal sekunder til mm:ss format
     Bruges til visning af event-tidspunkt */
    private String formatTime(int totalSeconds){
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /** Konverterer event-typen til en læsbar tekst
     Bruges til præsentation i UI */
    private String formatEventType(){

        String eventString = "";

        switch (event.getType()){
            case Goal -> eventString = " Goal (" + event.getCurrentScore() + ")";
            case GoalUndo -> eventString = " Goal cancelled (" + event.getCurrentScore() + ")";
            case Suspension -> eventString = " Suspension";
            case SuspensionUndo -> eventString = " Suspension cancelled";
        }

        return eventString;
    }

    /** Returnerer formatteret tekst til visning i ListView
     Indeholder tid, hold-side og event-type */
    @Override
    public String toString() {
        return String.format(
                formatTime(event.getEventTimestampSeconds()) +
                        " " +
                        event.getTeamSide() +
                        formatEventType()
        );
    }

}
