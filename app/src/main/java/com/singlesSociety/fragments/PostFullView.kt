package com.singlesSociety.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.singlesSociety.ImagePostFullView
import com.singlesSociety.R
import com.singlesSociety.VideoPostFullView
import com.singlesSociety.databinding.PostFullViewBinding

const val viewTypeVideo = 1
const val viewTypeImage = 2
class PostFullView : AppCompatActivity() {

    private lateinit var viewBinding: PostFullViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = PostFullViewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
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

        viewBinding.backButton.setOnClickListener {
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