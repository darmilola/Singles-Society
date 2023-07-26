package com.singlesSociety

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.singlesSociety.databinding.ActivitySocietyBinding
import com.singlesSociety.databinding.ActivitySocietySwipeBinding
import com.singlesSociety.databinding.FragmentSpacesMainBinding
import com.singlesSociety.databinding.SettingsActivityBinding
import com.singlesSociety.fragments.HomeFragment
import com.singlesSociety.fragments.SpacesMainFragment
import com.singlesSociety.fragments.TextLibraryFragment
import com.singlesSociety.fragments.VisitProfileActivity

class SocietyActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySocietyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySocietyBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initView()
    }


    private fun initView() {

        loadFragment(SpacesMainFragment(visitProfileListener = {
            startActivity(Intent(this, VisitProfileActivity::class.java))
        }, exitSpaceListener = {
            if (supportFragmentManager.backStackEntryCount == 1){
                finish()
            }
            else{
                supportFragmentManager.popBackStack()
            }
        }))

        viewBinding.exitSpaceButton.setOnClickListener {
            finish()
        }

    }
    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.special_activity_background)
            window.statusBarColor = ContextCompat.getColor(this, R.color.special_activity_background)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.spaceContainer,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}