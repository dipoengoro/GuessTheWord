package id.dipoengoro.guesstheword.screens.game

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import id.dipoengoro.guesstheword.R
import id.dipoengoro.guesstheword.databinding.GameFragmentBinding
import id.dipoengoro.guesstheword.screens.game.GameViewModel.Companion.DEFAULT_SCORE

class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        val binding: GameFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.game_fragment,
            container,
            false
        )
        viewModel.apply {
            eventGameFinish.observe(viewLifecycleOwner) {
                if (it) {
                    findNavController(this@GameFragment)
                        .navigate(
                            GameFragmentDirections.actionGameFragmentToScoreFragment(
                                this.score.value ?: DEFAULT_SCORE
                            )
                        )
                    this.onGameFinishComplete()
                }
            }
        }
        binding.apply {
            gameViewModel = viewModel
            gameFragment = this@GameFragment
            lifecycleOwner = this@GameFragment
            return root
        }
    }

    fun formatTime(time: Long): String = DateUtils.formatElapsedTime(time)
}
