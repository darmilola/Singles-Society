package com.singlesSociety

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.singlesSociety.UiModels.SignupModel
import com.singlesSociety.UiModels.SignupModel.SignupListener
import com.singlesSociety.UiModels.Utils.NetworkUtils
import com.singlesSociety.databinding.ActivityNewWelcomeBinding
import com.singlesSociety.fragments.WelcomeFragmentLandingPage
import com.singlesSociety.fragments.WelcomeLandingSecondPage
import com.singlesSociety.fragments.WelcomeLandingThirdPage


class NewWelcome : AppCompatActivity() {


    private val GC_SIGN_IN = 1
    private lateinit var viewBinding: ActivityNewWelcomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityNewWelcomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initView()
    }

    private fun initView() {
        val viewPager = viewBinding.viewPager
        viewPager.adapter = PageAdapter(supportFragmentManager)
        viewPager.autoScroll(3000)
        viewBinding.dotsIndicator.setViewPager(viewPager)
        //viewPager.setPageTransformer(false, LayoutUtils.FadePageTransformer());

       /* viewBinding.googleSignIn.setOnClickListener(
            View.OnClickListener { startGoogleSignIn() })*/
        viewBinding.emailSignIn.setOnClickListener(View.OnClickListener {

           /* val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@NewWelcome,
                (viewBinding.welcomePeople as View?)!!, "welcomePeople"
            )*/
            val intent = Intent(this, ConnectWithEmail::class.java)
            startActivity(intent)
        })

        viewBinding.createNewAccount.setOnClickListener {
            val intent = Intent(this, SignUpWithEmail::class.java)
            startActivity(intent)
        }
       /* viewBinding.getStarted.setOnClickListener(View.OnClickListener {

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@NewWelcome,
                (viewBinding.welcomePeople as View?)!!, "welcomePeople"
            )
            val intent = Intent(this, SignUpWithEmail::class.java)
            startActivity(intent, options.toBundle())
        })*/
    }


    override fun onResume() {
        super.onResume()
        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.special_activity_background)
            window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }
    private fun startGoogleSignIn() {
        val mSignInClient: GoogleSignInClient
        val options =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
                .requestProfile()
                .build()
        mSignInClient = GoogleSignIn.getClient(this, options)
        val intent = mSignInClient.signInIntent
        startActivityForResult(intent, GC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            if (task.isSuccessful) {
                val acct = task.result
                handleSignInResult(acct)
            } else {
                Toast.makeText(this, "Error Signing in Please try again", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun handleSignInResult(account: GoogleSignInAccount?) {
        val firstname = account!!.givenName
        val lastname = account.familyName
        val userEmail = account.email
        val userPhotoUrl = account.photoUrl.toString()
        val signupModel =
            SignupModel(firstname, lastname, userEmail, userPhotoUrl, this)
        if (!NetworkUtils(this).isNetworkAvailable) {
            Toast.makeText(this, "No Network", Toast.LENGTH_SHORT).show()
        } else if (firstname.equals("null", ignoreCase = true) || lastname.equals(
                "null",
                ignoreCase = true
            )
        ) {
            Toast.makeText(this, "Credentials not valid", Toast.LENGTH_SHORT).show()
        } else {
            signupModel.SignupUsingGmail()
            signupModel.setSignupListener(object : SignupListener {
                override fun isSuccessful(message: String) {
                    val intent = Intent(this@NewWelcome, MainActivityV2::class.java)
                    intent.putExtra("email", userEmail)
                    startActivity(intent)
                    finish()
                }

                override fun isFailed(message: String) {
                    Toast.makeText(this@NewWelcome, message, Toast.LENGTH_LONG).show()
                }
            })
        }
    }


    class PageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return 3
        }

        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> {
                    WelcomeFragmentLandingPage()
                }

                1 -> {
                    WelcomeLandingSecondPage()
                }

                2 -> {
                    WelcomeLandingThirdPage()
                }

                else -> {
                    WelcomeFragmentLandingPage()
                }
            }
        }
    }
    private fun ViewPager.autoScroll(interval: Long) {

        val handler = Handler()
        var scrollPosition = 0

        val runnable = object : Runnable {

            override fun run() {

                /**
                 * Calculate "scroll position" with
                 * adapter pages count and current
                 * value of scrollPosition.
                 */
                val count = adapter?.count ?: 0
                setCurrentItem(scrollPosition++ % count, true)

                handler.postDelayed(this, interval)
            }
        }

        addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                // Updating "scroll position" when user scrolls manually
                scrollPosition = position + 1
            }

            override fun onPageScrollStateChanged(state: Int) {
                // Not necessary
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // Not necessary
            }
        })

        handler.post(runnable)
    }


}
