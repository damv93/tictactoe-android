package com.damv93.tictactoe.game.viewmodel.mapper

import com.damv93.tictactoe.R
import com.damv93.tictactoe.domain.usecase.model.TicTacToeGameStatus
import com.damv93.tictactoe.domain.usecase.model.TicTacToePlayer
import com.damv93.tictactoe.game.model.TicTacToeResultModel

fun TicTacToeGameStatus.toResultModel(): TicTacToeResultModel? {
    return when (this) {
        TicTacToeGameStatus.InProgress -> null
        TicTacToeGameStatus.Draw -> {
            TicTacToeResultModel(
                nameId = R.string.ticTacToeGameResult_draw,
                winnerId = null
            )
        }
        is TicTacToeGameStatus.Win -> {
            val playerViewId = when (winner) {
                TicTacToePlayer.X -> R.id.textView_ticTacToeGameResult_playerX
                TicTacToePlayer.O -> R.id.textView_ticTacToeGameResult_playerO
            }
            TicTacToeResultModel(
                nameId = R.string.ticTacToeGameResult_winner,
                winnerId = playerViewId
            )
        }
    }
}