package com.example.android.volleyballscoreboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int scoreTeamLeft;
    int scoreTeamRight;
    int setsWonLeft;
    int setsWonRight;
    String teamNameLeft;
    String teamNameRight;
    String initialTeamNameOnLeft;
    String initialTeamNameOnRight;
    String message;
    int setNumber;
    boolean switched;
    boolean started;
    int firstSetScoreLeft;
    int secondSetScoreLeft;
    int thirdSetScoreLeft;
    int fourthSetScoreLeft;
    int fifthSetScoreLeft;
    int firstSetScoreRight;
    int secondSetScoreRight;
    int thirdSetScoreRight;
    int fourthSetScoreRight;
    int fifthSetScoreRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreTeamLeft = 0;
        scoreTeamRight = 0;
        setsWonLeft = 0;
        setsWonRight = 0;
        message = " ";
        teamNameLeft = getString(R.string.defaultTeamNameOnLeft);
        teamNameRight = getString(R.string.defaultTeamNameOnRight);
        setNumber = 1;
        switched = false;
        started = false;
        firstSetScoreLeft = 0;
        secondSetScoreLeft = 0;
        thirdSetScoreLeft = 0;
        fourthSetScoreLeft = 0;
        fifthSetScoreLeft = 0;
        firstSetScoreRight = 0;
        secondSetScoreRight = 0;
        thirdSetScoreRight = 0;
        fourthSetScoreRight = 0;
        fifthSetScoreRight = 0;
    }


    //This methods saves an instance of the variable values during rotation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("scoreTeamLeft", scoreTeamLeft);
        outState.putInt("scoreTeamRight", scoreTeamRight);
        outState.putInt("setsWonLeft", setsWonLeft);
        outState.putInt("setsWonRight", setsWonRight);
        outState.putString("teamNameLeft", teamNameLeft);
        outState.putString("teamNameRight", teamNameRight);
        outState.putString("message", message);
        outState.putInt("firstSetScoreLeft", firstSetScoreLeft);
        outState.putInt("secondSetScoreLeft", secondSetScoreLeft);
        outState.putInt("thirdSetScoreLeft", thirdSetScoreLeft);
        outState.putInt("fourthSetScoreLeft", fourthSetScoreLeft);
        outState.putInt("fifthSetScoreLeft", fifthSetScoreLeft);
        outState.putInt("firstSetScoreRight", firstSetScoreRight);
        outState.putInt("secondSetScoreRight", secondSetScoreRight);
        outState.putInt("thirdSetScoreRight", thirdSetScoreRight);
        outState.putInt("fourthSetScoreRight", fourthSetScoreRight);
        outState.putInt("fifthSetScoreRight", fifthSetScoreRight);
        outState.putInt("setNumber", setNumber);
    }

    //This method assigns back the saved values to variables after rotation
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        scoreTeamLeft = savedInstanceState.getInt("scoreTeamLeft");
        scoreTeamRight = savedInstanceState.getInt("scoreTeamRight");
        setsWonLeft = savedInstanceState.getInt("setsWonLeft");
        setsWonRight = savedInstanceState.getInt("setsWonRight");
        setNumber = savedInstanceState.getInt("setNumber");
        message = savedInstanceState.getString("message");
        teamNameLeft = savedInstanceState.getString("teamNameLeft");
        teamNameRight = savedInstanceState.getString("teamNameRight");
        firstSetScoreLeft = savedInstanceState.getInt("firstSetScoreLeft");
        secondSetScoreLeft = savedInstanceState.getInt("secondSetScoreLeft");
        thirdSetScoreLeft = savedInstanceState.getInt("thirdSetScoreLeft");
        fourthSetScoreLeft = savedInstanceState.getInt("fourthSetScoreLeft");
        fifthSetScoreLeft = savedInstanceState.getInt("fifthSetScoreLeft");
        firstSetScoreRight = savedInstanceState.getInt("firstSetScoreRight");
        secondSetScoreRight = savedInstanceState.getInt("secondSetScoreRight");
        thirdSetScoreRight = savedInstanceState.getInt("thirdSetScoreRight");
        fourthSetScoreRight = savedInstanceState.getInt("fourthSetScoreRight");
        fifthSetScoreRight = savedInstanceState.getInt("fifthSetScoreRight");
        displayScoreForTeamOnLeft(scoreTeamLeft);
        displayScoreForTeamOnRight(scoreTeamRight);
        displaySetsForTeamOnLeft(setsWonLeft);
        displaySetsForTeamOnRight(setsWonRight);
        displayMessage(message);
        displayTeamNameonLeft(teamNameLeft);
        displayTeamNameonRight(teamNameRight);
        displayOnTableS1_TA(firstSetScoreLeft);
        displayOnTableS1_TB(firstSetScoreRight);
        displayOnTableS2_TA(secondSetScoreLeft);
        displayOnTableS2_TB(secondSetScoreRight);
        displayOnTableS3_TA(thirdSetScoreLeft);
        displayOnTableS3_TB(thirdSetScoreRight);
        displayOnTableS4_TA(fourthSetScoreLeft);
        displayOnTableS4_TB(fourthSetScoreRight);
        displayOnTableS5_TA(fifthSetScoreLeft);
        displayOnTableS5_TB(fifthSetScoreRight);
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
        RadioGroup starter = (RadioGroup) findViewById(R.id.whoStarts);
        if (starter.getCheckedRadioButtonId() == R.id.optionOrange) {
            String message = teamNameLeft + " " + getString(com.example.android.volleyballscoreboard.R.string.serve);
            displayMessage(message);
        }
        if (starter.getCheckedRadioButtonId() == R.id.optionBlue) {
            String message = teamNameRight + " " + getString(com.example.android.volleyballscoreboard.R.string.serve);
            displayMessage(message);
        }
        View startView = findViewById(R.id.startScreen);
        startView.setVisibility(View.GONE);
    }

    public void flipCoin(View view) {
        double chance = Math.random();
        chance *= 2;
        if (chance < 1) { //heads
            ImageView coin = (ImageView) findViewById(R.id.coin);
            coin.setImageResource(R.drawable.heads);
        } else { //tails
            ImageView coin = (ImageView) findViewById(R.id.coin);
            coin.setImageResource(R.drawable.one_euro);
        }
    }

    public void displayScoreForTeamOnLeft(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_left_score);
        scoreView.setText(String.valueOf(score));
    }

    public void displayScoreForTeamOnRight(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_right_score);
        scoreView.setText(String.valueOf(score));
    }

    public void displaySetsForTeamOnLeft(int score) {
        TextView scoreView = (TextView) findViewById(R.id.setsForTeamOnLeft);
        scoreView.setText(String.valueOf(score));
    }

    public void displaySetsForTeamOnRight(int score) {
        TextView scoreView = (TextView) findViewById(R.id.setsForTeamOnRight);
        scoreView.setText(String.valueOf(score));
    }

    public void displayMessage(String message) {
        TextView scoreView = (TextView) findViewById(R.id.message);
        scoreView.setText(message);
    }

    public void displayTeamNameonLeft(String message) {
        TextView scoreView = (TextView) findViewById(R.id.team_on_left);
        scoreView.setText(message);
    }

    public void displayTeamNameonRight(String message) {
        TextView scoreView = (TextView) findViewById(R.id.team_on_right);
        scoreView.setText(message);
    }

    //Methods below are for displaying a summary of all set scores in a table.
    // Each method is for one cell
    public void displayOnTableTeamA(String message) {
        TextView scoreView = (TextView) findViewById(R.id.teamA);
        scoreView.setText(message);
    }

    public void displayOnTableTeamB(String message) {
        TextView scoreView = (TextView) findViewById(R.id.teamB);
        scoreView.setText(message);
    }

    public void displayOnTableS1_TA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.s1_tA);
        scoreView.setText(String.valueOf(score));
    }

    public void displayOnTableS1_TB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.s1_tB);
        scoreView.setText(String.valueOf(score));
    }

    public void displayOnTableS2_TA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.s2_tA);
        scoreView.setText(String.valueOf(score));
    }

    public void displayOnTableS2_TB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.s2_tB);
        scoreView.setText(String.valueOf(score));
    }

    public void displayOnTableS3_TA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.s3_tA);
        scoreView.setText(String.valueOf(score));
    }

    public void displayOnTableS3_TB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.s3_tB);
        scoreView.setText(String.valueOf(score));
    }

    public void displayOnTableS4_TA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.s4_tA);
        scoreView.setText(String.valueOf(score));
    }

    public void displayOnTableS4_TB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.s4_tB);
        scoreView.setText(String.valueOf(score));
    }

    public void displayOnTableS5_TA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.s5_tA);
        scoreView.setText(String.valueOf(score));
    }

    public void displayOnTableS5_TB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.s5_tB);
        scoreView.setText(String.valueOf(score));
    }

    //This method adds a point to the team on the left and shows it on the scoreboard.
    //It shows the same value simultaneously on the set summary table as well.
    public void add1pointToTeamOnLeft(View view) {
        if (started) {
            scoreTeamLeft++;
            displayScoreForTeamOnLeft(scoreTeamLeft);
            //switch statement assures that the score is shown for the right team
            //It also keeps a backup of current scoreteam for showing in the table after rotation
            switch (setNumber) {
                case 1: {
                    if (!switched) {
                        displayOnTableS1_TA(scoreTeamLeft);
                        firstSetScoreLeft = scoreTeamLeft;
                    } else {
                        displayOnTableS1_TB(scoreTeamLeft);
                        firstSetScoreRight = scoreTeamLeft;
                    }
                    break;
                }
                case 2: {
                    if (!switched) {
                        displayOnTableS2_TA(scoreTeamLeft);
                        secondSetScoreLeft = scoreTeamLeft;
                    } else {
                        displayOnTableS2_TB(scoreTeamLeft);
                        secondSetScoreRight = scoreTeamLeft;
                    }
                    break;
                }
                case 3: {
                    if (!switched) {
                        displayOnTableS3_TA(scoreTeamLeft);
                        thirdSetScoreLeft = scoreTeamLeft;
                    } else {
                        displayOnTableS3_TB(scoreTeamLeft);
                        thirdSetScoreRight = scoreTeamLeft;
                    }
                    break;
                }
                case 4: {
                    if (!switched) {
                        displayOnTableS4_TA(scoreTeamLeft);
                        fourthSetScoreLeft = scoreTeamLeft;
                    } else {
                        displayOnTableS4_TB(scoreTeamLeft);
                        fourthSetScoreRight = scoreTeamLeft;
                    }
                    break;
                }
                case 5: {
                    if (!switched) {
                        displayOnTableS5_TA(scoreTeamLeft);
                        fifthSetScoreLeft = scoreTeamLeft;
                    } else {
                        displayOnTableS5_TB(scoreTeamLeft);
                        fifthSetScoreRight = scoreTeamLeft;
                    }
                    break;
                }
            }
            if (setNumber < 5) {
                if ((scoreTeamLeft >= 25) && ((scoreTeamLeft - scoreTeamRight) >= 2)) {
                    //if it is a set winning point..
                    //Switch case takes a backup of final set score before reseting it; according to set number
                    switch (setNumber) {
                        case 1: {
                            if (!switched) {
                                firstSetScoreLeft = scoreTeamLeft;
                                firstSetScoreRight = scoreTeamRight;
                            } else {
                                firstSetScoreRight = scoreTeamLeft;
                                firstSetScoreLeft = scoreTeamRight;
                            }
                            break;
                        }
                        case 2: {
                            if (!switched) {
                                secondSetScoreLeft = scoreTeamLeft;
                                secondSetScoreRight = scoreTeamRight;
                            } else {
                                secondSetScoreRight = scoreTeamLeft;
                                secondSetScoreLeft = scoreTeamRight;
                            }
                            break;
                        }
                        case 3: {
                            if (!switched) {
                                thirdSetScoreLeft = scoreTeamLeft;
                                thirdSetScoreRight = scoreTeamRight;
                            } else {
                                thirdSetScoreRight = scoreTeamLeft;
                                thirdSetScoreLeft = scoreTeamRight;
                            }
                            break;
                        }
                        case 4: {
                            if (!switched) {
                                fourthSetScoreLeft = scoreTeamLeft;
                                fourthSetScoreRight = scoreTeamRight;
                            } else {
                                fourthSetScoreRight = scoreTeamLeft;
                                fourthSetScoreLeft = scoreTeamRight;
                            }
                            break;
                        }
                        case 5: {
                            if (!switched) {
                                fifthSetScoreLeft = scoreTeamLeft;
                                fifthSetScoreRight = scoreTeamRight;
                            } else {
                                fifthSetScoreRight = scoreTeamLeft;
                                fifthSetScoreLeft = scoreTeamRight;
                            }
                            break;
                        }
                    }
                    setsWonLeft++;
                    displaySetsForTeamOnLeft(setsWonLeft);
                    switch (setNumber) {
                        case 1: {
                            TextView tw = (TextView) findViewById(R.id.s1_tA);
                            tw.setBackgroundColor(Color.YELLOW);
                            break;
                        }
                        case 2: {
                            TextView tw = (TextView) findViewById(R.id.s2_tA);
                            tw.setBackgroundColor(Color.YELLOW);
                            break;
                        }
                        case 3: {
                            TextView tw2 = (TextView) findViewById(R.id.s3_tA);
                            tw2.setBackgroundColor(Color.YELLOW);
                            break;
                        }
                        case 4: {
                            TextView tw2 = (TextView) findViewById(R.id.s4_tA);
                            tw2.setBackgroundColor(Color.YELLOW);
                            break;
                        }
                        case 5: {
                            TextView tw2 = (TextView) findViewById(R.id.s5_tA);
                            tw2.setBackgroundColor(Color.YELLOW);
                            break;
                        }
                    }
                    if (setsWonLeft == 3) { //if it a match winning set
                        String winner = teamNameLeft + " " + getString(R.string.wonMatch);
                        displayMessage(winner);
                    } else { //if it is not a match winning set
                        String message = teamNameLeft + " " + getString(R.string.wonSet);
                        displayMessage(message);

                        setNumber++;
                        scoreTeamLeft = 0;
                        scoreTeamRight = 0;
                        displayScoreForTeamOnLeft(scoreTeamLeft);
                        displayScoreForTeamOnRight(scoreTeamRight);
                    }
                } else { //if it is not a set winning point
                    String message = teamNameLeft + " " + getString(R.string.serve);
                    displayMessage(message);
                }
            } else { // if it is the fifth set; it will switch to a tie break: the final set will be up to 15 points.
                if ((scoreTeamLeft >= 15) && ((scoreTeamLeft - scoreTeamRight) >= 2)) {
                    String message = teamNameLeft + " " + getString(R.string.wonMatch);
                    displayMessage(message);
                    setsWonLeft++;
                    displaySetsForTeamOnLeft(setsWonLeft);
                    scoreTeamLeft = 0;
                    scoreTeamRight = 0;
                    displayScoreForTeamOnLeft(scoreTeamLeft);
                    displayScoreForTeamOnRight(scoreTeamRight);
                } else {
                    String tieBreak = getString(R.string.tieBreaker);
                    displayMessage(tieBreak);
                }
            }
        }
    }


    //Same as the previous method, but for the opposite side.
    public void add1pointToTeamOnRight(View view) {
        if (started) {
            scoreTeamRight++;
            displayScoreForTeamOnRight(scoreTeamRight);
            switch (setNumber) {
                case 1: {
                    if (!switched) {
                        displayOnTableS1_TB(scoreTeamRight);
                        firstSetScoreRight = scoreTeamRight;
                    } else {
                        displayOnTableS1_TA(scoreTeamRight);
                        firstSetScoreLeft = scoreTeamRight;
                    }
                    break;
                }
                case 2: {
                    if (!switched) {
                        displayOnTableS2_TB(scoreTeamRight);
                        secondSetScoreRight = scoreTeamRight;
                    } else {
                        displayOnTableS2_TA(scoreTeamRight);
                        secondSetScoreLeft = scoreTeamRight;
                    }
                    break;
                }
                case 3: {
                    if (!switched) {
                        displayOnTableS3_TB(scoreTeamRight);
                        thirdSetScoreRight = scoreTeamRight;
                    } else {
                        displayOnTableS3_TA(scoreTeamRight);
                        thirdSetScoreLeft = scoreTeamRight;
                    }
                    break;
                }
                case 4: {
                    if (!switched) {
                        displayOnTableS4_TB(scoreTeamRight);
                        fourthSetScoreRight = scoreTeamRight;
                    } else {
                        displayOnTableS4_TA(scoreTeamRight);
                        fourthSetScoreLeft = scoreTeamRight;
                    }
                    break;
                }
                case 5: {
                    if (!switched) {
                        displayOnTableS5_TB(scoreTeamRight);
                        fifthSetScoreRight = scoreTeamRight;
                    } else {
                        displayOnTableS5_TA(scoreTeamRight);
                        fifthSetScoreLeft = scoreTeamRight;
                    }
                    break;
                }
            }
            if (setNumber < 5) {
                if ((scoreTeamRight >= 25) && ((scoreTeamRight - scoreTeamLeft) >= 2)) {
                    switch (setNumber) {
                        case 1: {
                            if (!switched) {
                                firstSetScoreLeft = scoreTeamLeft;
                                firstSetScoreRight = scoreTeamRight;
                            } else {
                                firstSetScoreRight = scoreTeamLeft;
                                firstSetScoreLeft = scoreTeamRight;
                            }
                            break;
                        }
                        case 2: {
                            if (!switched) {
                                secondSetScoreLeft = scoreTeamLeft;
                                secondSetScoreRight = scoreTeamRight;
                            } else {
                                secondSetScoreRight = scoreTeamLeft;
                                secondSetScoreLeft = scoreTeamRight;
                            }
                            break;
                        }
                        case 3: {
                            if (!switched) {
                                thirdSetScoreLeft = scoreTeamLeft;
                                thirdSetScoreRight = scoreTeamRight;
                            } else {
                                thirdSetScoreRight = scoreTeamLeft;
                                thirdSetScoreLeft = scoreTeamRight;
                            }
                            break;
                        }
                        case 4: {
                            if (!switched) {
                                fourthSetScoreLeft = scoreTeamLeft;
                                fourthSetScoreRight = scoreTeamRight;
                            } else {
                                fourthSetScoreRight = scoreTeamLeft;
                                fourthSetScoreLeft = scoreTeamRight;
                            }
                            break;
                        }
                        case 5: {
                            if (!switched) {
                                fifthSetScoreLeft = scoreTeamLeft;
                                fifthSetScoreRight = scoreTeamRight;
                            } else {
                                fifthSetScoreRight = scoreTeamLeft;
                                fifthSetScoreLeft = scoreTeamRight;
                            }
                            break;
                        }
                    }
                    setsWonRight++;
                    displaySetsForTeamOnRight(setsWonRight);
                    switch (setNumber) {
                        case 1: {
                            TextView tw = (TextView) findViewById(R.id.s1_tB);
                            tw.setBackgroundColor(Color.YELLOW);
                            break;
                        }
                        case 2: {
                            TextView tw = (TextView) findViewById(R.id.s2_tB);
                            tw.setBackgroundColor(Color.YELLOW);
                            break;
                        }
                        case 3: {
                            TextView tw2 = (TextView) findViewById(R.id.s3_tB);
                            tw2.setBackgroundColor(Color.YELLOW);
                            break;
                        }
                        case 4: {
                            TextView tw2 = (TextView) findViewById(R.id.s4_tB);
                            tw2.setBackgroundColor(Color.YELLOW);
                            break;
                        }
                        case 5: {
                            TextView tw2 = (TextView) findViewById(R.id.s5_tB);
                            tw2.setBackgroundColor(Color.YELLOW);
                            break;
                        }
                    }
                    if (setsWonRight == 3) {
                        String winner = teamNameRight + " " + getString(R.string.wonMatch);
                        displayMessage(winner);
                        setNumber++;
                        scoreTeamLeft = 0;
                        scoreTeamRight = 0;
                        displayScoreForTeamOnLeft(scoreTeamLeft);
                        displayScoreForTeamOnRight(scoreTeamRight);
                    } else {
                        String message = teamNameRight + " " + getString(R.string.wonSet);
                        displayMessage(message);
                        setNumber++;
                        scoreTeamLeft = 0;
                        scoreTeamRight = 0;
                        displayScoreForTeamOnLeft(scoreTeamLeft);
                        displayScoreForTeamOnRight(scoreTeamRight);
                    }
                } else {
                    String message = teamNameRight + " " + getString(R.string.serve);
                    displayMessage(message);
                }
            } else {
                if ((scoreTeamRight >= 15) && ((scoreTeamRight - scoreTeamLeft) >= 2)) {
                    String message = teamNameRight + " " + getString(R.string.wonMatch);
                    displayMessage(message);
                    setsWonRight++;
                    displaySetsForTeamOnLeft(setsWonRight);
                    scoreTeamLeft = 0;
                    scoreTeamRight = 0;
                    displayScoreForTeamOnLeft(scoreTeamLeft);
                    displayScoreForTeamOnRight(scoreTeamRight);
                } else {
                    String tieBreak = getString(R.string.tieBreaker);
                    displayMessage(tieBreak);
                }
            }
        }
    }

    //This method exchange sides up on users click on the exchange button
    public void exchangeSides(View view) {
        if ((scoreTeamLeft == 0) && (scoreTeamRight == 0)) {
            if (switched) switched = false;
            else switched = true;
            int temporary = setsWonLeft;
            setsWonLeft = setsWonRight;
            setsWonRight = temporary;
            displaySetsForTeamOnLeft(setsWonLeft);
            displaySetsForTeamOnRight(setsWonRight);
            if (teamNameLeft.equals(initialTeamNameOnLeft)) {
                teamNameLeft = initialTeamNameOnRight;
                displayTeamNameonLeft(teamNameLeft);
                teamNameRight = initialTeamNameOnLeft;
                displayTeamNameonRight(teamNameRight);
                View view1 = findViewById(R.id.viewOrange);
                view1.setBackgroundColor(Color.parseColor("#03A9F4"));
                View view2 = findViewById(R.id.viewBlue);
                view2.setBackgroundColor(Color.parseColor("#FF9800"));
                TextView tw1 = (TextView) findViewById(R.id.team_on_left);
                tw1.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.blueborder));
                TextView tw2 = (TextView) findViewById(R.id.team_on_right);
                tw2.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.cellborder));
            } else {
                teamNameLeft = initialTeamNameOnLeft;
                displayTeamNameonLeft(teamNameLeft);
                teamNameRight = initialTeamNameOnRight;
                displayTeamNameonRight(teamNameRight);
                View view1 = findViewById(R.id.viewOrange);
                view1.setBackgroundColor(Color.parseColor("#FF9800"));
                View view2 = findViewById(R.id.viewBlue);
                view2.setBackgroundColor(Color.parseColor("#03A9F4"));
                TextView tw1 = (TextView) findViewById(R.id.team_on_left);
                tw1.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.cellborder));
                TextView tw2 = (TextView) findViewById(R.id.team_on_right);
                tw2.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.blueborder));
            }
        }
    }

    public void pause(View view) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Time left:");
        intent.putExtra(AlarmClock.EXTRA_LENGTH, 60);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    //This is for correcting a point mistakenly given.
    public void correctionLeft(View view) {
        if (scoreTeamLeft == 0 && setNumber == 1) return;
        if (scoreTeamLeft > 0) {
            scoreTeamLeft--;
            displayScoreForTeamOnLeft(scoreTeamLeft);
            String message = getString(R.string.error);
            displayMessage(message);
        }
        if (scoreTeamLeft == 0 && setNumber > 1) {
            setNumber--;
            setsWonLeft--;
            displaySetsForTeamOnLeft(setsWonLeft);
            switch (setNumber) {
                case 1: {
                    scoreTeamLeft = firstSetScoreLeft - 1;
                    displayScoreForTeamOnLeft(scoreTeamLeft);
                    displayOnTableS1_TA(scoreTeamLeft);
                    scoreTeamRight = firstSetScoreRight;
                    displayScoreForTeamOnRight(scoreTeamRight);
                    TextView tw = (TextView) findViewById(R.id.s1_tA);
                    tw.setBackgroundColor(Color.TRANSPARENT);
                    break;
                }
                case 2: {
                    scoreTeamLeft = secondSetScoreLeft - 1;
                    displayScoreForTeamOnLeft(scoreTeamLeft);
                    displayOnTableS2_TA(scoreTeamLeft);
                    scoreTeamRight = secondSetScoreRight;
                    displayScoreForTeamOnRight(scoreTeamRight);
                    TextView tw = (TextView) findViewById(R.id.s2_tA);
                    tw.setBackgroundColor(Color.TRANSPARENT);
                    break;
                }
                case 3: {
                    scoreTeamLeft = thirdSetScoreLeft - 1;
                    displayScoreForTeamOnLeft(scoreTeamLeft);
                    displayOnTableS3_TA(scoreTeamLeft);
                    scoreTeamRight = thirdSetScoreRight;
                    displayScoreForTeamOnRight(scoreTeamRight);
                    TextView tw = (TextView) findViewById(R.id.s3_tA);
                    tw.setBackgroundColor(Color.TRANSPARENT);
                    break;
                }
                case 4: {
                    scoreTeamLeft = fourthSetScoreLeft - 1;
                    displayScoreForTeamOnLeft(scoreTeamLeft);
                    displayOnTableS4_TA(scoreTeamLeft);
                    scoreTeamRight = fourthSetScoreRight;
                    displayScoreForTeamOnRight(scoreTeamRight);
                    TextView tw = (TextView) findViewById(R.id.s4_tA);
                    tw.setBackgroundColor(Color.TRANSPARENT);
                    break;
                }
                case 5: {
                    scoreTeamLeft = fifthSetScoreLeft - 1;
                    displayScoreForTeamOnLeft(scoreTeamLeft);
                    displayOnTableS5_TA(scoreTeamLeft);
                    scoreTeamRight = fifthSetScoreRight;
                    displayScoreForTeamOnRight(scoreTeamRight);
                    TextView tw = (TextView) findViewById(R.id.s5_tA);
                    tw.setBackgroundColor(Color.TRANSPARENT);
                    break;
                }
            }
        }
    }

    //This is for correcting a point mistakenly given.
    public void correctionRight(View view) {
        if (scoreTeamRight == 0 && setNumber == 1) return;
        if (scoreTeamRight > 0) {
            scoreTeamRight--;
            displayScoreForTeamOnRight(scoreTeamRight);
            String message = getString(R.string.error);
            displayMessage(message);
        }
        if (scoreTeamLeft == 0 && setNumber > 1) {
            setNumber--;
            setsWonRight--;
            displaySetsForTeamOnRight(setsWonRight);
            switch (setNumber) {
                case 1: {
                    scoreTeamRight = firstSetScoreRight - 1;
                    displayOnTableS1_TB(scoreTeamRight);
                    displayScoreForTeamOnRight(scoreTeamRight);
                    scoreTeamLeft = firstSetScoreLeft;
                    displayScoreForTeamOnLeft(scoreTeamLeft);
                    TextView tw = (TextView) findViewById(R.id.s1_tB);
                    tw.setBackgroundColor(Color.TRANSPARENT);
                    break;
                }
                case 2: {
                    scoreTeamRight = secondSetScoreRight - 1;
                    displayOnTableS2_TB(scoreTeamRight);
                    displayScoreForTeamOnRight(scoreTeamRight);
                    scoreTeamLeft = secondSetScoreLeft;
                    displayScoreForTeamOnLeft(scoreTeamLeft);
                    TextView tw = (TextView) findViewById(R.id.s2_tB);
                    tw.setBackgroundColor(Color.TRANSPARENT);
                    break;
                }
                case 3: {
                    scoreTeamRight = thirdSetScoreRight - 1;
                    displayOnTableS3_TB(scoreTeamRight);
                    displayScoreForTeamOnRight(scoreTeamRight);
                    scoreTeamLeft = thirdSetScoreLeft;
                    displayScoreForTeamOnLeft(scoreTeamLeft);
                    TextView tw = (TextView) findViewById(R.id.s3_tB);
                    tw.setBackgroundColor(Color.TRANSPARENT);
                    break;
                }
                case 4: {
                    scoreTeamRight = fourthSetScoreRight - 1;
                    displayOnTableS4_TB(scoreTeamRight);
                    displayScoreForTeamOnRight(scoreTeamRight);
                    scoreTeamLeft = fourthSetScoreLeft;
                    displayScoreForTeamOnLeft(scoreTeamLeft);
                    TextView tw = (TextView) findViewById(R.id.s4_tB);
                    tw.setBackgroundColor(Color.TRANSPARENT);
                    break;
                }
                case 5: {
                    scoreTeamRight = fifthSetScoreRight - 1;
                    displayOnTableS5_TB(scoreTeamRight);
                    displayScoreForTeamOnRight(scoreTeamRight);
                    scoreTeamLeft = fifthSetScoreLeft;
                    displayScoreForTeamOnLeft(scoreTeamLeft);
                    TextView tw = (TextView) findViewById(R.id.s5_tB);
                    tw.setBackgroundColor(Color.TRANSPARENT);
                    break;
                }
            }

        }
    }

    //This method resets everything up on clickof the user on the reset button.
    public void reset(View view) {
        scoreTeamLeft = 0;
        scoreTeamRight = 0;
        setsWonLeft = 0;
        setsWonRight = 0;
        setNumber = 1;
        switched = false;
        displayScoreForTeamOnLeft(scoreTeamLeft);
        displayScoreForTeamOnRight(scoreTeamRight);
        displaySetsForTeamOnLeft(setsWonLeft);
        displaySetsForTeamOnRight(setsWonRight);
        String message = "";
        displayMessage(message);
        teamNameLeft = initialTeamNameOnLeft;
        displayTeamNameonLeft(teamNameLeft);
        teamNameRight = initialTeamNameOnRight;
        displayTeamNameonRight(teamNameRight);
        displayOnTableS1_TA(0);
        displayOnTableS1_TB(0);
        displayOnTableS2_TA(0);
        displayOnTableS2_TB(0);
        displayOnTableS3_TA(0);
        displayOnTableS3_TB(0);
        displayOnTableS4_TA(0);
        displayOnTableS4_TB(0);
        displayOnTableS5_TA(0);
        displayOnTableS5_TB(0);
        firstSetScoreLeft = 0;
        secondSetScoreLeft = 0;
        thirdSetScoreLeft = 0;
        fourthSetScoreLeft = 0;
        fifthSetScoreLeft = 0;
        firstSetScoreRight = 0;
        secondSetScoreRight = 0;
        thirdSetScoreRight = 0;
        fourthSetScoreRight = 0;
        fifthSetScoreRight = 0;
        TextView tw1 = (TextView) findViewById(R.id.s1_tA);
        tw1.setBackgroundColor(Color.TRANSPARENT);
        TextView tw2 = (TextView) findViewById(R.id.s2_tA);
        tw2.setBackgroundColor(Color.TRANSPARENT);
        TextView tw3 = (TextView) findViewById(R.id.s3_tA);
        tw3.setBackgroundColor(Color.TRANSPARENT);
        TextView tw4 = (TextView) findViewById(R.id.s4_tA);
        tw4.setBackgroundColor(Color.TRANSPARENT);
        TextView tw5 = (TextView) findViewById(R.id.s5_tA);
        tw5.setBackgroundColor(Color.TRANSPARENT);
        TextView tw6 = (TextView) findViewById(R.id.s1_tB);
        tw6.setBackgroundColor(Color.TRANSPARENT);
        TextView tw7 = (TextView) findViewById(R.id.s2_tB);
        tw7.setBackgroundColor(Color.TRANSPARENT);
        TextView tw8 = (TextView) findViewById(R.id.s3_tB);
        tw8.setBackgroundColor(Color.TRANSPARENT);
        TextView tw9 = (TextView) findViewById(R.id.s4_tB);
        tw9.setBackgroundColor(Color.TRANSPARENT);
        TextView tw10 = (TextView) findViewById(R.id.s5_tB);
        tw10.setBackgroundColor(Color.TRANSPARENT);
    }
}
