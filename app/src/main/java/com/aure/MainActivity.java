package com.aure;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.aure.UiAdapters.ShowcaseMainAdapter;
import com.aure.UiModels.MainActivityModel;
import com.aure.UiModels.PreviewProfileModel;
import com.aure.UiModels.ShowCaseMainModel;
import com.aure.UiModels.ShowCaseModel;
import com.aure.UiModels.ShowcaseMetadata;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;
import com.yuyakaido.android.cardstackview.SwipeableMethod;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CardStackListener {

    CardStackView userShowcaseStack;
    CardStackLayoutManager manager;
    ShowcaseMainAdapter showcaseMainAdapter;
    ArrayList<ShowCaseMainModel> showCaseMainModelArrayList = new ArrayList<>();
    CardView leftSwipeCard,rightSwipeCard;
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FrameLayout mainView;
    ProgressBar progressBar;
    LinearLayout mainInfoToggleLayout;
    LinearLayout mainMarketPlace;
    LinearLayout matchesMenu;
    LinearLayout completeProfile;
    LinearLayout filterProfile;
    LinearLayout myAccount;
    LinearLayout inviteAFriend;
    LinearLayout helpDesk;
    LinearLayout logOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main_layout);
        initView();
    }

    private void initView(){

        String userEmail = getIntent().getStringExtra("email");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        preferences.edit().putString("userEmail",userEmail).apply();
        inviteAFriend = findViewById(R.id.invite_a_friend_layout);
        helpDesk = findViewById(R.id.help_desk_layout);
        logOut = findViewById(R.id.log_out_layout);
        myAccount = findViewById(R.id.activity_main_my_account);
        matchesMenu = findViewById(R.id.matches_menu);
        mainMarketPlace = findViewById(R.id.main_marketplace);
        completeProfile = findViewById(R.id.complete_profile);
        filterProfile = findViewById(R.id.filter_layout);

        inviteAFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CompleteProfilePrompt.class));
            }
        });

        filterProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PreferenceFilter.class));
            }
        });

        helpDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TakeActionOnProfile.class));
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MetMatchPage.class));
            }
        });

        matchesMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MatchesActivity.class));
            }
        });

        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MyAccountActivity.class));
            }
        });

        mainMarketPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Marketplace.class));
            }
        });

        completeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CompleteProfile.class));
            }
        });

        drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        leftSwipeCard = findViewById(R.id.user_swipe_left);
        rightSwipeCard = findViewById(R.id.user_swipe_right);
        userShowcaseStack = findViewById(R.id.showcase_main_recyclerview);
        toolbar = findViewById(R.id.activity_main_toolbar);
        mainView = findViewById(R.id.activity_main_main_view);
        progressBar = findViewById(R.id.activity_main_progressbar);
        mainInfoToggleLayout = findViewById(R.id.main_info_toggle_layout);

        drawerToggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,toolbar,R.string.app_name,R.string.app_name){
            @Override
            public void onDrawerSlide(View drawerView,float slideOffset){
                super.onDrawerSlide(drawerView,slideOffset);
                mainView.setTranslationX(slideOffset * drawerView.getWidth());
                drawerLayout.bringChildToFront(drawerView);
                drawerLayout.requestLayout();
            }
            @Override
            public void onDrawerOpened(View drawerView){

            }
            @Override
            public void onDrawerClosed(View drawerView){
                // setTransluscentNavFlag(false);
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);


        mainInfoToggleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        leftSwipeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                        .setDirection(Direction.Left)
                        .setDuration(Duration.Slow.duration)
                        .setInterpolator(new AccelerateInterpolator())
                        .build();
                manager.setSwipeAnimationSetting(setting);
                userShowcaseStack.swipe();

            }
        });
        rightSwipeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                        .setDirection(Direction.Right)
                        .setDuration(Duration.Slow.duration)
                        .setInterpolator(new AccelerateInterpolator())
                        .build();
                manager.setSwipeAnimationSetting(setting);
                userShowcaseStack.swipe();

            }
        });

        MainActivityModel mainActivityModel = new MainActivityModel(MainActivity.this);
        mainActivityModel.GetUserInfo();
        mainActivityModel.setInfoReadyListener(new MainActivityModel.InfoReadyListener() {
            @Override
            public void onReady(MainActivityModel mainActivityModel) {
                if(mainActivityModel.getIsProfileCompleted().equalsIgnoreCase("false")){
                    Intent intent = new Intent(MainActivity.this,CompleteProfilePrompt.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    MainActivityModel mainActivityModel2 = new MainActivityModel(MainActivity.this);
                    mainActivityModel2.GetShowUserInfo();
                    mainActivityModel2.setShowcaseInfoReadyListener(new MainActivityModel.ShowcaseInfoReadyListener() {
                        @Override
                        public void onReady(ArrayList<PreviewProfileModel> previewProfileModels) {
                            for (PreviewProfileModel previewProfileModel: previewProfileModels) {

                                ArrayList<ShowCaseModel> showCaseModelArrayList = new ArrayList<>();
                                ArrayList<String> mainStrings = new ArrayList<>();
                                ArrayList<String> quoteStrings = new ArrayList<>();
                                ArrayList<String> aboutStrings = new ArrayList<>();
                                ArrayList<String> careerStrings = new ArrayList<>();
                                ArrayList<String> aboutTextStrings = new ArrayList<>();
                                ArrayList<String> imageStrings = new ArrayList<>();
                                ArrayList<String> goalStrings = new ArrayList<>();

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
                                ShowCaseMainModel showCaseMainModel = new ShowCaseMainModel(showCaseModelArrayList);
                                showCaseMainModelArrayList.add(showCaseMainModel);
                            }
                            showcaseMainAdapter = new ShowcaseMainAdapter(MainActivity.this,showCaseMainModelArrayList);
                            initializeCardStack();
                            progressBar.setVisibility(View.GONE);
                            mainView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
            @Override
            public void onError(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initializeCardStack() {

        manager = new CardStackLayoutManager(this,this);
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(2);
        manager.setTranslationInterval(6.0f);
        manager.setScaleInterval(0.90f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(30.0f);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setCanScrollHorizontal(true);
        manager.setCanScrollVertical(false);
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        userShowcaseStack.setLayoutManager(manager);
        userShowcaseStack.setAdapter(showcaseMainAdapter);

    }




    @Override
    public void onResume() {

        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.special_activity_background));
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.special_activity_background));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {
      //showcaseMovementProgress.setProgress(10);
      //recyclerviewProgress = 0;
    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {

    }

    @Override
    public void onCardDisappeared(View view, int position) {
        if(position == showCaseMainModelArrayList.size()-1){
            //At the last position of the card
           Intent intent = new Intent(MainActivity.this,CaughtUpActivity.class);
           startActivity(intent);
           finish();
        }
    }
}
