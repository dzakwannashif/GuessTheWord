package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    // The current word
    private var _word = MutableLiveData<String>()
    val word : LiveData<String>
        get() = _word

    // Event which triggers the end of the game
    private var _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish : LiveData<Boolean>
        get() = _eventGameFinish

    // The current score
    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>
    private lateinit var dataCList: MutableList<String>

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    private fun dbCList() {
        dataCList = mutableListOf()
        dataCList.shuffle()
    }

    init {

        _word.value = ""
        _score.value = 0

        Log.i("GameViewModel", "GameViewModel: Created")
        resetList()
        dbCList()
        nextWord()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {

        if(wordList.isEmpty()){
            onGameFinished()
        } else {
            // Select and remove a _word from the list
            _word.value = wordList.removeAt(0)
        }

        /** The other odd method for looping **/
//        if (wordList.isNotEmpty()) {
//            //Select and remove a word from the list
//            Log.i("word", "$wordList")
//            _word.value = wordList.removeAt(0)
//            dataCList.add(word.value.toString())
//        } else if (wordList.isEmpty()) {
//            _word.value = dataCList[1]
//            Log.i("word", "$dataCList")
//            wordList.addAll(dataCList.shuffled())
//            dataCList.removeAll(dataCList)
//        }
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed")
    }

    fun onGameFinished() {
        _eventGameFinish.value = true
    }

    fun onGameFinishedComplete() {
        _eventGameFinish.value = false
    }
}