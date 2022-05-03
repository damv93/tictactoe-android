package com.damv93.tictactoe.domain.usecase

import com.damv93.tictactoe.domain.usecase.model.TicTacToeGame
import com.damv93.tictactoe.domain.usecase.model.TicTacToeGameState
import com.damv93.tictactoe.domain.usecase.model.TicTacToePlayer

class PlayTicTacToeUseCase {

    companion object {
        const val BOARD_SIZE = 3
    }

    private var board = Array(BOARD_SIZE) { Array<TicTacToePlayer?>(BOARD_SIZE) { null } }
    private var gameState: TicTacToeGameState = TicTacToeGameState.InProgress
    private var moveCount = 0
    private var currentPlayer = TicTacToePlayer.X

    fun getGameInfo(): TicTacToeGame {
        return TicTacToeGame(
            board,
            currentPlayer,
            gameState
        )
    }

    fun makeMove(pos: Pair<Int, Int>): TicTacToeGame {

        if (gameState != TicTacToeGameState.InProgress) {
            throw GameIsOverException()
        }

        if (board[pos.first][pos.second] != null) {
            throw InvalidPositionException()
        }
        board[pos.first][pos.second] = currentPlayer
        moveCount++

        var row = 0
        var col = 0
        var diag = 0
        var rdiag = 0
        for (i in board.indices) {
            if (board[pos.first][i] == currentPlayer) row++
            if (board[i][pos.second] == currentPlayer) col++
            if (board[i][i] == currentPlayer) diag++
            if (board[i][board.lastIndex - i] == currentPlayer) rdiag++
        }

        val boardSize = board.size
        gameState = when {
            row == boardSize || col == boardSize || diag == boardSize || rdiag == boardSize -> {
                TicTacToeGameState.Win(winner = currentPlayer)
            }
            moveCount == boardSize * boardSize -> {
                TicTacToeGameState.Draw
            }
            else -> {
                TicTacToeGameState.InProgress
            }
        }

        currentPlayer = when (currentPlayer) {
            TicTacToePlayer.X -> TicTacToePlayer.O
            TicTacToePlayer.O -> TicTacToePlayer.X
        }

        return getGameInfo()
    }

    fun resetGame(firstPlayer: TicTacToePlayer) {
        board = Array(BOARD_SIZE) { Array(BOARD_SIZE) { null } }
        gameState = TicTacToeGameState.InProgress
        moveCount = 0
        currentPlayer = firstPlayer
    }

    class GameIsOverException : Exception()
    class InvalidPositionException : Exception()
}