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
                mapOfOranges[TEAM_NAME] = userChoice?.getString(KEY_TEAM_NAME_ORANGE) ?: "Oranges"
                mapOfBlues[TEAM_NAME] = userChoice?.getString(KEY_TEAM_NAME_BLUE) ?: "Blues"
                totalSetsToPlay = userChoice?.getInt(KEY_NUMBER_OF_TOTAL_SETS) ?: 5
                setFinishingScore = userChoice?.getInt(KEY_SET_FINISHING_SCORE) ?: 25
                tieBreakerScore = userChoice?.getInt(KEY_TIE_BREAKER_SCORE) ?: 15
                starterTeamId = userChoice?.getInt(KEY_STARTING_TEAM) ?: R.id.optionOrange
                message.set(getString(R.string.serve,
                        if (starterTeamId == R.id.optionBlue) mapOfBlues[TEAM_NAME]
                        else mapOfOranges[TEAM_NAME]))
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.exchange_button -> exchangeSides()
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
            R.id.reset -> reset()
        }
    }

    //This method exchange sides up on users click on the exchange button
    private fun exchangeSides() {
        if (mViewModel.mapOfOranges[SCORE] == 0 && mViewModel.mapOfBlues[SCORE] == 0) {
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
