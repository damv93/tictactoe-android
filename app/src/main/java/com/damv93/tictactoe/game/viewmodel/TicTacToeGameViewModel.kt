package com.damv93.tictactoe.game.viewmodel

import com.damv93.libs.base.viewmodel.BaseViewModel
import com.damv93.libs.common.viewstate.SingleEvent
import com.damv93.tictactoe.domain.usecase.PlayTicTacToeUseCase
import com.damv93.tictactoe.domain.usecase.PlayTicTacToeUseCase.GameIsOverException
import com.damv93.tictactoe.domain.usecase.PlayTicTacToeUseCase.InvalidPositionException
import com.damv93.tictactoe.domain.usecase.model.TicTacToeGame
import com.damv93.tictactoe.game.model.TicTacToeGameState
import com.damv93.tictactoe.game.viewmodel.mapper.toResultModel

class TicTacToeGameViewModel : BaseViewModel<TicTacToeGameState>() {

    override val initialState = TicTacToeGameState()
    private val playTicTacToeUseCase = PlayTicTacToeUseCase()

    init {
        state = initialState
        getGameInfo()
    }

    private fun getGameInfo() {
        val game = playTicTacToeUseCase.getGameInfo()
        setGameState(game)
    }

    fun makeMove(position: Pair<Int, Int>) {
        try {
            val game = playTicTacToeUseCase.makeMove(position)
            setGameState(game)
        } catch (e: GameIsOverException) {
        } catch (e: InvalidPositionException) {
        }
    }

    private fun setGameState(game: TicTacToeGame) {
        val result = game.status.toResultModel()
        state = state.copy(
            board = game.board.map { row ->
                row.map { it?.name ?: "" }
            },
            currentPlayer = game.currentPlayer.name,
            showResult = result?.let { SingleEvent(it) }
        )
    }
}