package com.wejrup.handballprojekt;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


import static java.time.Clock.tick;

public class MatchTimer {

    private final int matchLengthSeconds;
    private int currentSeconds;
    private final Timeline timeline;
    private Runnable onTick;

    //Skult data kerne som er i sekunder mens udati er i minutter
    public MatchTimer(int minutter){
        matchLengthSeconds = minutter * 60;
        currentSeconds = 0;

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> tick())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);

    }
    private void tick(){
        if (currentSeconds < matchLengthSeconds){
            currentSeconds++;
            if (onTick != null){
                onTick.run();
            }
        }
        else{
            timeline.stop();
        }
    }

    public void startTimer(){
        timeline.play();
    }

    public void pauseTimer(){
        timeline.pause();
    }

    public void resetTimer(){
        timeline.stop();
        currentSeconds = 0;
    }

    public int getCurrentSeconds() {
        return currentSeconds;
    }

    public void setOnTick(Runnable onTick) {
        this.onTick = onTick;
    }

}
