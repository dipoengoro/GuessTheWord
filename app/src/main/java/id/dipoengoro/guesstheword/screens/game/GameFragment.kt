package id.dipoengoro.guesstheword.screens.game

import android.os.Bundle
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: GameFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.game_fragment,
            container,
            false
        )
        val viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        binding.correctButton.setOnClickListener {
            viewModel.onCorrect()
        }
        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
        }
        viewModel.score.observe(viewLifecycleOwner) {
            binding.scoreText.text = it.toString()
        }
        viewModel.word.observe(viewLifecycleOwner) {
            binding.wordText.text = it
        }
        viewModel.eventGameFinish.observe(viewLifecycleOwner) {
            if (it) findNavController(this)
                    .navigate(GameFragmentDirections
                        .actionGameFragmentToScoreFragment(viewModel.score.value ?: DEFAULT_SCORE))
        }
        return binding.root
    }
}
