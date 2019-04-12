package com.example.android.volleyballscoreboard

import android.app.Application
import android.content.Context
import android.content.Intent
import android.provider.AlarmClock
import android.util.Log
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel

const val TEAM_NAME = "teamName"
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

    val orangeTeam = TeamScores(TEAM_ORANGE)
    val blueTeam = TeamScores(TEAM_BLUE)

    private val context: Context by lazy {
        application.applicationContext
    }

    private val setFinishingScoreForCurrentSet: Int
        get() = if (setNumber < totalSetsToPlay) setFinishingScore else tieBreakerScore

    fun add1pointToOranges(view: View) = addPoint(orangeTeam, blueTeam)
    fun add1pointToBlues(view: View) = addPoint(blueTeam, orangeTeam)

    private fun addPoint(pointMakerTeam: TeamScores, opponentTeam: TeamScores) {
        val newScore = ++pointMakerTeam.score
        lastPointer = pointMakerTeam.teamName
        undoEnabled.set(true)
        pointMakerTeam.listOfSetScores[setNumber] = newScore
        if (newScore >= setFinishingScoreForCurrentSet && (newScore - opponentTeam.score) >= 2) {
            //if it is a set winning point..
            pointMakerTeam.setsWon++
            if (pointMakerTeam.setsWon == (totalSetsToPlay + 1) / 2) {
                //if it is a match winning set
                message.set(context.getString(R.string.wonMatch, pointMakerTeam.teamName))
            } else { //if it is not a match winning set
                message.set(context.getString(R.string.wonSet, pointMakerTeam.teamName))
                setNumber++
                pointMakerTeam.score = 0
                opponentTeam.score = 0
            }
        } else { //if it is not a set winning point
            message.set(context.getString(R.string.serve, pointMakerTeam.teamName))
        }
    }

    fun undo(view: View) {
        Log.d("UNDO", "lastPointer: $lastPointer")
        when (lastPointer) {
            START -> return //Do nothing
            orangeTeam.teamName -> correction(orangeTeam, blueTeam)
            blueTeam.teamName -> correction(blueTeam, orangeTeam)
        }
    }

    private fun correction(pointMakerTeam: TeamScores, opponentTeam: TeamScores) {
        undoEnabled.set(false)
        message.set(context.getString(R.string.error))
        if (pointMakerTeam.score > 0) {
            pointMakerTeam.score--
            pointMakerTeam.listOfSetScores[setNumber] = pointMakerTeam.score
        } else if (setNumber > 0) {
            setNumber--
            pointMakerTeam.setsWon--
            pointMakerTeam.score = --pointMakerTeam.listOfSetScores[setNumber]
            opponentTeam.score = opponentTeam.listOfSetScores[setNumber]
        }
    }

    fun pauseOrange(view: View) = pause(orangeTeam)
    fun pauseBlue(view: View) = pause(blueTeam)

    private fun pause(pauseTeam: TeamScores) {
        val timeOffCount = pauseTeam.timeOffCount
        if (timeOffCount == 0) {
            context.toast(R.string.all_timeoffs_used)
            return
        }
        openCountDownTimer()
        pauseTeam.timeOffCount--
        val message = context.getString(R.string.used_timeoffs, 2 - timeOffCount, timeOffCount)
        context.toast(message)
    }

    private fun openCountDownTimer() {
        val intent = Intent(AlarmClock.ACTION_SET_TIMER)
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Time left:")
        intent.putExtra(AlarmClock.EXTRA_LENGTH, 30)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    //This method exchange sides up on users click on the exchange button
    fun exchangeSides(view: View) {
        if (orangeTeam.score == 0 && blueTeam.score == 0) {
            isSwitched.set(!isSwitched.get())
        } else {
            context.toast(R.string.exchange_only_between_sets)
        }
    }
}