package com.aure

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aure.UiModels.BottomNav
import com.aure.fragments.*
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.activity_main_v2.*

private const val ID_HOME = 0
private const val ID_EXPLORE = 1
private const val ID_MESSAGE = 2
private const val ID_NOTIFICATION = 3
private const val ID_ACCOUNT = 4

class MainAct2 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_main_v2)


         val userEmail: String? = intent.getStringExtra("email")
         val intent = Intent(this, NotificationService::class.java)
         intent.putExtra("userId", userEmail)
         startService(intent)
         val preferences = PreferenceManager.getDefaultSharedPreferences(this)
         preferences.edit().putString("userEmail", userEmail).apply()

        bottomNavigation?.apply {

            add(
                    BottomNav.Model(
                            ID_HOME,
                            R.drawable.fire_icon,
                            "Home"
                    )
            )
            add(
                    BottomNav.Model(
                            ID_EXPLORE,
                            R.drawable.explore_ic,
                            "Trending"
                    )
            )
            add(
                    BottomNav.Model(
                            ID_MESSAGE,
                            R.drawable.chat_icon,
                            "Chat"
                    )
            )
            add(
                    BottomNav.Model(
                            ID_ACCOUNT,
                            R.drawable.user_icon,
                            "Account"
                    )
            )
            add(
                BottomNav.Model(
                    ID_NOTIFICATION,
                    R.drawable.ring_icon,
                    "Notification"
                )
            )
        }?.show(ID_HOME)
        loadFragment(HomeFragment(visitProfileListener = {
            loadFragment(VisitProfileFragment( visitProfileExitListener = {

                supportFragmentManager.popBackStack()

            }))
        }))
        bottomNavigation?.setCount(ID_MESSAGE,"25")

        bottomNavigation.setOnClickMenuListener {
            when(it.id){
                ID_HOME -> {
                    loadFragment(HomeFragment(visitProfileListener = {
                        loadFragment(VisitProfileFragment( visitProfileExitListener = {

                            supportFragmentManager.popBackStack()

                        }))
                    }))
                }
               ID_EXPLORE ->{
                   loadFragment(TrendingFragment())
               }
               ID_MESSAGE -> {
                   loadFragment(ChatFragment())
               }
              ID_ACCOUNT -> {
                   loadFragment(UserProfileFragment())
              }
            }
        }

        notificationIcon.setOnClickListener {
            bottomNavigation.onClickedListener(BottomNav.Model(
                ID_NOTIFICATION,
                R.drawable.ring_icon,
                "Notification"
            ))
            loadFragment(NotificationFragment())
        }




    }


    private fun loadFragment(fragment: Fragment){
        if(fragment is NotificationFragment){
            bottomNavigation.callListenerWhenIsSelected = true
            notificationIcon.setImageResource(R.drawable.notification_clicked_icon)
        }
        else{
            bottomNavigation.callListenerWhenIsSelected = false
            notificationIcon.setImageResource(R.drawable.ring_icon)
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
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

