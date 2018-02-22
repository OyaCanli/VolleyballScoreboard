package com.example.android.volleyballscoreboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

/**
 * Created by Oya on 22-02-18.
 */

public class SettingsActivity extends AppCompatActivity {

    EditText eText1, eText2;
    String teamNameLeft, teamNameRight;
    RadioGroup starter;
    public final static String TEAM_NAME_LEFT = "teamNameLeft";
    public final static String TEAM_NAME_RIGHT = "teamNameRight";
    public final static String STARTING_TEAM = "startingTeam";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        eText1 = findViewById(R.id.usersTeamName1);
        eText2 = findViewById(R.id.usersTeamName2);
        starter = findViewById(R.id.whoStarts);
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
