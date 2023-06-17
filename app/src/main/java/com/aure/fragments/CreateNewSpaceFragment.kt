package com.aure.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aure.R
import kotlinx.android.synthetic.main.fragment_create_new_space.*


class CreateNewSpaceFragment(private var createSpaceExitListener: Function0<Unit>? = null) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_new_space, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exitButton.setOnClickListener {
            createSpaceExitListener?.invoke()
        }
    }
}