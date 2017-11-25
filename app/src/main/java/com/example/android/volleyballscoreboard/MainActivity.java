package com.example.android.volleyballscoreboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int scoreTeamLeft = 0;
    int scoreTeamRight = 0;
    int setsWonLeft = 0;
    int setsWonRight = 0;
    String teamNameLeft = "Team A";
    String teamNameRight = "Team B";
    String message = "";
    int setNumber = 1;
    boolean switched = false;
    int firstSetScoreLeft = 0;
    int secondSetScoreLeft = 0;
    int thirdSetScoreLeft = 0;
    int fourthSetScoreLeft = 0;
    int fifthSetScoreLeft = 0;
    int firstSetScoreRight = 0;
    int secondSetScoreRight = 0;
    int thirdSetScoreRight = 0;
    int fourthSetScoreRight = 0;
    int fifthSetScoreRight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        secondSetScoreLeft= savedInstanceState.getInt("secondSetScoreLeft");
        thirdSetScoreLeft= savedInstanceState.getInt("thirdSetScoreLeft");
        fourthSetScoreLeft= savedInstanceState.getInt("fourthSetScoreLeft");
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
        scoreTeamLeft++;
        displayScoreForTeamOnLeft(scoreTeamLeft);
        //switch statement assures that the score is shown for the right team
        //It also keeps a backup of current scoreteam for showing in the table after rotation
        switch (setNumber) {
            case 1: {
                if(!switched) {
                    displayOnTableS1_TA(scoreTeamLeft);
                    firstSetScoreLeft = scoreTeamLeft;
                }
                else {
                    displayOnTableS1_TB(scoreTeamLeft);
                    firstSetScoreRight = scoreTeamLeft;
                }
                break;
            }
            case 2: {
                if(!switched) {
                    displayOnTableS2_TA(scoreTeamLeft);
                    secondSetScoreLeft = scoreTeamLeft;
                }
                else {
                    displayOnTableS2_TB(scoreTeamLeft);
                    secondSetScoreRight = scoreTeamLeft;
                }
                break;
            }
            case 3: {
                if(!switched) {
                    displayOnTableS3_TA(scoreTeamLeft);
                    thirdSetScoreLeft = scoreTeamLeft;
                }
                else {
                    displayOnTableS3_TB(scoreTeamLeft);
                    thirdSetScoreRight = scoreTeamLeft;
                }
                break;
            }
            case 4: {
                if(!switched) {
                    displayOnTableS4_TA(scoreTeamLeft);
                    fourthSetScoreLeft = scoreTeamLeft;
                }
                else {
                    displayOnTableS4_TB(scoreTeamLeft);
                    fourthSetScoreRight = scoreTeamLeft;
                }
                break;
            }
            case 5: {
                if(!switched) {
                    displayOnTableS5_TA(scoreTeamLeft);
                    fifthSetScoreLeft = scoreTeamLeft;
                }
                else {
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
                        if(!switched) {
                            firstSetScoreLeft = scoreTeamLeft;
                            firstSetScoreRight = scoreTeamRight;
                        }
                        else {
                            firstSetScoreRight = scoreTeamLeft;
                            firstSetScoreLeft = scoreTeamRight;
                        }
                        break;
                    }
                    case 2: {
                        if(!switched) {
                            secondSetScoreLeft = scoreTeamLeft;
                            secondSetScoreRight = scoreTeamRight;
                        }
                        else {
                            secondSetScoreRight = scoreTeamLeft;
                            secondSetScoreLeft = scoreTeamRight;
                        }
                        break;
                    }
                    case 3: {
                        if(!switched) {
                            thirdSetScoreLeft = scoreTeamLeft;
                            thirdSetScoreRight = scoreTeamRight;
                        }
                        else{
                            thirdSetScoreRight = scoreTeamLeft;
                            thirdSetScoreLeft = scoreTeamRight;
                        }
                        break;
                    }
                    case 4: {
                        if(!switched) {
                            fourthSetScoreLeft = scoreTeamLeft;
                            fourthSetScoreRight = scoreTeamRight;
                        }
                        else{
                            fourthSetScoreRight = scoreTeamLeft;
                            fourthSetScoreLeft = scoreTeamRight;
                        }
                        break;
                    }
                    case 5: {
                        if(!switched) {
                            fifthSetScoreLeft = scoreTeamLeft;
                            fifthSetScoreRight = scoreTeamRight;
                        }
                        else{
                            fifthSetScoreRight = scoreTeamLeft;
                            fifthSetScoreLeft = scoreTeamRight;
                        }
                        break;
                    }
                }
                setsWonLeft++;
                displaySetsForTeamOnLeft(setsWonLeft);
                if (setsWonLeft == 3) { //if it a match winning set
                    String winner = "Team A won the match!";
                    displayMessage(winner);
                } else { //if it is not a match winning set
                    String message = "Team A won the set!";
                    displayMessage(message);
                    setNumber++;
                    scoreTeamLeft = 0;
                    scoreTeamRight = 0;
                    displayScoreForTeamOnLeft(scoreTeamLeft);
                    displayScoreForTeamOnRight(scoreTeamRight);
                }
            } else { //if it is not a set winning point
                String message = teamNameLeft + " will serve the ball.";
                displayMessage(message);
            }
        } else { // if it is the fifth set; it will switch to a tie break: the final set will be up to 15 points.
            if ((scoreTeamLeft >= 15) && ((scoreTeamLeft - scoreTeamRight) >= 2)) {
                String message = "Team A won the match! Congradulations!";
                displayMessage(message);
                setsWonLeft++;
                displaySetsForTeamOnLeft(setsWonLeft);
                scoreTeamLeft = 0;
                scoreTeamRight = 0;
                displayScoreForTeamOnLeft(scoreTeamLeft);
                displayScoreForTeamOnRight(scoreTeamRight);
            } else {
                String tieBreak = "Tie break!";
                displayMessage(tieBreak);
            }
        }
    }
//Same as the previous method, but for the opposite side.
    public void add1pointToTeamOnRight(View view) {
        scoreTeamRight++;
        displayScoreForTeamOnRight(scoreTeamRight);
        switch (setNumber) {
            case 1: {
                if(!switched) {
                    displayOnTableS1_TB(scoreTeamRight);
                    firstSetScoreRight = scoreTeamRight;
                }
                else{
                    displayOnTableS1_TA(scoreTeamRight);
                    firstSetScoreLeft = scoreTeamRight;
                }
                break;
            }
            case 2: {
                if(!switched) {
                    displayOnTableS2_TB(scoreTeamRight);
                    secondSetScoreRight = scoreTeamRight;
                }
                else {
                    displayOnTableS2_TA(scoreTeamRight);
                    secondSetScoreLeft = scoreTeamRight;
                }
                break;
            }
            case 3: {
                if(!switched){
                    displayOnTableS3_TB(scoreTeamRight);
                    thirdSetScoreRight = scoreTeamRight;
                }
                else {
                    displayOnTableS3_TA(scoreTeamRight);
                    thirdSetScoreLeft = scoreTeamRight;
                }
                break;
            }
            case 4: {
                if(!switched) {
                    displayOnTableS4_TB(scoreTeamRight);
                    fourthSetScoreRight = scoreTeamRight;
                }
                else {
                    displayOnTableS4_TA(scoreTeamRight);
                    fourthSetScoreLeft = scoreTeamRight;
                }
                break;
            }
            case 5: {
                if(!switched){
                    displayOnTableS5_TB(scoreTeamRight);
                    fifthSetScoreRight = scoreTeamRight;
                }
                else{
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
                        if(!switched) {
                            firstSetScoreLeft = scoreTeamLeft;
                            firstSetScoreRight = scoreTeamRight;
                        }
                        else {
                            firstSetScoreRight = scoreTeamLeft;
                            firstSetScoreLeft = scoreTeamRight;
                        }
                        break;
                    }
                    case 2: {
                        if(!switched) {
                            secondSetScoreLeft = scoreTeamLeft;
                            secondSetScoreRight = scoreTeamRight;
                        }
                        else {
                            secondSetScoreRight = scoreTeamLeft;
                            secondSetScoreLeft = scoreTeamRight;
                        }
                        break;
                    }
                    case 3: {
                        if(!switched) {
                            thirdSetScoreLeft = scoreTeamLeft;
                            thirdSetScoreRight = scoreTeamRight;
                        }
                        else{
                            thirdSetScoreRight = scoreTeamLeft;
                            thirdSetScoreLeft = scoreTeamRight;
                        }
                        break;
                    }
                    case 4: {
                        if(!switched) {
                            fourthSetScoreLeft = scoreTeamLeft;
                            fourthSetScoreRight = scoreTeamRight;
                        }
                        else{
                            fourthSetScoreRight = scoreTeamLeft;
                            fourthSetScoreLeft = scoreTeamRight;
                        }
                        break;
                    }
                    case 5: {
                        if(!switched) {
                            fifthSetScoreLeft = scoreTeamLeft;
                            fifthSetScoreRight = scoreTeamRight;
                        }
                        else{
                            fifthSetScoreRight = scoreTeamLeft;
                            fifthSetScoreLeft = scoreTeamRight;
                        }
                        break;
                    }
                }
                setsWonRight++;
                displaySetsForTeamOnRight(setsWonRight);
                if (setsWonRight == 3) {
                    String winner = "Team B won the match!";
                    displayMessage(winner);
                    setNumber++;
                    scoreTeamLeft = 0;
                    scoreTeamRight = 0;
                    displayScoreForTeamOnLeft(scoreTeamLeft);
                    displayScoreForTeamOnRight(scoreTeamRight);
                } else {
                    String message = "Team B won the set!";
                    displayMessage(message);
                    setNumber++;
                    scoreTeamLeft = 0;
                    scoreTeamRight = 0;
                    displayScoreForTeamOnLeft(scoreTeamLeft);
                    displayScoreForTeamOnRight(scoreTeamRight);
                }
            } else {
                String message = teamNameRight + " will serve the ball.";
                displayMessage(message);
            }
        } else {
            if ((scoreTeamRight >= 15) && ((scoreTeamRight - scoreTeamLeft) >= 2)) {
                String message = "Team B won the match! Congratulations!";
                displayMessage(message);
                setsWonRight++;
                displaySetsForTeamOnLeft(setsWonRight);
                scoreTeamLeft = 0;
                scoreTeamRight = 0;
                displayScoreForTeamOnLeft(scoreTeamLeft);
                displayScoreForTeamOnRight(scoreTeamRight);
            } else {
                String tieBreak = "Tie break!";
                displayMessage(tieBreak);
            }
        }
    }
//This method exchange sides up on users click on the exchange button
    public void exchangeSides(View view) {
        if ((scoreTeamLeft == 0) && (scoreTeamRight == 0)) {
            if(switched) switched = false;
            else switched = true;
            int temporary = setsWonLeft;
            setsWonLeft = setsWonRight;
            setsWonRight = temporary;
            displaySetsForTeamOnLeft(setsWonLeft);
            displaySetsForTeamOnRight(setsWonRight);
            if (teamNameLeft.equals("Team A")) {
                teamNameLeft = "Team B";
                displayTeamNameonLeft(teamNameLeft);
                teamNameRight = "Team A";
                displayTeamNameonRight(teamNameRight);
            } else {
                teamNameLeft = "Team A";
                displayTeamNameonLeft(teamNameLeft);
                teamNameRight = "Team B";
                displayTeamNameonRight(teamNameRight);
            }
        }
    }
//This is for correcting a point mistakenly given.
    public void correctionLeft(View view) {
        if (scoreTeamLeft > 0) {
            scoreTeamLeft--;
            displayScoreForTeamOnLeft(scoreTeamLeft);
            String message = "Oops! Sorry, that was an error.";
            displayMessage(message);
        }
    }
    //This is for correcting a point mistakenly given.
    public void correctionRight(View view) {
        if (scoreTeamRight > 0) {
            scoreTeamRight--;
            displayScoreForTeamOnRight(scoreTeamRight);
            String message = "Oops! Sorry, that was an error.";
            displayMessage(message);
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
        teamNameLeft = "Team A";
        displayTeamNameonLeft(teamNameLeft);
        teamNameRight = "Team B";
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
    }
}
