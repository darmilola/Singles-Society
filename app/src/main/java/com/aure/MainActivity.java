package com.aure;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import io.socket.client.IO;
import io.socket.client.Socket;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.aure.UiAdapters.ShowcaseMainAdapter;
import com.aure.UiModels.MainActivityModel;
import com.aure.UiModels.PreviewProfileModel;
import com.aure.UiModels.ShowCaseMainModel;
import com.aure.UiModels.ShowCaseModel;
import com.aure.UiModels.ShowcaseMetadata;
import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;
import com.yuyakaido.android.cardstackview.SwipeableMethod;


import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CardStackListener {

    private static int PREFERENCE_INT = 1;
    CardStackView userShowcaseStack;
    CardStackLayoutManager manager;
    ShowcaseMainAdapter showcaseMainAdapter;
    ArrayList<ShowCaseMainModel> showCaseMainModelArrayList = new ArrayList<>();
    CardView leftSwipeCard,rightSwipeCard;
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    CircleImageView profileImageView;
    Toolbar toolbar;
    FrameLayout mainView;
    ProgressBar progressBar;
    LinearLayout emptyLayoutRoot,activityCaughtUpRoot,takeAction;
    LinearLayout mainInfoToggleLayout;
    LinearLayout mainMarketPlace;
    LinearLayout matchesMenu;
    LinearLayout completeProfile;
    LinearLayout filterProfile;
    LinearLayout myAccount;
    LinearLayout inviteAFriend;
    LinearLayout helpDesk;
    LinearLayout logOut;
    LinearLayout counselling;
    MaterialButton changePreference,visitMarketplace,completeProfileStartChatting,erroRetryButton;
    RelativeLayout swipeToolRoot;
    ArrayList<String> likedUserId = new ArrayList<>();
    ArrayList<PreviewProfileModel> previewProfileModels = new ArrayList<>();
    String currentlyDisplayedUserId;
    boolean isRightSwipe = false;
    LinearLayout metMatchRoot,completeProfileRoot,errorLayoutRoot;
    MainActivityModel mainActivityModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main_layout);
        initView();
    }


    private void initView(){
        counselling = findViewById(R.id.main_counselling_layout);
        profileImageView = findViewById(R.id.main_profile_imageview);
        swipeToolRoot = findViewById(R.id.showcase_swipe_layout);
        changePreference = findViewById(R.id.empty_search_change_preference);
        visitMarketplace = findViewById(R.id.caught_up_visit_marketplace);
        emptyLayoutRoot = findViewById(R.id.empty_layout_root);
        activityCaughtUpRoot = findViewById(R.id.caught_up_root);
        erroRetryButton = findViewById(R.id.error_page_retry);
        metMatchRoot = findViewById(R.id.met_match_root);
        errorLayoutRoot = findViewById(R.id.error_layout_root);
        completeProfileRoot = findViewById(R.id.complete_profile_prompt_root);
        completeProfileStartChatting = findViewById(R.id.main_complete_profile_start_chatting);
        String userEmail = getIntent().getStringExtra("email");
        Intent intent = new Intent(this, NotificationService.class);
        intent.putExtra("userId",userEmail);
        startService(intent);
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
        takeAction = findViewById(R.id.showcase_take_action);


        counselling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Counselling.class));
            }
        });

        erroRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyLayoutRoot.setVisibility(View.GONE);
                completeProfileRoot.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                activityCaughtUpRoot.setVisibility(View.GONE);
                mainView.setVisibility(View.GONE);
                activityCaughtUpRoot.setVisibility(View.GONE);
                userShowcaseStack.setVisibility(View.GONE);
                swipeToolRoot.setVisibility(View.GONE);
                takeAction.setVisibility(View.GONE);
                metMatchRoot.setVisibility(View.GONE);
                errorLayoutRoot.setVisibility(View.GONE);
                mainActivityModel.GetUserInfo();
            }
        });


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });


        completeProfileStartChatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CompleteProfile.class));
            }
        });

        visitMarketplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Marketplace.class));
            }
        });

        changePreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                Intent intent = new Intent(MainActivity.this,PreferenceFilter.class);
                startActivityForResult(intent,PREFERENCE_INT);
            }
        });

        inviteAFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        filterProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                Intent intent = new Intent(MainActivity.this,PreferenceFilter.class);
                startActivityForResult(intent,PREFERENCE_INT);
            }
        });

        takeAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TakeActionOnProfile.class));
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

        mainActivityModel = new MainActivityModel(MainActivity.this);
        mainActivityModel.GetUserInfo();
        mainActivityModel.setInfoReadyListener(new MainActivityModel.InfoReadyListener() {
            @Override
            public void onReady(MainActivityModel mainActivityModel) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                preferences.edit().putString("firstname",mainActivityModel.getFirstname()).apply();
                preferences.edit().putString("lastname",mainActivityModel.getLastname()).apply();
                preferences.edit().putString("imageUrl",mainActivityModel.getImageUrl()).apply();
                preferences.edit().putString("phonenumber",mainActivityModel.getPhonenumber()).apply();

                Glide.with(MainActivity.this)
                        .load(mainActivityModel.getImageUrl())
                        .placeholder(R.drawable.profileplaceholder)
                        .error(R.drawable.profileplaceholder)
                        .into(profileImageView);

                ParseUserResponse(mainActivityModel);
            }
            @Override
            public void onError(String message) {

                emptyLayoutRoot.setVisibility(View.GONE);
                completeProfileRoot.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                activityCaughtUpRoot.setVisibility(View.GONE);
                mainView.setVisibility(View.VISIBLE);
                activityCaughtUpRoot.setVisibility(View.GONE);
                userShowcaseStack.setVisibility(View.GONE);
                swipeToolRoot.setVisibility(View.GONE);
                takeAction.setVisibility(View.GONE);
                metMatchRoot.setVisibility(View.GONE);
                errorLayoutRoot.setVisibility(View.VISIBLE);

            }
        });

    }
    private void ParseUserResponse(MainActivityModel mainActivityModel){
        if(mainActivityModel.getIsProfileCompleted().equalsIgnoreCase("false")){
            emptyLayoutRoot.setVisibility(View.GONE);
            completeProfileRoot.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            activityCaughtUpRoot.setVisibility(View.GONE);
            mainView.setVisibility(View.VISIBLE);
            activityCaughtUpRoot.setVisibility(View.GONE);
            userShowcaseStack.setVisibility(View.GONE);
            swipeToolRoot.setVisibility(View.GONE);
            takeAction.setVisibility(View.GONE);
            metMatchRoot.setVisibility(View.GONE);
        }
        else{
            MainActivityModel mainActivityModel2 = new MainActivityModel(MainActivity.this);
            mainActivityModel2.GetShowUserInfo();
            mainActivityModel2.setShowcaseInfoReadyListener(new MainActivityModel.ShowcaseInfoReadyListener() {
                @Override
                public void onReady(ArrayList<PreviewProfileModel> previewProfileModels,ArrayList<String> likeIds) {
                    MainActivity.this.likedUserId = likeIds;
                    MainActivity.this.previewProfileModels = previewProfileModels;
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
                    emptyLayoutRoot.setVisibility(View.GONE);
                    activityCaughtUpRoot.setVisibility(View.GONE);
                    userShowcaseStack.setVisibility(View.VISIBLE);
                    swipeToolRoot.setVisibility(View.VISIBLE);
                    takeAction.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError(String message) {
                    emptyLayoutRoot.setVisibility(View.GONE);
                    completeProfileRoot.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    activityCaughtUpRoot.setVisibility(View.GONE);
                    mainView.setVisibility(View.VISIBLE);
                    activityCaughtUpRoot.setVisibility(View.GONE);
                    userShowcaseStack.setVisibility(View.GONE);
                    swipeToolRoot.setVisibility(View.GONE);
                    takeAction.setVisibility(View.GONE);
                    metMatchRoot.setVisibility(View.GONE);
                    errorLayoutRoot.setVisibility(View.VISIBLE);
                }

                @Override
                public void onEmptyResponse() {
                    emptyLayoutRoot.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    activityCaughtUpRoot.setVisibility(View.GONE);
                    mainView.setVisibility(View.VISIBLE);
                    activityCaughtUpRoot.setVisibility(View.GONE);
                    userShowcaseStack.setVisibility(View.GONE);
                    swipeToolRoot.setVisibility(View.GONE);
                    takeAction.setVisibility(View.GONE);
                }
            });
        }
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


    private void logOut(){
        GoogleSignInClient mSignInClient;
        GoogleSignInOptions options =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestProfile()
                        .build();
        mSignInClient = GoogleSignIn.getClient(this, options);
        mSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                        preferences.edit().remove("userEmail").apply();
                        startActivity(new Intent(MainActivity.this,WelcomeActivity.class));
                        finish();
                    }
                });

    }


    @Override
    public void onBackPressed(){

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
        if(direction == Direction.Right){
            isRightSwipe = true;
        }
        else if(direction == Direction.Left){
            isRightSwipe = false;
        }
        else{

        }
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
        boolean isMatched = false;
        currentlyDisplayedUserId = previewProfileModels.get(position).getUserId();
        MainActivityModel mainActivityModel = new MainActivityModel(currentlyDisplayedUserId,MainActivity.this);
             if(isRightSwipe){
                    //set like to db
                    mainActivityModel.setLiked();
                    for(int i = 0; i < likedUserId.size(); i++) {
                        if (currentlyDisplayedUserId.equalsIgnoreCase(likedUserId.get(i))) {
                            //There is a Match set match break
                            mainActivityModel.setMatched();
                            publishMatchNotification(currentlyDisplayedUserId,previewProfileModels.get(position).getFirstname(),previewProfileModels.get(position).getImage1Url());
                            isMatched = true;
                            emptyLayoutRoot.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            activityCaughtUpRoot.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            mainView.setVisibility(View.VISIBLE);
                            userShowcaseStack.setVisibility(View.GONE);
                            swipeToolRoot.setVisibility(View.GONE);
                            takeAction.setVisibility(View.GONE);
                            metMatchRoot.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                    if(!isMatched && position == showCaseMainModelArrayList.size()-1){
                        emptyLayoutRoot.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        activityCaughtUpRoot.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        mainView.setVisibility(View.VISIBLE);
                        userShowcaseStack.setVisibility(View.GONE);
                        swipeToolRoot.setVisibility(View.GONE);
                        takeAction.setVisibility(View.GONE);
                    }
             }

          else if(position == showCaseMainModelArrayList.size()-1 && !isRightSwipe){
            //At the last position of the card
            emptyLayoutRoot.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            activityCaughtUpRoot.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            mainView.setVisibility(View.VISIBLE);
            userShowcaseStack.setVisibility(View.GONE);
            swipeToolRoot.setVisibility(View.GONE);
            takeAction.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PREFERENCE_INT && resultCode == RESULT_OK) {
            progressBar.setVisibility(View.VISIBLE);
            mainView.setVisibility(View.GONE);
            mainActivityModel.GetUserInfo();

        }
    }

    private void publishMatchNotification(String receiverId,String matchFirstname,String matchImageUrl){
        try {
            Socket mSocket = IO.socket("https://quiet-dusk-08267.herokuapp.com/");
            mSocket.connect();
            mSocket.emit("match",receiverId,matchFirstname,matchImageUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

}

