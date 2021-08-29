package com.aure;

import androidx.annotation.ArrayRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aure.UiAdapters.MatchesAdapter;
import com.aure.UiAdapters.MessagesAdapter;
import com.aure.UiModels.MatchesModel;
import com.aure.UiModels.MessageConnectionModel;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;

public class MatchesActivity extends AppCompatActivity {

    RecyclerView messagesRecyclerview;
    RecyclerView matchesRecyclerview;
    ImageView backButton;
    ArrayList<MatchesModel> matchesList = new ArrayList<>();
    ArrayList<MessageConnectionModel>messagesList = new ArrayList<>();
    MessagesAdapter messagesAdapter;
    MatchesAdapter matchesAdapter;
    LinearLayout matchesRoot;
    ProgressBar progressBar;
    TextView noMessages,messagesText;
    View matchesView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_matches);
        initView();
    }

    private void initView(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MatchesActivity.this);
        String userEmail = preferences.getString("userEmail","");
        matchesRoot = findViewById(R.id.matches_root);
        progressBar = findViewById(R.id.matches_progressbar);
        noMessages = findViewById(R.id.matches_no_messages);
        messagesText = findViewById(R.id.matches_messsges_text);
        messagesRecyclerview = findViewById(R.id.messages_recyclerview);
        matchesRecyclerview = findViewById(R.id.matches_recyclerview);
        matchesView = findViewById(R.id.matches_messages_view);

        LinearLayoutManager matchesManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager messagesManger = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        messagesRecyclerview.setLayoutManager(messagesManger);
        matchesRecyclerview.setLayoutManager(matchesManager);

        MessageConnectionModel messageConnectionModel = new MessageConnectionModel(userEmail,MatchesActivity.this);

        messageConnectionModel.getConnection();
        messageConnectionModel.setConnectionListener(new MessageConnectionModel.ConnectionListener() {
            @Override
            public void onConnectionReady(ArrayList<MessageConnectionModel> messageConnectionModels, ArrayList<MatchesModel> matchesModelArrayList) {
                messagesAdapter = new MessagesAdapter(MatchesActivity.this,messagesList);
                matchesAdapter = new MatchesAdapter(MatchesActivity.this,matchesList);
                messagesRecyclerview.setAdapter(messagesAdapter);
                matchesRecyclerview.setAdapter(matchesAdapter);
                progressBar.setVisibility(View.GONE);
                matchesRoot.setVisibility(View.VISIBLE);
                matchesRecyclerview.setVisibility(View.VISIBLE);
                messagesRecyclerview.setVisibility(View.VISIBLE);
                Toast.makeText(MatchesActivity.this, "here", Toast.LENGTH_SHORT).show();

                if(matchesModelArrayList.size() < 1){
                    matchesRoot.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    messagesText.setVisibility(View.GONE);
                    matchesView.setVisibility(View.GONE);
                    noMessages.setVisibility(View.VISIBLE);
                    matchesRecyclerview.setVisibility(View.GONE);
                    messagesRecyclerview.setVisibility(View.GONE);
                    noMessages.setText("No Matches");
                }

               else if(messageConnectionModels.size() < 1){
                    matchesRoot.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    messagesText.setVisibility(View.GONE);
                    matchesView.setVisibility(View.VISIBLE);
                    noMessages.setVisibility(View.VISIBLE);
                    messagesRecyclerview.setVisibility(View.GONE);
                }
            }

            @Override
            public void onConnectionEmpty(String message) {
                 matchesRoot.setVisibility(View.VISIBLE);
                 progressBar.setVisibility(View.GONE);
                 messagesText.setVisibility(View.GONE);
                 matchesView.setVisibility(View.GONE);
                 noMessages.setVisibility(View.VISIBLE);
                 messagesRecyclerview.setVisibility(View.GONE);
                 matchesRecyclerview.setVisibility(View.GONE);
                 noMessages.setText("No Matches");
                 Toast.makeText(MatchesActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
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
