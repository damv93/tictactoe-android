package com.damv93.tictactoe.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.damv93.tictactoe.R
import com.damv93.tictactoe.databinding.FragmentTicTactToeGameBinding
import com.damv93.tictactoe.databinding.LayoutTicTacToeBoardCellBinding
import com.damv93.tictactoe.databinding.LayoutTicTacToeBoardRowBinding
import com.damv93.tictactoe.game.model.TicTacToeGameState
import com.damv93.tictactoe.game.model.TicTacToeResultModel
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
        showPlayerTurn(state.playerTurn)
        drawBoard(state.board)
        showGameResult(state.result)
    }

    private fun showPlayerTurn(playerTurn: String) {
        with(binding.textViewTicTacToeGamePlayerTurn) {
            text = getString(R.string.ticTacToeGame_playerTurn, playerTurn)
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

    private fun showGameResult(result: TicTacToeResultModel?) {
        with(binding.viewTicTacToeGameResult) {
            root.isInvisible = result == null
            if (root.isInvisible) return
            val resultName = result?.nameId?.let { getString(it) } ?: ""
            val winnerId = result?.winnerId
            textViewTicTacToeGameResult.text = resultName
            linearLayoutTicTacToeGameResultPlayers.forEach {
                it.isVisible = winnerId == null || winnerId == it.id
            }
        }
    }
}