package com.aure

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aure.UiModels.BottomNav
import kotlinx.android.synthetic.main.activity_main_v2.*

private const val ID_HOME = 0
private const val ID_EXPLORE = 1
private const val ID_MESSAGE = 2
private const val ID_NOTIFICATION = 3
private const val ID_ACCOUNT = 4

class MainAct2 : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_v2)

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
                            "Favorite"
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
                            ID_NOTIFICATION,
                            R.drawable.user_icon,
                            "Notification"
                    )
            )
        }
        bottomNavigation?.setCount(ID_MESSAGE,"99")


    }



}

