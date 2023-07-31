package com.singlesSociety.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.facebook.drawee.backends.pipeline.Fresco
import com.singlesSociety.R
import com.singlesSociety.databinding.FragmentVisitProfileBinding
import io.getstream.avatarview.coil.loadImage


private const val ID_IMAGE_LIBRARY = 9
private const val ID_VIDEO_LIBRARY = 10
class VisitProfileActivity() : AppCompatActivity() {


    private lateinit var viewBinding: FragmentVisitProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = FragmentVisitProfileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initView()

    }


    private fun initView(){
        viewBinding.profileInfoArena.profileInfoImageArena.userProfileImageViewWithIndicator.loadImage(resources.getDrawable(R.drawable.woman_official))
        val viewPager = viewBinding.viewPager
        viewPager.adapter = PageAdapter(supportFragmentManager)
        val tabLayout = viewBinding.tabLayout
        tabLayout.setupWithViewPager(viewPager)
        setupTabIcons()

        viewBinding.exitProfileVisit.setOnClickListener {
            finish()
        }
    }



    private fun setupTabIcons() {
        viewBinding.tabLayout.getTabAt(0)?.icon = resources.getDrawable(R.drawable.pen_draw_icon)
        viewBinding.tabLayout.getTabAt(1)?.icon = resources.getDrawable(R.drawable.brand_tinder_icon)
    }

    class PageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return 2;
        }

        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> {
                    TextLibraryFragment()
                }

                1 -> {
                    DatingProfileFragment(isFromUser = true)
                }

                else -> {
                    TextLibraryFragment()
                }
            }
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



}