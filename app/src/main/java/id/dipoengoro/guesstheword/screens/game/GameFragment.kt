package id.dipoengoro.guesstheword.screens.game

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
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
            eventBuzz.observe(viewLifecycleOwner) {
                if(it != GameViewModel.BuzzType.NO_BUZZ) {
                    buzz(it.pattern)
                    onBuzzComplete()
                }
            }
        }
        binding.apply {
            gameViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
            return root
        }
    }

    private fun buzz(pattern: LongArray) {
        val buzzer = requireActivity().getSystemService<Vibrator>()
        buzzer?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                buzzer.vibrate(pattern, -1)
            }
        }
    }
}
