package com.example.android.volleyballscoreboard;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

public class MainViewModel extends ViewModel {

    public final ObservableInt scoreOranges = new ObservableInt(0);
    public final ObservableInt scoreBlues = new ObservableInt(0);
    public final ObservableInt setsWonOrange = new ObservableInt(0);
    public final ObservableInt setsWonBlue = new ObservableInt(0);
    public final ObservableInt setNumber = new ObservableInt(0);
    public final ObservableBoolean isSwitched = new ObservableBoolean(false);
    public final ObservableBoolean undoEnabled = new ObservableBoolean(false);
    public final ObservableArrayList<Integer> setScoresOrange = new ObservableArrayList<>();
    public final ObservableArrayList<Integer> setScoresBlue = new ObservableArrayList<>();
    public final ObservableField<String> message = new ObservableField<>();

    private String teamNameForOrange = "Oranges";
    private String teamNameForBlue = "Blues";
    private int totalSetsToPlay = 0;
    private int setFinishingScore = 0;
    private int tieBreakerScore = 0;
    private int starterTeamId = 0;
    private String lastPointer = Constants.START;
    private int timeOffCountOrange = 0;
    private int timeOffCountBlue = 0;

    public MainViewModel() {
        //Initialize lists with Os
        for (int i = 0; i < 5; i++) {
            setScoresOrange.add(0);
            setScoresBlue.add(0);
        }
    }

    ////////////// Getters ///////////////////////

    public String getTeamNameForOrange() {
        return teamNameForOrange;
    }

    public String getTeamNameForBlue() {
        return teamNameForBlue;
    }

    int getTotalSetsToPlay() {
        return totalSetsToPlay;
    }

    int getStarterTeamId() {
        return starterTeamId;
    }

    String getLastPointer() {
        return lastPointer;
    }

    public int getTimeOffCountOrange() {
        return timeOffCountOrange;
    }

    public int getTimeOffCountBlue() {
        return timeOffCountBlue;
    }

    int getSetFinishingScoreForCurrentSet() {
        return setNumber.get() < totalSetsToPlay ? setFinishingScore : tieBreakerScore;
    }

    public int getTieBreakerScore() {
        return tieBreakerScore;
    }

    /////////////// Setters //////////////////////

    public void setTeamNameForOrange(String teamNameForOrange) {
        this.teamNameForOrange = teamNameForOrange;
    }

    public void setTeamNameForBlue(String teamNameForBlue) {
        this.teamNameForBlue = teamNameForBlue;
    }

    void setTotalSetsToPlay(int totalSetsToPlay) {
        this.totalSetsToPlay = totalSetsToPlay;
    }

    public void setSetFinishingScore(int setFinishingScore) {
        this.setFinishingScore = setFinishingScore;
    }

    public void setTieBreakerScore(int tieBreakerScore) {
        this.tieBreakerScore = tieBreakerScore;
    }

    public void setStarterTeamId(int starterTeamId) {
        this.starterTeamId = starterTeamId;
    }

    public void setLastPointer(String lastPointer) {
        this.lastPointer = lastPointer;
    }

    public void setTimeOffCountOrange(int timeOffCountOrange) {
        this.timeOffCountOrange = timeOffCountOrange;
    }

    public void setTimeOffCountBlue(int timeOffCountBlue) {
        this.timeOffCountBlue = timeOffCountBlue;
    }

}
