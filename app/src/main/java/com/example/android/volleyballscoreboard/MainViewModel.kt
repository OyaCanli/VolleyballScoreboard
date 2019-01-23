package com.example.android.volleyballscoreboard

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt

class MainViewModel : ViewModel() {

    val scoreOranges = ObservableInt(0)
    val scoreBlues = ObservableInt(0)
    val setsWonOrange = ObservableInt(0)
    val setsWonBlue = ObservableInt(0)
    val setNumber = ObservableInt(0)
    val isSwitched = ObservableBoolean(false)
    val undoEnabled = ObservableBoolean(false)
    val setScoresOrange = ObservableArrayList<Int>()
    val setScoresBlue = ObservableArrayList<Int>()
    val message = ObservableField<String>()

    var teamNameForOrange = "Oranges"
    var teamNameForBlue = "Blues"
    internal var totalSetsToPlay = 5
    internal var setFinishingScore = 25
    var tieBreakerScore = 15
    internal var starterTeamId = R.id.optionOrange
    internal var lastPointer = START
    var timeOffCountOrange = 0
    var timeOffCountBlue = 0

    internal val setFinishingScoreForCurrentSet: Int
        get() = if (setNumber.get() < totalSetsToPlay) setFinishingScore else tieBreakerScore

    init {
        //Initialize lists with Os
        for (i in 0..4) {
            setScoresOrange.add(0)
            setScoresBlue.add(0)
        }
    }

}
