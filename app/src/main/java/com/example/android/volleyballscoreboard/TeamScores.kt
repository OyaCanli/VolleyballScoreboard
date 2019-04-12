package com.example.android.volleyballscoreboard

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList

class TeamScores(var teamName: String,
                 _score: Int = 0,
                 _setsWon: Int = 0,
                 _timeOffCount: Int = 2) : BaseObservable() {

    val listOfSetScores: ObservableArrayList<Int> = ObservableArrayList<Int>().apply {
        repeat(5) { add(0) }
    }

    @get: Bindable
    var score = _score
        set(value) {
            field = value
            notifyPropertyChanged(BR.score)
        }

    @get: Bindable
    var setsWon = _setsWon
        set(value) {
            field = value
            notifyPropertyChanged(BR.setsWon)
        }

    @get: Bindable
    var timeOffCount = _timeOffCount
        set(value) {
            field = value
            notifyPropertyChanged(BR.timeOffCount)
        }

}