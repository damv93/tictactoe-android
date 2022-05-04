package com.damv93.tictactoe.game.viewmodel

import com.damv93.libs.base.viewmodel.BaseViewModel
import com.damv93.tictactoe.domain.usecase.PlayTicTacToeUseCase
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
}