package com.damv93.tictactoe.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.damv93.tictactoe.R
import com.damv93.tictactoe.databinding.FragmentTicTactToeBoardBinding
import com.damv93.tictactoe.databinding.LayoutTicTacToeBoardCellBinding
import com.damv93.tictactoe.databinding.LayoutTicTacToeBoardRowBinding
import com.damv93.tictactoe.game.model.TicTacToeBoardState
import com.damv93.tictactoe.game.viewmodel.TicTacToeBoardViewModel

class TicTacToeBoardFragment : Fragment() {

    private lateinit var binding: FragmentTicTactToeBoardBinding
    private val viewModel by viewModels<TicTacToeBoardViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTicTactToeBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observableState.observe(viewLifecycleOwner, ::onStateChange)
    }

    private fun onStateChange(state: TicTacToeBoardState) {
        setPlayerTurn(state.currentPlayer)
        drawBoard(state.board)
    }

    private fun setPlayerTurn(currentPlayer: String) {
        binding.textViewTicTacToeBoardPlayerTurn.text = getString(
            R.string.ticTacToeBoard_playerTurn,
            currentPlayer
        )
    }

    private fun drawBoard(board: List<List<String>>) {
        val boardView = binding.tableLayoutTicTacToeBoard
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
}