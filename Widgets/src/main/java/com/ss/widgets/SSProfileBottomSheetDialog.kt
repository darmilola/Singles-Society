package com.ss.widgets


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.singlesSociety.Widgets.R
import com.singlesSociety.Widgets.databinding.FragmentProfileBottomSheetBinding


class SSProfileBottomSheetDialog : BottomSheetDialogFragment() {


    private lateinit var viewBinding: FragmentProfileBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentProfileBottomSheetBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }



}