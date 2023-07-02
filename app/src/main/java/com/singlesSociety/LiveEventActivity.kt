package com.singlesSociety

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.singlesSociety.fragments.LiveEventBottomsheet

class LiveEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_event)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar?.hide();

        val commentingSection = LiveEventBottomsheet()
        commentingSection.show(supportFragmentManager, "commentingSection")
    }
}