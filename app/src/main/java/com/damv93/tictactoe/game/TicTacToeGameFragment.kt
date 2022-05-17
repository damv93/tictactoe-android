package com.damv93.tictactoe.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.core.view.isEmpty
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
        viewModel.observableState.observe(viewLifecycleOwner, ::onStateChanged)
        setRestartGameButtonOnClickListener()
    }

    private fun setRestartGameButtonOnClickListener() {
        binding.buttonTicTacToeGameRestartGame.setOnClickListener {
            viewModel.restartGame()
        }
    }

    /**
     * This method is the only entry point where the UI is updated to show the new state.
     * This helps finding any issues in the UI since this is the only place to look.
     * No other methods can change the UI.
     */
    private fun onStateChanged(state: TicTacToeGameState) {
        showPlayerTurn(state.playerTurn)
        showBoard(state.board)
        showGameResult(state.result)
    }

    private fun showPlayerTurn(playerTurn: String) {
        with(binding.textViewTicTacToeGamePlayerTurn) {
            text = getString(R.string.ticTacToeGame_playerTurn, playerTurn)
        }
    }

    private fun showBoard(board: List<List<String>>) {
        with(binding.viewTicTacToeGameBoard.tableLayoutTicTacToeGameBoard) {
            if (isEmpty()) {
                drawBoard(board)
            } else {
                updateBoard(board)
            }
        }
    }

    private fun drawBoard(board: List<List<String>>) {
        with(binding.viewTicTacToeGameBoard.tableLayoutTicTacToeGameBoard) {
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

    private fun updateBoard(board: List<List<String>>) {
        with(binding.viewTicTacToeGameBoard.tableLayoutTicTacToeGameBoard) {
            forEachIndexed { i, rowView ->
                (rowView as ViewGroup).forEachIndexed { j, cellView ->
                    (cellView as TextView).text = board[i][j]
                }
            }
        }
    }

    private fun showGameResult(result: TicTacToeResultModel?) {
        with(binding.viewTicTacToeGameResult) {
            root.isInvisible = result == null
            if (result == null) return
            textViewTicTacToeGameResult.setText(result.nameId)
            val winnerId = result.winnerId
            linearLayoutTicTacToeGameResultPlayers.forEach {
                it.isVisible = winnerId == null || winnerId == it.id
            }
        }
    }

}