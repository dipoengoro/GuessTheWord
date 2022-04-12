package id.dipoengoro.guesstheword.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import id.dipoengoro.guesstheword.R
import id.dipoengoro.guesstheword.databinding.ScoreFragmentBinding

class ScoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate view and obtain an instance of the binding class.
        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.score_fragment,
            container,
            false
        )
        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()
        val factory = ScoreViewModelFactory(scoreFragmentArgs.score)
        val viewModel = ViewModelProvider(this, factory)[ScoreViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        binding.scoreViewModel = viewModel
        viewModel.eventPlayAgain.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(ScoreFragmentDirections.actionScoreFragmentToGameFragment())
                viewModel.onPlayAgainComplete()
            }
        }
        return binding.root
    }
}
