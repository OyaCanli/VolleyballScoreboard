package com.example.android.volleyballscoreboard;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.volleyballscoreboard.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ScoreViewModel mViewModel;
    private int[] orangeCellIds = {R.id.s1_tA, R.id.s2_tA, R.id.s3_tA, R.id.s4_tA, R.id.s5_tA};
    private int[] blueCellIds = {R.id.s1_tB, R.id.s2_tB, R.id.s3_tB, R.id.s4_tB, R.id.s5_tB};
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //Initialize view model
        mViewModel = ViewModelProviders.of(this).get(ScoreViewModel.class);

        //set click listeners on buttons
        binding.teamLeftScore.setOnClickListener(this);
        binding.teamRightScore.setOnClickListener(this);
        binding.exchangeButton.setOnClickListener(this);
        binding.pauseLeft.setOnClickListener(this);
        binding.pauseRight.setOnClickListener(this);
        binding.undo.setOnClickListener(this);
        binding.reset.setOnClickListener(this);

        //get user choices from the previous activity and display
        Bundle userChoise = getIntent().getExtras();

        if(savedInstanceState == null){
            //Set these values in the view model
            mViewModel.setTeamNameLeft(userChoise.getString(Constants.TEAM_NAME_LEFT));
            mViewModel.setInitialTeamNameOnLeft(userChoise.getString(Constants.TEAM_NAME_LEFT));
            mViewModel.setTeamNameRight(userChoise.getString(Constants.TEAM_NAME_RIGHT));
            mViewModel.setInitialTeamNameOnRight(userChoise.getString(Constants.TEAM_NAME_RIGHT));
            mViewModel.setTotalSetsToPlay(userChoise.getInt(Constants.NUMBER_OF_TOTAL_SETS));
            mViewModel.setSetFinishingScore(userChoise.getInt(Constants.SET_FINISHING_SCORE));
            mViewModel.setTieBreakerScore(userChoise.getInt(Constants.TIE_BREAKER_SCORE));
            mViewModel.setStarterTeamId(userChoise.getInt(Constants.STARTING_TEAM));
            if (mViewModel.getStarterTeamId() == R.id.optionBlue) {
                mViewModel.setMessage(getString(R.string.serve, mViewModel.getTeamNameRight()));
            } else {
                mViewModel.setMessage(getString(R.string.serve, mViewModel.getTeamNameLeft()));
            }
        } else{
            for(int i = 0; i<5 ; i++){
                TextView orangeCell = findViewById(orangeCellIds[i]);
                orangeCell.setText(String.valueOf(mViewModel.getSetScoresOrange()[i]));
                orangeCell.setBackgroundColor(mViewModel.getOrangeRowColors()[i]);
                TextView blueCell = findViewById(blueCellIds[i]);
                blueCell.setText(String.valueOf(mViewModel.getSetScoresBlue()[i]));
                blueCell.setBackgroundColor(mViewModel.getBlueRowColors()[i]);
            }
            if(mViewModel.isUndoEnabled()) {
                binding.undo.setEnabled(true);
            }
        }

        //Display names on scoreboard and on summary table
        displayOnTableTeamA(mViewModel.getTeamNameLeft());
        displayOnTableTeamB(mViewModel.getTeamNameRight());
        displayTeamNameonLeft(mViewModel.getTeamNameLeft());
        displayTeamNameonRight(mViewModel.getTeamNameRight());
        displaySetsForTeamOnLeft(mViewModel.getSetsWonLeft());
        displaySetsForTeamOnRight(mViewModel.getSetsWonRight());
        displayMessage(mViewModel.getMessage());
        displayScoreForTeamOnLeft(mViewModel.getScoreLeft());
        displayScoreForTeamOnRight(mViewModel.getScoreRight());

        if(mViewModel.isSwitched()){
            binding.viewOrange.setBackgroundColor(getResources().getColor(R.color.orange_background));
            binding.viewBlue.setBackgroundColor(getResources().getColor(R.color.blue_background));
            binding.teamOnLeft.setBackground(this.getResources().getDrawable(R.drawable.blueborder));
            binding.teamOnRight.setBackground(this.getResources().getDrawable(R.drawable.cellborder));
        }
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.exchange_button:{
                exchangeSides();
                break;
            }
            case R.id.team_left_score:{
                add1pointToTeamOnLeft();
                break;
            }
            case R.id.team_right_score:{
                add1pointToTeamOnRight();
                break;
            }
            case R.id.pauseLeft: {
                pauseLeft();
                break;
            }
            case R.id.pauseRight: {
                pauseRight();
                break;
            }
            case R.id.undo: {
                undo();
                break;
            }

            case R.id.reset: {
                reset();
                break;
            }
        }
    }

    public void add1pointToTeamOnLeft() {
            mViewModel.setScoreLeft(mViewModel.getScoreLeft() + 1);
            displayScoreForTeamOnLeft(mViewModel.getScoreLeft());
            mViewModel.setLastPointer(Constants.TEAM_LEFT);
        binding.undo.setEnabled(true);
            mViewModel.setUndoEnabled(true);
            //same score is shown in the score summary table as well.
            //if sides are switched blue team is on the left
            //scores should be added in the correct row in the table accordingly
            if (!mViewModel.isSwitched()) {
                mViewModel.setSetScoresOrange(mViewModel.getSetNumber(), mViewModel.getScoreLeft());
                TextView scoreTable = findViewById(orangeCellIds[mViewModel.getSetNumber()]);
                scoreTable.setText(String.valueOf(mViewModel.getSetScoresOrange()[mViewModel.getSetNumber()]));
            } else {
                mViewModel.setSetScoresBlue(mViewModel.getSetNumber(), mViewModel.getScoreLeft());
                TextView scoreTable = findViewById(blueCellIds[mViewModel.getSetNumber()]);
                scoreTable.setText(String.valueOf(mViewModel.getSetScoresBlue()[mViewModel.getSetNumber()]));
            }

            if (mViewModel.getSetNumber() < mViewModel.getTotalSetsToPlay()) {
                if ((mViewModel.getScoreLeft() >= mViewModel.getSetFinishingScore()) && ((mViewModel.getScoreLeft() - mViewModel.getScoreRight()) >= 2)) {
                    //if it is a set winning point..
                    if (!mViewModel.isSwitched()){
                        TextView scoreTable = findViewById(orangeCellIds[mViewModel.getSetNumber()]);
                        scoreTable.setBackgroundColor(Color.YELLOW);
                        mViewModel.setOrangeRowColors(mViewModel.getSetNumber(), Color.YELLOW);
                        mViewModel.setBlueRowColors(mViewModel.getSetNumber(), Color.TRANSPARENT);
                    } else {
                        TextView scoreTable = findViewById(blueCellIds[mViewModel.getSetNumber()]);
                        scoreTable.setBackgroundColor(Color.YELLOW);
                        mViewModel.setBlueRowColors(mViewModel.getSetNumber(), Color.YELLOW);
                        mViewModel.setOrangeRowColors(mViewModel.getSetNumber(), Color.TRANSPARENT);
                    }
                    mViewModel.setSetsWonLeft(mViewModel.getSetsWonLeft() + 1);
                    displaySetsForTeamOnLeft(mViewModel.getSetsWonLeft());
                    if (mViewModel.getSetsWonLeft() == (mViewModel.getTotalSetsToPlay()+1)/2) { //if it a match winning set
                        mViewModel.setMessage(getString(R.string.wonMatch,  mViewModel.getTeamNameLeft()));
                        displayMessage(mViewModel.getMessage());
                    } else { //if it is not a match winning set
                        mViewModel.setMessage(getString(R.string.wonSet, mViewModel.getTeamNameLeft()));
                        displayMessage(mViewModel.getMessage());
                        mViewModel.setSetNumber(mViewModel.getSetNumber() + 1);
                        mViewModel.setScoreLeft(0);
                        mViewModel.setScoreRight(0);
                        displayScoreForTeamOnLeft(0);
                        displayScoreForTeamOnRight(0);
                    }
                } else { //if it is not a set winning point
                    mViewModel.setMessage(getString(R.string.serve, mViewModel.getTeamNameLeft()));
                    displayMessage(mViewModel.getMessage());
                }
            } else { // if it is the tiebreaker set
                if ((mViewModel.getScoreLeft() >= mViewModel.getTieBreakerScore()) && ((mViewModel.getScoreLeft() - mViewModel.getScoreRight()) >= 2)) {
                    mViewModel.setMessage(getString(R.string.wonMatch, mViewModel.getTeamNameLeft()));
                    displayMessage(mViewModel.getMessage());
                    mViewModel.setSetsWonLeft(mViewModel.getSetsWonLeft() + 1);
                    displaySetsForTeamOnLeft(mViewModel.getSetsWonLeft());
                    mViewModel.setScoreLeft(0);
                    mViewModel.setScoreRight(0);
                    displayScoreForTeamOnLeft(0);
                    displayScoreForTeamOnRight(0);
                }
            }
        }

    public void add1pointToTeamOnRight() {
            mViewModel.setScoreRight(mViewModel.getScoreRight() + 1);
            displayScoreForTeamOnRight(mViewModel.getScoreRight());
            mViewModel.setLastPointer(Constants.TEAM_RIGHT);
        binding.undo.setEnabled(true);
            mViewModel.setUndoEnabled(true);
        //same score is shown in the score summary table as well.
        //if sides are switched blue team is on the left
        //scores should be added in the correct row in the table accordingly
            if (!mViewModel.isSwitched()) {
                mViewModel.setSetScoresBlue(mViewModel.getSetNumber(), mViewModel.getScoreRight());
                TextView scoreTable = findViewById(blueCellIds[mViewModel.getSetNumber()]);
                scoreTable.setText(String.valueOf(mViewModel.getSetScoresBlue()[mViewModel.getSetNumber()]));
            } else {
                mViewModel.setSetScoresOrange(mViewModel.getSetNumber(), mViewModel.getScoreRight());
                TextView scoreTable = findViewById(orangeCellIds[mViewModel.getSetNumber()]);
                scoreTable.setText(String.valueOf(mViewModel.getSetScoresOrange()[mViewModel.getSetNumber()]));
            }
            if (mViewModel.getSetNumber() < mViewModel.getTotalSetsToPlay()) {
                if ((mViewModel.getScoreRight() >= mViewModel.getSetFinishingScore()) && ((mViewModel.getScoreRight() - mViewModel.getScoreLeft()) >= 2)) {
                    //if it is a set winning point..
                    if (!mViewModel.isSwitched()){ //corresponding cell in the table returns yellow
                        TextView scoreTable = findViewById(blueCellIds[mViewModel.getSetNumber()]);
                        scoreTable.setBackgroundColor(Color.YELLOW);
                        //keep track of cell colors for redrawing them after rotation
                        mViewModel.setBlueRowColors(mViewModel.getSetNumber(), Color.YELLOW);
                        mViewModel.setOrangeRowColors(mViewModel.getSetNumber(), Color.TRANSPARENT);
                    } else { //if sides are switched, the cell in the other row turns yellow.
                        TextView scoreTable = findViewById(orangeCellIds[mViewModel.getSetNumber()]);
                        scoreTable.setBackgroundColor(Color.YELLOW);
                        mViewModel.setOrangeRowColors(mViewModel.getSetNumber(), Color.YELLOW);
                        mViewModel.setBlueRowColors(mViewModel.getSetNumber(), Color.TRANSPARENT);
                    }
                    mViewModel.setSetsWonRight(mViewModel.getSetsWonRight() + 1);
                    displaySetsForTeamOnRight(mViewModel.getSetsWonRight());
                    if (mViewModel.getSetsWonRight() == (mViewModel.getTotalSetsToPlay()+1)/2) { //if it a match winning set
                        mViewModel.setMessage(getString(R.string.wonMatch, mViewModel.getTeamNameRight()));
                        displayMessage(mViewModel.getMessage());
                    } else { //if it is not a match winning set
                        mViewModel.setMessage(getString(R.string.wonSet, mViewModel.getTeamNameRight()));
                        displayMessage(mViewModel.getMessage());
                        mViewModel.setSetNumber(mViewModel.getSetNumber() + 1);
                        mViewModel.setScoreLeft(0);
                        mViewModel.setScoreRight(0);
                        displayScoreForTeamOnLeft(0);
                        displayScoreForTeamOnRight(0);
                    }
                } else { //if it is not a set winning point
                    mViewModel.setMessage(getString(R.string.serve, mViewModel.getTeamNameRight()));
                    displayMessage(mViewModel.getMessage());
                }
            } else { // if it is the fifth set; it will switch to a tie break: the final set will be up to 15 points.
                if ((mViewModel.getScoreRight() >= mViewModel.getTieBreakerScore()) && ((mViewModel.getScoreRight() - mViewModel.getScoreLeft()) >= 2)) {
                    mViewModel.setMessage(getString(R.string.wonMatch, mViewModel.getTeamNameRight()));
                    displayMessage(mViewModel.getMessage());
                    mViewModel.setSetsWonRight(mViewModel.getSetsWonRight() + 1);
                    displaySetsForTeamOnRight(mViewModel.getSetsWonRight());
                    mViewModel.setScoreLeft(0);
                    mViewModel.setScoreRight(0);
                    displayScoreForTeamOnLeft(0);
                    displayScoreForTeamOnRight(0);
                }
            }
    }

    //This method exchange sides up on users click on the exchange button
    public void exchangeSides() {
        if ((mViewModel.getScoreLeft() == 0) && (mViewModel.getScoreRight() == 0)) {
            mViewModel.setSwitched(mViewModel.isSwitched() ^ true);
            if(mViewModel.getLastPointer().equals(Constants.TEAM_LEFT)){
                mViewModel.setLastPointer(Constants.TEAM_RIGHT);
            } else{
                mViewModel.setLastPointer(Constants.TEAM_LEFT);
            }
            int temporary = mViewModel.getSetsWonLeft();
            mViewModel.setSetsWonLeft(mViewModel.getSetsWonRight());
            mViewModel.setSetsWonRight(temporary);
            displaySetsForTeamOnLeft(mViewModel.getSetsWonLeft());
            displaySetsForTeamOnRight(mViewModel.getSetsWonRight());
            int temp = mViewModel.getTimeOffCountLeft();
            mViewModel.setTimeOffCountLeft(mViewModel.getTimeOffCountRight());
            mViewModel.setTimeOffCountRight(temp);
            if (mViewModel.getTeamNameLeft().equals(mViewModel.getInitialTeamNameOnLeft())) {
                mViewModel.setTeamNameLeft(mViewModel.getInitialTeamNameOnRight());
                displayTeamNameonLeft(mViewModel.getTeamNameLeft());
                mViewModel.setTeamNameRight(mViewModel.getInitialTeamNameOnLeft());
                displayTeamNameonRight(mViewModel.getTeamNameRight());
                binding.viewOrange.setBackgroundColor(getResources().getColor(R.color.blue_background));
                binding.viewBlue.setBackgroundColor(getResources().getColor(R.color.orange_background));
                binding.teamOnLeft.setBackground(this.getResources().getDrawable(R.drawable.blueborder));
                binding.teamOnRight.setBackground(this.getResources().getDrawable(R.drawable.cellborder));
            } else {
                mViewModel.setTeamNameLeft(mViewModel.getInitialTeamNameOnLeft());
                displayTeamNameonLeft(mViewModel.getTeamNameLeft());
                mViewModel.setTeamNameRight(mViewModel.getInitialTeamNameOnRight());
                displayTeamNameonRight(mViewModel.getTeamNameRight() );
                binding.viewOrange.setBackgroundColor(getResources().getColor(R.color.orange_background));
                binding.viewBlue.setBackgroundColor(getResources().getColor(R.color.blue_background));
                binding.teamOnLeft.setBackground(this.getResources().getDrawable(R.drawable.cellborder));
                binding.teamOnRight.setBackground(this.getResources().getDrawable(R.drawable.blueborder));
            }
        } else {
            Toast.makeText(this, R.string.exchange_only_between_sets, Toast.LENGTH_SHORT).show();
        }
    }

    public void pauseLeft() {
        if (mViewModel.getTimeOffCountLeft() == 2) {
            Toast.makeText(this, R.string.all_timeoffs_used, Toast.LENGTH_SHORT).show();
            return;
        }
        openCountDownTimer();
        mViewModel.setTimeOffCountLeft(mViewModel.getTimeOffCountLeft() + 1);
        int rest = 2 - mViewModel.getTimeOffCountLeft();
        String message = getString(R.string.used_timeoffs,mViewModel.getTimeOffCountLeft(), rest);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void pauseRight() {
        if (mViewModel.getTimeOffCountRight() == 2) {
            Toast.makeText(this, R.string.all_timeoffs_used, Toast.LENGTH_SHORT).show();
            return;
        }
        openCountDownTimer();
        mViewModel.setTimeOffCountRight(mViewModel.getTimeOffCountRight() + 1);
        int rest = 2 -mViewModel.getTimeOffCountRight();
        String message = getString(R.string.used_timeoffs,mViewModel.getTimeOffCountRight(), rest);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void openCountDownTimer(){
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Time left:");
        intent.putExtra(AlarmClock.EXTRA_LENGTH, 30);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void undo() {
        switch(mViewModel.getLastPointer()){
            case Constants.START:{
                //Do nothing
                break;
            }
            case Constants.TEAM_LEFT:{
                correctionLeft();
                binding.undo.setEnabled(false);
                mViewModel.setUndoEnabled(false);
                break;
            }
            case Constants.TEAM_RIGHT:{
                correctionRight();
                binding.undo.setEnabled(false);
                mViewModel.setUndoEnabled(false);
                break;
            }
        }
    }

    //This is for correcting a point mistakenly given.
    public void correctionLeft() {
        mViewModel.setMessage(getString(R.string.error));
        displayMessage(mViewModel.getMessage());
        if (mViewModel.getScoreLeft() > 0) {
            mViewModel.setScoreLeft(mViewModel.getScoreLeft() - 1);
            displayScoreForTeamOnLeft(mViewModel.getScoreLeft());
        } else if(mViewModel.getSetNumber() > 0) {
            mViewModel.setSetNumber(mViewModel.getSetNumber() - 1);
            mViewModel.setSetsWonLeft(mViewModel.getSetsWonLeft() - 1);
            displaySetsForTeamOnLeft(mViewModel.getSetsWonLeft());
            if(!mViewModel.isSwitched()){
                mViewModel.setScoreLeft(mViewModel.getSetScoresOrange()[mViewModel.getSetNumber()] - 1);
                mViewModel.setScoreRight(mViewModel.getSetScoresBlue()[mViewModel.getSetNumber()]);
            } else {
                mViewModel.setScoreLeft(mViewModel.getSetScoresBlue()[mViewModel.getSetNumber()] - 1);
                mViewModel.setScoreRight(mViewModel.getSetScoresOrange()[mViewModel.getSetNumber()]);
            }
            displayScoreForTeamOnRight(mViewModel.getScoreRight());
        }
        TextView scoreTable;
        if(!mViewModel.isSwitched()){
            scoreTable = findViewById(orangeCellIds[mViewModel.getSetNumber()]);
            mViewModel.setSetScoresBlue(mViewModel.getSetNumber(), mViewModel.getScoreRight());
            mViewModel.setSetScoresOrange(mViewModel.getSetNumber(), mViewModel.getScoreLeft());
            mViewModel.setOrangeRowColors(mViewModel.getSetNumber(), Color.TRANSPARENT);
        }
        else{
            scoreTable = findViewById(blueCellIds[mViewModel.getSetNumber()]);
            mViewModel.setSetScoresBlue(mViewModel.getSetNumber(), mViewModel.getScoreLeft());
            mViewModel.setSetScoresOrange(mViewModel.getSetNumber(), mViewModel.getScoreRight());
            mViewModel.setBlueRowColors(mViewModel.getSetNumber(), Color.TRANSPARENT);
        }
        scoreTable.setText(String.valueOf(mViewModel.getScoreLeft()));
        scoreTable.setBackgroundColor(Color.TRANSPARENT);
        displayScoreForTeamOnLeft(mViewModel.getScoreLeft());
    }

    //This is for correcting a point mistakenly given.
    public void correctionRight() {
        if (mViewModel.getScoreRight() > 0) {
            mViewModel.setScoreRight(mViewModel.getScoreRight() - 1);
            displayScoreForTeamOnRight(mViewModel.getScoreRight());
            mViewModel.setMessage( getString(R.string.error));
            displayMessage(mViewModel.getMessage());
        } else if(mViewModel.getSetNumber() > 1) {
            mViewModel.setSetNumber(mViewModel.getSetNumber() - 1);
            mViewModel.setSetsWonRight(mViewModel.getSetsWonRight() - 1);
            displaySetsForTeamOnRight(mViewModel.getSetsWonRight());
            if(!mViewModel.isSwitched()){
                mViewModel.setScoreLeft(mViewModel.getSetScoresOrange()[mViewModel.getSetNumber()]);
                mViewModel.setScoreRight(mViewModel.getSetScoresBlue()[mViewModel.getSetNumber()] - 1);
            } else {
                mViewModel.setScoreLeft(mViewModel.getSetScoresBlue()[mViewModel.getSetNumber()]);
                mViewModel.setScoreRight(mViewModel.getSetScoresOrange()[mViewModel.getSetNumber()] - 1);
            }
            displayScoreForTeamOnLeft(mViewModel.getScoreLeft());
        }
        displayScoreForTeamOnRight(mViewModel.getScoreRight());
        TextView scoreTable;
        if(!mViewModel.isSwitched()){
            scoreTable = findViewById(blueCellIds[mViewModel.getSetNumber()]);
            mViewModel.setSetScoresBlue(mViewModel.getSetNumber(), mViewModel.getScoreRight());
            mViewModel.setSetScoresOrange(mViewModel.getSetNumber(), mViewModel.getScoreLeft());
            mViewModel.setBlueRowColors(mViewModel.getSetNumber(), Color.TRANSPARENT);
        }
        else{
            scoreTable = findViewById(orangeCellIds[mViewModel.getSetNumber()]);
            mViewModel.setSetScoresBlue(mViewModel.getSetNumber(), mViewModel.getScoreLeft());
            mViewModel.setSetScoresOrange(mViewModel.getSetNumber(), mViewModel.getScoreRight());
            mViewModel.setOrangeRowColors(mViewModel.getSetNumber(), Color.TRANSPARENT);
        }
        scoreTable.setText(String.valueOf(mViewModel.getScoreRight()));
        scoreTable.setBackgroundColor(Color.TRANSPARENT);

    }

    public void displayScoreForTeamOnLeft(int score) {
        binding.teamLeftScore.setText(String.valueOf(score));
    }

    public void displayScoreForTeamOnRight(int score) {
        binding.teamRightScore.setText(String.valueOf(score));
    }

    public void displaySetsForTeamOnLeft(int score) {
        binding.setsForTeamOnLeft.setText(String.valueOf(score));
    }

    public void displaySetsForTeamOnRight(int score) {
        binding.setsForTeamOnRight.setText(String.valueOf(score));
    }

    public void displayTeamNameonLeft(String message) {
        binding.teamOnLeft.setText(message);
    }

    public void displayTeamNameonRight(String message) {
        binding.teamOnRight.setText(message);
    }

    public void displayMessage(String message) {
        binding.message.setText(message);
    }

    public void displayOnTableTeamA(String message) {
        binding.teamA.setText(message);
    }

    public void displayOnTableTeamB(String message) {
        binding.teamB.setText(message);
    }

    public void reset(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
        builder.setMessage(R.string.reset_warning);
        builder.setPositiveButton(R.string.reset, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        builder.setNeutralButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                finish();
                startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.create();
        builder.show();

    }
}
