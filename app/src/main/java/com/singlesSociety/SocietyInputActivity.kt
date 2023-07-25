package com.singlesSociety

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.singlesSociety.UiModels.Utils.FragmentTarget
import com.singlesSociety.fragments.ChipInputFragment
import com.singlesSociety.fragments.SelectionInputFragment
import kotlin.math.log

class SocietyInputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_society_input)

        if (savedInstanceState == null) {
            val args: Bundle = intent.extras ?: Bundle()
            when (fragmentTarget) {
                "select" -> {
                    openFragment(
                        SelectionInputFragment.newInstance(args)
                    )
                }
                "chip" -> {
                     openFragment(ChipInputFragment.newInstance(args))
                }
                else -> throw IllegalStateException(
                )
            }
        }
    }

    private val fragmentTarget: String? by lazy {
        intent.getStringExtra(TARGET_FRAGMENT_FIELD) as String
    }

    private val TARGET_FRAGMENT_FIELD: String = "targetFragmentClassName"
    private val inputSelectionScreens = listOf(
        FragmentTarget.LIST_SELECTION_FRAGMENT,
        FragmentTarget.CHIP_SELECTION_FRAGMENT
    )

    fun start(
        callingActivity: Activity,
        fragmentToOpen: String,
        arguments: Bundle? = null,
        requestCode: Int,
        flags: Int = 0
    ) {
        val intent = Intent(callingActivity, SocietyInputActivity::class.java)
        intent.putExtra(TARGET_FRAGMENT_FIELD,fragmentToOpen)
        callingActivity.startActivityForResult(intent, requestCode)

    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.commit(true) {
            add(R.id.inputContainer, fragment)
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

}