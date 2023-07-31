package com.singlesSociety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.singlesSociety.uiAdapters.ConnectionMatchesAdapter;
import com.singlesSociety.uiAdapters.ConnectionsAdapter;
import com.singlesSociety.UiModels.MatchesModel;
import com.singlesSociety.UiModels.MessageConnectionModel;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MatchesActivity extends AppCompatActivity {

    RecyclerView messagesRecyclerview;
    RecyclerView matchesRecyclerview;
    ImageView backButton;
    ArrayList<MatchesModel> matchesList = new ArrayList<>();
    ArrayList<MessageConnectionModel>messagesList = new ArrayList<>();
    ConnectionsAdapter connectionsAdapter;
    ConnectionMatchesAdapter connectionMatchesAdapter;
    LinearLayout matchesRoot;
    ProgressBar progressBar;
    TextView noMessages;
    LinearLayout errorLayout;
    MaterialButton errorRetry;
    LinearLayout goBack;
    MessageConnectionModel messageConnectionModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_matches);
        initView();
    }

    private void initView(){
        errorRetry = findViewById(R.id.error_page_retry);
        errorLayout = findViewById(R.id.error_layout_root);
        goBack = findViewById(R.id.matches_back_button);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MatchesActivity.this);
        String userEmail = preferences.getString("userEmail","");
        matchesRoot = findViewById(R.id.matches_root);
        progressBar = findViewById(R.id.matches_progressbar);
        noMessages = findViewById(R.id.matches_no_messages);
        messagesRecyclerview = findViewById(R.id.messages_recyclerview);
        matchesRecyclerview = findViewById(R.id.matches_recyclerview);

        messageConnectionModel = new MessageConnectionModel(userEmail,MatchesActivity.this);

        messageConnectionModel.getConnection();
        messageConnectionModel.setConnectionListener(new MessageConnectionModel.ConnectionListener() {
            @Override
            public void onConnectionReady(ArrayList<MessageConnectionModel> messageConnectionModels, ArrayList<MatchesModel> matchesModelArrayList) {
                connectionsAdapter = new ConnectionsAdapter(MatchesActivity.this,messageConnectionModels);
                connectionMatchesAdapter = new ConnectionMatchesAdapter(MatchesActivity.this,matchesModelArrayList);
                messagesRecyclerview.setAdapter(connectionsAdapter);
                matchesRecyclerview.setAdapter(connectionMatchesAdapter);
                LinearLayoutManager matchesManager = new LinearLayoutManager(MatchesActivity.this,LinearLayoutManager.HORIZONTAL,false);
                LinearLayoutManager messagesManger = new LinearLayoutManager(MatchesActivity.this,LinearLayoutManager.VERTICAL,false);
                messagesRecyclerview.setLayoutManager(messagesManger);
                matchesRecyclerview.setLayoutManager(matchesManager);
                progressBar.setVisibility(View.GONE);
                matchesRoot.setVisibility(View.VISIBLE);
                matchesRecyclerview.setVisibility(View.VISIBLE);
                messagesRecyclerview.setVisibility(View.VISIBLE);
                noMessages.setVisibility(View.GONE);

                if(matchesModelArrayList.size() < 1){
                    matchesRoot.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    noMessages.setVisibility(View.VISIBLE);
                    matchesRecyclerview.setVisibility(View.GONE);
                    messagesRecyclerview.setVisibility(View.VISIBLE);
                    noMessages.setText("No Matches");
                }

               else if(messageConnectionModels.size() < 1){
                    matchesRoot.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    noMessages.setVisibility(View.VISIBLE);
                    messagesRecyclerview.setVisibility(View.GONE);
                    matchesRecyclerview.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onConnectionEmpty(String message) {
                 matchesRoot.setVisibility(View.VISIBLE);
                 progressBar.setVisibility(View.GONE);
                 noMessages.setVisibility(View.VISIBLE);
                 messagesRecyclerview.setVisibility(View.GONE);
                 matchesRecyclerview.setVisibility(View.GONE);
                 noMessages.setText("No Matches");
            }

            @Override
            public void onError() {
                matchesRoot.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                noMessages.setVisibility(View.GONE);
                messagesRecyclerview.setVisibility(View.GONE);
                matchesRecyclerview.setVisibility(View.GONE);
                errorLayout.setVisibility(View.VISIBLE);
            }
        });

        errorRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchesRoot.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                noMessages.setVisibility(View.GONE);
                messagesRecyclerview.setVisibility(View.GONE);
                matchesRecyclerview.setVisibility(View.GONE);
                errorLayout.setVisibility(View.GONE);
                messageConnectionModel.getConnection();
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void onResume() {

        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.white));
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.white));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
          }
    }

}
