package com.aure;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import io.socket.client.IO;
import io.socket.client.Socket;
import jp.alessandro.android.iab.BillingApi;
import jp.alessandro.android.iab.BillingContext;
import jp.alessandro.android.iab.BillingException;
import jp.alessandro.android.iab.BillingProcessor;
import jp.alessandro.android.iab.Purchase;
import jp.alessandro.android.iab.PurchaseType;
import jp.alessandro.android.iab.handler.PurchaseHandler;
import jp.alessandro.android.iab.handler.StartActivityHandler;
import jp.alessandro.android.iab.logger.SystemLogger;
import jp.alessandro.android.iab.response.PurchaseResponse;
import nl.dionsegijn.konfetti.core.Angle;
import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.Rotation;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.core.models.Size;
import nl.dionsegijn.konfetti.xml.KonfettiView;


import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aure.UiAdapters.TrendingMainAdapter;
import com.aure.UiModels.CommunityPostModel;
import com.aure.UiModels.MainActivityModel;
import com.aure.UiModels.PaymentModel;
import com.aure.UiModels.PreviewProfileModel;
import com.aure.UiModels.ShowCaseMainModel;
import com.aure.UiModels.ShowCaseModel;
import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;
import com.yuyakaido.android.cardstackview.SwipeableMethod;


import org.apache.commons.text.StringEscapeUtils;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements CardStackListener {

    private static int PREFERENCE_INT = 1;
    CardStackView userShowcaseStack;
    CardStackLayoutManager manager;
    TrendingMainAdapter trendingMainAdapter;
    ArrayList<ShowCaseMainModel> showCaseMainModelArrayList = new ArrayList<>();

    LinearLayout rightSwipeCard, leftSwipeCard;
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    CircleImageView profileImageView;
    TextView metMatchText;
    SimpleDraweeView caught_up_first_image, caught_up_second_image;
    RoundedImageView match_first_image, match_second_image;
    Toolbar toolbar;
    FrameLayout mainView;
    ProgressBar progressBar;
    LinearLayout emptyLayoutRoot,activityCaughtUpRoot;
    LinearLayout mainInfoToggleLayout;
    LinearLayout mainMarketPlace;
    LinearLayout matchesMenu;
    LinearLayout completeProfile;
    LinearLayout filterProfile;
    LinearLayout myAccount;
    LinearLayout inviteAFriend;
    LinearLayout logOut;
    LinearLayout counselling;
    MaterialButton changePreference,visitMarketplace,completeProfileStartChatting,erroRetryButton,matchedSubscribe;
    LinearLayout matchedStartChatting;
    LinearLayout swipeToolRoot;
    ArrayList<String> likedUserId = new ArrayList<>();
    ArrayList<PreviewProfileModel> previewProfileModels = new ArrayList<>();
    String currentlyDisplayedUserId;
    boolean isRightSwipe = false;
    LinearLayout completeProfileRoot,errorLayoutRoot,alreadyMatchedRoot;
    FrameLayout metMatchRoot;
    MainActivityModel mainActivityModel;
    private BillingProcessor mBillingProcessor;

    private static final int TYPE_SHOWCASE = 102;

    private KonfettiView konfettiView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main_layout);
        initView();
    }

    private final PurchaseHandler mPurchaseHandler = new PurchaseHandler() {
        @Override
        public void call(PurchaseResponse response) {
            if (response.isSuccess()) {
                Purchase purchase = response.getPurchase();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                String userEmail =  preferences.getString("userEmail","");
                PaymentModel paymentModel = new PaymentModel(MainActivity.this, userEmail);
                paymentModel.subscribe();
                paymentModel.setPaymentListener(new PaymentModel.PaymentListener() {
                    @Override
                    public void onSuccess() {
                        showAlert();
                    }
                    @Override
                    public void onFailure() {
                        Toast.makeText(MainActivity.this, "Error Occurred please try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {

                // Handle the error
            }
        }
    };


    private void initBillingProcessor() {
        BillingContext.Builder builder = new BillingContext.Builder()
                .setContext(MainActivity.this) // App context
                .setPublicKeyBase64("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2fDRqNLFSm7LYoCPZ/rG+8CpQXn/LCQNAxPtVRdt2ZNdpORpH0yvCm0vV8VOcSb6zWeM9s7dCt36wCLSJqllNw4fNkEWn/GcEV2iWNa3WT/I4JgDwstv4KGFq8FAYRA0Y+zICdvBUf833v/UuRWMAxUi2GfYmzJel+8uQtva1fzwHyzjRCYa1Od4F98IUebR0BfJ3Jp4KS3E5mr8GAuii61MxaR+n32YsEPC5gNCzkvpJO3PCbZr/XwiGG/l/sGPKQTEDapmLIhBOhnatwWj+Wmusww5RlsrEDwHnY6zRHQrwler1pW0IlqXzpyBDCKftGPa9N/o3KWof1WnUIGkXQIDAQAB") // Public key generated on the Google Play Console
                .setApiVersion(BillingApi.VERSION_5) // It also supports version 5
                .setLogger(new SystemLogger());

        mBillingProcessor = new BillingProcessor(builder.build(), mPurchaseHandler);
    }


    private void initView(){
        initBillingProcessor();
        caught_up_first_image = findViewById(R.id.caught_up_image1);
        caught_up_second_image = findViewById(R.id.caught_up_image2);
        matchedStartChatting = findViewById(R.id.message_match);
        matchedSubscribe = findViewById(R.id.main_matched_subscribe);
        alreadyMatchedRoot = findViewById(R.id.already_matched_root);
        metMatchText = findViewById(R.id.met_match_text);
        match_first_image = findViewById(R.id.met_match_image1);
        match_second_image = findViewById(R.id.met_match_image2);
        counselling = findViewById(R.id.main_counselling_layout);
        profileImageView = findViewById(R.id.main_profile_imageview);
        swipeToolRoot = findViewById(R.id.showcase_swipe_layout);
        changePreference = findViewById(R.id.empty_search_change_preference);
        visitMarketplace = findViewById(R.id.caught_up_visit_marketplace);
        emptyLayoutRoot = findViewById(R.id.empty_layout_root);
        activityCaughtUpRoot = findViewById(R.id.caught_up_root);
        erroRetryButton = findViewById(R.id.error_page_retry);
        metMatchRoot = findViewById(R.id.met_match_root);
        konfettiView = findViewById(R.id.konfettiView);
        errorLayoutRoot = findViewById(R.id.error_layout_root);
        completeProfileRoot = findViewById(R.id.complete_profile_prompt_root);
        completeProfileStartChatting = findViewById(R.id.main_complete_profile_start_chatting);
       // Blurry.with(this).radius(25).sampling(2).onto(metMatchRoot);

        String userEmail = getIntent().getStringExtra("email");
        Intent intent = new Intent(this, NotificationService.class);
        intent.putExtra("userId",userEmail);
        startService(intent);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        preferences.edit().putString("userEmail",userEmail).apply();
        inviteAFriend = findViewById(R.id.invite_a_friend_layout);
        logOut = findViewById(R.id.log_out_layout);
        myAccount = findViewById(R.id.activity_main_my_account);
        matchesMenu = findViewById(R.id.matches_menu);
        mainMarketPlace = findViewById(R.id.main_marketplace);
        completeProfile = findViewById(R.id.complete_profile);
        filterProfile = findViewById(R.id.filter_layout);
        mainMarketPlace.setVisibility(View.GONE);

       ArrayList<Size> size = new ArrayList<>();
       size.add(Size.Companion.getLARGE());
        size.add(Size.Companion.getMEDIUM());
        size.add(Size.Companion.getSMALL());

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(0xfce18a);
        colors.add(0xff726d);
        colors.add(0xf4306d);
        colors.add(0xb48def);

        ArrayList<Shape> sh = new ArrayList<Shape>();
        sh.add(new Shape.Rectangle(0.4f));



       Party mParty = new  Party(
               Angle.TOP,
               360,
               120f,
               0f,
               0.9f,
                size,
                colors,
               sh,
               3000,
               true,
               new Position.Relative(0, 1),
                0,
                new Rotation(),
                new Emitter(5, TimeUnit.MINUTES).perSecond(500));


        konfettiView.start(mParty);

        Uri uri = Uri.parse("https://timesbuddy.s3.us-east-1.amazonaws.com/images/image-1639226408.png");
        caught_up_first_image.setImageURI(uri);
        Uri uri2 = Uri.parse("https://timesbuddy.s3.us-east-1.amazonaws.com/images/image-1639226540.png");
        caught_up_second_image.setImageURI(uri2);





        matchedSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startSubProcess();

            }
        });


        matchedStartChatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MatchesActivity.class));
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
                metMatchRoot.setVisibility(View.GONE);
                alreadyMatchedRoot.setVisibility(View.GONE);
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
                {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Download auratayya match making App on playstore http://play.google.com/store/apps/details?id=pk.nimgade.Bostan.Train.Schedule");
                    Intent intent = Intent.createChooser(shareIntent, "Share");
                    startActivity(intent);

                }
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
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }

                Intent intent = new Intent(MainActivity.this,CompleteProfile.class);
                startActivityForResult(intent,PREFERENCE_INT);
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
                isRightSwipe = false;
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
                isRightSwipe = true;
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
            public void onReady(MainActivityModel mMainActivityModel) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                preferences.edit().putString("firstname",mMainActivityModel.getFirstname()).apply();
                preferences.edit().putString("lastname",mMainActivityModel.getLastname()).apply();
                preferences.edit().putString("imageUrl",mMainActivityModel.getImageUrl()).apply();
                preferences.edit().putString("phonenumber",mMainActivityModel.getPhonenumber()).apply();

                counselling.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mMainActivityModel.getIsSubscribed().equalsIgnoreCase("false")) {
                            showSubAlert();
                        } else {
                            startActivity(new Intent(MainActivity.this, Counselling.class));
                        }
                    }
                });

                Glide.with(MainActivity.this)
                        .load(mMainActivityModel.getImageUrl())
                        .placeholder(R.drawable.profileplaceholder)
                        .error(R.drawable.profileplaceholder)
                        .into(profileImageView);

                ParseUserResponse(mMainActivityModel);
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
                alreadyMatchedRoot.setVisibility(View.GONE);
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
            metMatchRoot.setVisibility(View.GONE);
            alreadyMatchedRoot.setVisibility(View.GONE);
            Uri uri = Uri.parse("https://timesbuddy.s3.us-east-1.amazonaws.com/images/image-1639226408.png");
            caught_up_first_image.setImageURI(uri);
            Uri uri2 = Uri.parse("https://timesbuddy.s3.us-east-1.amazonaws.com/images/image-1639226540.png");
            caught_up_second_image.setImageURI(uri2);
        }
       else if(mainActivityModel.getIsMatched().equalsIgnoreCase("true") && mainActivityModel.getIsSubscribed().equalsIgnoreCase("false")){
            emptyLayoutRoot.setVisibility(View.GONE);
            completeProfileRoot.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            activityCaughtUpRoot.setVisibility(View.GONE);
            mainView.setVisibility(View.VISIBLE);
            activityCaughtUpRoot.setVisibility(View.GONE);
            userShowcaseStack.setVisibility(View.GONE);
            swipeToolRoot.setVisibility(View.GONE);
            metMatchRoot.setVisibility(View.GONE);
            alreadyMatchedRoot.setVisibility(View.VISIBLE);
            Uri uri = Uri.parse("https://timesbuddy.s3.us-east-1.amazonaws.com/images/image-1639226408.png");
            caught_up_first_image.setImageURI(uri);
            Uri uri2 = Uri.parse("https://timesbuddy.s3.us-east-1.amazonaws.com/images/image-1639226540.png");
            caught_up_second_image.setImageURI(uri2);
        }
        else{
            MainActivityModel mainActivityModel2 = new MainActivityModel(MainActivity.this);
            mainActivityModel2.GetShowUserInfo();
            mainActivityModel2.setShowcaseInfoReadyListener(new MainActivityModel.ShowcaseInfoReadyListener() {
                @Override
                public void onReady(ArrayList<PreviewProfileModel> previewProfileModels,ArrayList<String> likeIds) {
                    MainActivity.this.previewProfileModels.clear();
                    MainActivity.this.likedUserId.clear();
                    MainActivity.this.showCaseMainModelArrayList.clear();
                    MainActivity.this.likedUserId = likeIds;
                    MainActivity.this.previewProfileModels = previewProfileModels;
                    for (PreviewProfileModel previewProfileModel: previewProfileModels) {
                        ArrayList<ShowCaseModel> showCaseModelArrayList = new ArrayList<>();
                        ArrayList<String> mainStrings = new ArrayList<>();
                        ArrayList<String> quoteStrings = new ArrayList<>();
                        ArrayList<String> aboutStrings = new ArrayList<>();
                        ArrayList<String> careerStrings = new ArrayList<>();
                        ArrayList<String> imageStrings = new ArrayList<>();
                        ArrayList<String> goalStrings = new ArrayList<>();


                        mainStrings.add(previewProfileModel.getFirstname());
                        mainStrings.add(String.valueOf(previewProfileModel.getAge()));
                        mainStrings.add(previewProfileModel.getCity());
                        mainStrings.add(previewProfileModel.getOccupation());
                        mainStrings.add(previewProfileModel.getImage1Url());
                        mainStrings.add(previewProfileModel.getUserId());
                        ShowCaseModel showCaseModel = new ShowCaseModel(mainStrings,1,likeIds);
                        showCaseModelArrayList.add(showCaseModel);

                        quoteStrings.add(previewProfileModel.getQuote());
                        ShowCaseModel showCaseModel1 = new ShowCaseModel(quoteStrings,2,likeIds);
                        showCaseModelArrayList.add(showCaseModel1);

                        ShowCaseModel showCaseModel9 = new ShowCaseModel(goalStrings,9,likeIds);
                        showCaseModelArrayList.add(showCaseModel9);

                        aboutStrings.add(previewProfileModel.getStatus());
                        aboutStrings.add(previewProfileModel.getSmoking());
                        aboutStrings.add(previewProfileModel.getDrinking());
                        aboutStrings.add(previewProfileModel.getLanguage());
                        aboutStrings.add(previewProfileModel.getReligion());
                        aboutStrings.add(previewProfileModel.getMarriageGoals());
                        ShowCaseModel showCaseModel2 = new ShowCaseModel(aboutStrings,3,likeIds);
                        showCaseModelArrayList.add(showCaseModel2);

                        careerStrings.add(previewProfileModel.getEducationLevel());
                        careerStrings.add(previewProfileModel.getOccupation());
                        careerStrings.add(previewProfileModel.getWorkplace());
                        careerStrings.add(previewProfileModel.getImage2Url());
                        ShowCaseModel showCaseModel3 = new ShowCaseModel(careerStrings,4,likeIds);
                        showCaseModelArrayList.add(showCaseModel3);

                       // aboutTextStrings.add(previewProfileModel.getAbout());
                        //ShowCaseModel showCaseModel4 = new ShowCaseModel(aboutTextStrings,5,likeIds);
                       // showCaseModelArrayList.add(showCaseModel4);

                        imageStrings.add(previewProfileModel.getImage3Url());
                        ShowCaseModel showCaseModel5 = new ShowCaseModel(imageStrings,6,likeIds);
                        showCaseModelArrayList.add(showCaseModel5);

                        CommunityPostModel communityPostModel = new CommunityPostModel();
                        ArrayList<CommunityPostModel> communityPostModelArrayList = new ArrayList<>();
                        for(int i = 0; i < 5; i++){
                            communityPostModelArrayList.add(communityPostModel);
                        }
                        ShowCaseMainModel showCaseMainModel1 = new ShowCaseMainModel(communityPostModelArrayList,2);
                        ShowCaseMainModel showCaseMainModel = new ShowCaseMainModel(showCaseModelArrayList,1,TYPE_SHOWCASE);
                        showCaseMainModelArrayList.add(showCaseMainModel);
                        showCaseMainModelArrayList.add(showCaseMainModel1);
                    }
                    trendingMainAdapter = new TrendingMainAdapter(MainActivity.this,showCaseMainModelArrayList);
                    initializeCardStack();
                    progressBar.setVisibility(View.GONE);
                    mainView.setVisibility(View.VISIBLE);
                    emptyLayoutRoot.setVisibility(View.GONE);
                    activityCaughtUpRoot.setVisibility(View.GONE);
                    userShowcaseStack.setVisibility(View.VISIBLE);
                    swipeToolRoot.setVisibility(View.VISIBLE);
                    alreadyMatchedRoot.setVisibility(View.GONE);

                   // if(!likeIds.isEmpty()) displayLikedNotification();
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
                    metMatchRoot.setVisibility(View.GONE);
                    alreadyMatchedRoot.setVisibility(View.GONE);
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
                    alreadyMatchedRoot.setVisibility(View.GONE);
                    Uri uri = Uri.parse("https://timesbuddy.s3.us-east-1.amazonaws.com/images/image-1639226408.png");
                    caught_up_first_image.setImageURI(uri);
                    Uri uri2 = Uri.parse("https://timesbuddy.s3.us-east-1.amazonaws.com/images/image-1639226540.png");
                    caught_up_second_image.setImageURI(uri2);
                }
            });
        }
    }

    private void initializeCardStack() {

        manager = new CardStackLayoutManager(this,this);
        manager.setStackFrom(StackFrom.Top);
        manager.setVisibleCount(2);
        manager.setTranslationInterval(6.0f);
        manager.setScaleInterval(0.90f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(50.0f);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setCanScrollHorizontal(true);
        manager.setCanScrollVertical(false);
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        userShowcaseStack.setLayoutManager(manager);
        userShowcaseStack.setAdapter(trendingMainAdapter);

    }


    private void logOut(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        preferences.edit().remove("userEmail").apply();
        startActivity(new Intent(MainActivity.this,WelcomeActivity.class));
        finish();
      /*  GoogleSignInClient mSignInClient;
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
                });*/
    }

    private void showAlert(){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Subscription successful")
                .setMessage("You have successfully subscribed for Auratayya Premium, you can restart the App to activate more features")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showSubAlert(){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Subscribe")
                .setMessage("This feature is only available for subscribed users, please subscribe now to unlock")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Subscribe", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                       startSubProcess();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
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
                            alreadyMatchedRoot.setVisibility(View.GONE);
                            userShowcaseStack.setVisibility(View.GONE);
                            swipeToolRoot.setVisibility(View.GONE);
                            metMatchRoot.setVisibility(View.VISIBLE);
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                            String imgUrl =  preferences.getString("imageUrl","");
                            Uri uri = Uri.parse(previewProfileModels.get(position).getImage1Url());
                          //  match_first_image.setImageURI(uri);
                            Uri uri2 = Uri.parse(imgUrl);
                           // match_second_image.setImageURI(uri2);
                            metMatchText.setText("You and "+ previewProfileModels.get(position).getFirstname()+" have matched");
                            break;
                        }
                    }
                    if(!isMatched && position == showCaseMainModelArrayList.size()-1){
                        emptyLayoutRoot.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        activityCaughtUpRoot.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        mainView.setVisibility(View.VISIBLE);
                        alreadyMatchedRoot.setVisibility(View.GONE);
                        userShowcaseStack.setVisibility(View.GONE);
                        swipeToolRoot.setVisibility(View.GONE);
                        Uri uri = Uri.parse("https://timesbuddy.s3.us-east-1.amazonaws.com/images/image-1639226408.png");
                        caught_up_first_image.setImageURI(uri);
                        Uri uri2 = Uri.parse("https://timesbuddy.s3.us-east-1.amazonaws.com/images/image-1639226540.png");
                        caught_up_second_image.setImageURI(uri2);
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
            alreadyMatchedRoot.setVisibility(View.GONE);
            swipeToolRoot.setVisibility(View.GONE);
                 Uri uri = Uri.parse("https://timesbuddy.s3.us-east-1.amazonaws.com/images/image-1639226408.png");
                 caught_up_first_image.setImageURI(uri);
                 Uri uri2 = Uri.parse("https://timesbuddy.s3.us-east-1.amazonaws.com/images/image-1639226540.png");
                 caught_up_second_image.setImageURI(uri2);

        }

    }

    private void startSubProcess(){
        Activity activity = MainActivity.this;
        int requestCode = 10; // YOUR REQUEST CODE
        String itemId = "com.aure.sub";
        PurchaseType purchaseType = PurchaseType.SUBSCRIPTION; // or PurchaseType.SUBSCRIPTION for subscriptions
        String developerPayload = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2fDRqNLFSm7LYoCPZ/rG+8CpQXn/LCQNAxPtVRdt2ZNdpORpH0yvCm0vV8VOcSb6zWeM9s7dCt36wCLSJqllNw4fNkEWn/GcEV2iWNa3WT/I4JgDwstv4KGFq8FAYRA0Y+zICdvBUf833v/UuRWMAxUi2GfYmzJel+8uQtva1fzwHyzjRCYa1Od4F98IUebR0BfJ3Jp4KS3E5mr8GAuii61MxaR+n32YsEPC5gNCzkvpJO3PCbZr/XwiGG/l/sGPKQTEDapmLIhBOhnatwWj+Wmusww5RlsrEDwHnY6zRHQrwler1pW0IlqXzpyBDCKftGPa9N/o3KWof1WnUIGkXQIDAQAB";

        mBillingProcessor.startPurchase(activity, requestCode, itemId, purchaseType, developerPayload,
                new StartActivityHandler() {
                    @Override
                    public void onSuccess() {
                        // Billing activity started successfully
                        // Do nothing
                    }

                    @Override
                    public void onError(BillingException e) {
                        // Handle the error
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PREFERENCE_INT && resultCode == RESULT_OK) {
            progressBar.setVisibility(View.VISIBLE);
            mainView.setVisibility(View.GONE);
            mainActivityModel.GetUserInfo();
            isRightSwipe = false;

        }

        if (mBillingProcessor.onActivityResult(requestCode, resultCode, data)) {
            // The response will be sent through PurchaseHandler
            return;
        }
    }

    private void publishMatchNotification(String receiverId,String matchFirstname,String matchImageUrl){
        try {
            Socket mSocket = IO.socket("https://strong-swan-8610e4.netlify.app");
            mSocket.connect();
            mSocket.emit("match",receiverId,matchFirstname,matchImageUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed(){

    }

    private void displayLikedNotification(){
        String message = "This List Contains a possible match";
        String CHANNEL_ID = "AURATAYYA";
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this,CHANNEL_ID)
                        .setSmallIcon(R.drawable.iconfinder_usa)
                        .setContentText(StringEscapeUtils.unescapeJava(message))
                        .setAutoCancel(true)
                        .setOngoing(false)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.profileplaceholder))
                        .setPriority(NotificationCompat.PRIORITY_MAX);

        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, 0);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "AURATAYYA_MESSAGES";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(1, mBuilder.build());
    }

}

