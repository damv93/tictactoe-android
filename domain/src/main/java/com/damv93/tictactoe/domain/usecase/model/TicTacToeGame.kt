package com.damv93.tictactoe.domain.usecase.model

class TicTacToeGame(
    val board: Array<Array<TicTacToePlayer?>>,
    val currentPlayer: TicTacToePlayer,
    val status: TicTacToeGameStatus
)
