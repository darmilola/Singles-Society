package com.aure;

import androidx.annotation.ArrayRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.aure.UiAdapters.MatchesAdapter;
import com.aure.UiAdapters.MessagesAdapter;
import com.aure.UiModels.MatchesModel;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;

public class MatchesActivity extends AppCompatActivity {

    RecyclerView messagesRecyclerview;
    RecyclerView matchesRecyclerview;
    ImageView backButton;
    ArrayList<MatchesModel> matchesList = new ArrayList<>();
    ArrayList<String>messagesList = new ArrayList<>();
    MessagesAdapter messagesAdapter;
    MatchesAdapter matchesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_matches);
        initView();
    }

    private void initView(){
        messagesRecyclerview = findViewById(R.id.messages_recyclerview);
        matchesRecyclerview = findViewById(R.id.matches_recyclerview);
        backButton = findViewById(R.id.matches_back_button);
        MatchesModel matchesModel = new MatchesModel(0);
        MatchesModel matchesModel1 = new MatchesModel(1);
        for(int i = 0; i < 10; i++){
            matchesList.add(matchesModel);
            matchesList.add(matchesModel1);
            messagesList.add("");
        }
        messagesAdapter = new MessagesAdapter(this,messagesList);
        matchesAdapter = new MatchesAdapter(this,matchesList);

        LinearLayoutManager matchesManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager messagesManger = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        messagesRecyclerview.setLayoutManager(messagesManger);
        matchesRecyclerview.setLayoutManager(matchesManager);

        messagesRecyclerview.setAdapter(messagesAdapter);
        matchesRecyclerview.setAdapter(matchesAdapter);

    }


    @Override
    public void onResume() {

        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.special_activity_background));
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.special_activity_background));
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }

}
