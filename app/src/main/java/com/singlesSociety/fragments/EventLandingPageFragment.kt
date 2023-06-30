package com.singlesSociety.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singlesSociety.R
import kotlinx.android.synthetic.main.fragment_event_landing_page.*
import kotlinx.android.synthetic.main.fragment_visit_profile.*


class EventLandingPageFragment(private var eventLandingExitListener: Function0<Unit>? = null) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_landing_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButton.setOnClickListener {
            eventLandingExitListener?.invoke()
        }
    }
}