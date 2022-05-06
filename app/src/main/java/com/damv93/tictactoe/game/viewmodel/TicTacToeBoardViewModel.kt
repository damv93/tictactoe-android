package com.damv93.tictactoe.game.viewmodel

import com.damv93.libs.base.viewmodel.BaseViewModel
import com.damv93.tictactoe.domain.usecase.PlayTicTacToeUseCase
import com.damv93.tictactoe.domain.usecase.PlayTicTacToeUseCase.GameIsOverException
import com.damv93.tictactoe.domain.usecase.PlayTicTacToeUseCase.InvalidPositionException
import com.damv93.tictactoe.game.model.TicTacToeBoardState

class TicTacToeBoardViewModel : BaseViewModel<TicTacToeBoardState>() {

    override val initialState = TicTacToeBoardState()
    private val playTicTacToeUseCase = PlayTicTacToeUseCase()

    init {
        state = initialState
        setGameInfo()
    }

    private fun setGameInfo() {
        val game = playTicTacToeUseCase.getGameInfo()
        state = state.copy(
            board = game.board.map { row ->
                row.map { it?.name ?: "" }
            },
            currentPlayer = game.currentPlayer.name
        )
    }

    fun makeMove(position: Pair<Int, Int>) {
        try {
            val game = playTicTacToeUseCase.makeMove(position)
            state = state.copy(
                board = game.board.map { row ->
                    row.map { it?.name ?: "" }
                },
                currentPlayer = game.currentPlayer.name
            )
        } catch (e: GameIsOverException) {
        } catch (e: InvalidPositionException) {
        }
    }
}