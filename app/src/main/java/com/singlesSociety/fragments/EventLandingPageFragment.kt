package com.singlesSociety.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singlesSociety.LiveEventActivity
import com.singlesSociety.R
import com.singlesSociety.databinding.FragmentEventLandingPageBinding


class EventLandingPageFragment(private var eventLandingExitListener: Function0<Unit>? = null) : Fragment() {

    private lateinit var viewBinding: FragmentEventLandingPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentEventLandingPageBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.backButton.setOnClickListener {
            eventLandingExitListener?.invoke()
        }
        viewBinding.eventGetPass.setOnClickListener {
            startActivity(Intent(activity,LiveEventActivity::class.java))
        }
    }
}