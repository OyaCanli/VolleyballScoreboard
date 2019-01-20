package com.example.android.volleyballscoreboard;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.android.volleyballscoreboard.databinding.ActivitySettingsBinding;

/**
 * Created by Oya on 22-02-18.
 */

public class SettingsActivity extends AppCompatActivity {

    private SettingsViewModel mViewModel;
    private ActivitySettingsBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        mViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        binding.setViewModel(mViewModel);

        //Initialize spinners
        initializeSpinner(R.array.numberOfSets, binding.spin1);
        initializeSpinner(R.array.setFinishingScore, binding.spin2);
        initializeSpinner(R.array.tieBreakerScore, binding.spin3);
    }

    private void initializeSpinner(int arrayId, Spinner spinner) {
        ArrayAdapter<CharSequence> spinAdapter1 = ArrayAdapter.createFromResource(this,
                arrayId, R.layout.spinner_item);
        spinAdapter1.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(spinAdapter1);
    }

    public void startGame(View view) {
        Intent userChoices = new Intent(SettingsActivity.this, MainActivity.class);
        userChoices.putExtra(Constants.TEAM_NAME_ORANGE, mViewModel.getOrangeTeamName());
        userChoices.putExtra(Constants.TEAM_NAME_BLUE, mViewModel.getBlueTeamName());
        userChoices.putExtra(Constants.STARTING_TEAM, mViewModel.getStarterTeamId());
        userChoices.putExtra(Constants.NUMBER_OF_TOTAL_SETS, mViewModel.getNumbersOfTotalSets());
        userChoices.putExtra(Constants.SET_FINISHING_SCORE, mViewModel.getSetFinishingScore());
        userChoices.putExtra(Constants.TIE_BREAKER_SCORE, mViewModel.getTieBreakerScore());
        startActivity(userChoices);
    }

    public void flipCoin(View view) {
        double chance = Math.random();
        chance *= 2;
        if (chance < 1) { //heads
            binding.coin.setImageResource(R.drawable.head);
        } else { //tails
            binding.coin.setImageResource(R.drawable.tails);
        }
    }
}
