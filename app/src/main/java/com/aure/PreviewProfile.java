package com.aure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aure.UiAdapters.ViewProfileAdapter;
import com.aure.UiModels.CompleteProfileModel;
import com.aure.UiModels.PreviewProfileModel;
import com.aure.UiModels.RecyclerViewPagerIndicator;
import com.aure.UiModels.ShowCaseModel;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_profile);
        initView();
    }

    private void initView(){

        progressBar = findViewById(R.id.preview_profile_progress);
        recyclerView = findViewById(R.id.preview_recycerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerViewPagerIndicator(this));

        PreviewProfileModel previewProfileModel = new PreviewProfileModel(PreviewProfile.this);
        previewProfileModel.GetUserInfo();
        previewProfileModel.setInfoReadyListener(new PreviewProfileModel.InfoReadyListener() {
            @Override
            public void onReady(PreviewProfileModel previewProfileModel) {
              progressBar.setVisibility(View.GONE);
              recyclerView.setVisibility(View.VISIBLE);

              mainStrings.add(previewProfileModel.getFirstname());
              mainStrings.add(String.valueOf(previewProfileModel.getAge()));
              mainStrings.add(previewProfileModel.getCity());
              mainStrings.add(previewProfileModel.getOccupation());
              mainStrings.add(previewProfileModel.getImage1Url());
              ShowCaseModel showCaseModel = new ShowCaseModel(mainStrings,1);
              showCaseModelArrayList.add(showCaseModel);

              quoteStrings.add(previewProfileModel.getQuote());
              ShowCaseModel showCaseModel1 = new ShowCaseModel(quoteStrings,2);
              showCaseModelArrayList.add(showCaseModel1);

              aboutStrings.add(previewProfileModel.getStatus());
              aboutStrings.add(previewProfileModel.getSmoking());
              aboutStrings.add(previewProfileModel.getDrinking());
              aboutStrings.add(previewProfileModel.getLanguage());
              aboutStrings.add(previewProfileModel.getReligion());
              ShowCaseModel showCaseModel2 = new ShowCaseModel(aboutStrings,3);
              showCaseModelArrayList.add(showCaseModel2);

              careerStrings.add(previewProfileModel.getEducationLevel());
              careerStrings.add(previewProfileModel.getOccupation());
              careerStrings.add(previewProfileModel.getWorkplace());
              careerStrings.add(previewProfileModel.getImage2Url());
              ShowCaseModel showCaseModel3 = new ShowCaseModel(careerStrings,4);
              showCaseModelArrayList.add(showCaseModel3);

              aboutTextStrings.add(previewProfileModel.getAbout());
              ShowCaseModel showCaseModel4 = new ShowCaseModel(aboutTextStrings,5);
              showCaseModelArrayList.add(showCaseModel4);

              imageStrings.add(previewProfileModel.getImage3Url());
              ShowCaseModel showCaseModel5 = new ShowCaseModel(imageStrings,6);
              showCaseModelArrayList.add(showCaseModel5);

              goalStrings.add(previewProfileModel.getMarriageGoals());
              ShowCaseModel showCaseModel6 = new ShowCaseModel(goalStrings,7);
              showCaseModelArrayList.add(showCaseModel6);

              adapter = new ViewProfileAdapter(PreviewProfile.this,showCaseModelArrayList);
              recyclerView.setAdapter(adapter);

            }

            @Override
            public void onError(String message) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                Toast.makeText(PreviewProfile.this, message, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onResume() {

        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.ixpecial));
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.ixpecial));
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }
}
