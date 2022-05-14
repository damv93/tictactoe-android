package com.damv93.tictactoe.game.model

import com.damv93.libs.base.viewstate.BaseViewState

data class TicTacToeGameState(
    val playerTurn: String = "",
    val board: List<List<String>> = emptyList(),
    val result: TicTacToeResultModel? = null
) : BaseViewState
