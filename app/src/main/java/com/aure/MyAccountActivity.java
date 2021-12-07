package com.aure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAccountActivity extends AppCompatActivity {

    LinearLayout myListings;
    CircleImageView circleImageView;
    TextView accountName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        initView();
    }

    private void initView(){
        myListings = findViewById(R.id.account_my_listings);
        circleImageView = findViewById(R.id.my_account_profile_image);
        accountName = findViewById(R.id.my_account_name);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String firstname =  preferences.getString("firstname","");
        String lastname =  preferences.getString("lastname","");
        String imageUrl =  preferences.getString("imageUrl","");
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
    }
}
