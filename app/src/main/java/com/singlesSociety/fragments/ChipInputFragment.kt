package com.singlesSociety.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singlesSociety.R
import com.singlesSociety.databinding.FragmentChipInputBinding
import com.singlesSociety.databinding.FragmentSelectionInputBinding


class ChipInputFragment : Fragment() {

    private lateinit var viewBinding: FragmentChipInputBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewBinding = FragmentChipInputBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.closeChipInput.setOnClickListener {
            activity?.finish()
        }
    }

    companion object {

        fun newInstance(args: Bundle): ChipInputFragment {
            return ChipInputFragment().apply {
                arguments = args
            }
        }
    }

}