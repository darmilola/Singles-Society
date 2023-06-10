package com.aure

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aure.Arvi.widget.CardStackLayoutManager
import com.aure.Arvi.widget.SwipeableMethod
import com.aure.UiAdapters.HomeMainAdapter
import com.aure.UiAdapters.ShowCaseAdapter
import com.aure.UiAdapters.ViewProfileAdapter
import com.aure.UiModels.*
import com.google.android.material.button.MaterialButton
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.Duration
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import io.socket.client.IO
import jp.alessandro.android.iab.BillingProcessor
import kotlinx.android.synthetic.main.activity_complete_profile_prompt.*
import kotlinx.android.synthetic.main.activity_dating_profile_full_view.*
import kotlinx.android.synthetic.main.activity_dating_profile_full_view.activity_main_main_view
import kotlinx.android.synthetic.main.activity_dating_profile_full_view.explorePageBackButton
import kotlinx.android.synthetic.main.activity_dating_profile_full_view.loaderView
import kotlinx.android.synthetic.main.activity_dating_profile_full_view.showcase_swipe_layout
import kotlinx.android.synthetic.main.activity_dating_profile_full_view.userShowcaseStack
import kotlinx.android.synthetic.main.activity_dating_profile_full_view.user_swipe_left
import kotlinx.android.synthetic.main.activity_dating_profile_full_view.user_swipe_right
import kotlinx.android.synthetic.main.activity_met_match_page.*
import kotlinx.android.synthetic.main.emptyfilter_layout.*
import kotlinx.android.synthetic.main.error_page.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.matched_layout.*
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Rotation
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.models.Shape
import nl.dionsegijn.konfetti.core.models.Size
import org.apache.commons.text.StringEscapeUtils
import java.net.URISyntaxException
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class DatingProfileFullView : AppCompatActivity(), CardStackListener {


    private var showCaseAdapter: ShowCaseAdapter? = null

    var recyclerView: RecyclerView? = null
    var showCaseModelArrayList = ArrayList<ShowCaseModel>()
    var adapter: ViewProfileAdapter? = null
    var progressBar: ProgressBar? = null
    var mainStrings = ArrayList<String>()
    var quoteStrings = ArrayList<String>()
    var aboutStrings = ArrayList<String>()
    var careerStrings = ArrayList<String>()
    var aboutTextStrings = ArrayList<String>()
    var imageStrings = ArrayList<String>()
    var goalStrings = ArrayList<String>()
    var search: TextView? = null
    var errorLayout: LinearLayout? = null
    var errorRetry: MaterialButton? = null
    var previewProfileModel: PreviewProfileModel? = null
    var previewBack: LinearLayout? = null

    private val PREFERENCE_INT = 1
    var manager: CardStackLayoutManager? = null
    var homeMainAdapter: HomeMainAdapter? = null
    var showCaseMainModelArrayList = ArrayList<ShowCaseMainModel>()
    var likedUserId = java.util.ArrayList<String>()
    var mPreviewProfileModels = ArrayList<PreviewProfileModel>()
    var currentlyDisplayedUserId: String = ""
    var isRightSwipe = false
    var mainActivityModel: MainActivityModel? = null
    private var mBillingProcessor: BillingProcessor? = null
    private val TYPE_SHOWCASE = 102
    private var isMatched = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dating_profile_full_view)
        initView()
    }


    private fun initView() {
        val size = ArrayList<Size>()
        size.add(Size.LARGE)
        size.add(Size.MEDIUM)
        size.add(Size.SMALL)
        val colors = ArrayList<Int>()
        colors.add(0xfce18a)
        colors.add(0xff726d)
        colors.add(0xf4306d)
        colors.add(0xb48def)
        val sh = ArrayList<Shape>()
        sh.add(Shape.Rectangle(0.4f))
        val mParty = Party(
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
            Position.Relative(0.0, 1.0),
            0,
            Rotation(),
            Emitter(5, TimeUnit.MINUTES).perSecond(500))
        konfettiView.start(mParty)
        //matchedStartChatting.setOnClickListener(View.OnClickListener { startActivity(Intent(requireContext(), MatchesActivity::class.java)) })
        error_page_retry.setOnClickListener(View.OnClickListener {
            empty_layout_root.setVisibility(View.GONE)
            //  complete_profile_root.setVisibility(View.GONE)
            loaderView.setVisibility(View.VISIBLE)
            // caught_up_visit_marketplace.setVisibility(View.GONE)
            activity_main_main_view.setVisibility(View.GONE)
            userShowcaseStack?.setVisibility(View.GONE)
            showcase_swipe_layout.setVisibility(View.GONE)
            met_match_root.setVisibility(View.GONE)
            already_matched_root.setVisibility(View.GONE)
            error_layout_root.setVisibility(View.GONE)
            mainActivityModel?.GetUserInfo()
        })
        main_complete_profile_start_chatting.setOnClickListener(View.OnClickListener { startActivity(
            Intent(this, CompleteProfile::class.java)
        ) })

        explorePageBackButton.setOnClickListener {
            finish()
        }


        user_swipe_left.setOnClickListener(View.OnClickListener {
            isRightSwipe = false
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Slow.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager?.setSwipeAnimationSetting(setting)
            userShowcaseStack?.swipe()
        })

        user_swipe_right.setOnClickListener(View.OnClickListener {
            isRightSwipe = true
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Slow.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager?.setSwipeAnimationSetting(setting)
            userShowcaseStack?.swipe()
        })
        mainActivityModel = MainActivityModel(this)
        mainActivityModel?.GetUserInfo()
        mainActivityModel?.setInfoReadyListener(object : MainActivityModel.InfoReadyListener {
            override fun onReady(previewProfileModel: PreviewProfileModel,
                                 likeIds: ArrayList<String>) {


                ParseUserResponse(mainActivityModel!!)
            }

            override fun onError(message: String) {
                empty_layout_root.setVisibility(View.GONE)
                //   complete_profile_root.setVisibility(View.GONE)
                loaderView.setVisibility(View.GONE)
                activity_main_main_view.setVisibility(View.VISIBLE)
                userShowcaseStack?.setVisibility(View.GONE)
                showcase_swipe_layout.setVisibility(View.GONE)
                already_matched_root.setVisibility(View.GONE)
                met_match_root.setVisibility(View.GONE)
                error_layout_root.setVisibility(View.VISIBLE)
            }
        })
    }


    private fun ParseUserResponse(mainActivityModel: MainActivityModel) {
        if (mainActivityModel.isProfileCompleted.equals("false", ignoreCase = true)) {

            empty_layout_root.setVisibility(View.GONE)
            //   complete_profile_root.setVisibility(View.VISIBLE)
            loaderView.setVisibility(View.GONE)
            activity_main_main_view.setVisibility(View.VISIBLE)
            userShowcaseStack?.setVisibility(View.GONE)
            showcase_swipe_layout.setVisibility(View.GONE)
            already_matched_root.setVisibility(View.GONE)
            met_match_root.setVisibility(View.GONE)
            error_layout_root.setVisibility(View.GONE)

        } else if (mainActivityModel.isMatched.equals("true", ignoreCase = true) && mainActivityModel.isSubscribed.equals("false", ignoreCase = true)) {
            empty_layout_root.setVisibility(View.GONE)
            //  complete_profile_root.setVisibility(View.GONE)
            loaderView.setVisibility(View.GONE)
            activity_main_main_view.setVisibility(View.VISIBLE)
            userShowcaseStack?.setVisibility(View.GONE)
            showcase_swipe_layout.setVisibility(View.GONE)
            already_matched_root.setVisibility(View.GONE)
            met_match_root.setVisibility(View.GONE)
            error_layout_root.setVisibility(View.GONE)
            already_matched_root.visibility = View.VISIBLE

        } else {
           val mainActivityModel = MainActivityModel(this)
            mainActivityModel?.GetUserInfo()
            mainActivityModel?.setInfoReadyListener(object : MainActivityModel.InfoReadyListener {
                override fun onReady(
                    previewProfileModel: PreviewProfileModel,
                    likeIds: ArrayList<String>
                ) {

                    val showCaseModelArrayList = ArrayList<ShowCaseModel>()
                    val mainStrings = ArrayList<String>()
                    val quoteStrings = ArrayList<String>()
                    val aboutStrings = ArrayList<String>()
                    val careerStrings = ArrayList<String>()
                    val imageStrings = ArrayList<String>()
                    val goalStrings = ArrayList<String>()
                    mainStrings.add(previewProfileModel.firstname)
                    mainStrings.add(previewProfileModel.age.toString())
                    mainStrings.add(previewProfileModel.city)
                    mainStrings.add(previewProfileModel.occupation)
                    mainStrings.add(previewProfileModel.image1Url)
                    mainStrings.add(previewProfileModel.userId)
                    val showCaseModel =
                        ShowCaseModel(mainStrings, 1, likeIds, previewProfileModel.userId)
                    showCaseModelArrayList.add(showCaseModel)
                    quoteStrings.add(previewProfileModel.quote)
                    val showCaseModel1 =
                        ShowCaseModel(quoteStrings, 2, likeIds, previewProfileModel.userId)
                    showCaseModelArrayList.add(showCaseModel1)
                    val showCaseModel9 =
                        ShowCaseModel(goalStrings, 9, likeIds, previewProfileModel.userId)
                    showCaseModelArrayList.add(showCaseModel9)
                    aboutStrings.add(previewProfileModel.status)
                    aboutStrings.add(previewProfileModel.smoking)
                    aboutStrings.add(previewProfileModel.drinking)
                    aboutStrings.add(previewProfileModel.language)
                    aboutStrings.add(previewProfileModel.religion)
                    aboutStrings.add(previewProfileModel.marriageGoals)
                    val showCaseModel2 =
                        ShowCaseModel(aboutStrings, 3, likeIds, previewProfileModel.userId)
                    showCaseModelArrayList.add(showCaseModel2)
                    careerStrings.add(previewProfileModel.educationLevel)
                    careerStrings.add(previewProfileModel.occupation)
                    careerStrings.add(previewProfileModel.workplace)
                    careerStrings.add(previewProfileModel.image2Url)
                    val showCaseModel3 =
                        ShowCaseModel(careerStrings, 4, likeIds, previewProfileModel.userId)
                    showCaseModelArrayList.add(showCaseModel3)

                    imageStrings.add(previewProfileModel.image3Url)
                    val showCaseModel5 =
                        ShowCaseModel(imageStrings, 6, likeIds, previewProfileModel.userId)
                    showCaseModelArrayList.add(showCaseModel5)

                    val showCaseMainModel =
                        ShowCaseMainModel(showCaseModelArrayList, 0, true)
                    showCaseMainModelArrayList.add(showCaseMainModel)



                    homeMainAdapter =
                        HomeMainAdapter(
                            this@DatingProfileFullView,
                            showCaseMainModelArrayList
                        )
                    initializeCardStack()
                    loaderView.setVisibility(View.GONE)
                    activity_main_main_view.setVisibility(View.VISIBLE)
                    empty_layout_root.setVisibility(View.GONE)
                    userShowcaseStack?.setVisibility(View.VISIBLE)
                    showcase_swipe_layout.setVisibility(View.VISIBLE)
                    already_matched_root.setVisibility(View.GONE)
                    met_match_root.visibility = View.GONE

                }

                override fun onError(message: String) {
                    empty_layout_root.setVisibility(View.GONE)
                    //    complete_profile_root.setVisibility(View.GONE)
                    loaderView.setVisibility(View.GONE)
                    activity_main_main_view.setVisibility(View.VISIBLE)
                    userShowcaseStack?.setVisibility(View.GONE)
                    showcase_swipe_layout.setVisibility(View.GONE)
                    already_matched_root.setVisibility(View.GONE)
                    met_match_root.setVisibility(View.GONE)
                    error_layout_root.setVisibility(View.VISIBLE)
                    already_matched_root.visibility = View.GONE
                }


            })
        }
    }

    private fun initializeCardStack() {
        manager = CardStackLayoutManager(this, this)
        manager?.setTranslationInterval(6.0f)
        manager?.setScaleInterval(0.95f)
        manager?.setSwipeThreshold(0.5f)
        manager?.setMaxDegree(5.0f)
        manager?.setDirections(Direction.HORIZONTAL)
        manager?.setCanScrollHorizontal(true)
        manager?.setCanScrollVertical(false)
        manager?.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        userShowcaseStack?.layoutManager = manager
        userShowcaseStack?.adapter = homeMainAdapter
    }

    override fun onCardDragging(direction: Direction, ratio: Float) {
        if (direction == Direction.Right) {
            isRightSwipe = true
        } else if (direction == Direction.Left) {
            isRightSwipe = false
        } else {
        }
    }

    override fun onCardSwiped(direction: Direction?) {
        //showcaseMovementProgress.setProgress(10);
        //recyclerviewProgress = 0;
    }

    override fun onCardRewound() {}

    override fun onCardCanceled() {}

    override fun onCardAppeared(view: View?, position: Int) {
        if(showCaseMainModelArrayList[position].itemViewType == 1 || showCaseMainModelArrayList[position].itemViewType == 2){
            showcase_swipe_layout.visibility = View.GONE
        }
        else if(isMatched){
            showcase_swipe_layout.visibility = View.GONE
        }
        else{
            showcase_swipe_layout.visibility = View.VISIBLE
        }
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        if(showCaseMainModelArrayList.get(position).showCaseModelArrayList != null){
            currentlyDisplayedUserId = showCaseMainModelArrayList[position].showCaseModelArrayList[0].userId
            val mainActivityModel = MainActivityModel(currentlyDisplayedUserId, this)
            if (isRightSwipe) {
                //set like to db
                mainActivityModel.setLiked()
                for (i in likedUserId.indices) {
                    if (currentlyDisplayedUserId.equals(likedUserId.get(i), ignoreCase = true)) {
                        //There is a Match set match break
                        //mainActivityModel.setMatched()
                        publishMatchNotification(
                            currentlyDisplayedUserId,
                            showCaseMainModelArrayList[position].showCaseModelArrayList[0].modelInfoList[0],
                            showCaseMainModelArrayList[position].showCaseModelArrayList[0].modelInfoList[4]
                        )
                        isMatched = true


                        empty_layout_root.setVisibility(View.GONE)
                        //   complete_profile_root.setVisibility(View.GONE)
                        loaderView.setVisibility(View.GONE)
                        activity_main_main_view.setVisibility(View.VISIBLE)
                        userShowcaseStack?.setVisibility(View.GONE)
                        showcase_swipe_layout.setVisibility(View.GONE)
                        already_matched_root.setVisibility(View.GONE)
                        met_match_root.setVisibility(View.VISIBLE)
                        error_layout_root.setVisibility(View.GONE)
                        already_matched_root.visibility = View.GONE

                        val preferences =
                            PreferenceManager.getDefaultSharedPreferences(this)
                        val imgUrl = preferences.getString("imageUrl", "")
                        val uri = Uri.parse(showCaseMainModelArrayList[position].showCaseModelArrayList[0].modelInfoList[4])
                        //  match_first_image.setImageURI(uri);
                        val uri2 = Uri.parse(imgUrl)
                        // match_second_image.setImageURI(uri2);
                        break

                    }
                    else if(position == showCaseMainModelArrayList.size - 1){
                        empty_layout_root.setVisibility(View.VISIBLE)
                        //   complete_profile_root.setVisibility(View.GONE)
                        loaderView.setVisibility(View.GONE)
                        activity_main_main_view.setVisibility(View.VISIBLE)
                        userShowcaseStack?.setVisibility(View.GONE)
                        showcase_swipe_layout.setVisibility(View.GONE)
                        already_matched_root.setVisibility(View.GONE)
                        met_match_root.setVisibility(View.GONE)
                        error_layout_root.setVisibility(View.GONE)
                        already_matched_root.visibility = View.GONE
                    }

                }
                if (position == showCaseMainModelArrayList.size - 1) {


                }
            } else if (position == showCaseMainModelArrayList.size - 1 && !isRightSwipe) {
                empty_layout_root.setVisibility(View.VISIBLE)
                //   complete_profile_root.setVisibility(View.GONE)
                loaderView.setVisibility(View.GONE)
                activity_main_main_view.setVisibility(View.VISIBLE)
                userShowcaseStack?.setVisibility(View.GONE)
                showcase_swipe_layout.setVisibility(View.GONE)
                already_matched_root.setVisibility(View.GONE)
                met_match_root.setVisibility(View.GONE)
                error_layout_root.setVisibility(View.GONE)
                already_matched_root.visibility = View.GONE

            }
        }
        else if(showCaseMainModelArrayList.size -1 == position){
            empty_layout_root.setVisibility(View.VISIBLE)
            //   complete_profile_root.setVisibility(View.GONE)
            loaderView.setVisibility(View.GONE)
            activity_main_main_view.setVisibility(View.VISIBLE)
            userShowcaseStack?.setVisibility(View.GONE)
            showcase_swipe_layout.setVisibility(View.GONE)
            already_matched_root.setVisibility(View.GONE)
            met_match_root.setVisibility(View.GONE)
            error_layout_root.setVisibility(View.GONE)
            already_matched_root.visibility = View.GONE
        }
    }





    private fun publishMatchNotification(receiverId: String, matchFirstname: String, matchImageUrl: String) {
        try {
            val mSocket = IO.socket("https://strong-swan-8610e4.netlify.app")
            mSocket.connect()
            mSocket.emit("match", receiverId, matchFirstname, matchImageUrl)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }


    private fun displayLikedNotification() {
        val message = "This List Contains a possible match"
        val CHANNEL_ID = "AURATAYYA"
        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.iconfinder_usa)
            .setContentText(StringEscapeUtils.unescapeJava(message))
            .setAutoCancel(true)
            .setOngoing(false)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.profileplaceholder))
            .setPriority(NotificationCompat.PRIORITY_MAX)
        val resultIntent = Intent(this, MainActivity::class.java)
        val resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, 0)
        mBuilder.setContentIntent(resultPendingIntent)
        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "AURATAYYA_MESSAGES"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(CHANNEL_ID, name, importance)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(1, mBuilder.build())
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