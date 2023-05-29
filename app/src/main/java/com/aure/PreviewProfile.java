package com.aure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aure.UiAdapters.ViewProfileAdapter;
import com.aure.UiModels.CompleteProfileModel;
import com.aure.UiModels.PreviewProfileModel;
import com.aure.UiModels.RecyclerViewPagerIndicator;
import com.aure.UiModels.ShowCaseModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class PreviewProfile extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ShowCaseModel> showCaseModelArrayList = new ArrayList<>();
    ViewProfileAdapter adapter;
    ProgressBar progressBar;
    ArrayList<String> mainStrings = new ArrayList<>();
    ArrayList<String> quoteStrings = new ArrayList<>();
    ArrayList<String> aboutStrings = new ArrayList<>();
    ArrayList<String> careerStrings = new ArrayList<>();
    ArrayList<String> aboutTextStrings = new ArrayList<>();
    ArrayList<String> imageStrings = new ArrayList<>();
    ArrayList<String> goalStrings = new ArrayList<>();
    TextView search;
    LinearLayout errorLayout;
    MaterialButton errorRetry;
    PreviewProfileModel previewProfileModel;
    LinearLayout previewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_profile);
        initView();
    }

    private void initView(){

        previewBack = findViewById(R.id.preview_profile_back);
        errorLayout = findViewById(R.id.error_layout_root);
        errorRetry = findViewById(R.id.error_page_retry);
        search = findViewById(R.id.preview_profile_search);
        progressBar = findViewById(R.id.preview_profile_progress);
        recyclerView = findViewById(R.id.preview_recycerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerViewPagerIndicator(this));


        previewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        previewProfileModel = new PreviewProfileModel(PreviewProfile.this);
        previewProfileModel.GetUserInfo();
        previewProfileModel.setInfoReadyListener(new PreviewProfileModel.InfoReadyListener() {
            @Override
            public void onReady(PreviewProfileModel previewProfileModel) {
              progressBar.setVisibility(View.GONE);
          /*    recyclerView.setVisibility(View.VISIBLE);

              mainStrings.add(previewProfileModel.getFirstname());
              mainStrings.add(String.valueOf(previewProfileModel.getAge()));
              mainStrings.add(previewProfileModel.getCity());
              mainStrings.add(previewProfileModel.getOccupation());
              mainStrings.add(previewProfileModel.getImage1Url());
            //  ShowCaseModel showCaseModel = new ShowCaseModel(mainStrings,1,new ArrayList<String>());
             // showCaseModelArrayList.add(showCaseModel);

              quoteStrings.add(previewProfileModel.getQuote());
              ShowCaseModel showCaseModel1 = new ShowCaseModel(quoteStrings,2,new ArrayList<String>());
              showCaseModelArrayList.add(showCaseModel1);

              aboutStrings.add(previewProfileModel.getStatus());
              aboutStrings.add(previewProfileModel.getSmoking());
              aboutStrings.add(previewProfileModel.getDrinking());
              aboutStrings.add(previewProfileModel.getLanguage());
              aboutStrings.add(previewProfileModel.getReligion());
              ShowCaseModel showCaseModel2 = new ShowCaseModel(aboutStrings,3,new ArrayList<String>());
              showCaseModelArrayList.add(showCaseModel2);

              careerStrings.add(previewProfileModel.getEducationLevel());
              careerStrings.add(previewProfileModel.getOccupation());
              careerStrings.add(previewProfileModel.getWorkplace());
              careerStrings.add(previewProfileModel.getImage2Url());
              ShowCaseModel showCaseModel3 = new ShowCaseModel(careerStrings,4,new ArrayList<String>());
              showCaseModelArrayList.add(showCaseModel3);

              aboutTextStrings.add(previewProfileModel.getAbout());
              ShowCaseModel showCaseModel4 = new ShowCaseModel(aboutTextStrings,5,new ArrayList<String>());
              showCaseModelArrayList.add(showCaseModel4);

              imageStrings.add(previewProfileModel.getImage3Url());
              ShowCaseModel showCaseModel5 = new ShowCaseModel(imageStrings,6,new ArrayList<String>());
              showCaseModelArrayList.add(showCaseModel5);

              goalStrings.add(previewProfileModel.getMarriageGoals());
              ShowCaseModel showCaseModel6 = new ShowCaseModel(goalStrings,7,new ArrayList<String>());
              showCaseModelArrayList.add(showCaseModel6);

              adapter = new ViewProfileAdapter(PreviewProfile.this,showCaseModelArrayList);
              recyclerView.setAdapter(adapter);*/

            }

            @Override
            public void onError(String message) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                errorLayout.setVisibility(View.VISIBLE);
            }
        });

        errorRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                errorLayout.setVisibility(View.GONE);
                previewProfileModel.GetUserInfo();
            }
        });

    }

    @Override
    public void onResume() {

        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.ixpecial));
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.ixpecial));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        }
    }
}
