package com.aure.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aure.ImagePostFullView
import com.aure.R
import com.aure.VideoPostFullView
import kotlinx.android.synthetic.main.activity_main_v2.*
import kotlinx.android.synthetic.main.post_full_view.*

const val viewTypeVideo = 1
const val viewTypeImage = 2
class PostFullView : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_full_view)
        if(intent.extras?.get("type") == viewTypeVideo){
            loadFragment(VideoPostFullView( videoPostProfileVisitListener = {
                finish()
            }))
        }
        else{
            loadFragment(ImagePostFullView( imagePostProfileVisitListener = {
                finish()
            }))
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.special_activity_background)
            window.statusBarColor = ContextCompat.getColor(this, R.color.special_activity_background)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.postContainer,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}