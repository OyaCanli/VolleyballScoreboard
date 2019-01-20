package com.example.android.volleyballscoreboard;

import android.arch.lifecycle.ViewModel;
import android.databinding.adapters.AdapterViewBindingAdapter;

public class SettingsViewModel extends ViewModel {

    private String orangeTeamName;
    private String blueTeamName;
    private int starterTeamId;
    //Initialize these with default values
    private int numbersOfTotalSets = 5;
    private int setFinishingScore = 25;
    private int tieBreakerScore = 15;

    public String getOrangeTeamName() {
        return orangeTeamName;
    }

    public void setOrangeTeamName(String orangeTeamName) {
        this.orangeTeamName = orangeTeamName;
    }

    public String getBlueTeamName() {
        return blueTeamName;
    }

    public void setBlueTeamName(String blueTeamName) {
        this.blueTeamName = blueTeamName;
    }

    public int getStarterTeamId() {
        return starterTeamId;
    }

    public void setStarterTeamId(int starterTeamId) {
        this.starterTeamId = starterTeamId;
    }

    public AdapterViewBindingAdapter.OnItemSelected onItemSelected = (spinner, view, position, id) -> {
        //the method is used by all spinners.
        //Switch statement differentiate which spinners is selected
        //Then if-else statements capture user's choices
        switch (spinner.getId()) {
            case R.id.spin1: {
                if (position == 0) numbersOfTotalSets = 5;
                else numbersOfTotalSets = 3;
                break;
            }
            case R.id.spin2: {
                if (position == 0) setFinishingScore = 25;
                else setFinishingScore = 15;
                break;
            }
            case R.id.spin3: {
                if (position == 0) tieBreakerScore = 15;
                else tieBreakerScore = 25;
                break;
            }
        }
    };

    int getNumbersOfTotalSets() {
        return numbersOfTotalSets;
    }

    int getSetFinishingScore() {
        return setFinishingScore;
    }

    int getTieBreakerScore() {
        return tieBreakerScore;
    }

}
