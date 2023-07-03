package com.singlesSociety

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.singlesSociety.UiModels.BottomNav
import com.singlesSociety.fragments.*
import com.facebook.drawee.backends.pipeline.Fresco
import com.singlesSociety.databinding.ActivityMainV2Binding


private const val ID_HOME = 0
private const val ID_EXPLORE = 1
private const val ID_MESSAGE = 2
private const val ID_NOTIFICATION = 3
private const val ID_ACCOUNT = 4
private const val ID_CREATE = 5

class MainAct2 : AppCompatActivity() {


    private lateinit var viewBinding: ActivityMainV2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        viewBinding = ActivityMainV2Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)


         val userEmail: String? = intent.getStringExtra("email")
         val intent = Intent(this, NotificationService::class.java)
         intent.putExtra("userId", userEmail)
         startService(intent)
         val preferences = PreferenceManager.getDefaultSharedPreferences(this)
         preferences.edit().putString("userEmail", userEmail).apply()

        viewBinding.bottomNavigation?.apply {

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
        viewBinding.bottomNavigation?.setCount(ID_MESSAGE,"25")

        viewBinding.bottomNavigation.setOnClickMenuListener {
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
                           adjustLayoutParams(true, true, true)
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
                       },
                       visitEventListener = {
                           adjustLayoutParams(true, true, true)
                           loadFragment(EventLandingPageFragment( eventLandingExitListener = {
                               adjustLayoutParams(false)
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

        viewBinding.notificationIcon.setOnClickListener {
            viewBinding.bottomNavigation.onClickedListener(BottomNav.Model(
                ID_NOTIFICATION,
                R.drawable.ring_icon,
                "Notification"
            ))
            loadFragment(NotificationFragment())
        }




    }

    private fun adjustLayoutParams(adjustView: Boolean, adjustToolbar: Boolean = false, adjustBottombar: Boolean = false){
        val params: ViewGroup.MarginLayoutParams = viewBinding.fragmentContainer.layoutParams as ViewGroup.MarginLayoutParams
        if(adjustToolbar && adjustView){
            params.topMargin = 0
            viewBinding.mainActivityToolbar.visibility = View.GONE

        }
        if(adjustBottombar && adjustView){
            viewBinding.bottomNavigation.visibility = View.GONE
            params.bottomMargin = 0
        }
        else if(!adjustView){
            viewBinding.bottomNavigation.visibility = View.VISIBLE
            viewBinding.mainActivityToolbar.visibility = View.VISIBLE
        }
        viewBinding.fragmentContainer.layoutParams = params

    }

   /* private fun adjustLayoutParamsForCreateSpace(adjust: Boolean){
        val params: ViewGroup.MarginLayoutParams = fragmentContainer.layoutParams as ViewGroup.MarginLayoutParams
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
        fragmentContainer.layoutParams = params

    }*/




    private fun loadFragment(fragment: Fragment){
        if(fragment is NotificationFragment){
            viewBinding.bottomNavigation.callListenerWhenIsSelected = true
            viewBinding.notificationIcon.setImageResource(R.drawable.notification_clicked_icon)
        }
        else{
            viewBinding.bottomNavigation.callListenerWhenIsSelected = false
            viewBinding.notificationIcon.setImageResource(R.drawable.ring_icon)
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer,fragment)
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

