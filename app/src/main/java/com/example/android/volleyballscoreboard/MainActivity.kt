package com.example.android.volleyballscoreboard

import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.provider.AlarmClock
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast

import com.example.android.volleyballscoreboard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewmodel = mViewModel
        //set click listeners on buttons
        binding.teamLeftScore.setOnClickListener(this)
        binding.teamRightScore.setOnClickListener(this)
        binding.exchangeButton.setOnClickListener(this)
        binding.pauseLeft.setOnClickListener(this)
        binding.pauseRight.setOnClickListener(this)
        binding.undo.setOnClickListener(this)
        binding.reset.setOnClickListener(this)

        //get user choices from the previous activity and display
        val userChoice = intent.extras

        if (savedInstanceState == null) {
            //Set these values in the view model
            with(mViewModel) {
                teamNameForOrange = userChoice?.getString(TEAM_NAME_ORANGE) ?: "Oranges"
                teamNameForBlue = userChoice?.getString(TEAM_NAME_BLUE) ?: "Blues"
                totalSetsToPlay = userChoice?.getInt(NUMBER_OF_TOTAL_SETS) ?: 5
                setFinishingScore = userChoice?.getInt(SET_FINISHING_SCORE) ?: 25
                tieBreakerScore = userChoice?.getInt(TIE_BREAKER_SCORE) ?: 15
                starterTeamId = userChoice?.getInt(STARTING_TEAM) ?: R.id.optionOrange
                message.set(getString(R.string.serve,
                        if (starterTeamId == R.id.optionBlue) teamNameForBlue
                        else teamNameForOrange))
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.exchange_button -> {
                exchangeSides()
            }
            R.id.team_left_score -> {
                if (!mViewModel.isSwitched.get()) {
                    add1pointToOranges()
                } else {
                    add1pointToBlues()
                }
            }
            R.id.team_right_score -> {
                if (mViewModel.isSwitched.get()) {
                    add1pointToOranges()
                } else {
                    add1pointToBlues()
                }
            }
            R.id.pauseLeft -> {
                if (!mViewModel.isSwitched.get()) {
                    pauseOrange()
                } else {
                    pauseBlue()
                }
            }
            R.id.pauseRight -> {
                if (mViewModel.isSwitched.get()) {
                    pauseOrange()
                } else {
                    pauseBlue()
                }
            }
            R.id.undo -> {
                undo()
            }

            R.id.reset -> {
                reset()
            }
        }
    }

    private fun add1pointToOranges() {
        val newScore = mViewModel.scoreOranges.get() + 1
        mViewModel.scoreOranges.set(newScore)
        mViewModel.lastPointer = TEAM_ORANGE
        mViewModel.undoEnabled.set(true)
        mViewModel.setScoresOrange[mViewModel.setNumber.get()] = newScore
        if (newScore >= mViewModel.setFinishingScoreForCurrentSet && newScore - mViewModel.scoreBlues.get() >= 2) {
            //if it is a set winning point..
            mViewModel.setsWonOrange.set(mViewModel.setsWonOrange.get() + 1)
            if (mViewModel.setsWonOrange.get() == (mViewModel.totalSetsToPlay + 1) / 2) {
                //if it is a match winning set
                mViewModel.message.set(getString(R.string.wonMatch, mViewModel.teamNameForOrange))
            } else { //if it is not a match winning set
                mViewModel.message.set(getString(R.string.wonSet, mViewModel.teamNameForOrange))
                mViewModel.setNumber.set(mViewModel.setNumber.get() + 1)
                mViewModel.scoreOranges.set(0)
                mViewModel.scoreBlues.set(0)
            }
        } else { //if it is not a set winning point
            mViewModel.message.set(getString(R.string.serve, mViewModel.teamNameForOrange))
        }
    }

    private fun add1pointToBlues() {
        val newScore = mViewModel.scoreBlues.get() + 1
        mViewModel.scoreBlues.set(newScore)
        mViewModel.lastPointer = TEAM_BLUE
        mViewModel.undoEnabled.set(true)
        mViewModel.setScoresBlue[mViewModel.setNumber.get()] = newScore
        if (newScore >= mViewModel.setFinishingScoreForCurrentSet && newScore - mViewModel.scoreOranges.get() >= 2) {
            //if it is a set winning point..
            mViewModel.setsWonBlue.set(mViewModel.setsWonBlue.get() + 1)
            if (mViewModel.setsWonBlue.get() == (mViewModel.totalSetsToPlay + 1) / 2) {
                //if it is a match winning set
                mViewModel.message.set(getString(R.string.wonMatch, mViewModel.teamNameForBlue))
            } else { //if it is not a match winning set
                mViewModel.message.set(getString(R.string.wonSet, mViewModel.teamNameForBlue))
                mViewModel.setNumber.set(mViewModel.setNumber.get() + 1)
                mViewModel.scoreOranges.set(0)
                mViewModel.scoreBlues.set(0)
            }
        } else { //if it is not a set winning point
            mViewModel.message.set(getString(R.string.serve, mViewModel.teamNameForBlue))
        }
    }

    //This method exchange sides up on users click on the exchange button
    private fun exchangeSides() {
        if (mViewModel.scoreOranges.get() == 0 && mViewModel.scoreBlues.get() == 0) {
            mViewModel.isSwitched.set(!mViewModel.isSwitched.get())
        } else {
            Toast.makeText(this, R.string.exchange_only_between_sets, Toast.LENGTH_SHORT).show()
        }
    }

    private fun pauseOrange() {
        val timeOffCount = mViewModel.timeOffCountOrange
        if (timeOffCount == 2) {
            Toast.makeText(this, R.string.all_timeoffs_used, Toast.LENGTH_SHORT).show()
            return
        }
        openCountDownTimer()
        mViewModel.timeOffCountOrange = timeOffCount + 1
        val rest = 2 - timeOffCount
        val message = getString(R.string.used_timeoffs, timeOffCount, rest)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun pauseBlue() {
        val timeOffCount = mViewModel.timeOffCountBlue
        if (timeOffCount == 2) {
            Toast.makeText(this, R.string.all_timeoffs_used, Toast.LENGTH_SHORT).show()
            return
        }
        openCountDownTimer()
        mViewModel.timeOffCountBlue = timeOffCount + 1
        val rest = 2 - timeOffCount
        val message = getString(R.string.used_timeoffs, timeOffCount, rest)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun openCountDownTimer() {
        val intent = Intent(AlarmClock.ACTION_SET_TIMER)
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Time left:")
        intent.putExtra(AlarmClock.EXTRA_LENGTH, 30)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun undo() {
        when (mViewModel.lastPointer) {
            START -> {
            }//Do nothing
            TEAM_ORANGE -> {
                correctionOrange()
                mViewModel.undoEnabled.set(false)
            }
            TEAM_BLUE -> {
                correctionBlue()
                mViewModel.undoEnabled.set(false)
            }
        }
    }

    //This is for correcting a point mistakenly given.
    private fun correctionOrange() {
        mViewModel.message.set(getString(R.string.error))
        if (mViewModel.scoreOranges.get() > 0) {
            mViewModel.scoreOranges.set(mViewModel.scoreOranges.get() - 1)
            mViewModel.setScoresOrange[mViewModel.setNumber.get()] = mViewModel.scoreOranges.get()
        } else if (mViewModel.setNumber.get() > 0) {
            //It is was a set winning point
            mViewModel.setNumber.set(mViewModel.setNumber.get() - 1)
            mViewModel.setsWonOrange.set(mViewModel.setsWonOrange.get() - 1)
            val previousScoreOfOranges = mViewModel.setScoresOrange[mViewModel.setNumber.get()] - 1
            mViewModel.scoreOranges.set(previousScoreOfOranges)
            mViewModel.setScoresOrange[mViewModel.setNumber.get()] = previousScoreOfOranges
            mViewModel.scoreBlues.set(mViewModel.setScoresBlue[mViewModel.setNumber.get()])
        }
    }

    //This is for correcting a point mistakenly given.
    private fun correctionBlue() {
        mViewModel.message.set(getString(R.string.error))
        if (mViewModel.scoreBlues.get() > 0) {
            mViewModel.scoreBlues.set(mViewModel.scoreBlues.get() - 1)
            mViewModel.setScoresBlue[mViewModel.setNumber.get()] = mViewModel.scoreBlues.get()
        } else if (mViewModel.setNumber.get() > 0) {
            //It is was a set winning point
            mViewModel.setNumber.set(mViewModel.setNumber.get() - 1)
            mViewModel.setsWonBlue.set(mViewModel.setsWonBlue.get() - 1)
            val previousScoreOfBlues = mViewModel.setScoresBlue[mViewModel.setNumber.get()] - 1
            mViewModel.scoreBlues.set(previousScoreOfBlues)
            mViewModel.setScoresBlue[mViewModel.setNumber.get()] = previousScoreOfBlues
            mViewModel.scoreOranges.set(mViewModel.setScoresOrange[mViewModel.setNumber.get()])
        }
    }

    private fun reset() {
        val builder = AlertDialog.Builder(this@MainActivity, R.style.Theme_AppCompat_DayNight_Dialog)
        builder.setMessage(R.string.reset_warning)
        builder.setPositiveButton(R.string.reset) { _, _ ->
            val intent = intent
            finish()
            startActivity(intent)
        }
        builder.setNeutralButton(R.string.settings) { _, _ ->
            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            finish()
            startActivity(intent)
        }
        builder.setNegativeButton(R.string.cancel) { _, _ -> }
        builder.create()
        builder.show()
    }
}
