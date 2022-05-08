package com.damv93.tictactoe.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.damv93.libs.common.extensions.gone
import com.damv93.libs.common.extensions.visible
import com.damv93.tictactoe.R
import com.damv93.tictactoe.databinding.FragmentTicTactToeGameBinding
import com.damv93.tictactoe.databinding.LayoutTicTacToeBoardCellBinding
import com.damv93.tictactoe.databinding.LayoutTicTacToeBoardRowBinding
import com.damv93.tictactoe.game.model.TicTacToeGameState
import com.damv93.tictactoe.game.viewmodel.TicTacToeGameViewModel

class TicTacToeGameFragment : Fragment() {

    private lateinit var binding: FragmentTicTactToeGameBinding
    private val viewModel by viewModels<TicTacToeGameViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTicTactToeGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observableState.observe(viewLifecycleOwner, ::onStateChange)
    }

    private fun onStateChange(state: TicTacToeGameState) {
        setPlayerTurn(state.currentPlayer)
        drawBoard(state.board)
        showResult(state)
    }

    private fun setPlayerTurn(currentPlayer: String) {
        binding.textViewTicTacToeGamePlayerTurn.text = getString(
            R.string.ticTacToeGame_playerTurn,
            currentPlayer
        )
    }

    private fun drawBoard(board: List<List<String>>) {
        val boardView = binding.tableLayoutTicTacToeGame
        boardView.removeAllViews()
        board.forEachIndexed { i, row ->
            val rowBinding = LayoutTicTacToeBoardRowBinding.inflate(
                layoutInflater,
                boardView,
                true
            )
            row.forEachIndexed { j, cell ->
                val cellBinding = LayoutTicTacToeBoardCellBinding.inflate(
                    layoutInflater,
                    rowBinding.root,
                    true
                )
                cellBinding.root.text = cell
                cellBinding.root.setOnClickListener {
                    viewModel.makeMove(i to j)
                }
            }
        }
    }

    private fun showResult(state: TicTacToeGameState) {
        state.showResult?.consume()?.let { result ->
            val winnerId = result.winnerId
            binding.textViewTicTacToeGamePlayerTurn.gone()
            binding.tableLayoutTicTacToeGame.gone()
            with(binding.viewTicTacToeGameResult) {
                root.visible()
                linearLayoutTicTacToeGameResultPlayers.forEach {
                    it.isVisible = winnerId == null || winnerId == it.id
                }
                textViewTicTacToeGameResult.setText(result.nameId)
            }
        }
    }
}