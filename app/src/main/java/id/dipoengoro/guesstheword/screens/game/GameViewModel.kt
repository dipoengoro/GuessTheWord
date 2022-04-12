package id.dipoengoro.guesstheword.screens.game

import android.os.CountDownTimer
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
        const val DONE = 0L
        const val ONE_SECOND = 1000L
        const val COUNTDOWN_TIME = 60000L
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

    private val timer: CountDownTimer

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    private lateinit var wordList: MutableList<String>

    init {
        resetList()
        nextWord()
        _score.value = DEFAULT_SCORE
        _eventGameFinish.value = GAME_IN_PROGRESS
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished / ONE_SECOND
            }

            override fun onFinish() {
                _currentTime.value = DONE
                _eventGameFinish.value = GAME_FINISH
            }

        }

        timer.start()
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

    private fun nextWord() = if (wordList.isEmpty()) resetList()
     else _word.value = wordList.removeAt(FIRST_WORD)

    fun onSkip() {
        _score.value = (_score.value)?.minus(DEFAULT_SCORE_CHANGE)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (_score.value)?.plus(DEFAULT_SCORE_CHANGE)
        nextWord()
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = GAME_IN_PROGRESS
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
}