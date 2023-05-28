package com.aure.fragments

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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.aure.CompleteProfile
import com.aure.MainActivity
import com.aure.R
import com.aure.UiAdapters.TrendingMainAdapter
import com.aure.UiModels.*
import com.aure.UiModels.MainActivityModel.ShowcaseInfoReadyListener
import com.aure.UiModels.PaymentModel.PaymentListener
import com.yuyakaido.android.cardstackview.*
import io.socket.client.IO
import jp.alessandro.android.iab.BillingApi
import jp.alessandro.android.iab.BillingContext
import jp.alessandro.android.iab.BillingProcessor
import jp.alessandro.android.iab.handler.PurchaseHandler
import jp.alessandro.android.iab.logger.SystemLogger
import kotlinx.android.synthetic.main.activity_complete_profile_prompt.*
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
import nl.dionsegijn.konfetti.core.models.Size.Companion.LARGE
import nl.dionsegijn.konfetti.core.models.Size.Companion.MEDIUM
import nl.dionsegijn.konfetti.core.models.Size.Companion.SMALL
import org.apache.commons.text.StringEscapeUtils
import java.net.URISyntaxException
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment(), CardStackListener  {

    private val PREFERENCE_INT = 1
    var manager: CardStackLayoutManager? = null
    var trendingMainAdapter: TrendingMainAdapter? = null
    var showCaseMainModelArrayList = java.util.ArrayList<ShowCaseMainModel>()
    var likedUserId = java.util.ArrayList<String>()
    var mPreviewProfileModels = ArrayList<PreviewProfileModel>()
    var currentlyDisplayedUserId: String = ""
    var isRightSwipe = false
    var mainActivityModel: MainActivityModel? = null
    private var mBillingProcessor: BillingProcessor? = null
    private val TYPE_SHOWCASE = 102


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    private fun initBillingProcessor() {
        val builder = BillingContext.Builder()
                .setContext(requireContext()) // App context
                .setPublicKeyBase64("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2fDRqNLFSm7LYoCPZ/rG+8CpQXn/LCQNAxPtVRdt2ZNdpORpH0yvCm0vV8VOcSb6zWeM9s7dCt36wCLSJqllNw4fNkEWn/GcEV2iWNa3WT/I4JgDwstv4KGFq8FAYRA0Y+zICdvBUf833v/UuRWMAxUi2GfYmzJel+8uQtva1fzwHyzjRCYa1Od4F98IUebR0BfJ3Jp4KS3E5mr8GAuii61MxaR+n32YsEPC5gNCzkvpJO3PCbZr/XwiGG/l/sGPKQTEDapmLIhBOhnatwWj+Wmusww5RlsrEDwHnY6zRHQrwler1pW0IlqXzpyBDCKftGPa9N/o3KWof1WnUIGkXQIDAQAB") // Public key generated on the Google Play Console
                .setApiVersion(BillingApi.VERSION_5) // It also supports version 5
                .setLogger(SystemLogger())
        mBillingProcessor = BillingProcessor(builder.build(), mPurchaseHandler)
    }

    private val mPurchaseHandler = PurchaseHandler { response ->
        if (response.isSuccess) {
            val purchase = response.purchase
            val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val userEmail = preferences.getString("userEmail", "")
            val paymentModel = PaymentModel(requireContext(), userEmail)
            paymentModel.subscribe()
            paymentModel.setPaymentListener(object : PaymentListener {
                override fun onSuccess() {
                    showAlert()
                }

                override fun onFailure() {
                    Toast.makeText(requireContext(), "Error Occurred please try again", Toast.LENGTH_SHORT).show()
                }
            })
        } else {

            // Handle the error
        }
    }

    companion object

    fun newInstance(): HomeFragment {
        val fragment = HomeFragment()
        return fragment
    }


    private fun initView() {
        initBillingProcessor()
        // Blurry.with(this).radius(25).sampling(2).onto(metMatchRoot);
        val size = ArrayList<Size>()
        size.add(LARGE)
        size.add(MEDIUM)
        size.add(SMALL)
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
            activity_main_progressbar.setVisibility(View.VISIBLE)
           // caught_up_visit_marketplace.setVisibility(View.GONE)
            activity_main_main_view.setVisibility(View.GONE)
            userShowcaseStack?.setVisibility(View.GONE)
            showcase_swipe_layout.setVisibility(View.GONE)
            met_match_root.setVisibility(View.GONE)
            already_matched_root.setVisibility(View.GONE)
            error_layout_root.setVisibility(View.GONE)
            mainActivityModel?.GetUserInfo()
        })
        main_complete_profile_start_chatting.setOnClickListener(View.OnClickListener { startActivity(Intent(requireContext(), CompleteProfile::class.java)) })


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
        mainActivityModel = MainActivityModel(requireContext())
        mainActivityModel?.GetUserInfo()
        mainActivityModel?.setInfoReadyListener(object : MainActivityModel.InfoReadyListener {
            override fun onReady(mMainActivityModel: MainActivityModel) {
                val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
                preferences.edit().putString("firstname", mMainActivityModel.firstname).apply()
                preferences.edit().putString("lastname", mMainActivityModel.lastname).apply()
                preferences.edit().putString("imageUrl", mMainActivityModel.imageUrl).apply()
                preferences.edit().putString("phonenumber", mMainActivityModel.phonenumber).apply()

                ParseUserResponse(mainActivityModel!!)
            }

            override fun onError(message: String) {
                empty_layout_root.setVisibility(View.GONE)
             //   complete_profile_root.setVisibility(View.GONE)
                activity_main_progressbar.setVisibility(View.GONE)
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
            activity_main_progressbar.setVisibility(View.GONE)
            activity_main_main_view.setVisibility(View.VISIBLE)
            userShowcaseStack?.setVisibility(View.GONE)
            showcase_swipe_layout.setVisibility(View.GONE)
            already_matched_root.setVisibility(View.GONE)
            met_match_root.setVisibility(View.GONE)
            error_layout_root.setVisibility(View.GONE)

        } else if (mainActivityModel.isMatched.equals("true", ignoreCase = true) && mainActivityModel.isSubscribed.equals("false", ignoreCase = true)) {
            empty_layout_root.setVisibility(View.GONE)
          //  complete_profile_root.setVisibility(View.GONE)
            activity_main_progressbar.setVisibility(View.GONE)
            activity_main_main_view.setVisibility(View.VISIBLE)
            userShowcaseStack?.setVisibility(View.GONE)
            showcase_swipe_layout.setVisibility(View.GONE)
            already_matched_root.setVisibility(View.GONE)
            met_match_root.setVisibility(View.GONE)
            error_layout_root.setVisibility(View.GONE)
            already_matched_root.visibility = View.VISIBLE

        } else {
            val mainActivityModel2 = MainActivityModel(requireContext())
            mainActivityModel2.GetShowUserInfo()
            mainActivityModel2.setShowcaseInfoReadyListener(object : ShowcaseInfoReadyListener {
                override fun onReady(previewProfileModels: ArrayList<PreviewProfileModel>, likeIds: ArrayList<String>) {
                    mPreviewProfileModels.clear()
                    mPreviewProfileModels = previewProfileModels
                    Log.e("onReady: ", mPreviewProfileModels.size.toString())
                    likedUserId.clear()
                    showCaseMainModelArrayList.clear()
                    likedUserId = likeIds
                    for (previewProfileModel in previewProfileModels) {
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
                        val showCaseModel = ShowCaseModel(mainStrings, 1, likeIds)
                        showCaseModelArrayList.add(showCaseModel)
                        quoteStrings.add(previewProfileModel.quote)
                        val showCaseModel1 = ShowCaseModel(quoteStrings, 2, likeIds)
                        showCaseModelArrayList.add(showCaseModel1)
                        val showCaseModel9 = ShowCaseModel(goalStrings, 9, likeIds)
                        showCaseModelArrayList.add(showCaseModel9)
                        aboutStrings.add(previewProfileModel.status)
                        aboutStrings.add(previewProfileModel.smoking)
                        aboutStrings.add(previewProfileModel.drinking)
                        aboutStrings.add(previewProfileModel.language)
                        aboutStrings.add(previewProfileModel.religion)
                        aboutStrings.add(previewProfileModel.marriageGoals)
                        val showCaseModel2 = ShowCaseModel(aboutStrings, 3, likeIds)
                        showCaseModelArrayList.add(showCaseModel2)
                        careerStrings.add(previewProfileModel.educationLevel)
                        careerStrings.add(previewProfileModel.occupation)
                        careerStrings.add(previewProfileModel.workplace)
                        careerStrings.add(previewProfileModel.image2Url)
                        val showCaseModel3 = ShowCaseModel(careerStrings, 4, likeIds)
                        showCaseModelArrayList.add(showCaseModel3)

                        // aboutTextStrings.add(previewProfileModel.getAbout());
                        //ShowCaseModel showCaseModel4 = new ShowCaseModel(aboutTextStrings,5,likeIds);
                        // showCaseModelArrayList.add(showCaseModel4);
                        imageStrings.add(previewProfileModel.image3Url)
                        val showCaseModel5 = ShowCaseModel(imageStrings, 6, likeIds)
                        showCaseModelArrayList.add(showCaseModel5)
                        val communityPostModel = CommunityPostModel()
                        val communityPostModelArrayList = java.util.ArrayList<CommunityPostModel>()
                        for (i in 0..4) {
                            communityPostModelArrayList.add(communityPostModel)
                        }
                        val showCaseMainModel1 = ShowCaseMainModel(communityPostModelArrayList, 1)
                        val showCaseMainModel =
                            ShowCaseMainModel(showCaseModelArrayList, 0, TYPE_SHOWCASE)
                        showCaseMainModelArrayList.add(showCaseMainModel)
                        showCaseMainModelArrayList.add(showCaseMainModel1)
                    }
                    trendingMainAdapter =
                        TrendingMainAdapter(
                            requireContext(),
                            showCaseMainModelArrayList
                        )
                    initializeCardStack()
                    activity_main_progressbar.setVisibility(View.GONE)
                    activity_main_main_view.setVisibility(View.VISIBLE)
                    empty_layout_root.setVisibility(View.GONE)
                    userShowcaseStack?.setVisibility(View.VISIBLE)
                    showcase_swipe_layout.setVisibility(View.VISIBLE)
                    already_matched_root.setVisibility(View.GONE)
                    met_match_root.visibility = View.GONE

                    // if(!likeIds.isEmpty()) displayLikedNotification();
                }

                override fun onError(message: String) {
                    empty_layout_root.setVisibility(View.GONE)
                //    complete_profile_root.setVisibility(View.GONE)
                    activity_main_progressbar.setVisibility(View.GONE)
                    activity_main_main_view.setVisibility(View.VISIBLE)
                    userShowcaseStack?.setVisibility(View.GONE)
                    showcase_swipe_layout.setVisibility(View.GONE)
                    already_matched_root.setVisibility(View.GONE)
                    met_match_root.setVisibility(View.GONE)
                    error_layout_root.setVisibility(View.VISIBLE)
                    already_matched_root.visibility = View.GONE
                }

                override fun onEmptyResponse() {
                    empty_layout_root.setVisibility(View.VISIBLE)
                //    complete_profile_root.setVisibility(View.GONE)
                    activity_main_progressbar.setVisibility(View.GONE)
                    activity_main_main_view.setVisibility(View.VISIBLE)
                    userShowcaseStack?.setVisibility(View.GONE)
                    showcase_swipe_layout.setVisibility(View.GONE)
                    already_matched_root.setVisibility(View.GONE)
                    met_match_root.setVisibility(View.GONE)
                    error_layout_root.setVisibility(View.GONE)
                    already_matched_root.visibility = View.GONE
                }
            })
        }
    }

    private fun initializeCardStack() {
        manager = CardStackLayoutManager(requireContext(), this)
        manager?.setStackFrom(StackFrom.Top)
        manager?.setTranslationInterval(6.0f)
        manager?.setVisibleCount(2)
        manager?.setScaleInterval(0.90f)
        manager?.setSwipeThreshold(0.3f)
        manager?.setMaxDegree(50.0f)
        manager?.setDirections(Direction.HORIZONTAL)
        manager?.setCanScrollHorizontal(true)
        manager?.setCanScrollVertical(false)
        manager?.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        userShowcaseStack?.setLayoutManager(manager)
        userShowcaseStack?.setAdapter(trendingMainAdapter)
    }



    private fun showAlert() {
        AlertDialog.Builder(requireContext())
                .setTitle("Subscription successful")
                .setMessage("You have successfully subscribed for Auratayya Premium, you can restart the App to activate more features") // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Okay") { dialog, which -> dialog.dismiss() }
                .show()
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
       if(showCaseMainModelArrayList[position].itemViewType == 1){
           showcase_swipe_layout.visibility = View.GONE
       }
        else{
           showcase_swipe_layout.visibility = View.VISIBLE
        }
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        var isMatched = false
        currentlyDisplayedUserId = mPreviewProfileModels.get(position).getUserId()
        val mainActivityModel = MainActivityModel(currentlyDisplayedUserId, requireContext())
        if (isRightSwipe) {
            //set like to db
            mainActivityModel.setLiked()
            for (i in likedUserId.indices) {
                if (currentlyDisplayedUserId.equals(likedUserId.get(i), ignoreCase = true)) {
                    //There is a Match set match break
                    mainActivityModel.setMatched()
                    publishMatchNotification(currentlyDisplayedUserId, mPreviewProfileModels.get(position).getFirstname(), mPreviewProfileModels.get(position).getImage1Url())
                    isMatched = true


                    empty_layout_root.setVisibility(View.GONE)
                 //   complete_profile_root.setVisibility(View.GONE)
                    activity_main_progressbar.setVisibility(View.GONE)
                    activity_main_main_view.setVisibility(View.VISIBLE)
                    userShowcaseStack?.setVisibility(View.GONE)
                    showcase_swipe_layout.setVisibility(View.GONE)
                    already_matched_root.setVisibility(View.GONE)
                    met_match_root.setVisibility(View.VISIBLE)
                    error_layout_root.setVisibility(View.GONE)
                    already_matched_root.visibility = View.GONE

                    val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
                    val imgUrl = preferences.getString("imageUrl", "")
                    val uri = Uri.parse(mPreviewProfileModels.get(position).getImage1Url())
                    //  match_first_image.setImageURI(uri);
                    val uri2 = Uri.parse(imgUrl)
                    // match_second_image.setImageURI(uri2);
                    met_match_text.setText("You and " + mPreviewProfileModels.get(position).getFirstname() + " have matched")
                    break

                }
            }
            if (!isMatched && position == showCaseMainModelArrayList.size - 1) {

                // Nothing to show
            }
        } else if (position == showCaseMainModelArrayList.size - 1 && !isRightSwipe) {
            //At the last position of the show

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
        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.iconfinder_usa)
                .setContentText(StringEscapeUtils.unescapeJava(message))
                .setAutoCancel(true)
                .setOngoing(false)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.profileplaceholder))
                .setPriority(NotificationCompat.PRIORITY_MAX)
        val resultIntent = Intent(requireContext(), MainActivity::class.java)
        val resultPendingIntent = PendingIntent.getActivity(requireContext(), 0, resultIntent, 0)
        mBuilder.setContentIntent(resultPendingIntent)
        val notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "AURATAYYA_MESSAGES"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(CHANNEL_ID, name, importance)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(1, mBuilder.build())
    }



}