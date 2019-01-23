package com.example.android.volleyballscoreboard

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Context
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt

class MainViewModel(application: Application) : AndroidViewModel(application) {

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
    val context: Context by lazy {
        application.applicationContext
    }

    internal val setFinishingScoreForCurrentSet: Int
        get() = if (setNumber.get() < totalSetsToPlay) setFinishingScore else tieBreakerScore

    init {
        //Initialize lists with Os
        for (i in 0..4) {
            setScoresOrange.add(0)
            setScoresBlue.add(0)
        }
    }

    fun add1pointToOranges() {
        val newScore = scoreOranges.get() + 1
        scoreOranges.set(newScore)
        lastPointer = TEAM_ORANGE
        undoEnabled.set(true)
        setScoresOrange[setNumber.get()] = newScore
        if (newScore >= setFinishingScoreForCurrentSet && newScore - scoreBlues.get() >= 2) {
            //if it is a set winning point..
            setsWonOrange.set(setsWonOrange.get() + 1)
            if (setsWonOrange.get() == (totalSetsToPlay + 1) / 2) {
                //if it is a match winning set
                message.set(context.getString(R.string.wonMatch, teamNameForOrange))
            } else { //if it is not a match winning set
                message.set(context.getString(R.string.wonSet, teamNameForOrange))
                setNumber.set(setNumber.get() + 1)
                scoreOranges.set(0)
                scoreBlues.set(0)
            }
        } else { //if it is not a set winning point
            message.set(context.getString(R.string.serve, teamNameForOrange))
        }
    }

    fun add1pointToBlues() {
        val newScore = scoreBlues.get() + 1
        scoreBlues.set(newScore)
        lastPointer = TEAM_BLUE
        undoEnabled.set(true)
        setScoresBlue[setNumber.get()] = newScore
        if (newScore >= setFinishingScoreForCurrentSet && newScore - scoreOranges.get() >= 2) {
            //if it is a set winning point..
            setsWonBlue.set(setsWonBlue.get() + 1)
            if (setsWonBlue.get() == (totalSetsToPlay + 1) / 2) {
                //if it is a match winning set
                message.set(context.getString(R.string.wonMatch, teamNameForBlue))
            } else { //if it is not a match winning set
                message.set(context.getString(R.string.wonSet, teamNameForBlue))
                setNumber.set(setNumber.get() + 1)
                scoreOranges.set(0)
                scoreBlues.set(0)
            }
        } else { //if it is not a set winning point
            message.set(context.getString(R.string.serve, teamNameForBlue))
        }
    }

    //This is for correcting a point mistakenly given.
    fun correctionOrange() {
        message.set(context.getString(R.string.error))
        if (scoreOranges.get() > 0) {
            scoreOranges.set(scoreOranges.get() - 1)
            setScoresOrange[setNumber.get()] = scoreOranges.get()
        } else if (setNumber.get() > 0) {
            //It is was a set winning point
            setNumber.set(setNumber.get() - 1)
            setsWonOrange.set(setsWonOrange.get() - 1)
            val previousScoreOfOranges = setScoresOrange[setNumber.get()] - 1
            scoreOranges.set(previousScoreOfOranges)
            setScoresOrange[setNumber.get()] = previousScoreOfOranges
            scoreBlues.set(setScoresBlue[setNumber.get()])
        }
    }

    //This is for correcting a point mistakenly given.
    fun correctionBlue() {
        message.set(context.getString(R.string.error))
        if (scoreBlues.get() > 0) {
            scoreBlues.set(scoreBlues.get() - 1)
            setScoresBlue[setNumber.get()] = scoreBlues.get()
        } else if (setNumber.get() > 0) {
            //It is was a set winning point
            setNumber.set(setNumber.get() - 1)
            setsWonBlue.set(setsWonBlue.get() - 1)
            val previousScoreOfBlues = setScoresBlue[setNumber.get()] - 1
            scoreBlues.set(previousScoreOfBlues)
            setScoresBlue[setNumber.get()] = previousScoreOfBlues
            scoreOranges.set(setScoresOrange[setNumber.get()])
        }
    }

}


