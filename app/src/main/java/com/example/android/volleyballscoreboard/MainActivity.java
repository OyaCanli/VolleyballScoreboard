package com.example.android.volleyballscoreboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int scoreLeft, scoreRight, setsWonLeft, setsWonRight, setNumber;
    String teamNameLeft, teamNameRight, initialTeamNameOnLeft, initialTeamNameOnRight, lastPointer;
    String message;
    boolean switched, undoEnabled;
    int totalSetsToPlay, setFinishingScore, tieBreakerScore;
    int timeOffCountLeft, timeOffCountRight, starter_team_id;
    Button scoreLeftButton, scoreRightButton, pauseLeftButton, pauseRightButton;
    Button undoButton, resetButton, exchangeButton;
    int[] orangeCellIds = {R.id.s1_tA, R.id.s2_tA, R.id.s3_tA, R.id.s4_tA, R.id.s5_tA};
    int[] blueCellIds = {R.id.s1_tB, R.id.s2_tB, R.id.s3_tB, R.id.s4_tB, R.id.s5_tB};
    int setScoresOrange[] = new int[5];
    int setScoresBlue[] = new int[5];
    int orangeRowColors[] = new int[5];
    int blueRowColors[] = new int[5];
    public final static String SCORE_LEFT = "scoreLeft";
    public final static String SCORE_RIGHT = "scoreRight";
    public final static String SETS_WON_LEFT = "setsWonLeft";
    public final static String SETS_WON_RIGHT = "setsWonRight";
    public final static String SET_NUMBER = "setNumber";
    public final static String MESSAGE = "message";
    public final static String SET_SCORES_ORANGE = "SetScoresOrange";
    public final static String SET_SCORES_BLUE = "SetScoresBlue";
    public final static String ORANGE_ROW_COLORS = "OrangeRowColors";
    public final static String BLUE_ROW_COLORS = "BlueRowColors";
    public final static String TEAM_NAME_LEFT = "teamNameLeft";
    public final static String TEAM_NAME_RIGHT = "teamNameRight";
    public final static String INITIAL_NAME_LEFT = "initialNameLeft";
    public final static String INITIAL_NAME_RIGHT= "initialNameRight";
    public final static String SWITCHED = "switched";
    public final static String TIMEOFF_COUNT_LEFT = "timeoffCountLeft";
    public final static String TIMEOFF_COUNT_RIGHT = "timeoffCountRight";
    public final static String STARTING_TEAM = "startingTeam";
    public final static String NUMBER_OF_TOTAL_SETS = "numberOfTotalSets";
    public final static String SET_FINISHING_SCORE = "setFinishingScore";
    public final static String TIE_BREAKER_SCORE = "tieBreakerScore";
    public final static String LAST_POINTER = "LastPointer";
    public final static String UNDO_ENABLED = "undoEnabled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get user choices from the previous activity and display
        Bundle userChoise = getIntent().getExtras();
        initialTeamNameOnLeft = teamNameLeft = userChoise.getString(TEAM_NAME_LEFT);
        initialTeamNameOnRight = teamNameRight = userChoise.getString(TEAM_NAME_RIGHT);
        displayOnTableTeamA(teamNameLeft);
        displayTeamNameonLeft(teamNameLeft);
        displayOnTableTeamB(teamNameRight);
        displayTeamNameonRight(teamNameRight);
        totalSetsToPlay = userChoise.getInt(NUMBER_OF_TOTAL_SETS);
        setFinishingScore = userChoise.getInt(SET_FINISHING_SCORE);
        tieBreakerScore = userChoise.getInt(TIE_BREAKER_SCORE);
        starter_team_id = userChoise.getInt(STARTING_TEAM);
        if (starter_team_id == R.id.optionBlue) {
            message = teamNameRight + " " + getString(R.string.serve);
            displayMessage(message);
            starter_team_id = R.id.optionBlue;
        } else {
            message = teamNameLeft + " " + getString(R.string.serve);
            displayMessage(message);
            starter_team_id = R.id.optionOrange;
        }
        //initialize buttons
        scoreLeftButton = findViewById(R.id.team_left_score);
        scoreRightButton = findViewById(R.id.team_right_score);
        exchangeButton = findViewById(R.id.exchange_button);
        pauseLeftButton = findViewById(R.id.pauseLeft);
        pauseRightButton = findViewById(R.id.pauseRight);
        undoButton = findViewById(R.id.undo);
        resetButton = findViewById(R.id.reset);
        //set click listeners on buttons
        scoreLeftButton.setOnClickListener(this);
        scoreRightButton.setOnClickListener(this);
        exchangeButton.setOnClickListener(this);
        pauseLeftButton.setOnClickListener(this);
        pauseRightButton.setOnClickListener(this);
        undoButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        lastPointer = "Start";
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

    //This methods saves an instance of the variable values during rotation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SCORE_LEFT, scoreLeft);
        outState.putInt(SCORE_RIGHT, scoreRight);
        outState.putInt(SETS_WON_LEFT, setsWonLeft);
        outState.putInt(SETS_WON_RIGHT, setsWonRight);
        outState.putInt(SET_NUMBER, setNumber);
        outState.putString(TEAM_NAME_LEFT, teamNameLeft);
        outState.putString(TEAM_NAME_RIGHT, teamNameRight);
        outState.putString(INITIAL_NAME_LEFT, initialTeamNameOnLeft);
        outState.putString(INITIAL_NAME_RIGHT, initialTeamNameOnRight);
        outState.putString(MESSAGE, message);
        outState.putString(LAST_POINTER, lastPointer);
        outState.putBoolean(SWITCHED, switched);
        outState.putBoolean(UNDO_ENABLED, undoEnabled);
        outState.putInt(TIMEOFF_COUNT_LEFT, timeOffCountLeft);
        outState.putInt(TIMEOFF_COUNT_RIGHT, timeOffCountRight);
        outState.putIntArray(SET_SCORES_ORANGE, setScoresOrange);
        outState.putIntArray(SET_SCORES_BLUE, setScoresBlue);
        outState.putIntArray(ORANGE_ROW_COLORS, orangeRowColors);
        outState.putIntArray(BLUE_ROW_COLORS, blueRowColors);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        scoreLeft = savedInstanceState.getInt(SCORE_LEFT);
        scoreRight = savedInstanceState.getInt(SCORE_RIGHT);
        setsWonLeft = savedInstanceState.getInt(SETS_WON_LEFT);
        setsWonRight = savedInstanceState.getInt(SETS_WON_RIGHT);
        setNumber = savedInstanceState.getInt(SET_NUMBER);
        message = savedInstanceState.getString(MESSAGE);
        teamNameLeft = savedInstanceState.getString(TEAM_NAME_LEFT);
        teamNameRight = savedInstanceState.getString(TEAM_NAME_RIGHT);
        initialTeamNameOnLeft = savedInstanceState.getString(INITIAL_NAME_LEFT);
        initialTeamNameOnRight = savedInstanceState.getString(INITIAL_NAME_RIGHT);
        lastPointer = savedInstanceState.getString(LAST_POINTER);
        undoEnabled = savedInstanceState.getBoolean(UNDO_ENABLED);
        if(undoEnabled) undoButton.setEnabled(true);
        setNumber = savedInstanceState.getInt(SET_NUMBER);
        displayScoreForTeamOnLeft(scoreLeft);
        displayScoreForTeamOnRight(scoreRight);
        displaySetsForTeamOnLeft(setsWonLeft);
        displaySetsForTeamOnRight(setsWonRight);
        displayMessage(message);
        displayTeamNameonLeft(teamNameLeft);
        displayTeamNameonRight(teamNameRight);
        //if the sides were switched before rotation, switch them again
        switched = savedInstanceState.getBoolean(SWITCHED);
        if (switched) {
            View view1 = findViewById(R.id.viewOrange);
            view1.setBackgroundColor(getResources().getColor(R.color.orange_background));
            View view2 = findViewById(R.id.viewBlue);
            view2.setBackgroundColor(getResources().getColor(R.color.blue_background));
            TextView tw1 = findViewById(R.id.team_on_left);
            tw1.setBackground(this.getResources().getDrawable(R.drawable.blueborder));
            TextView tw2 = findViewById(R.id.team_on_right);
            tw2.setBackground(this.getResources().getDrawable(R.drawable.cellborder));
        }
        setScoresOrange = savedInstanceState.getIntArray(SET_SCORES_ORANGE);
        setScoresBlue = savedInstanceState.getIntArray(SET_SCORES_BLUE);
        orangeRowColors = savedInstanceState.getIntArray(ORANGE_ROW_COLORS);
        blueRowColors = savedInstanceState.getIntArray(BLUE_ROW_COLORS);
        //Rewrite the corresponding values in the table
        for(int i = 0; i<5 ; i++){
            TextView orangeCell = findViewById(orangeCellIds[i]);
            orangeCell.setText(String.valueOf(setScoresOrange[i]));
            orangeCell.setBackgroundColor(orangeRowColors[i]);
            TextView blueCell = findViewById(blueCellIds[i]);
            blueCell.setText(String.valueOf(setScoresBlue[i]));
            blueCell.setBackgroundColor(blueRowColors[i]);
        }
    }

    public void add1pointToTeamOnLeft() {
            scoreLeft++;
            displayScoreForTeamOnLeft(scoreLeft);
            lastPointer = "TeamLeft";
            undoButton.setEnabled(true);
            undoEnabled = true;
            //same score is shown in the score summary table as well.
            //if sides are switched blue team is on the left
            //scores should be added in the correct row in the table accordingly
            if (!switched) {
                setScoresOrange[setNumber] = scoreLeft;
                TextView scoreTable = findViewById(orangeCellIds[setNumber]);
                scoreTable.setText(String.valueOf(setScoresOrange[setNumber]));
            } else {
                setScoresBlue[setNumber] = scoreLeft;
                TextView scoreTable = findViewById(blueCellIds[setNumber]);
                scoreTable.setText(String.valueOf(setScoresBlue[setNumber]));
            }

            if (setNumber < totalSetsToPlay) {
                if ((scoreLeft >= setFinishingScore) && ((scoreLeft - scoreRight) >= 2)) {
                    //if it is a set winning point..
                    if (!switched){
                        TextView scoreTable = findViewById(orangeCellIds[setNumber]);
                        scoreTable.setBackgroundColor(Color.YELLOW);
                        orangeRowColors[setNumber] = Color.YELLOW;
                        blueRowColors[setNumber] = Color.TRANSPARENT;
                    } else {
                        TextView scoreTable = findViewById(blueCellIds[setNumber]);
                        scoreTable.setBackgroundColor(Color.YELLOW);
                        blueRowColors[setNumber] = Color.YELLOW;
                        orangeRowColors[setNumber] = Color.TRANSPARENT;
                    }
                    setsWonLeft++;
                    displaySetsForTeamOnLeft(setsWonLeft);
                    if (setsWonLeft == (totalSetsToPlay+1)/2) { //if it a match winning set
                        message = teamNameLeft + " " + getString(R.string.wonMatch);
                        displayMessage(message);
                    } else { //if it is not a match winning set
                        message = teamNameLeft + " " + getString(R.string.wonSet);
                        displayMessage(message);
                        setNumber++;
                        scoreLeft = 0;
                        scoreRight = 0;
                        displayScoreForTeamOnLeft(scoreLeft);
                        displayScoreForTeamOnRight(scoreRight);
                    }
                } else { //if it is not a set winning point
                    message = teamNameLeft + " " + getString(R.string.serve);
                    displayMessage(message);
                }
            } else { // if it is the tiebreaker set
                if ((scoreLeft >= tieBreakerScore) && ((scoreLeft - scoreRight) >= 2)) {
                    message = teamNameLeft + " " + getString(R.string.wonMatch);
                    displayMessage(message);
                    setsWonLeft++;
                    displaySetsForTeamOnLeft(setsWonLeft);
                    scoreLeft = 0;
                    scoreRight = 0;
                    displayScoreForTeamOnLeft(scoreLeft);
                    displayScoreForTeamOnRight(scoreRight);
                }
            }
        }

    public void add1pointToTeamOnRight() {
            scoreRight++;
            displayScoreForTeamOnRight(scoreRight);
            lastPointer = "TeamRight";
            undoButton.setEnabled(true);
            undoEnabled = true;
        //same score is shown in the score summary table as well.
        //if sides are switched blue team is on the left
        //scores should be added in the correct row in the table accordingly
            if (!switched) {
                setScoresBlue[setNumber] = scoreRight;
                TextView scoreTable = findViewById(blueCellIds[setNumber]);
                scoreTable.setText(String.valueOf(setScoresBlue[setNumber]));
            } else {
                setScoresOrange[setNumber] = scoreRight;
                TextView scoreTable = findViewById(orangeCellIds[setNumber]);
                scoreTable.setText(String.valueOf(setScoresOrange[setNumber]));
            }
            if (setNumber < totalSetsToPlay) {
                if ((scoreRight >= setFinishingScore) && ((scoreRight - scoreLeft) >= 2)) {
                    //if it is a set winning point..
                    if (!switched){ //corresponding cell in the table returns yellow
                        TextView scoreTable = findViewById(blueCellIds[setNumber]);
                        scoreTable.setBackgroundColor(Color.YELLOW);
                        //keep track of cell colors for redrawing them after rotation
                        blueRowColors[setNumber] = Color.YELLOW;
                        orangeRowColors[setNumber] = Color.TRANSPARENT;
                    } else { //if sides are switched, the cell in the other row turns yellow.
                        TextView scoreTable = findViewById(orangeCellIds[setNumber]);
                        scoreTable.setBackgroundColor(Color.YELLOW);
                        orangeRowColors[setNumber] = Color.YELLOW;
                        blueRowColors[setNumber] = Color.TRANSPARENT;
                    }
                    setsWonRight++;
                    displaySetsForTeamOnRight(setsWonRight);
                    if (setsWonRight == (totalSetsToPlay+1)/2) { //if it a match winning set
                        message = teamNameRight + " " + getString(R.string.wonMatch);
                        displayMessage(message);
                    } else { //if it is not a match winning set
                        message = teamNameRight + " " + getString(R.string.wonSet);
                        displayMessage(message);
                        setNumber++;
                        scoreLeft = 0;
                        scoreRight = 0;
                        displayScoreForTeamOnLeft(scoreLeft);
                        displayScoreForTeamOnRight(scoreRight);
                    }
                } else { //if it is not a set winning point
                    message = teamNameRight + " " + getString(R.string.serve);
                    displayMessage(message);
                }
            } else { // if it is the fifth set; it will switch to a tie break: the final set will be up to 15 points.
                if ((scoreRight >= tieBreakerScore) && ((scoreRight - scoreLeft) >= 2)) {
                    message = teamNameLeft + " " + getString(R.string.wonMatch);
                    displayMessage(message);
                    setsWonRight++;
                    displaySetsForTeamOnRight(setsWonRight);
                    scoreLeft = 0;
                    scoreRight = 0;
                    displayScoreForTeamOnLeft(scoreLeft);
                    displayScoreForTeamOnRight(scoreRight);
                }
            }
    }

    //This method exchange sides up on users click on the exchange button
    public void exchangeSides() {
        if ((scoreLeft == 0) && (scoreRight == 0)) {
            switched ^= true;
            int temporary = setsWonLeft;
            setsWonLeft = setsWonRight;
            setsWonRight = temporary;
            displaySetsForTeamOnLeft(setsWonLeft);
            displaySetsForTeamOnRight(setsWonRight);
            int temp = timeOffCountLeft;
            timeOffCountLeft = timeOffCountRight;
            timeOffCountRight = temp;
            if (teamNameLeft.equals(initialTeamNameOnLeft)) {
                teamNameLeft = initialTeamNameOnRight;
                displayTeamNameonLeft(teamNameLeft);
                teamNameRight = initialTeamNameOnLeft;
                displayTeamNameonRight(teamNameRight);
                View view1 = findViewById(R.id.viewOrange);
                view1.setBackgroundColor(getResources().getColor(R.color.blue_background));
                View view2 = findViewById(R.id.viewBlue);
                view2.setBackgroundColor(getResources().getColor(R.color.orange_background));
                TextView tw1 = findViewById(R.id.team_on_left);
                tw1.setBackground(this.getResources().getDrawable(R.drawable.blueborder));
                TextView tw2 = findViewById(R.id.team_on_right);
                tw2.setBackground(this.getResources().getDrawable(R.drawable.cellborder));
            } else {
                teamNameLeft = initialTeamNameOnLeft;
                displayTeamNameonLeft(teamNameLeft);
                teamNameRight = initialTeamNameOnRight;
                displayTeamNameonRight(teamNameRight);
                View view1 = findViewById(R.id.viewOrange);
                view1.setBackgroundColor(getResources().getColor(R.color.orange_background));
                View view2 = findViewById(R.id.viewBlue);
                view2.setBackgroundColor(getResources().getColor(R.color.blue_background));
                TextView tw1 = findViewById(R.id.team_on_left);
                tw1.setBackground(this.getResources().getDrawable(R.drawable.cellborder));
                TextView tw2 =  findViewById(R.id.team_on_right);
                tw2.setBackground(this.getResources().getDrawable(R.drawable.blueborder));
            }
        } else {
            Toast.makeText(this, "You can exchange sides only between the sets.", Toast.LENGTH_SHORT).show();
        }
    }

    public void pauseLeft() {
        if (timeOffCountLeft == 2) {
            Toast.makeText(this, "You have used all your time-offs.", Toast.LENGTH_SHORT).show();
            return;
        }
        openCountDownTimer();
        timeOffCountLeft++;
        int rest = 2 - timeOffCountLeft;
        String message = "You have used " + timeOffCountLeft + " timeoffs. You have " + rest + " time-off left.";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void pauseRight() {
        if (timeOffCountRight == 2) {
            Toast.makeText(this, "You have used all your time-offs.", Toast.LENGTH_SHORT).show();
            return;
        }
        openCountDownTimer();
        timeOffCountRight++;
        int rest = 2 - timeOffCountRight;
        String message = "You have used " + timeOffCountRight + " timeoffs. You have " + rest + " time-off left.";
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
        if(lastPointer.equals("Start")) return;
        else if(lastPointer.equals("TeamLeft")) correctionLeft();
        else correctionRight();
        undoButton.setEnabled(false);
        undoEnabled = false;
    }

    //This is for correcting a point mistakenly given.
    public void correctionLeft() {
        message = getString(R.string.error);
        displayMessage(message);
        if (scoreLeft > 0) {
            scoreLeft--;
            displayScoreForTeamOnLeft(scoreLeft);

        } else if(setNumber > 0) {
            setNumber--;
            setsWonLeft--;
            displaySetsForTeamOnLeft(setsWonLeft);
            if(!switched){
                scoreLeft = setScoresOrange[setNumber]-1;
                scoreRight = setScoresBlue[setNumber];
            } else {
                scoreLeft = setScoresBlue[setNumber]-1;
                scoreRight = setScoresOrange[setNumber];
            }
            displayScoreForTeamOnRight(scoreRight);
        }
        TextView scoreTable;
        if(!switched){
            scoreTable = findViewById(orangeCellIds[setNumber]);
            setScoresBlue[setNumber] = scoreRight;
            setScoresOrange[setNumber] = scoreLeft;
            orangeRowColors[setNumber] = Color.TRANSPARENT;
        }
        else{
            scoreTable = findViewById(blueCellIds[setNumber]);
            setScoresBlue[setNumber] = scoreLeft;
            setScoresOrange[setNumber] = scoreRight;
            blueRowColors[setNumber] = Color.TRANSPARENT;
        }
        scoreTable.setText(String.valueOf(scoreLeft));
        scoreTable.setBackgroundColor(Color.TRANSPARENT);
        displayScoreForTeamOnLeft(scoreLeft);
    }

    //This is for correcting a point mistakenly given.
    public void correctionRight() {
        if (scoreRight > 0) {
            scoreRight--;
            displayScoreForTeamOnRight(scoreRight);
            message = getString(R.string.error);
            displayMessage(message);
        } else if(setNumber > 1) {
            setNumber--;
            setsWonRight--;
            displaySetsForTeamOnLeft(setsWonLeft);
            if(!switched){
                scoreLeft = setScoresOrange[setNumber];
                scoreRight = setScoresBlue[setNumber]-1;
            } else {
                scoreLeft = setScoresBlue[setNumber];
                scoreRight = setScoresOrange[setNumber]-1;
            }
            displayScoreForTeamOnLeft(scoreLeft);
        }
        displayScoreForTeamOnRight(scoreRight);
        TextView scoreTable;
        if(!switched){
            scoreTable = findViewById(blueCellIds[setNumber]);
            setScoresBlue[setNumber] = scoreRight;
            setScoresOrange[setNumber] = scoreLeft;
            blueRowColors[setNumber] = Color.TRANSPARENT;
        }
        else{
            scoreTable = findViewById(orangeCellIds[setNumber]);
            setScoresBlue[setNumber] = scoreLeft;
            setScoresOrange[setNumber] = scoreRight;
            orangeRowColors[setNumber] = Color.TRANSPARENT;
        }
        scoreTable.setText(String.valueOf(scoreRight));
        scoreTable.setBackgroundColor(Color.TRANSPARENT);

    }

    public void displayScoreForTeamOnLeft(int score) {
        TextView scoreView = findViewById(R.id.team_left_score);
        scoreView.setText(String.valueOf(score));
    }

    public void displayScoreForTeamOnRight(int score) {
        TextView scoreView = findViewById(R.id.team_right_score);
        scoreView.setText(String.valueOf(score));
    }

    public void displaySetsForTeamOnLeft(int score) {
        TextView scoreView = findViewById(R.id.setsForTeamOnLeft);
        scoreView.setText(String.valueOf(score));
    }

    public void displaySetsForTeamOnRight(int score) {
        TextView scoreView = findViewById(R.id.setsForTeamOnRight);
        scoreView.setText(String.valueOf(score));
    }

    public void displayTeamNameonLeft(String message) {
        TextView scoreView = findViewById(R.id.team_on_left);
        scoreView.setText(message);
    }

    public void displayTeamNameonRight(String message) {
        TextView scoreView = findViewById(R.id.team_on_right);
        scoreView.setText(message);
    }

    public void displayMessage(String message) {
        TextView scoreView = findViewById(R.id.message);
        scoreView.setText(message);
    }

    public void displayOnTableTeamA(String message) {
        TextView scoreView = findViewById(R.id.teamA);
        scoreView.setText(message);
    }

    public void displayOnTableTeamB(String message) {
        TextView scoreView = findViewById(R.id.teamB);
        scoreView.setText(message);
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
