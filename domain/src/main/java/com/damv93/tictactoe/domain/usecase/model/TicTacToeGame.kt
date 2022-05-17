package com.damv93.tictactoe.domain.usecase.model

class TicTacToeGame(
    val board: List<List<TicTacToePlayer?>>,
    val currentPlayer: TicTacToePlayer,
    val status: TicTacToeGameStatus
)
