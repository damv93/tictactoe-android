package com.damv93.tictactoe.game.model

import com.damv93.libs.base.viewstate.BaseViewState
import com.damv93.libs.common.viewstate.SingleEvent

data class TicTacToeGameState(
    val board: List<List<String>> = emptyList(),
    val currentPlayer: String = "",
    val showResult: SingleEvent<TicTacToeResultModel>? = null
) : BaseViewState
