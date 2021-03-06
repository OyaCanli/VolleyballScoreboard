package com.example.android.volleyballscoreboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.android.volleyballscoreboard.databinding.ActivitySettingsBinding

/**
 * Created by Oya on 22-02-18.
 */

class SettingsActivity : AppCompatActivity() {

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(SettingsViewModel::class.java)
    }
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        binding.viewModel = mViewModel

        //Initialize spinners
        initializeSpinner(R.array.numberOfSets, binding.spin1)
        initializeSpinner(R.array.setFinishingScore, binding.spin2)
        initializeSpinner(R.array.tieBreakerScore, binding.spin3)
    }

    private fun initializeSpinner(arrayId: Int, spinner: Spinner) {
        val spinAdapter = ArrayAdapter.createFromResource(this,
                arrayId, R.layout.spinner_item)
        spinAdapter.setDropDownViewResource(R.layout.spinner_dropdown)
        spinner.adapter = spinAdapter
    }

    fun startGame(view: View) {
        val userChoices = Intent(this@SettingsActivity, MainActivity::class.java)
        with(mViewModel) {
            userChoices.putExtra(KEY_TEAM_NAME_ORANGE, orangeTeamName)
            userChoices.putExtra(KEY_TEAM_NAME_BLUE, blueTeamName)
            userChoices.putExtra(KEY_STARTING_TEAM, starterTeamId)
            userChoices.putExtra(KEY_NUMBER_OF_TOTAL_SETS, numbersOfTotalSets)
            userChoices.putExtra(KEY_SET_FINISHING_SCORE, setFinishingScore)
            userChoices.putExtra(KEY_TIE_BREAKER_SCORE, tieBreakerScore)
        }
        startActivity(userChoices)
    }

    fun flipCoin(view: View) {
        var chance = Math.random()
        chance *= 2.0
        binding.coin.setImageResource(if (chance < 1) R.drawable.head else R.drawable.tails)
    }
}
