package com.aure.fragments


import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.aure.Arvi.widget.CardStackLayoutManager
import com.aure.Arvi.widget.SwipeableMethod
import com.aure.CompleteProfile
import com.aure.MainActivity
import com.aure.R
import com.aure.UiAdapters.HomeMainAdapter
import com.aure.UiModels.*
import com.yuyakaido.android.cardstackview.*
import io.socket.client.IO
import jp.alessandro.android.iab.BillingApi
import jp.alessandro.android.iab.BillingContext
import jp.alessandro.android.iab.BillingProcessor
import jp.alessandro.android.iab.handler.PurchaseHandler
import jp.alessandro.android.iab.logger.SystemLogger
import kotlinx.android.synthetic.main.error_page.*
import kotlinx.android.synthetic.main.fragment_dating_profile.*



class DatingProfileFragment : Fragment(), CardStackListener {

    var manager: CardStackLayoutManager? = null
    var homeMainAdapter: HomeMainAdapter? = null
    var showCaseMainModelArrayList = java.util.ArrayList<ShowCaseMainModel>()
    var likedUserId = java.util.ArrayList<String>()
    var mPreviewProfileModels = ArrayList<PreviewProfileModel>()
    var currentlyDisplayedUserId: String = ""
    var isRightSwipe = false
    var mainActivityModel: MainActivityModel? = null
    private var mBillingProcessor: BillingProcessor? = null
    private val TYPE_SHOWCASE = 102
    private var isMatched = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dating_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    private val mPurchaseHandler = PurchaseHandler { response ->
        if (response.isSuccess) {
            val purchase = response.purchase
            val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val userEmail = preferences.getString("userEmail", "")
            val paymentModel = PaymentModel(requireContext(), userEmail)
            paymentModel.subscribe()
            paymentModel.setPaymentListener(object : PaymentModel.PaymentListener {
                override fun onSuccess() {
                    showAlert()
                }

                override fun onFailure() {
                    // Toast.makeText(this, "Error Occurred please try again", Toast.LENGTH_SHORT).show()
                }
            })
        } else {

            // Handle the error
        }
    }

    private fun initBillingProcessor() {
        val builder = BillingContext.Builder()
            .setContext(requireContext()) // App context
            .setPublicKeyBase64("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2fDRqNLFSm7LYoCPZ/rG+8CpQXn/LCQNAxPtVRdt2ZNdpORpH0yvCm0vV8VOcSb6zWeM9s7dCt36wCLSJqllNw4fNkEWn/GcEV2iWNa3WT/I4JgDwstv4KGFq8FAYRA0Y+zICdvBUf833v/UuRWMAxUi2GfYmzJel+8uQtva1fzwHyzjRCYa1Od4F98IUebR0BfJ3Jp4KS3E5mr8GAuii61MxaR+n32YsEPC5gNCzkvpJO3PCbZr/XwiGG/l/sGPKQTEDapmLIhBOhnatwWj+Wmusww5RlsrEDwHnY6zRHQrwler1pW0IlqXzpyBDCKftGPa9N/o3KWof1WnUIGkXQIDAQAB") // Public key generated on the Google Play Console
            .setApiVersion(BillingApi.VERSION_5) // It also supports version 5
            .setLogger(SystemLogger())
        mBillingProcessor = BillingProcessor(builder.build(), mPurchaseHandler)
    }


    private fun initView() {
        initBillingProcessor()
         error_page_retry.setOnClickListener(View.OnClickListener {
            //  complete_profile_root.setVisibility(View.GONE)
            loaderView.setVisibility(View.VISIBLE)
            // caught_up_visit_marketplace.setVisibility(View.GONE)
            main_view.setVisibility(View.GONE)
            userShowcaseStack?.setVisibility(View.GONE)
            error_layout_root.setVisibility(View.GONE)
            mainActivityModel?.GetUserInfo()
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

                parseUserResponse(mainActivityModel!!)

            }

            override fun onError(message: String) {
                //   complete_profile_root.setVisibility(View.GONE)
                loaderView.setVisibility(View.GONE)
                main_view.setVisibility(View.VISIBLE)
                userShowcaseStack?.setVisibility(View.GONE)
                error_layout_root.setVisibility(View.VISIBLE)
            }
        })
    }


    private fun parseUserResponse(mainActivityModel: MainActivityModel) {
        val mainActivityModel2 = MainActivityModel(requireContext())
        mainActivityModel2.GetShowUserInfo()
        mainActivityModel2.setShowcaseInfoReadyListener(object :
            MainActivityModel.ShowcaseInfoReadyListener {
            override fun onReady(
                previewProfileModels: ArrayList<PreviewProfileModel>,
                likeIds: ArrayList<String>
            ) {
                mPreviewProfileModels.clear()
                mPreviewProfileModels = previewProfileModels
                Log.e("onReady: ", mPreviewProfileModels.size.toString())
                likedUserId.clear()
                showCaseMainModelArrayList.clear()
                likedUserId = likeIds
                var i = 0
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

                        // aboutTextStrings.add(previewProfileModel.getAbout());
                        //ShowCaseModel showCaseModel4 = new ShowCaseModel(aboutTextStrings,5,likeIds);
                        // showCaseModelArrayList.add(showCaseModel4);
                        imageStrings.add(previewProfileModel.image3Url)
                        val showCaseModel5 =
                            ShowCaseModel(imageStrings, 6, likeIds, previewProfileModel.userId)
                        showCaseModelArrayList.add(showCaseModel5)

                        val showCaseMainModel =
                            ShowCaseMainModel(showCaseModelArrayList, 0, true)
                        showCaseMainModelArrayList.add(showCaseMainModel)

                    }


                    homeMainAdapter =
                        HomeMainAdapter(
                            requireContext(),
                            showCaseMainModelArrayList
                        )
                    initializeCardStack()
                    loaderView.setVisibility(View.GONE)
                    main_view.setVisibility(View.VISIBLE)
                    userShowcaseStack?.setVisibility(View.VISIBLE)

                    // if(!likeIds.isEmpty()) displayLikedNotification();
                }


            override fun onError(message: String) {
                loaderView.setVisibility(View.GONE)
                main_view.setVisibility(View.VISIBLE)
                userShowcaseStack?.setVisibility(View.GONE)
                error_layout_root.setVisibility(View.VISIBLE)
            }

            override fun onEmptyResponse() {

            }
        })
    }


    private fun initializeCardStack() {
        manager = CardStackLayoutManager(requireContext(), this)
        manager?.setStackFrom(StackFrom.Top)
        manager?.setTranslationInterval(6.0f)
        manager?.setVisibleCount(2)
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



    private fun showAlert() {
        AlertDialog.Builder(requireContext())
            .setTitle("Subscription successful")
            .setMessage("You have successfully subscribed for Auratayya Premium, you can restart the App to activate more features") // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton("Okay") { dialog, which -> dialog.dismiss() }
            .show()
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        // Not needed
    }

    override fun onCardSwiped(direction: Direction?) {
        // Not needed
    }

    override fun onCardRewound() {
        // Not needed
    }

    override fun onCardCanceled() {
        // Not needed
    }

    override fun onCardAppeared(view: View?, position: Int) {
        // Not needed
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        // Not needed
    }


}