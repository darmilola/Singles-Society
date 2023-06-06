package com.aure

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.aure.UiModels.SignupModel
import com.aure.UiModels.SignupModel.SignupListener
import com.aure.UiModels.Utils.NetworkUtils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_new_welcome.*

class NewWelcome : AppCompatActivity() {


    private val GC_SIGN_IN = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_welcome)
        initView()
    }

    private fun initView() {

        googleSignIn.setOnClickListener(
            View.OnClickListener { startGoogleSignIn() })
        emailSignIn.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this,
                    ConnectWithEmail::class.java
                )
            )
        })
        getStarted.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this,
                    SignUpWithEmail::class.java
                )
            )
        })
    }


    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.special_activity_background)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
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

}
