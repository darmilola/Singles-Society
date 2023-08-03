package com.singlesSociety

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.singlesSociety.databinding.ActivityCreatePostBinding
import com.singlesSociety.fragments.CreateEventFragment
import com.singlesSociety.fragments.CreatePostTypeText

class CreatePostActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityCreatePostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        loadFragment(CreatePostTypeText())

        viewBinding.createSwitch.switchEventPost.setOnCheckedChangeListener { _, checked ->
            when {
                checked -> {
                    loadFragment(CreateEventFragment())
                    viewBinding.createSwitch.tvSwitchEvent.setTextColor(ContextCompat.getColor(this,R.color.special_activity_background))
                    viewBinding.createSwitch.tvSwitchPost.setTextColor(ContextCompat.getColor(this,R.color.society_pink))
                }
                else -> {
                    loadFragment(CreatePostTypeText())
                    viewBinding.createSwitch.tvSwitchEvent.setTextColor(ContextCompat.getColor(this,R.color.society_pink))
                    viewBinding.createSwitch.tvSwitchPost.setTextColor(ContextCompat.getColor(this,R.color.special_activity_background))
                }
            }
        }
        viewBinding.cancelCreatePost.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.special_activity_background)
            window.statusBarColor = ContextCompat.getColor(this, R.color.special_activity_background)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }


}