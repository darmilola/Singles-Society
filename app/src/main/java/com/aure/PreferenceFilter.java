package com.aure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aure.UiModels.Utils.ListDialog;

import java.util.ArrayList;

public class PreferenceFilter extends AppCompatActivity {

    LinearLayout languageSelect,statusSelect,religionSelect,educationSelect,marriageGoalsSelect,employmentSelect,drinkingSelect,smokingSelect;
    TextView languageText,statusText,religionText,educationText,marriageGoalsText,employmentText,drinkingText,smokingText;
    ListDialog listDialog;
    ArrayList<String> languageItems = new ArrayList<>();
    ArrayList<String> statusItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_filter);
        initView();
    }

    private void initView(){
        populateDialogListView();
        languageSelect = findViewById(R.id.language_filter_select);
        statusSelect = findViewById(R.id.status__filter_select);
        religionSelect = findViewById(R.id.religion_filter_select);
        educationSelect = findViewById(R.id.education_filter_select);
        marriageGoalsSelect = findViewById(R.id.marriage_goals_filter_select);
        employmentSelect = findViewById(R.id.employment_filter_select);
        drinkingSelect = findViewById(R.id.drinking_filter_select);
        smokingSelect = findViewById(R.id.smoking_filter_select);

        languageText = findViewById(R.id.language_filter_select_textview);
        statusText = findViewById(R.id.status_filter_select_textview);
        religionText = findViewById(R.id.religion_filter_select_textview);
        educationText = findViewById(R.id.education_filter_select_textview);
        marriageGoalsText = findViewById(R.id.marriage_goal_filter_select_textview);
        employmentText = findViewById(R.id.employment_filter_select_textview);
        drinkingText = findViewById(R.id.drinking_filter_select_textview);
        smokingText = findViewById(R.id.smoking_filter_select_textview);

        languageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               listDialog = new ListDialog(languageItems,PreferenceFilter.this);
               listDialog.showListDialog();
               listDialog.setItemClickedListener(new ListDialog.OnItemClickedListener() {
                   @Override
                   public void onItemClicked(String item) {
                       languageText.setText(item);
                   }
               });
            }
        });

        statusSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDialog = new ListDialog(statusItems,PreferenceFilter.this);
                listDialog.showListDialog();
                listDialog.setItemClickedListener(new ListDialog.OnItemClickedListener() {
                    @Override
                    public void onItemClicked(String item) {
                        statusText.setText(item);
                    }
                });
            }
        });


    }

    private void populateDialogListView(){
        statusItems.add("Single");
        statusItems.add("Married");
        statusItems.add("Divorced");

        languageItems.add("Hausa and English");
        languageItems.add("Ibo and English");
        languageItems.add("Yoruba and English");
        languageItems.add("English Only");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.special_activity_background));
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.special_activity_background));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
