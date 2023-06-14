package com.aure;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.simform.custombottomnavigation.SSCustomBottomNavigation;

import java.util.ArrayList;

public class MainActivityV2 extends AppCompatActivity {

    SSCustomBottomNavigation bottomNavigation;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setModels(populateBottombar());
    }

    private ArrayList<SSCustomBottomNavigation.Model> populateBottombar(){
        SSCustomBottomNavigation.Model main = new SSCustomBottomNavigation.Model(1,R.drawable.chat_icon,"Chat");
        SSCustomBottomNavigation.Model main2 = new SSCustomBottomNavigation.Model(2,R.drawable.about,"About");
        SSCustomBottomNavigation.Model main3 = new SSCustomBottomNavigation.Model(3,R.drawable.love,"Love");
        SSCustomBottomNavigation.Model main4 = new SSCustomBottomNavigation.Model(4,R.drawable.cancel_icon,"Cancel");

        ArrayList<SSCustomBottomNavigation.Model> iconList = new ArrayList<SSCustomBottomNavigation.Model>();
        iconList.add(main);
        iconList.add(main2);
        iconList.add(main3);
        iconList.add(main4);

        return iconList;

    }

}