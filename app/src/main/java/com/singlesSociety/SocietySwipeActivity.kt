package com.singlesSociety

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.singlesSociety.Arvi.Direction
import com.singlesSociety.Arvi.widget.CardStackLayoutManager
import com.singlesSociety.Arvi.widget.CardStackListener
import com.singlesSociety.Arvi.widget.SwipeableMethod
import com.singlesSociety.uiAdapters.CardStackAdapter
import com.singlesSociety.uiAdapters.CardStackAdapter.VisibleUserListener
import com.singlesSociety.UiModels.MainActivityModel
import com.singlesSociety.UiModels.PreviewProfileModel
import com.yuyakaido.android.cardstackview.Duration
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import kotlinx.android.synthetic.main.activity_society_swipe.*
import kotlinx.android.synthetic.main.showcase_stack_layout.*

class SocietySwipeActivity : AppCompatActivity() {
    lateinit var manager: CardStackLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_society_swipe)
        supportPostponeEnterTransition()
        initView()
    }

    private fun initView(){

        navigationBack.setOnClickListener {
            finishAfterTransition()
        }

        cardStackGoFullView.visibility = View.GONE
        manager = CardStackLayoutManager(this)
        manager.setVisibleCount(2)
        manager.setScaleInterval(1.0f)
        manager.setStackFrom(StackFrom.Top)
        manager.setSwipeThreshold(0.2f)
        manager.setMaxDegree(80.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(false)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        showcaseCardStackView.setLayoutManager(manager)
        val userProfileToPreview: ArrayList<PreviewProfileModel> = intent.getParcelableArrayListExtra<Parcelable>("userProfileToPreview") as ArrayList<PreviewProfileModel>
        val userLikedList: ArrayList<String> = intent.getStringArrayListExtra("userLikedList") as ArrayList<String>
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val imageTransitionName: String? = intent.extras?.getString("transitionName")
            rootView.setTransitionName(imageTransitionName)
        }
        val userProfileInPreview = arrayOf("")

        val cardStackAdapter = CardStackAdapter(userProfileToPreview, userLikedList, this)
        cardStackAdapter.setVisibleUserListener(VisibleUserListener { userId ->
            userProfileInPreview[0] = userId
        })


      user_swipe_right.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(com.yuyakaido.android.cardstackview.Direction.Right)
                .setDuration(Duration.Slow.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
           manager.setSwipeAnimationSetting(setting)
              showcaseCardStackView.swipe()
        }


        user_swipe_left.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(com.yuyakaido.android.cardstackview.Direction.Left)
                .setDuration(Duration.Slow.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            showcaseCardStackView.swipe()
        }

        showcaseCardStackView.adapter = cardStackAdapter
        manager.setListener(object : CardStackListener {
            override fun onCardDragging(direction: Direction, ratio: Float) {
                // ((ShowcaseItemViewHolder) holder).swipeLayout.setVisibility(View.GONE);
            }

            override fun onCardSwiped(direction: Direction) {
                if (direction == Direction.Right) {
                    val mainActivityModel = MainActivityModel(userProfileInPreview.get(0), this@SocietySwipeActivity)
                    mainActivityModel.setLiked()
                  //  profileMatchedListener.invoke()
                }
            /*    if (cardPosition.get(0) == societyModelArrayList.get(position).getUserProfileToPreview().size - 1
                ) {
                    profileEmptyListener.invoke()
                }*/
            }

            override fun onCardRewound() {}
            override fun onCardCanceled() {}
            override fun onCardAppeared(view: View, position: Int) {}
            override fun onCardDisappeared(view: View, position: Int) {
              //  cardPosition.get(0) = position
            }
        })

        supportStartPostponedEnterTransition()
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.special_activity_background)
            window.statusBarColor = ContextCompat.getColor(this, R.color.special_activity_background)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}