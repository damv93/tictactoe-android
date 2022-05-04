package com.damv93.tictactoe.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.damv93.tictactoe.databinding.FragmentTicTactToeBoardBinding

class TicTacToeBoardFragment : Fragment() {

    private lateinit var binding: FragmentTicTactToeBoardBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTicTactToeBoardBinding.inflate(inflater, container, false)
        return binding.root
    }
}