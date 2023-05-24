package com.aure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aure.UiModels.SignupModel;
import com.aure.UiModels.Utils.NetworkUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;

public class WelcomeActivity extends AppCompatActivity {

    LinearLayout googleSignIn,emailSignin, getStarted;
    private static final int GC_SIGN_IN = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_v2);
        initView();
    }

    private void initView(){

        googleSignIn = findViewById(R.id.signin_with_google);
        emailSignin = findViewById(R.id.signin_with_email);
        getStarted = findViewById(R.id.get_started);


   /*     // set up spanned string with url
        SpannableString termsOfUseString = new SpannableString("By Creating your Auretayya account you agree to our  Terms of Use and Privacy Policy");
        String privacyUrl = "https://developer.android.com";
        termsOfUseString.setSpan(new URLSpan(privacyUrl), 53, 65, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        termsOfUseString.setSpan(new URLSpan(privacyUrl), 70, 84, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/


        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGoogleSignIn();
            }
        });
        emailSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this,ConnectWithEmail.class));
            }
        });

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this,SignUpWithEmail.class));
            }
        });
    }


    @Override
    public void onResume() {

        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.White));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void startGoogleSignIn(){

        GoogleSignInClient mSignInClient;
        GoogleSignInOptions options =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestProfile()
                        .build();
        mSignInClient = GoogleSignIn.getClient(this, options);
        Intent intent = mSignInClient.getSignInIntent();
        startActivityForResult(intent, GC_SIGN_IN);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GC_SIGN_IN){
            Task<GoogleSignInAccount> task =
                    GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()) {
                GoogleSignInAccount acct = task.getResult();
                handleSignInResult(acct);
            } else {

                Toast.makeText(this, "Error Signing in Please try again", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void handleSignInResult(GoogleSignInAccount account){
        String firstname = account.getGivenName();
        String lastname = account.getFamilyName();
        String userEmail = account.getEmail();
        String userPhotoUrl = String.valueOf(account.getPhotoUrl());

        SignupModel signupModel = new SignupModel(firstname,lastname,userEmail,userPhotoUrl,WelcomeActivity.this);
        if(!new NetworkUtils(WelcomeActivity.this).isNetworkAvailable()) {
            Toast.makeText(WelcomeActivity.this, "No Network", Toast.LENGTH_SHORT).show();
        }
        else if(firstname.equalsIgnoreCase("null") || lastname.equalsIgnoreCase("null")){
            Toast.makeText(WelcomeActivity.this, "Credentials not valid", Toast.LENGTH_SHORT).show();
        }
        else {
            signupModel.SignupUsingGmail();
            signupModel.setSignupListener(new SignupModel.SignupListener() {
                @Override
                public void isSuccessful(String message) {
                    Intent intent = new Intent(WelcomeActivity.this,MainActivityV2.class);
                    intent.putExtra("email",userEmail);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void isFailed(String message) {
                    Toast.makeText(WelcomeActivity.this, message, Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}
