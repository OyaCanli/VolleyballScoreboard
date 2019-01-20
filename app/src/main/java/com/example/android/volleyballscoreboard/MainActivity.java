package com.example.android.volleyballscoreboard;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.android.volleyballscoreboard.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //Initialize view model
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        binding.setViewmodel(mViewModel);

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

        if (savedInstanceState == null) {
            //Set these values in the view model
            mViewModel.setTeamNameForOrange(userChoise.getString(Constants.TEAM_NAME_ORANGE));
            mViewModel.setTeamNameForBlue(userChoise.getString(Constants.TEAM_NAME_BLUE));
            mViewModel.setTotalSetsToPlay(userChoise.getInt(Constants.NUMBER_OF_TOTAL_SETS));
            mViewModel.setSetFinishingScore(userChoise.getInt(Constants.SET_FINISHING_SCORE));
            mViewModel.setTieBreakerScore(userChoise.getInt(Constants.TIE_BREAKER_SCORE));
            mViewModel.setStarterTeamId(userChoise.getInt(Constants.STARTING_TEAM));
            if (mViewModel.getStarterTeamId() == R.id.optionBlue) {
                mViewModel.message.set(getString(R.string.serve, mViewModel.getTeamNameForBlue()));
            } else {
                mViewModel.message.set(getString(R.string.serve, mViewModel.getTeamNameForOrange()));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exchange_button: {
                exchangeSides();
                break;
            }
            case R.id.team_left_score: {
                if (!mViewModel.isSwitched.get()) {
                    add1pointToOranges();
                } else {
                    add1pointToBlues();
                }
                break;
            }
            case R.id.team_right_score: {
                if (mViewModel.isSwitched.get()) {
                    add1pointToOranges();
                } else {
                    add1pointToBlues();
                }
                break;
            }
            case R.id.pauseLeft: {
                if (!mViewModel.isSwitched.get()) {
                    pauseOrange();
                } else {
                    pauseBlue();
                }
                break;
            }
            case R.id.pauseRight: {
                if (mViewModel.isSwitched.get()) {
                    pauseOrange();
                } else {
                    pauseBlue();
                }
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

    public void add1pointToOranges() {
        int newScore = mViewModel.scoreOranges.get() + 1;
        mViewModel.scoreOranges.set(newScore);
        mViewModel.setLastPointer(Constants.TEAM_ORANGE);
        mViewModel.undoEnabled.set(true);
        mViewModel.setScoresOrange.set(mViewModel.setNumber.get(), newScore);
        if ((newScore >= mViewModel.getSetFinishingScoreForCurrentSet()) && ((newScore - mViewModel.scoreBlues.get()) >= 2)) {
            //if it is a set winning point..
            mViewModel.setsWonOrange.set(mViewModel.setsWonOrange.get() + 1);
            if (mViewModel.setsWonOrange.get() == (mViewModel.getTotalSetsToPlay() + 1) / 2) {
                //if it is a match winning set
                mViewModel.message.set(getString(R.string.wonMatch, mViewModel.getTeamNameForOrange()));
            } else { //if it is not a match winning set
                mViewModel.message.set(getString(R.string.wonSet, mViewModel.getTeamNameForOrange()));
                mViewModel.setNumber.set(mViewModel.setNumber.get() + 1);
                mViewModel.scoreOranges.set(0);
                mViewModel.scoreBlues.set(0);
            }
        } else { //if it is not a set winning point
            mViewModel.message.set(getString(R.string.serve, mViewModel.getTeamNameForOrange()));
        }
    }

    public void add1pointToBlues() {
        int newScore = mViewModel.scoreBlues.get() + 1;
        mViewModel.scoreBlues.set(newScore);
        mViewModel.setLastPointer(Constants.TEAM_BLUE);
        mViewModel.undoEnabled.set(true);
        mViewModel.setScoresBlue.set(mViewModel.setNumber.get(), newScore);
        if ((newScore >= mViewModel.getSetFinishingScoreForCurrentSet()) && ((newScore - mViewModel.scoreOranges.get()) >= 2)) {
            //if it is a set winning point..
            mViewModel.setsWonBlue.set(mViewModel.setsWonBlue.get() + 1);
            if (mViewModel.setsWonBlue.get() == (mViewModel.getTotalSetsToPlay() + 1) / 2) {
                //if it is a match winning set
                mViewModel.message.set(getString(R.string.wonMatch, mViewModel.getTeamNameForBlue()));
            } else { //if it is not a match winning set
                mViewModel.message.set(getString(R.string.wonSet, mViewModel.getTeamNameForBlue()));
                mViewModel.setNumber.set(mViewModel.setNumber.get() + 1);
                mViewModel.scoreOranges.set(0);
                mViewModel.scoreBlues.set(0);
            }
        } else { //if it is not a set winning point
            mViewModel.message.set(getString(R.string.serve, mViewModel.getTeamNameForBlue()));
        }
    }

    //This method exchange sides up on users click on the exchange button
    public void exchangeSides() {
        if ((mViewModel.scoreOranges.get() == 0) && (mViewModel.scoreBlues.get() == 0)) {
            mViewModel.isSwitched.set(!mViewModel.isSwitched.get());
        } else {
            Toast.makeText(this, R.string.exchange_only_between_sets, Toast.LENGTH_SHORT).show();
        }
    }

    public void pauseOrange() {
        int timeOffCount = mViewModel.getTimeOffCountOrange();
        if (timeOffCount == 2) {
            Toast.makeText(this, R.string.all_timeoffs_used, Toast.LENGTH_SHORT).show();
            return;
        }
        openCountDownTimer();
        mViewModel.setTimeOffCountOrange(timeOffCount + 1);
        int rest = 2 - timeOffCount;
        String message = getString(R.string.used_timeoffs, timeOffCount, rest);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void pauseBlue() {
        int timeOffCount = mViewModel.getTimeOffCountBlue();
        if (timeOffCount == 2) {
            Toast.makeText(this, R.string.all_timeoffs_used, Toast.LENGTH_SHORT).show();
            return;
        }
        openCountDownTimer();
        mViewModel.setTimeOffCountBlue(timeOffCount + 1);
        int rest = 2 - timeOffCount;
        String message = getString(R.string.used_timeoffs, timeOffCount, rest);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void openCountDownTimer() {
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Time left:");
        intent.putExtra(AlarmClock.EXTRA_LENGTH, 30);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void undo() {
        switch (mViewModel.getLastPointer()) {
            case Constants.START: {
                //Do nothing
                break;
            }
            case Constants.TEAM_ORANGE: {
                correctionOrange();
                mViewModel.undoEnabled.set(false);
                break;
            }
            case Constants.TEAM_BLUE: {
                correctionBlue();
                mViewModel.undoEnabled.set(false);
                break;
            }
        }
    }

    //This is for correcting a point mistakenly given.
    public void correctionOrange() {
        mViewModel.message.set(getString(R.string.error));
        if (mViewModel.scoreOranges.get() > 0) {
            mViewModel.scoreOranges.set(mViewModel.scoreOranges.get() - 1);
            mViewModel.setScoresOrange.set(mViewModel.setNumber.get(), mViewModel.scoreOranges.get());
        } else if (mViewModel.setNumber.get() > 0) {
            //It is was a set winning point
            mViewModel.setNumber.set(mViewModel.setNumber.get() - 1);
            mViewModel.setsWonOrange.set(mViewModel.setsWonOrange.get() - 1);
            int previousScoreOfOranges = mViewModel.setScoresOrange.get(mViewModel.setNumber.get()) - 1;
            mViewModel.scoreOranges.set(previousScoreOfOranges);
            mViewModel.setScoresOrange.set(mViewModel.setNumber.get(), previousScoreOfOranges);
            mViewModel.scoreBlues.set(mViewModel.setScoresBlue.get(mViewModel.setNumber.get()));
        }
    }

    //This is for correcting a point mistakenly given.
    public void correctionBlue() {
        mViewModel.message.set(getString(R.string.error));
        if (mViewModel.scoreBlues.get() > 0) {
            mViewModel.scoreBlues.set(mViewModel.scoreBlues.get() - 1);
            mViewModel.setScoresBlue.set(mViewModel.setNumber.get(), mViewModel.scoreBlues.get());
        } else if (mViewModel.setNumber.get() > 0) {
            //It is was a set winning point
            mViewModel.setNumber.set(mViewModel.setNumber.get() - 1);
            mViewModel.setsWonBlue.set(mViewModel.setsWonBlue.get() - 1);
            int previousScoreOfBlues = mViewModel.setScoresBlue.get(mViewModel.setNumber.get()) - 1;
            mViewModel.scoreBlues.set(previousScoreOfBlues);
            mViewModel.setScoresBlue.set(mViewModel.setNumber.get(), previousScoreOfBlues);
            mViewModel.scoreOranges.set(mViewModel.setScoresOrange.get(mViewModel.setNumber.get()));
        }
    }

    public void reset() {
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
