package com.example.android.volleyballscoreboard;

import android.arch.lifecycle.ViewModel;

public class ScoreViewModel extends ViewModel {

    private int scoreLeft = 0;
    private int scoreRight = 0;
    private int setsWonLeft = 0;
    private int setsWonRight = 0;
    private int setNumber = 0;
    private String teamNameLeft;
    private String teamNameRight;
    private String initialTeamNameOnLeft;
    private String initialTeamNameOnRight;
    private int totalSetsToPlay = 0;
    private int setFinishingScore = 0;
    private int tieBreakerScore = 0;
    private int timeOffCountLeft = 0;
    private int timeOffCountRight = 0;
    private int starterTeamId = 0;
    private boolean switched;
    private boolean undoEnabled = false;
    private int setScoresOrange[] = new int[5];
    private int setScoresBlue[] = new int[5];
    private int orangeRowColors[] = new int[5];
    private int blueRowColors[] = new int[5];
    private String lastPointer = Constants.START;
    private String message;

    ////////////// Getters ///////////////////////

    public int getScoreLeft() {
        return scoreLeft;
    }

    public int getScoreRight() {
        return scoreRight;
    }

    public int getSetsWonLeft() {
        return setsWonLeft;
    }

    public int getSetsWonRight() {
        return setsWonRight;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public String getTeamNameLeft() {
        return teamNameLeft;
    }

    public String getTeamNameRight() {
        return teamNameRight;
    }

    public String getInitialTeamNameOnLeft() {
        return initialTeamNameOnLeft;
    }

    public String getInitialTeamNameOnRight() {
        return initialTeamNameOnRight;
    }

    public int getTotalSetsToPlay() {
        return totalSetsToPlay;
    }

    public int getSetFinishingScore() {
        return setFinishingScore;
    }

    public int getTieBreakerScore() {
        return tieBreakerScore;
    }

    public int getTimeOffCountLeft() {
        return timeOffCountLeft;
    }

    public int getTimeOffCountRight() {
        return timeOffCountRight;
    }

    public int getStarterTeamId() {
        return starterTeamId;
    }

    public boolean isSwitched() {
        return switched;
    }

    public String getLastPointer() {
        return lastPointer;
    }

    public String getMessage() {
        return message;
    }

    public boolean isUndoEnabled() {
        return undoEnabled;
    }

    public int[] getSetScoresOrange() {
        return setScoresOrange;
    }

    public int[] getSetScoresBlue() {
        return setScoresBlue;
    }

    public int[] getOrangeRowColors() {
        return orangeRowColors;
    }

    public int[] getBlueRowColors() {
        return blueRowColors;
    }

    /////////////// Setters //////////////////////

    public void setScoreLeft(int scoreLeft) {
        this.scoreLeft = scoreLeft;
    }

    public void setScoreRight(int scoreRight) {
        this.scoreRight = scoreRight;
    }

    public void setSetsWonLeft(int setsWonLeft) {
        this.setsWonLeft = setsWonLeft;
    }

    public void setSetsWonRight(int setsWonRight) {
        this.setsWonRight = setsWonRight;
    }

    public void setSetNumber(int setNumber) {
        this.setNumber = setNumber;
    }

    public void setTeamNameLeft(String teamNameLeft) {
        this.teamNameLeft = teamNameLeft;
    }

    public void setTeamNameRight(String teamNameRight) {
        this.teamNameRight = teamNameRight;
    }

    public void setInitialTeamNameOnLeft(String initialTeamNameOnLeft) {
        this.initialTeamNameOnLeft = initialTeamNameOnLeft;
    }

    public void setInitialTeamNameOnRight(String initialTeamNameOnRight) {
        this.initialTeamNameOnRight = initialTeamNameOnRight;
    }

    public void setTotalSetsToPlay(int totalSetsToPlay) {
        this.totalSetsToPlay = totalSetsToPlay;
    }

    public void setSetFinishingScore(int setFinishingScore) {
        this.setFinishingScore = setFinishingScore;
    }

    public void setTieBreakerScore(int tieBreakerScore) {
        this.tieBreakerScore = tieBreakerScore;
    }

    public void setTimeOffCountLeft(int timeOffCountLeft) {
        this.timeOffCountLeft = timeOffCountLeft;
    }

    public void setTimeOffCountRight(int timeOffCountRight) {
        this.timeOffCountRight = timeOffCountRight;
    }

    public void setStarterTeamId(int starterTeamId) {
        this.starterTeamId = starterTeamId;
    }

    public void setSwitched(boolean switched) {
        this.switched = switched;
    }

    public void setUndoEnabled(boolean undoEnabled) {
        this.undoEnabled = undoEnabled;
    }

    public void setSetScoresOrange(int setNumber, int setScoreOrange) {
        this.setScoresOrange[setNumber] = setScoreOrange;
    }

    public void setSetScoresBlue(int setNumber, int setScoreBlue) {
        this.setScoresBlue[setNumber] = setScoreBlue;
    }

    public void setOrangeRowColors(int setNumber, int orangeRowColor) {
        this.orangeRowColors[setNumber] = orangeRowColor;
    }

    public void setBlueRowColors(int setNumber, int blueRowColor) {
        this.blueRowColors[setNumber] = blueRowColor;
    }

    public void setLastPointer(String lastPointer) {
        this.lastPointer = lastPointer;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
