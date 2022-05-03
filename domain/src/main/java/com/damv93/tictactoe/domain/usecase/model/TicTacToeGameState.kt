package com.damv93.tictactoe.domain.usecase.model

sealed class TicTacToeGameState {
    object InProgress : TicTacToeGameState()
    object Draw : TicTacToeGameState()
    data class Win(val winner: TicTacToePlayer) : TicTacToeGameState()
}