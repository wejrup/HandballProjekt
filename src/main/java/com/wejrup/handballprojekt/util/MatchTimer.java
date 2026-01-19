package com.wejrup.handballprojekt.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * *************** MatchTimer ***************
 *
 * Utility-klasse som håndterer kampens tid.
 *
 * Klassen bruger JavaFX Timeline til at tælle
 * sekunder og giver mulighed for at registrere
 * callbacks som opdaterer UI’et løbende.
 *
 */
public class MatchTimer {

    // Kampens samlede længde i sekunder
    private final int matchLengthSeconds;

    // Aktuelt antal sekunder der er gået
    private int currentSeconds;

    // JavaFX Timeline der styrer timer-ticks
    private final Timeline timeline;

    // Callback som kaldes ved hvert tick (bruges typisk til UI-opdatering)
    private Runnable onTick;

    // Opretter en ny MatchTimer baseret på kampens længde i minutter
    public MatchTimer(int minutter){
        matchLengthSeconds = minutter * 60;
        currentSeconds = 0;

        // Initialiserer Timeline som kalder tick() hvert sekund
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> tick())
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void setOnTick(Runnable onTick) {
        this.onTick = onTick;
    }

    public int getCurrentSeconds() {
        return currentSeconds;
    }

    /** Opdaterer timeren med ét sekund
     Stopper automatisk når kampens længde er nået */
    private void tick(){

        if (currentSeconds < matchLengthSeconds){
            currentSeconds++;

            // Kalder callback hvis den er sat
            if (onTick != null){
                onTick.run();
            }
        }
        else{
            timeline.stop();
        }
    }

    // Starter timeren
    public void startTimer(){
        timeline.play();
    }

    // Pauser timeren
    public void pauseTimer(){
        timeline.pause();
    }

    // Nulstiller timeren og stopper Timeline
    public void resetTimer(){
        timeline.stop();
        currentSeconds = 0;
    }

}
