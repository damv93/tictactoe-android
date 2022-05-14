package com.damv93.tictactoe.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
        setRestartGameButtonOnClickListener()
    }

    private fun setRestartGameButtonOnClickListener() {
        binding.buttonTicTacToeGameRestartGame.setOnClickListener {
            viewModel.restartGame()
        }
    }

    private fun onStateChange(state: TicTacToeGameState) {
        showPlayerTurn(state)
        showBoard(state)
        showGameResult(state)
    }

    private fun showPlayerTurn(state: TicTacToeGameState) {
        with(binding.textViewTicTacToeGamePlayerTurn) {
            isVisible = state.isPlayerTurnVisible
            text = getString(R.string.ticTacToeGame_playerTurn, state.playerTurn)
        }
    }

    private fun showBoard(state: TicTacToeGameState) {
        with(binding.viewTicTacToeGameBoard.tableLayoutTicTacToeGameBoard) {
            isVisible = state.isBoardVisible
            if (!isVisible) return
            drawBoard(state.board)
        }
    }

    private fun drawBoard(board: List<List<String>>) {
        with(binding.viewTicTacToeGameBoard.tableLayoutTicTacToeGameBoard) {
            removeAllViews()
            board.forEachIndexed { i, row ->
                val rowBinding = LayoutTicTacToeBoardRowBinding.inflate(
                    layoutInflater,
                    this,
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
    }

    private fun showGameResult(state: TicTacToeGameState) {
        val result = state.result
        val resultName = result?.nameId?.let { getString(it) } ?: ""
        val winnerId = result?.winnerId
        with(binding.viewTicTacToeGameResult) {
            root.isVisible = state.isResultVisible
            textViewTicTacToeGameResult.text = resultName
            linearLayoutTicTacToeGameResultPlayers.forEach {
                it.isVisible = winnerId == null || winnerId == it.id
            }
        }
    }
}