package com.damv93.tictactoe.game.model

import com.damv93.libs.base.viewstate.BaseViewState

data class TicTacToeBoardState(
    val board: List<List<String>> = emptyList(),
    val currentPlayer: String = ""
) : BaseViewState
