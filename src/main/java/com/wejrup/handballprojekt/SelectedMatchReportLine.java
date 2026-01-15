package com.wejrup.handballprojekt;

public class SelectedMatchReportLine {
    private final Event event;

    public SelectedMatchReportLine(Event event){
        this.event = event;
    }

    private String formatTime(int totalSeconds){
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

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


    public String toString() {
        return String.format(formatTime(event.getEventTimestampSeconds()) + " " + event.getTeamSide() + formatEventType());
    }
}
