package com.singlesSociety

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.singlesSociety.databinding.FragmentEventLandingPageBinding


class EventLandingPage() :
    AppCompatActivity() {

    private lateinit var viewBinding: FragmentEventLandingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = FragmentEventLandingPageBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.joinEvent.setOnClickListener {
            startActivity(Intent(this,LiveEventActivity::class.java))
        }
        viewBinding.eventLandingExit.setOnClickListener {
             finish()
        }
    }
}