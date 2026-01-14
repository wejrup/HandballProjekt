package com.wejrup.handballprojekt;

public class LeaugeStandLine {
    private final int position;
    private final Team team;

    public LeaugeStandLine(int position, Team team) {
        this.position = position;
        this.team = team;
    }

    @Override
    public String toString() {

        return String.format("%2d  %-30s  %4d",
                position,
                team.getTeamName(),
                team.getPoints()
        );
    }
}
