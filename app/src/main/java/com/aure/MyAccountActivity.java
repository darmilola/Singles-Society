package com.aure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aure.UiModels.MainActivityModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAccountActivity extends AppCompatActivity {

    LinearLayout myListings, deleteAccount,aboutUs,helpDesk,rateUs,goBack;
    CircleImageView circleImageView;
    TextView accountName,terms,privacy;
    String userEmail;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        initView();
    }

    private void initView(){
        goBack = findViewById(R.id.my_account_goback);
        terms = findViewById(R.id.account_terms);
        privacy = findViewById(R.id.account_privacy_policy);
        aboutUs = findViewById(R.id.account_about_us);
        helpDesk = findViewById(R.id.account_help_desk);
        rateUs = findViewById(R.id.account_rate_us);
        myListings = findViewById(R.id.account_my_listings);
        circleImageView = findViewById(R.id.my_account_profile_image);
        accountName = findViewById(R.id.my_account_name);
        deleteAccount = findViewById(R.id.delete_my_account_layout);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String firstname =  preferences.getString("firstname","");
        String lastname =  preferences.getString("lastname","");
        String imageUrl =  preferences.getString("imageUrl","");
        userEmail =  preferences.getString("userEmail","");
        accountName.setText(firstname +" "+lastname);
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.profileplaceholder)
                .error(R.drawable.profileplaceholder)
                .into(circleImageView);

        myListings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyAccountActivity.this,MyListings.class));
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(browserIntent);
            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(browserIntent);
            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(browserIntent);
            }
        });

        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }

            }
        });

        helpDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] TO = {"auratayyaHq@gmail.com"};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Auratayya Support"));
                    finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MyAccountActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 showDeleteAlert();

            }
        });
    }

    private void showDeleteAlert(){
        new AlertDialog.Builder(MyAccountActivity.this)
                .setTitle("Delete Account")
                .setMessage("You are about to delete your Account, this will wipe all your data from our database.\n Note: You cannot undo this action.")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivityModel mainActivityModel = new MainActivityModel(userEmail,MyAccountActivity.this);
                        mainActivityModel.deleteAccount();
                        mainActivityModel.setDeletionListener(new MainActivityModel.DeletionListener() {
                            @Override
                            public void onSuccess() {

                                GoogleSignInClient mSignInClient;
                                GoogleSignInOptions options =
                                        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestProfile()
                                                .build();
                                mSignInClient = GoogleSignIn.getClient(MyAccountActivity.this, options);
                                mSignInClient.signOut()
                                        .addOnCompleteListener(MyAccountActivity.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(MyAccountActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyAccountActivity.this);
                                                preferences.edit().remove("userEmail").apply();
                                                startActivity(new Intent(MyAccountActivity.this,WelcomeActivity.class));
                                                finish();

                                            }
                                        });

                            }

                            @Override
                            public void onError() {

                            }
                        });
                    }
                })
                .show();
    }

    @Override
    public void onResume() {

        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.ixpecial));
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.ixpecial));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

}
