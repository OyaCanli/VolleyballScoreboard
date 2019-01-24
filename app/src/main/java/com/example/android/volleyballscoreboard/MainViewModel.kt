package com.example.android.volleyballscoreboard

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Context
import android.databinding.ObservableArrayList
import android.databinding.ObservableArrayMap
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.Log
import android.view.View

const val TEAM_NAME = "teamName"
const val SCORE = "score"
const val SETS_WON = "setsWon"
const val LIST_OF_SET_SCORES = "listOfSetScores"
const val START = "start"
const val TEAM_ORANGE = "Oranges"
const val TEAM_BLUE = "Blues"

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var setNumber = 0
    val isSwitched = ObservableBoolean(false)
    val undoEnabled = ObservableBoolean(false)
    val message = ObservableField<String>()

    internal var totalSetsToPlay = 5
    internal var setFinishingScore = 25
    internal var tieBreakerScore = 15
    internal var starterTeamId = R.id.optionOrange
    private var lastPointer = START
    var timeOffCountOrange = 0
    var timeOffCountBlue = 0

    val mapOfOranges = ObservableArrayMap<String, Any>().apply {
        put(TEAM_NAME, TEAM_ORANGE)
        put(SCORE, 0)
        put(SETS_WON, 0)
        put(LIST_OF_SET_SCORES, ObservableArrayList<Int>().apply {
            repeat(5) { add(0) }
        })
    }

    val mapOfBlues = ObservableArrayMap<String, Any>().apply {
        put(TEAM_NAME, TEAM_BLUE)
        put(SCORE, 0)
        put(SETS_WON, 0)
        put(LIST_OF_SET_SCORES, ObservableArrayList<Int>().apply {
            repeat(5) { add(0) }
        })
    }

    val setScoresOrange: ObservableArrayList<Int>
        get() = mapOfOranges[LIST_OF_SET_SCORES] as ObservableArrayList<Int>

    val setScoresBlue: ObservableArrayList<Int>
        get() = mapOfBlues[LIST_OF_SET_SCORES] as ObservableArrayList<Int>

    private val context: Context by lazy {
        application.applicationContext
    }

    private val setFinishingScoreForCurrentSet: Int
        get() = if (setNumber < totalSetsToPlay) setFinishingScore else tieBreakerScore

    fun add1pointToOranges(view: View) = addPoint(mapOfOranges, mapOfBlues)
    fun add1pointToBlues(view: View) = addPoint(mapOfBlues, mapOfOranges)

    private fun addPoint(mapOfPointMaker: ObservableArrayMap<String, Any>, mapOfOpponent: ObservableArrayMap<String, Any>) {
        val newScore = (mapOfPointMaker[SCORE] as Int) + 1
        mapOfPointMaker[SCORE] = newScore
        lastPointer = mapOfPointMaker[TEAM_NAME] as String
        undoEnabled.set(true)
        (mapOfPointMaker[LIST_OF_SET_SCORES] as ArrayList<Int>)[setNumber] = newScore
        if (newScore >= setFinishingScoreForCurrentSet && newScore - (mapOfOpponent[SCORE] as Int) >= 2) {
            //if it is a set winning point..
            mapOfPointMaker[SETS_WON] = (mapOfPointMaker[SETS_WON] as Int) + 1
            if ((mapOfPointMaker[SETS_WON] as Int) == (totalSetsToPlay + 1) / 2) {
                //if it is a match winning set
                message.set(context.getString(R.string.wonMatch, mapOfPointMaker[TEAM_NAME] as String))
            } else { //if it is not a match winning set
                message.set(context.getString(R.string.wonSet, mapOfPointMaker[TEAM_NAME] as String))
                setNumber++
                mapOfPointMaker[SCORE] = 0
                mapOfOpponent[SCORE] = 0
            }
        } else { //if it is not a set winning point
            message.set(context.getString(R.string.serve, mapOfPointMaker[TEAM_NAME] as String))
        }
    }

    fun undo(view: View) {
        Log.d("UNDO", "lastPointer: $lastPointer")
        when (lastPointer) {
            START -> {
            } //Do nothing
            TEAM_ORANGE -> correction(mapOfOranges, mapOfBlues)
            TEAM_BLUE -> correction(mapOfBlues, mapOfOranges)
        }
    }

    private fun correction(mapOfPointMaker: ObservableArrayMap<String, Any>, mapOfOpponent: ObservableArrayMap<String, Any>) {
        undoEnabled.set(false)
        message.set(context.getString(R.string.error))
        if (mapOfPointMaker[SCORE] as Int > 0) {
            mapOfPointMaker[SCORE] = (mapOfPointMaker[SCORE] as Int) - 1
            (mapOfPointMaker[LIST_OF_SET_SCORES] as ArrayList<Int>)[setNumber] = mapOfPointMaker[SCORE] as Int
        } else if (setNumber > 0) {
            setNumber--
            mapOfPointMaker[SETS_WON] = (mapOfPointMaker[SETS_WON] as Int) - 1
            mapOfPointMaker[SCORE] = (mapOfPointMaker[LIST_OF_SET_SCORES] as ArrayList<Int>)[setNumber] - 1
            mapOfOpponent[SCORE] = (mapOfOpponent[LIST_OF_SET_SCORES] as ArrayList<Int>)[setNumber]
        }
    }
}