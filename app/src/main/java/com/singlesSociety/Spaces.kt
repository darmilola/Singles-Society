package com.singlesSociety

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.singlesSociety.fragments.SpacesMainFragment
import com.singlesSociety.fragments.VisitProfileActivity


class Spaces : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spaces)
        loadFragment(SpacesMainFragment(visitProfileListener = {
            startActivity(Intent(this,VisitProfileActivity::class.java))
        }, exitSpaceListener = {
            if (supportFragmentManager.backStackEntryCount == 1){
                finish()
            }
            else{
                supportFragmentManager.popBackStack()
            }
        }))
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