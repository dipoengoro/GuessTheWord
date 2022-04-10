package id.dipoengoro.guesstheword.screens.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    companion object {
        const val DEFAULT_SCORE = 0
        const val DEFAULT_SCORE_CHANGE = 1
        const val GAME_IN_PROGRESS = false
        const val GAME_FINISH = true
        const val FIRST_WORD = 0
    }

    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    private lateinit var wordList: MutableList<String>

    init {
        resetList()
        nextWord()
        _score.value = DEFAULT_SCORE
        _eventGameFinish.value = GAME_IN_PROGRESS
    }

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

    private fun nextWord() = if (wordList.isEmpty()) _eventGameFinish.value = GAME_FINISH
     else _word.value = wordList.removeAt(FIRST_WORD)

    fun onSkip() {
        _score.value = (_score.value)?.minus(DEFAULT_SCORE_CHANGE)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (_score.value)?.plus(DEFAULT_SCORE_CHANGE)
        nextWord()
    }
}