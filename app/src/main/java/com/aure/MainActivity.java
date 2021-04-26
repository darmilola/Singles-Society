package com.aure;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;



import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.SeekBar;


import com.aure.UiAdapters.ShowcaseMainAdapter;
import com.aure.UiModels.ShowCaseMainModel;
import com.aure.UiModels.ShowCaseModel;
import com.aure.UiModels.ShowcaseMetadata;
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
    int recyclerviewProgress = 0;
    int recyclerviewOldProgress = 0;
    int recyclerviewNewProgress = 0;
    SeekBar showcaseMovementProgress;
    ShowcaseMetadata showcaseMetadata = new ShowcaseMetadata(6200);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){

        leftSwipeCard = findViewById(R.id.user_swipe_left);
        rightSwipeCard = findViewById(R.id.user_swipe_right);
        userShowcaseStack = findViewById(R.id.showcase_main_recyclerview);
        showcaseMovementProgress = findViewById(R.id.main_progressbar);
        showcaseMovementProgress.setProgress(5);

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


                recyclerviewProgress = recyclerviewProgress + progress;

                showcaseMovementProgress.setProgress(recyclerviewProgress);
                Log.e(TAG, "onProgressChange: "+recyclerviewProgress );
                //showcaseMovementProgress.setProgress(progress);

            }


            @Override
            public void onInitialize(int initialValue) {

                showcaseMovementProgress.setMax(initialValue);

            }

            @Override
            public void onNegativeProgress(int nProgress) {
                recyclerviewProgress = recyclerviewProgress - nProgress;
                showcaseMovementProgress.setProgress(recyclerviewProgress);
                Log.e(TAG, "onProgressChange: "+recyclerviewProgress );

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
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.special_activity_background));
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.special_activity_background));
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
           // getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {


    }

    @Override
    public void onCardSwiped(Direction direction) {

        showcaseMovementProgress.setProgress(10);
        recyclerviewProgress = 0;

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
