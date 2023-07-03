package com.singlesSociety.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singlesSociety.R
import com.singlesSociety.databinding.FragmentCreateNewSpaceBinding


class CreateNewSpaceFragment(private var createSpaceExitListener: Function0<Unit>? = null) : Fragment() {

    private lateinit var viewBinding: FragmentCreateNewSpaceBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentCreateNewSpaceBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.exitButton.setOnClickListener {
            createSpaceExitListener?.invoke()
        }
    }
}