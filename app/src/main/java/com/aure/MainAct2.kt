package com.aure

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aure.UiModels.BottomNav
import com.aure.UiModels.Utils.LayoutUtils
import com.aure.fragments.*
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.activity_main_v2.*
import kotlinx.android.synthetic.main.activity_main_v2.notificationIcon
import kotlinx.android.synthetic.main.fragment_create_new_space.*


private const val ID_HOME = 0
private const val ID_EXPLORE = 1
private const val ID_MESSAGE = 2
private const val ID_NOTIFICATION = 3
private const val ID_ACCOUNT = 4
private const val ID_CREATE = 5

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
                            R.drawable.hot_trending_icon,
                            "Home"
                    )
            )
            add(
                    BottomNav.Model(
                            ID_EXPLORE,
                            R.drawable.navigate_circle_outline_icon,
                            "Trending"
                    )
            )
            add(
                BottomNav.Model(
                    ID_CREATE,
                    R.drawable.plus_circle_fill_icon,
                    "Create"
                )
            )
            add(
                    BottomNav.Model(
                            ID_MESSAGE,
                            R.drawable.chat_bubble_empty_icon,
                            "Chat"
                    )
            )
            add(
                    BottomNav.Model(
                            ID_ACCOUNT,
                            R.drawable.person_icon,
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
                ID_CREATE -> startActivity(Intent(this,CreatePostActivity::class.java))
                ID_EXPLORE ->{
                   loadFragment(TrendingFragment(
                       enterSpaceListener = {
                           adjustLayoutParams(true)
                           loadFragment(SpacesMainFragment(visitProfileListener = {
                               loadFragment(VisitProfileFragment(null,true))
                           }, exitSpaceListener = {
                               adjustLayoutParams(false)
                               supportFragmentManager.popBackStack()

                           }))
                       },
                       createSpaceListener = {
                         //  adjustLayoutParamsForCreateSpace(true)
                           loadFragment(CreateNewSpaceFragment( createSpaceExitListener = {
                           //    adjustLayoutParamsForCreateSpace(false)
                               supportFragmentManager.popBackStack()

                           }))
                       }
                   ))
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

    private fun adjustLayoutParams(adjust: Boolean){
        val params: ViewGroup.MarginLayoutParams = container.layoutParams as ViewGroup.MarginLayoutParams
        if(adjust){
            params.topMargin = 0
            mainActivityToolbar.visibility = View.GONE
        }
        else{

        }
        container.layoutParams = params

    }

   /* private fun adjustLayoutParamsForCreateSpace(adjust: Boolean){
        val params: ViewGroup.MarginLayoutParams = container.layoutParams as ViewGroup.MarginLayoutParams
        if(adjust){
            params.topMargin = 0
            params.bottomMargin = 0
            mainActivityToolbar.visibility = View.GONE
            bottomNavigation.visibility = View.GONE
            window.statusBarColor = resources.getColor(R.color.pink)
            window.navigationBarColor = resources.getColor(R.color.new_pink)
            notificationIcon.visibility = View.GONE
            window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }
        else{
            window.statusBarColor = resources.getColor(R.color.special_activity_background)
            window.navigationBarColor = resources.getColor(R.color.special_activity_background)
            mainActivityToolbar.visibility = View.VISIBLE
            bottomNavigation.visibility = View.VISIBLE
            notificationIcon.visibility = View.VISIBLE
            params.topMargin = LayoutUtils().convertDpToPixel(55, context = this).toInt()
            params.bottomMargin = LayoutUtils().convertDpToPixel(55, context = this).toInt()
            clearLightStatusBar(activity = this)
        }
        container.layoutParams = params

    }*/



    private fun clearLightStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = activity.window.decorView.systemUiVisibility // get current flag
            flags =
                flags xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // use XOR here for remove LIGHT_STATUS_BAR from flags
            activity.window.decorView.systemUiVisibility = flags
        }
    }

    private fun loadFragment(fragment: Fragment){
        if((fragment !is SpacesMainFragment) && (fragment !is CreateNewSpaceFragment) ){
            adjustLayoutParams(false)
        }
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
            window.navigationBarColor = ContextCompat.getColor(this, R.color.white)
            window.statusBarColor = ContextCompat.getColor(this, R.color.white)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }



}

