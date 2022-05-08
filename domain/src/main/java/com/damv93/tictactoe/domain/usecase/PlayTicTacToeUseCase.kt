package com.damv93.tictactoe.domain.usecase

import com.damv93.tictactoe.domain.usecase.model.TicTacToeGame
import com.damv93.tictactoe.domain.usecase.model.TicTacToeGameStatus
import com.damv93.tictactoe.domain.usecase.model.TicTacToePlayer

class PlayTicTacToeUseCase {

    companion object {
        const val BOARD_SIZE = 3
    }

    private var board = Array(BOARD_SIZE) { Array<TicTacToePlayer?>(BOARD_SIZE) { null } }
    private var gameStatus: TicTacToeGameStatus = TicTacToeGameStatus.InProgress
    private var moveCount = 0
    private var currentPlayer = TicTacToePlayer.X

    fun getGameInfo(): TicTacToeGame {
        return TicTacToeGame(
            board,
            currentPlayer,
            gameStatus
        )
    }

    fun makeMove(pos: Pair<Int, Int>): TicTacToeGame {

        if (gameStatus != TicTacToeGameStatus.InProgress) {
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
        gameStatus = when {
            row == boardSize || col == boardSize || diag == boardSize || rdiag == boardSize -> {
                TicTacToeGameStatus.Win(winner = currentPlayer)
            }
            moveCount == boardSize * boardSize -> {
                TicTacToeGameStatus.Draw
            }
            else -> {
                TicTacToeGameStatus.InProgress
            }
        }

        currentPlayer = when (currentPlayer) {
            TicTacToePlayer.X -> TicTacToePlayer.O
            TicTacToePlayer.O -> TicTacToePlayer.X
        }

        return getGameInfo()
    }

    fun restartGame(firstPlayer: TicTacToePlayer): TicTacToeGame {
        board = Array(BOARD_SIZE) { Array(BOARD_SIZE) { null } }
        gameStatus = TicTacToeGameStatus.InProgress
        moveCount = 0
        currentPlayer = firstPlayer
        return getGameInfo()
    }

    class GameIsOverException : Exception()
    class InvalidPositionException : Exception()
}