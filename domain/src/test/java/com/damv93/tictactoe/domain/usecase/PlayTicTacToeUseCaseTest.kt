package com.damv93.tictactoe.domain.usecase

import com.damv93.tictactoe.domain.usecase.model.TicTacToeGame
import com.damv93.tictactoe.domain.usecase.model.TicTacToeGameState
import com.damv93.tictactoe.domain.usecase.model.TicTacToePlayer
import org.junit.Test

class PlayTicTacToeUseCaseTest {

    private val playTicTacToeUseCase = PlayTicTacToeUseCase()

    private class Move(
        val pos: Pair<Int, Int>,
        val currentPlayer: TicTacToePlayer,
        val nextPlayer: TicTacToePlayer
    )

    @Test
    fun `when getting initial game info, then board is empty and first player is X and state is 'In progress'`() {
        // when
        val game = playTicTacToeUseCase.getGameInfo()

        // then
        assert(game.board.all { row -> row.all { it == null } })
        assert(game.currentPlayer == TicTacToePlayer.X)
        assert(game.state == TicTacToeGameState.InProgress)
    }

    @Test
    fun `when players make some moves without filling any column or row or diagonal and board is not full, then game info is valid and state is 'In progress'`() {
        var game: TicTacToeGame?
        val moves = listOf(
            Move(pos = 0 to 0, currentPlayer = TicTacToePlayer.X, nextPlayer = TicTacToePlayer.O),
            Move(pos = 0 to 1, currentPlayer = TicTacToePlayer.O, nextPlayer = TicTacToePlayer.X),
            Move(pos = 0 to 2, currentPlayer = TicTacToePlayer.X, nextPlayer = TicTacToePlayer.O)
        )
        for (move in moves) {
            // when
            game = playTicTacToeUseCase.makeMove(move.pos)

            // then
            validateGameAfterMove(game, move, TicTacToeGameState.InProgress)
        }
    }

    @Test
    fun `when players make moves until board is full without filling any column or row or diagonal, then game info is valid and state is 'Draw'`() {
        var game: TicTacToeGame?
        val moves = listOf(
            Move(pos = 1 to 1, currentPlayer = TicTacToePlayer.X, nextPlayer = TicTacToePlayer.O),
            Move(pos = 0 to 1, currentPlayer = TicTacToePlayer.O, nextPlayer = TicTacToePlayer.X),
            Move(pos = 1 to 0, currentPlayer = TicTacToePlayer.X, nextPlayer = TicTacToePlayer.O),
            Move(pos = 1 to 2, currentPlayer = TicTacToePlayer.O, nextPlayer = TicTacToePlayer.X),
            Move(pos = 0 to 2, currentPlayer = TicTacToePlayer.X, nextPlayer = TicTacToePlayer.O),
            Move(pos = 2 to 0, currentPlayer = TicTacToePlayer.O, nextPlayer = TicTacToePlayer.X),
            Move(pos = 0 to 0, currentPlayer = TicTacToePlayer.X, nextPlayer = TicTacToePlayer.O),
            Move(pos = 2 to 2, currentPlayer = TicTacToePlayer.O, nextPlayer = TicTacToePlayer.X),
            Move(pos = 2 to 1, currentPlayer = TicTacToePlayer.X, nextPlayer = TicTacToePlayer.O)
        )
        for (i in 0..moves.lastIndex) {
            val move = moves[i]

            // when
            game = playTicTacToeUseCase.makeMove(move.pos)

            // then
            val state = if (i < moves.lastIndex) {
                TicTacToeGameState.InProgress
            } else {
                TicTacToeGameState.Draw
            }
            validateGameAfterMove(game, move, state)
        }
    }

    @Test
    fun `when player X fills a row, then game info is valid and player X wins`() {
        var game: TicTacToeGame?
        val moves = listOf(
            Move(pos = 0 to 0, currentPlayer = TicTacToePlayer.X, nextPlayer = TicTacToePlayer.O),
            Move(pos = 1 to 0, currentPlayer = TicTacToePlayer.O, nextPlayer = TicTacToePlayer.X),
            Move(pos = 0 to 1, currentPlayer = TicTacToePlayer.X, nextPlayer = TicTacToePlayer.O),
            Move(pos = 1 to 1, currentPlayer = TicTacToePlayer.O, nextPlayer = TicTacToePlayer.X),
            Move(pos = 0 to 2, currentPlayer = TicTacToePlayer.X, nextPlayer = TicTacToePlayer.O)
        )
        for (i in 0..moves.lastIndex) {
            val move = moves[i]

            // when
            game = playTicTacToeUseCase.makeMove(move.pos)

            // then
            val state = if (i < moves.lastIndex) {
                TicTacToeGameState.InProgress
            } else {
                TicTacToeGameState.Win(TicTacToePlayer.X)
            }
            validateGameAfterMove(game, move, state)
        }
    }

    @Test
    fun `when player X fills a column, then game info is valid and player X wins`() {
        var game: TicTacToeGame?
        val moves = listOf(
            Move(pos = 0 to 0, currentPlayer = TicTacToePlayer.X, nextPlayer = TicTacToePlayer.O),
            Move(pos = 0 to 1, currentPlayer = TicTacToePlayer.O, nextPlayer = TicTacToePlayer.X),
            Move(pos = 1 to 0, currentPlayer = TicTacToePlayer.X, nextPlayer = TicTacToePlayer.O),
            Move(pos = 0 to 2, currentPlayer = TicTacToePlayer.O, nextPlayer = TicTacToePlayer.X),
            Move(pos = 2 to 0, currentPlayer = TicTacToePlayer.X, nextPlayer = TicTacToePlayer.O)
        )
        for (i in 0..moves.lastIndex) {
            val move = moves[i]

            // when
            game = playTicTacToeUseCase.makeMove(move.pos)

            // then
            val state = if (i < moves.lastIndex) {
                TicTacToeGameState.InProgress
            } else {
                TicTacToeGameState.Win(TicTacToePlayer.X)
            }
            validateGameAfterMove(game, move, state)
        }
    }

    @Test
    fun `when player X fills the diagonal, then game info is valid and player X wins`() {
        var game: TicTacToeGame?
        val moves = listOf(
            Move(pos = 0 to 0, currentPlayer = TicTacToePlayer.X, nextPlayer = TicTacToePlayer.O),
            Move(pos = 0 to 1, currentPlayer = TicTacToePlayer.O, nextPlayer = TicTacToePlayer.X),
            Move(pos = 1 to 1, currentPlayer = TicTacToePlayer.X, nextPlayer = TicTacToePlayer.O),
            Move(pos = 0 to 2, currentPlayer = TicTacToePlayer.O, nextPlayer = TicTacToePlayer.X),
            Move(pos = 2 to 2, currentPlayer = TicTacToePlayer.X, nextPlayer = TicTacToePlayer.O)
        )
        for (i in 0..moves.lastIndex) {
            val move = moves[i]

            // when
            game = playTicTacToeUseCase.makeMove(move.pos)

            // then
            val state = if (i < moves.lastIndex) {
                TicTacToeGameState.InProgress
            } else {
                TicTacToeGameState.Win(TicTacToePlayer.X)
            }
            validateGameAfterMove(game, move, state)
        }
    }

    @Test
    fun `when player X fills the reversed diagonal, then game info is valid and player X wins`() {
        var game: TicTacToeGame?
        val moves = listOf(
            Move(pos = 0 to 2, currentPlayer = TicTacToePlayer.X, nextPlayer = TicTacToePlayer.O),
            Move(pos = 0 to 0, currentPlayer = TicTacToePlayer.O, nextPlayer = TicTacToePlayer.X),
            Move(pos = 1 to 1, currentPlayer = TicTacToePlayer.X, nextPlayer = TicTacToePlayer.O),
            Move(pos = 0 to 1, currentPlayer = TicTacToePlayer.O, nextPlayer = TicTacToePlayer.X),
            Move(pos = 2 to 0, currentPlayer = TicTacToePlayer.X, nextPlayer = TicTacToePlayer.O)
        )
        for (i in 0..moves.lastIndex) {
            val move = moves[i]

            // when
            game = playTicTacToeUseCase.makeMove(move.pos)

            // then
            val state = if (i < moves.lastIndex) {
                TicTacToeGameState.InProgress
            } else {
                TicTacToeGameState.Win(TicTacToePlayer.X)
            }
            validateGameAfterMove(game, move, state)
        }
    }

    @Test
    fun `given game state is 'Draw', when a player tries to make a move, then GameIsOverException is thrown`() {
        // given
        var game: TicTacToeGame? = null
        val moves = listOf(
            1 to 1,
            0 to 1,
            1 to 0,
            1 to 2,
            0 to 2,
            2 to 0,
            0 to 0,
            2 to 2,
            2 to 1
        )
        for (move in moves) {
            game = playTicTacToeUseCase.makeMove(move)
        }
        assert(game?.state == TicTacToeGameState.Draw)

        try {
            // when
            playTicTacToeUseCase.makeMove(0 to 1)
            assert(false)
        } catch (e: PlayTicTacToeUseCase.GameIsOverException) {
            // then
            assert(true)
        }
    }

    @Test
    fun `given game state is 'Win', when a player tries to make a move, then GameIsOverException is thrown`() {
        // given
        var game: TicTacToeGame? = null
        val moves = listOf(
            0 to 0,
            0 to 1,
            1 to 1,
            0 to 2,
            2 to 2
        )
        for (move in moves) {
            game = playTicTacToeUseCase.makeMove(move)
        }
        assert(game?.state is TicTacToeGameState.Win)

        try {
            // when
            playTicTacToeUseCase.makeMove(0 to 1)
            assert(false)
        } catch (e: PlayTicTacToeUseCase.GameIsOverException) {
            // then
            assert(true)
        }
    }

    @Test
    fun `given board has some cells filled, when a player tries to make a move in a filled cell, then InvalidPositionException is thrown`() {
        // given
        val previousMove = 0 to 1
        playTicTacToeUseCase.makeMove(previousMove)

        try {
            // when
            playTicTacToeUseCase.makeMove(previousMove)
            assert(false)
        } catch (e: PlayTicTacToeUseCase.InvalidPositionException) {
            // then
            assert(true)
        }
    }

    @Test
    fun `given game state is 'Win', when game is reset, then board is empty and first player is set correctly and state is 'In progress'`() {
        // given
        var game: TicTacToeGame? = null
        val moves = listOf(
            0 to 0,
            0 to 1,
            1 to 1,
            0 to 2,
            2 to 2
        )
        for (move in moves) {
           game = playTicTacToeUseCase.makeMove(move)
        }
        assert(game?.state is TicTacToeGameState.Win)

        // when
        playTicTacToeUseCase.resetGame(TicTacToePlayer.O)

        // then
        game = playTicTacToeUseCase.getGameInfo()
        assert(game.board.all { row -> row.all { it == null } })
        assert(game.currentPlayer == TicTacToePlayer.O)
        assert(game.state == TicTacToeGameState.InProgress)
    }

    private fun validateGameAfterMove(game: TicTacToeGame, move: Move, state: TicTacToeGameState) {
        val pos = move.pos
        assert(game.board[pos.first][pos.second] == move.currentPlayer)
        assert(game.currentPlayer == move.nextPlayer)
        assert(game.state == state)
    }
}