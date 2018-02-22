package com.example.android.volleyballscoreboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.math.BigDecimal;

/**
 * Created by Oya on 22-02-18.
 */

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText eText1, eText2;
    String teamNameLeft, teamNameRight;
    RadioGroup starter;
    int numbersOfTotalSets, setFinishingScore, tieBreakerScore;
    public final static String TEAM_NAME_LEFT = "teamNameLeft";
    public final static String TEAM_NAME_RIGHT = "teamNameRight";
    public final static String STARTING_TEAM = "startingTeam";
    public final static String NUMBER_OF_TOTAL_SETS = "numberOfTotalSets";
    public final static String SET_FINISHING_SCORE = "setFinishingScore";
    public final static String TIE_BREAKER_SCORE = "tieBreakerScore";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //Initialize spinner 1
        Spinner spin1 = findViewById(R.id.spin1);
        String[] numberOfSets = getResources().getStringArray(R.array.numberOfSets);
        ArrayAdapter<String> spinAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, numberOfSets);
        spinAdapter1.setDropDownViewResource(R.layout.spinner_dropdown);
        spin1.setAdapter(spinAdapter1);
        spin1.setOnItemSelectedListener(this);
        //Initialize spinner 2
        Spinner spin2 = findViewById(R.id.spin2);
        String[] setFinishingScores = getResources().getStringArray(R.array.setFinishingScore);
        ArrayAdapter<String> spinAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, setFinishingScores);
        spinAdapter2.setDropDownViewResource(R.layout.spinner_dropdown);
        spin2.setAdapter(spinAdapter2);
        spin2.setOnItemSelectedListener(this);
        //Initialize spinner 3
        Spinner spin3 = findViewById(R.id.spin3);
        String[] tieBreakerScore = getResources().getStringArray(R.array.tieBreakerScore);
        ArrayAdapter<String> spinAdapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tieBreakerScore);
        spinAdapter3.setDropDownViewResource(R.layout.spinner_dropdown);
        spin3.setAdapter(spinAdapter3);
        spin3.setOnItemSelectedListener(this);

        eText1 = findViewById(R.id.usersTeamName1);
        eText2 = findViewById(R.id.usersTeamName2);
        starter = findViewById(R.id.whoStarts);
    }

    public void onItemSelected(AdapterView<?> spinner, View arg1, int position, long row_id) {
        switch (spinner.getId()) {
            case R.id.spin1: {
                if(position == 0) numbersOfTotalSets = 3;
                else if (position == 1) numbersOfTotalSets = 5;
                break;
            }
            case R.id.spin2: {
                if(position == 0) setFinishingScore = 15;
                else if (position == 1) setFinishingScore = 25;
                break;
            }
            case R.id.spin3: {
                if(position == 0) tieBreakerScore = 15;
                else if (position == 1) tieBreakerScore = 25;
                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    public void startGame(View view) {
        teamNameLeft = eText1.getText().toString();
        if (teamNameLeft.equals("")) {
            teamNameLeft = getString(R.string.defaultTeamNameOnLeft);
        }

        teamNameRight = eText2.getText().toString();
        if (teamNameRight.equals("")) {
            teamNameRight = getString(R.string.defaultTeamNameOnRight);
        }

        Intent userChoices = new Intent(SettingsActivity.this, MainActivity.class);
        userChoices.putExtra(TEAM_NAME_LEFT, teamNameLeft);
        userChoices.putExtra(TEAM_NAME_RIGHT, teamNameRight);
        userChoices.putExtra(STARTING_TEAM, starter.getCheckedRadioButtonId());
        userChoices.putExtra(NUMBER_OF_TOTAL_SETS, numbersOfTotalSets);
        userChoices.putExtra(SET_FINISHING_SCORE, setFinishingScore);
        userChoices.putExtra(TIE_BREAKER_SCORE, tieBreakerScore);
        startActivity(userChoices);
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
}
