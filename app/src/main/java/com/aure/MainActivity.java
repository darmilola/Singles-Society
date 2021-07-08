package com.aure;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import com.aure.UiAdapters.ShowcaseMainAdapter;
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

    private static final String TAG = "MainAct";
    CardStackView userShowcaseStack;
    CardStackLayoutManager manager;
    ShowcaseMainAdapter showcaseMainAdapter;
    ArrayList<ShowCaseMainModel> showCaseMainModelArrayList = new ArrayList<>();
    ArrayList<ShowCaseModel> showCaseModelArrayList = new ArrayList<>();
    CardView leftSwipeCard,rightSwipeCard;
    ShowcaseMetadata showcaseMetadata = new ShowcaseMetadata(6200);
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FrameLayout mainView;
    LinearLayout mainInfoToggleLayout;
    LinearLayout mainMarketPlace;
    LinearLayout matchesMenu;
    LinearLayout filterLayout;
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

        inviteAFriend = findViewById(R.id.invite_a_friend_layout);
        helpDesk = findViewById(R.id.help_desk_layout);
        logOut = findViewById(R.id.log_out_layout);
        myAccount = findViewById(R.id.activity_main_my_account);
        matchesMenu = findViewById(R.id.matches_menu);
        mainMarketPlace = findViewById(R.id.main_marketplace);
        filterLayout = findViewById(R.id.filter_layout);

        inviteAFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CompleteProfileActivity.class));
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

        filterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Filter.class));
            }
        });

        drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        leftSwipeCard = findViewById(R.id.user_swipe_left);
        rightSwipeCard = findViewById(R.id.user_swipe_right);
        userShowcaseStack = findViewById(R.id.showcase_main_recyclerview);
        toolbar = findViewById(R.id.activity_main_toolbar);
        mainView = findViewById(R.id.activity_main_main_view);
        mainInfoToggleLayout = findViewById(R.id.main_info_toggle_layout);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,toolbar,R.string.app_name,R.string.app_name){



            @Override
            public void onDrawerSlide(View drawerView,float slideOffset){
                super.onDrawerSlide(drawerView,slideOffset);
                //setTransluscentNavFlag(true);
                //getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
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

        initIndividualUserToShowcase();

        for(int i = 0; i < 20; i++){

            ShowCaseMainModel showCaseMainModel = new ShowCaseMainModel(showCaseModelArrayList,showcaseMetadata);
            showCaseMainModelArrayList.add(showCaseMainModel);

        }

        showcaseMainAdapter = new ShowcaseMainAdapter(this,showCaseMainModelArrayList);

        showcaseMainAdapter.setShowcaseViewProgressStateChange(new ShowcaseMainAdapter.ShowcaseViewProgressStateChange() {
            @Override
            public void onProgressChange(int progress) {


             //   recyclerviewProgress = recyclerviewProgress + progress;

             //   showcaseMovementProgress.setProgress(recyclerviewProgress);
               // Log.e(TAG, "onProgressChange: "+recyclerviewProgress );
                //showcaseMovementProgress.setProgress(progress);

            }


            @Override
            public void onInitialize(int initialValue) {

                //showcaseMovementProgress.setMax(initialValue);

            }

            @Override
            public void onNegativeProgress(int nProgress) {
              //  recyclerviewProgress = recyclerviewProgress - nProgress;
              //  showcaseMovementProgress.setProgress(recyclerviewProgress);
             //   Log.e(TAG, "onProgressChange: "+recyclerviewProgress );

            }
        });
        initializeCardStack();

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

    private void initIndividualUserToShowcase(){

        ShowCaseModel showCaseModel = new ShowCaseModel(1);
        ShowCaseModel showCaseModel2 = new ShowCaseModel(2);
        ShowCaseModel showCaseModel3 = new ShowCaseModel(3);
        ShowCaseModel showCaseModel4 = new ShowCaseModel(4);
        ShowCaseModel showCaseModel5 = new ShowCaseModel(5);
        ShowCaseModel showCaseModel7 = new ShowCaseModel(7);
        ShowCaseModel showCaseModel6 = new ShowCaseModel(6);
        ShowCaseModel showCaseModel9 = new ShowCaseModel(9);
        ShowCaseModel showCaseModel10 = new ShowCaseModel(10);
        ShowCaseModel showCaseModel11 = new ShowCaseModel(11);
        ShowCaseModel showCaseModel8 = new ShowCaseModel(8);
        for(int i = 0; i < 1; i++){
            showCaseModelArrayList.add(showCaseModel);
            showCaseModelArrayList.add(showCaseModel3);
            showCaseModelArrayList.add(showCaseModel5);
            showCaseModelArrayList.add(showCaseModel2);
            showCaseModelArrayList.add(showCaseModel9);
            showCaseModelArrayList.add(showCaseModel4);
            showCaseModelArrayList.add(showCaseModel6);
            showCaseModelArrayList.add(showCaseModel7);
            showCaseModelArrayList.add(showCaseModel10);
            showCaseModelArrayList.add(showCaseModel11);
            showCaseModelArrayList.add(showCaseModel8);
        }

    }


    @Override
    public void onResume() {

        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.full_transparency));
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {


    }

    @Override
    public void onCardSwiped(Direction direction) {

       // showcaseMovementProgress.setProgress(10);
     //   recyclerviewProgress = 0;

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

    }
}
