package com.example.android.volleyballscoreboard;

import android.content.Intent;
import android.graphics.Color;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int scoreLeft, scoreRight, setsWonLeft, setsWonRight, setNumber;
    String teamNameLeft, teamNameRight, initialTeamNameOnLeft, initialTeamNameOnRight;
    String message;
    boolean switched, started;
    int timeOffCountLeft, timeOffCountRight, starter_team_id;;
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
    public final static String STARTED = "started";
    public final static String TIMEOFF_COUNT_LEFT = "timeoffCountLeft";
    public final static String TIMEOFF_COUNT_RIGHT = "timeoffCountRight";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialTeamNameOnLeft = teamNameLeft = getString(R.string.defaultTeamNameOnLeft);
        initialTeamNameOnRight = teamNameRight = getString(R.string.defaultTeamNameOnRight);
        starter_team_id = R.id.optionOrange;
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
        outState.putBoolean(SWITCHED, switched);
        outState.putBoolean(STARTED, started);
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
        setNumber = savedInstanceState.getInt(SET_NUMBER);
        displayScoreForTeamOnLeft(scoreLeft);
        displayScoreForTeamOnRight(scoreRight);
        displaySetsForTeamOnLeft(setsWonLeft);
        displaySetsForTeamOnRight(setsWonRight);
        displayMessage(message);
        displayTeamNameonLeft(teamNameLeft);
        displayTeamNameonRight(teamNameRight);
        switched = savedInstanceState.getBoolean(SWITCHED);
        if (switched) {
            View view1 = findViewById(R.id.viewOrange);
            view1.setBackgroundColor(getResources().getColor(R.color.background_teamA));
            View view2 = findViewById(R.id.viewBlue);
            view2.setBackgroundColor(getResources().getColor(R.color.background_teamB));
            TextView tw1 = (TextView) findViewById(R.id.team_on_left);
            tw1.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.blueborder));
            TextView tw2 = (TextView) findViewById(R.id.team_on_right);
            tw2.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.cellborder));
        }
        started = savedInstanceState.getBoolean(STARTED);
        if (started) {
            View startView = findViewById(R.id.startScreen);
            startView.setVisibility(View.GONE);
        }
        setScoresOrange = savedInstanceState.getIntArray(SET_SCORES_ORANGE);
        setScoresBlue = savedInstanceState.getIntArray(SET_SCORES_BLUE);
        orangeRowColors = savedInstanceState.getIntArray(ORANGE_ROW_COLORS);
        blueRowColors = savedInstanceState.getIntArray(BLUE_ROW_COLORS);
        for(int i = 0; i<5 ; i++){
            TextView orangeCell = findViewById(orangeCellIds[i]);
            orangeCell.setText(String.valueOf(setScoresOrange[i]));
            orangeCell.setBackgroundColor(orangeRowColors[i]);
            TextView blueCell = findViewById(blueCellIds[i]);
            blueCell.setText(String.valueOf(setScoresBlue[i]));
            blueCell.setBackgroundColor(blueRowColors[i]);
        }
    }

    public void startGame(View view) {
        started = true;
        EditText eText1 = (EditText) findViewById(R.id.usersTeamName1);
        initialTeamNameOnLeft = eText1.getText().toString();
        if (initialTeamNameOnLeft.equals("")) {
            initialTeamNameOnLeft = getString(R.string.defaultTeamNameOnLeft);
        }
        teamNameLeft = initialTeamNameOnLeft;
        displayOnTableTeamA(teamNameLeft);
        displayTeamNameonLeft(teamNameLeft);
        EditText eText2 = (EditText) findViewById(R.id.usersTeamName2);
        initialTeamNameOnRight = eText2.getText().toString();
        if (initialTeamNameOnRight.equals("")) {
            initialTeamNameOnRight = getString(R.string.defaultTeamNameOnRight);
        }
        teamNameRight = initialTeamNameOnRight;
        displayOnTableTeamB(teamNameRight);
        displayTeamNameonRight(teamNameRight);
        RadioGroup starter = findViewById(R.id.whoStarts);
        if (starter.getCheckedRadioButtonId() == R.id.optionBlue) {
            String message = teamNameRight + " " + getString(R.string.serve);
            displayMessage(message);
            starter_team_id = R.id.optionBlue;
        } else {
            String message = teamNameLeft + " " + getString(R.string.serve);
            displayMessage(message);
            starter_team_id = R.id.optionOrange;
        }

        View startView = findViewById(R.id.startScreen);
        startView.setVisibility(View.GONE);
    }

    public void flipCoin(View view) {
        double chance = Math.random();
        chance *= 2;
        if (chance < 1) { //heads
            ImageView coin =  findViewById(R.id.coin);
            coin.setImageResource(R.drawable.heads);
        } else { //tails
            ImageView coin = findViewById(R.id.coin);
            coin.setImageResource(R.drawable.one_euro);
        }
    }


    public void add1pointToTeamOnLeft(View view) {
        if(started) {
            scoreLeft++;
            displayScoreForTeamOnLeft(scoreLeft);
            if (!switched) {
                setScoresOrange[setNumber] = scoreLeft;
                TextView scoreTable = findViewById(orangeCellIds[setNumber]);
                scoreTable.setText(String.valueOf(setScoresOrange[setNumber]));
            } else {
                setScoresBlue[setNumber] = scoreLeft;
                TextView scoreTable = findViewById(blueCellIds[setNumber]);
                scoreTable.setText(String.valueOf(setScoresBlue[setNumber]));
            }

            if (setNumber < 5) {
                if ((scoreLeft >= 25) && ((scoreLeft - scoreRight) >= 2)) {
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
                    if (setsWonLeft == 3) { //if it a match winning set
                        String winner = teamNameLeft + " " + getString(R.string.wonMatch);
                        displayMessage(winner);
                    } else { //if it is not a match winning set
                        String message = teamNameLeft + " " + getString(R.string.wonSet);
                        displayMessage(message);
                        setNumber++;
                        scoreLeft = 0;
                        scoreRight = 0;
                        displayScoreForTeamOnLeft(scoreLeft);
                        displayScoreForTeamOnRight(scoreRight);
                    }
                } else { //if it is not a set winning point
                    String message = teamNameLeft + " " + getString(R.string.serve);
                    displayMessage(message);
                }
            } else { // if it is the fifth set; it will switch to a tie break: the final set will be up to 15 points.
                if ((scoreLeft >= 15) && ((scoreLeft - scoreRight) >= 2)) {
                    String message = teamNameLeft + " " + getString(R.string.wonMatch);
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
    }

    public void add1pointToTeamOnRight(View view) {
        if(started) {
            scoreRight++;
            displayScoreForTeamOnRight(scoreRight);
            if (!switched) {
                setScoresBlue[setNumber] = scoreRight;
                TextView scoreTable = findViewById(blueCellIds[setNumber]);
                scoreTable.setText(String.valueOf(setScoresBlue[setNumber]));
            } else {
                setScoresOrange[setNumber] = scoreRight;
                TextView scoreTable = findViewById(orangeCellIds[setNumber]);
                scoreTable.setText(String.valueOf(setScoresOrange[setNumber]));
            }
            if (setNumber < 5) {
                if ((scoreRight >= 25) && ((scoreRight - scoreLeft) >= 2)) {
                    //if it is a set winning point..
                    if (!switched){
                        TextView scoreTable = findViewById(blueCellIds[setNumber]);
                        scoreTable.setBackgroundColor(Color.YELLOW);
                        blueRowColors[setNumber] = Color.YELLOW;
                        orangeRowColors[setNumber] = Color.TRANSPARENT;
                    } else {
                        TextView scoreTable = findViewById(orangeCellIds[setNumber]);
                        scoreTable.setBackgroundColor(Color.YELLOW);
                        orangeRowColors[setNumber] = Color.YELLOW;
                        blueRowColors[setNumber] = Color.TRANSPARENT;
                    }
                    setsWonRight++;
                    displaySetsForTeamOnRight(setsWonRight);
                    if (setsWonRight == 3) { //if it a match winning set
                        String winner = teamNameRight + " " + getString(R.string.wonMatch);
                        displayMessage(winner);
                    } else { //if it is not a match winning set
                        String message = teamNameRight + " " + getString(R.string.wonSet);
                        displayMessage(message);
                        setNumber++;
                        scoreLeft = 0;
                        scoreRight = 0;
                        displayScoreForTeamOnLeft(scoreLeft);
                        displayScoreForTeamOnRight(scoreRight);
                    }
                } else { //if it is not a set winning point
                    String message = teamNameRight + " " + getString(R.string.serve);
                    displayMessage(message);
                }
            } else { // if it is the fifth set; it will switch to a tie break: the final set will be up to 15 points.
                if ((scoreRight >= 15) && ((scoreRight - scoreLeft) >= 2)) {
                    String message = teamNameLeft + " " + getString(R.string.wonMatch);
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
    }

    //This method exchange sides up on users click on the exchange button
    public void exchangeSides(View view) {
        if ((scoreLeft == 0) && (scoreRight == 0)) {
            if (switched) switched = false;
            else switched = true;
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
                view1.setBackgroundColor(getResources().getColor(R.color.background_teamB));
                View view2 = findViewById(R.id.viewBlue);
                view2.setBackgroundColor(getResources().getColor(R.color.background_teamA));
                TextView tw1 = findViewById(R.id.team_on_left);
                tw1.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.blueborder));
                TextView tw2 = findViewById(R.id.team_on_right);
                tw2.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.cellborder));
            } else {
                teamNameLeft = initialTeamNameOnLeft;
                displayTeamNameonLeft(teamNameLeft);
                teamNameRight = initialTeamNameOnRight;
                displayTeamNameonRight(teamNameRight);
                View view1 = findViewById(R.id.viewOrange);
                view1.setBackgroundColor(getResources().getColor(R.color.background_teamA));
                View view2 = findViewById(R.id.viewBlue);
                view2.setBackgroundColor(getResources().getColor(R.color.background_teamB));
                TextView tw1 = findViewById(R.id.team_on_left);
                tw1.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.cellborder));
                TextView tw2 =  findViewById(R.id.team_on_right);
                tw2.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.blueborder));
            }
        } else {
            Toast.makeText(this, "You can exchange sides only between the sets.", Toast.LENGTH_SHORT).show();
        }
    }

    public void pauseLeft(View view) {
        if (timeOffCountLeft == 2) {
            Toast.makeText(this, "You have used all your time-offs.", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Time left:");
        intent.putExtra(AlarmClock.EXTRA_LENGTH, 60);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        timeOffCountLeft++;
        int rest = 2 - timeOffCountLeft;
        String message = "You have used " + timeOffCountLeft + " timeoffs. You have " + rest + " time-off left.";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void pauseRight(View view) {
        if (timeOffCountRight == 2) {
            Toast.makeText(this, "You have used all your time-offs.", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Time left:");
        intent.putExtra(AlarmClock.EXTRA_LENGTH, 60);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        timeOffCountRight++;
        int rest = 2 - timeOffCountRight;
        String message = "You have used " + timeOffCountRight + " timeoffs. You have " + rest + " time-off left.";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //This is for correcting a point mistakenly given.
    public void correctionLeft(View view) {
        if (scoreLeft == 0 && setNumber == 1) return;
        if (scoreLeft > 0) {
            scoreLeft--;
            displayScoreForTeamOnLeft(scoreLeft);
            String message = getString(R.string.error);
            displayMessage(message);
        }
    }

    //This is for correcting a point mistakenly given.
    public void correctionRight(View view) {
        if (scoreRight == 0 && setNumber == 1) return;
        if (scoreRight > 0) {
            scoreRight--;
            displayScoreForTeamOnRight(scoreRight);
            String message = getString(R.string.error);
            displayMessage(message);
        }
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

    public void reset(View view){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
