package com.example.android.volleyballscoreboard

import androidx.databinding.adapters.AdapterViewBindingAdapter
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    var orangeTeamName: String = "Oranges"
    var blueTeamName: String = "Blues"
    var starterTeamId: Int = 0
    //Initialize these with default values
    internal var numbersOfTotalSets = 5
        private set
    internal var setFinishingScore = 25
        private set
    internal var tieBreakerScore = 15
        private set

    var spinnerListener = AdapterViewBindingAdapter.OnItemSelected { spinner, _, position, _ ->
        //the method is used by all spinners.
        //when statement differentiate which spinners is selected
        //Then if-else statements capture user's choices
        when (spinner?.id) {
            R.id.spin1 -> {
                numbersOfTotalSets = if (position == 0) 5 else 3
            }
            R.id.spin2 -> {
                setFinishingScore = if (position == 0) 25 else 15
            }
            R.id.spin3 -> {
                tieBreakerScore = if (position == 0) 15 else 25
            }
        }
    }
}
