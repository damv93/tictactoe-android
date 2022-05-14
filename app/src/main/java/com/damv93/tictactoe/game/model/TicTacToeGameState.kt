package com.damv93.tictactoe.game.model

import com.damv93.libs.base.viewstate.BaseViewState

data class TicTacToeGameState(
    val isPlayerTurnVisible: Boolean = false,
    val playerTurn: String = "",
    val isBoardVisible: Boolean = false,
    val board: List<List<String>> = emptyList(),
    val isResultVisible: Boolean = false,
    val result: TicTacToeResultModel? = null
) : BaseViewState
