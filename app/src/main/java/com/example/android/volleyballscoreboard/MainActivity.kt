package com.example.android.volleyballscoreboard

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.android.volleyballscoreboard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_main)
        binding.viewmodel = mViewModel
        binding.reset.setOnClickListener {
            reset()
        }
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

    private fun reset() {
        val builder = AlertDialog.Builder(this@MainActivity, R.style.Theme_AppCompat_DayNight_Dialog)
        with(builder) {
            setMessage(R.string.reset_warning)
            setPositiveButton(R.string.reset) { _, _ -> launchActivity<MainActivity>() }
            setNeutralButton(R.string.settings) { _, _ -> launchActivity<SettingsActivity>() }
            setNegativeButton(R.string.cancel) { _, _ -> }
            create()
            show()
        }
    }
}
